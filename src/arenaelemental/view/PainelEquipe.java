package arenaelemental.view;

import arenaelemental.modelo.Pokemon;
import javax.swing.*;
import java.awt.*;

public class PainelEquipe extends JPanel {
    private JanelaPrincipal janela;
    private JPanel listaEquipe = new JPanel();
    private JLabel lblBestiario = new JLabel();

    PainelEquipe(JanelaPrincipal janela) {
        this.janela = janela;
        setLayout(new BorderLayout(12, 12));
        setBackground(Constantes.NAVY);
        setBorder(BorderFactory.createEmptyBorder(24, 24, 24, 24));

        JLabel titulo = new JLabel("Sua equipe");
        titulo.setFont(Constantes.FONTE_RPG_TITULO.deriveFont(22f));
        titulo.setForeground(Color.WHITE);
        add(titulo, BorderLayout.NORTH);

        listaEquipe.setLayout(new FlowLayout(FlowLayout.LEFT, 14, 14));
        listaEquipe.setOpaque(false);
        JScrollPane scroll = new JScrollPane(listaEquipe);
        scroll.setOpaque(false);
        scroll.getViewport().setOpaque(false);
        scroll.setBorder(BorderFactory.createEmptyBorder());
        add(scroll, BorderLayout.CENTER);

        JPanel rodape = new JPanel(new BorderLayout());
        rodape.setOpaque(false);
        lblBestiario.setFont(Constantes.FONTE_RPG_TEXTO);
        lblBestiario.setForeground(Constantes.CYAN);
        rodape.add(lblBestiario, BorderLayout.WEST);
        BotaoRPG btnVoltar = new BotaoRPG("Voltar para a exploração", Constantes.CYAN);
        btnVoltar.addActionListener(e -> janela.mostrar("BATALHA"));
        rodape.add(btnVoltar, BorderLayout.EAST);
        add(rodape, BorderLayout.SOUTH);
    }

    void atualizar() {
        listaEquipe.removeAll();
        for (Pokemon c : janela.getTreinador().getEquipe()) {
            listaEquipe.add(criarMiniCartao(c));
        }
        lblBestiario.setText("Bestiário: " + janela.getBestiario().total() + " espécie(s) registrada(s)");
        listaEquipe.revalidate();
        listaEquipe.repaint();
    }

    private JPanel criarMiniCartao(Pokemon c) {
        JPanel p = new JPanel(new BorderLayout());
        p.setBackground(new Color(28, 24, 38));
        p.setBorder(BorderFactory.createLineBorder(c.getTipo().corPrincipal(), 2, true));
        p.setPreferredSize(new Dimension(270, 110));

        JPanel spriteP = new JPanel() {
            private Image sprite;
            {
                SpriteCarregador.carregarAsync(SpriteCarregador.nomeParaSprite(c), false,
                        img -> { sprite = img; repaint(); });
            }
            @Override protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                if (sprite != null && sprite.getWidth(null) > 0) {
                    int tam = 80;
                    int w = sprite.getWidth(null), h = sprite.getHeight(null);
                    double escala = (double) tam / Math.max(w, h);
                    int dw = (int) (w * escala), dh = (int) (h * escala);
                    Composite antigo = g2.getComposite();
                    if (!c.estaViva()) g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.35f));
                    g2.drawImage(sprite, getWidth() / 2 - dw / 2, getHeight() / 2 - dh / 2, dw, dh, this);
                    g2.setComposite(antigo);
                } else {
                    CriaturaSprite.desenhar(g2, c.getTipo(), getWidth() / 2, getHeight() / 2, 80, !c.estaViva());
                }
            }
        };
        spriteP.setOpaque(false);
        spriteP.setPreferredSize(new Dimension(100, 100));
        p.add(spriteP, BorderLayout.WEST);

        JPanel info = new JPanel();
        info.setOpaque(false);
        info.setLayout(new BoxLayout(info, BoxLayout.Y_AXIS));
        info.setBorder(BorderFactory.createEmptyBorder(10, 6, 10, 10));
        JLabel nome = new JLabel(c.getNome());
        nome.setFont(Constantes.FONTE_RPG_BOTAO.deriveFont(15f));
        nome.setForeground(Color.WHITE);
        JLabel tipo = new JLabel(c.getTipo().nomeExibicao());
        tipo.setFont(Constantes.FONTE_RPG_TEXTO.deriveFont(12f));
        tipo.setForeground(c.getTipo().corPrincipal());
        JLabel vida = new JLabel(c.getVidaAtual() + "/" + c.getVidaMaxima() + " HP");
        vida.setFont(Constantes.FONTE_RPG_TEXTO.deriveFont(12f));
        vida.setForeground(new Color(200, 200, 210));
        info.add(nome); info.add(tipo); info.add(vida);
        p.add(info, BorderLayout.CENTER);
        return p;
    }
}
