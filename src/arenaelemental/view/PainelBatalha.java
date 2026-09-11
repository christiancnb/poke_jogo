package arenaelemental.view;

import arenaelemental.modelo.Pokemon;
import arenaelemental.modelo.FabricaCriaturas;
import arenaelemental.treinador.Treinador;
import arenaelemental.batalha.Batalha;
import arenaelemental.batalha.Captura;
import javax.swing.*;
import java.awt.*;
import java.util.List;

public class PainelBatalha extends JPanel {
    private JanelaPrincipal janela;
    private PainelArena arena = new PainelArena();
    private BarraVida barraJogador, barraSelvagem;
    private JTextArea logArea = new JTextArea();
    private JButton btnAtacar, btnCapturar, btnFugir, btnExplorar, btnTrocar;
    private JLabel lblStatus = new JLabel(" ");
    private Batalha batalhaAtual;
    private int linhasMostradas = 0; // quantas linhas do log da Batalha já foram exibidas na tela

    PainelBatalha(JanelaPrincipal janela) {
        this.janela = janela;
        setLayout(new BorderLayout(10, 10));
        setBackground(Constantes.NAVY);
        setBorder(BorderFactory.createEmptyBorder(16, 16, 16, 16));

        JPanel topo = new JPanel(new BorderLayout());
        topo.setOpaque(false);
        lblStatus.setFont(Constantes.FONTE_RPG_TEXTO);
        lblStatus.setForeground(Constantes.CYAN);
        topo.add(lblStatus, BorderLayout.WEST);
        BotaoRPG btnEquipe = new BotaoRPG("Equipe / Bestiário", Constantes.AMBER);
        btnEquipe.addActionListener(e -> janela.mostrar("EQUIPE"));
        topo.add(btnEquipe, BorderLayout.EAST);
        add(topo, BorderLayout.NORTH);

        JPanel centro = new JPanel(new BorderLayout(8, 8));
        centro.setOpaque(false);
        centro.add(arena, BorderLayout.CENTER);

        JPanel painelBarras = new JPanel(new BorderLayout());
        painelBarras.setOpaque(false);
        barraJogador = new BarraVida(null);
        barraSelvagem = new BarraVida(null);
        JPanel esqBarra = new JPanel(); esqBarra.setOpaque(false); esqBarra.add(barraJogador);
        JPanel dirBarra = new JPanel(); dirBarra.setOpaque(false); dirBarra.add(barraSelvagem);
        painelBarras.add(esqBarra, BorderLayout.WEST);
        painelBarras.add(dirBarra, BorderLayout.EAST);
        centro.add(painelBarras, BorderLayout.SOUTH);
        add(centro, BorderLayout.CENTER);

        JPanel lado = new JPanel();
        lado.setLayout(new BoxLayout(lado, BoxLayout.Y_AXIS));
        lado.setPreferredSize(new Dimension(230, 10));
        lado.setOpaque(false);

        logArea.setEditable(false);
        logArea.setLineWrap(true);
        logArea.setWrapStyleWord(true);
        logArea.setFont(Constantes.FONTE_RPG_TEXTO);
        logArea.setBackground(new Color(10, 10, 16));
        logArea.setForeground(new Color(120, 255, 140));
        logArea.setCaretColor(new Color(120, 255, 140));
        logArea.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Constantes.HUD_BORDA_CLARA, 2),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)));
        JScrollPane scroll = new JScrollPane(logArea);
        scroll.setPreferredSize(new Dimension(230, 150));
        scroll.setBorder(BorderFactory.createEmptyBorder());
        lado.add(scroll);
        lado.add(Box.createVerticalStrut(14));

        btnExplorar = criarBotaoAcao("Explorar", Constantes.CYAN);
        btnExplorar.addActionListener(e -> explorar());
        btnAtacar = criarBotaoAcao("Atacar", Constantes.AMBER);
        btnAtacar.addActionListener(e -> atacar());
        btnCapturar = criarBotaoAcao("Tentar Capturar", Constantes.CYAN);
        btnCapturar.addActionListener(e -> capturar());
        btnFugir = criarBotaoAcao("Fugir", new Color(224, 90, 90));
        btnFugir.addActionListener(e -> fugir());
        btnTrocar = criarBotaoAcao("Trocar Pokémon", Constantes.AMBER);
        btnTrocar.addActionListener(e -> trocarPokemon());

        lado.add(btnExplorar);
        lado.add(Box.createVerticalStrut(8));
        lado.add(btnAtacar);
        lado.add(Box.createVerticalStrut(8));
        lado.add(btnCapturar);
        lado.add(Box.createVerticalStrut(8));
        lado.add(btnTrocar);
        lado.add(Box.createVerticalStrut(8));
        lado.add(btnFugir);

        add(lado, BorderLayout.EAST);
        atualizarBotoes(false);
    }

    private JButton criarBotaoAcao(String texto, Color cor) {
        BotaoRPG b = new BotaoRPG(texto, cor);
        b.setAlignmentX(Component.LEFT_ALIGNMENT);
        b.setMaximumSize(new Dimension(220, 44));
        b.setPreferredSize(new Dimension(220, 44));
        return b;
    }

    void iniciar(Treinador t) {
        Pokemon ativa = t.getAtiva();
        barraJogador.setCriatura(ativa);
        arena.configurar(ativa, null);
        lblStatus.setText("Treinador: " + t.getNome() + "  ·  Equipe: " + t.getEquipe().size() + "/" + Treinador.getTamanhoMaxEquipe());
        logArea.setText("Sua jornada começa! Clique em Explorar para encontrar uma criatura selvagem.\n");
        atualizarBotoes(false);
    }

    private void explorar() {
        Pokemon selvagem = FabricaCriaturas.selvagemAleatorio();
        janela.getBestiario().registrar(selvagem);

        Pokemon ativa = escolherPokemonParaBatalha();
        if (ativa == null) {
            logArea.append("Nenhum Pokémon foi escolhido. A batalha foi cancelada.\n");
            return;
        }
        janela.getTreinador().escolherPokemon(ativa);
        batalhaAtual = new Batalha(ativa, selvagem);
        barraJogador.setCriatura(ativa);
        barraSelvagem.setCriatura(selvagem);
        arena.configurar(ativa, selvagem);
        logArea.setText("");
        for (String linha : batalhaAtual.getLog()) logArea.append(linha + "\n");
        linhasMostradas = batalhaAtual.getLog().size();
        atualizarBotoes(true);
    }

    private Pokemon escolherPokemonParaBatalha() {
        List<Pokemon> disponiveis = new java.util.ArrayList<>();
        for (Pokemon pokemon : janela.getTreinador().getEquipe()) {
            if (pokemon.estaViva()) disponiveis.add(pokemon);
        }

        return SeletorPokemonDialog.mostrar(
                this,
                disponiveis,
                "Escolher Pokémon",
                "Escolha quem irá representar você na batalha");
    }

    private void trocarPokemon() {
        if (batalhaAtual == null || batalhaAtual.terminou()) return;

        List<Pokemon> disponiveis = new java.util.ArrayList<>();
        Pokemon atual = batalhaAtual.getDoJogador();
        for (Pokemon pokemon : janela.getTreinador().getEquipe()) {
            if (pokemon.estaViva() && pokemon != atual) disponiveis.add(pokemon);
        }

        if (disponiveis.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Você não possui outro Pokémon vivo para trocar.",
                    "Troca de Pokémon", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        Pokemon novo = SeletorPokemonDialog.mostrar(
                this,
                disponiveis,
                "Trocar Pokémon",
                "Escolha o próximo Pokémon a entrar na batalha");

        if (novo != null) {
            janela.getTreinador().escolherPokemon(novo);
            batalhaAtual.trocarPokemon(novo);
            processarResultadoDoTurno();
        }
    }

    private void atacar() {
        if (batalhaAtual == null || batalhaAtual.terminou()) return;
        batalhaAtual.turno();
        processarResultadoDoTurno();
    }

    private void capturar() {
        if (batalhaAtual == null || batalhaAtual.terminou()) return;
        Pokemon selvagem = batalhaAtual.getSelvagem();
        if (Captura.tentar(selvagem)) {
            logArea.append("Você capturou " + selvagem.getNome() + "!\n");
            janela.getBestiario().registrar(selvagem);
            if (!janela.getTreinador().adicionarNaEquipe(selvagem)) {
                logArea.append("(Equipe cheia — a criatura foi registrada no Bestiário, mas não coube no time)\n");
            }
            batalhaAtual = null;
            barraSelvagem.setCriatura(null);
            arena.configurar(janela.getTreinador().getAtiva(), null);
            atualizarBotoes(false);
        } else {
            logArea.append(selvagem.getNome() + " escapou da captura! Ela ataca de volta...\n");
            batalhaAtual.turno();
            processarResultadoDoTurno();
        }
    }

    private void fugir() {
        logArea.append("Você fugiu da batalha.\n");
        batalhaAtual = null;
        barraSelvagem.setCriatura(null);
        arena.configurar(janela.getTreinador().getAtiva(), null);
        atualizarBotoes(false);
    }

    /**
     * Processa o que aconteceu num turno (seja de um "Atacar" normal, seja do contra-ataque
     * que acontece quando uma captura falha): mostra TODAS as linhas novas do log (e não só a
     * última), atualiza as duas barras de vida e, se a batalha terminou, registra a vitória no
     * Bestiário quando for o caso — inclusive quando a batalha termina durante uma tentativa de
     * captura que falhou, cenário que antes ficava sem essa checagem.
     */
    private void processarResultadoDoTurno() {
        atualizarLog();
        arena.configurar(batalhaAtual.getDoJogador(), batalhaAtual.getSelvagem());
        barraJogador.repaint();
        barraSelvagem.repaint();
        if (batalhaAtual.terminou()) {
            if (!batalhaAtual.getSelvagem().estaViva()) {
                janela.getBestiario().registrar(batalhaAtual.getSelvagem());
                logArea.append("Você venceu a batalha!\n");
            } else if (!batalhaAtual.getDoJogador().estaViva()) {
                logArea.append("Você perdeu a batalha! Veja a Equipe para continuar com outra criatura.\n");
            }
            atualizarBotoes(false);
        }
    }

    private void atualizarLog() {
        List<String> log = batalhaAtual.getLog();
        for (int i = linhasMostradas; i < log.size(); i++) {
            logArea.append(log.get(i) + "\n");
        }
        linhasMostradas = log.size();
    }

    private void atualizarBotoes(boolean emBatalha) {
        btnExplorar.setVisible(!emBatalha);
        btnAtacar.setVisible(emBatalha);
        btnCapturar.setVisible(emBatalha);
        btnTrocar.setVisible(emBatalha && janela.getTreinador() != null && janela.getTreinador().getEquipe().size() > 1);
        btnFugir.setVisible(emBatalha);
    }
}
