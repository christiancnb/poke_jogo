package arenaelemental.view;

import arenaelemental.modelo.Pokemon;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * Popup de seleção de Pokémon com o mesmo visual RPG da interface principal.
 */
public class SeletorPokemonDialog extends JDialog {
    private Pokemon selecionado;

    private SeletorPokemonDialog(Window owner, List<Pokemon> pokemons, String titulo, String subtitulo) {
        super(owner, titulo, ModalityType.APPLICATION_MODAL);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setResizable(false);

        JPanel fundo = new JPanel(new BorderLayout(0, 12));
        fundo.setBackground(Constantes.NAVY);
        fundo.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Constantes.HUD_BORDA_CLARA, 2),
                BorderFactory.createEmptyBorder(16, 18, 16, 18)));
        setContentPane(fundo);

        JPanel cabecalho = new JPanel();
        cabecalho.setOpaque(false);
        cabecalho.setLayout(new BoxLayout(cabecalho, BoxLayout.Y_AXIS));

        JLabel tituloLabel = new JLabel(titulo.toUpperCase(), SwingConstants.CENTER);
        tituloLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        tituloLabel.setFont(Constantes.FONTE_RPG_TITULO.deriveFont(22f));
        tituloLabel.setForeground(Color.WHITE);

        JLabel subtituloLabel = new JLabel(subtitulo, SwingConstants.CENTER);
        subtituloLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        subtituloLabel.setFont(Constantes.FONTE_RPG_TEXTO);
        subtituloLabel.setForeground(Constantes.CYAN);

        cabecalho.add(tituloLabel);
        cabecalho.add(Box.createVerticalStrut(5));
        cabecalho.add(subtituloLabel);
        fundo.add(cabecalho, BorderLayout.NORTH);

        JPanel grade = new JPanel(new GridLayout(0, Math.min(3, pokemons.size()), 12, 12));
        grade.setOpaque(false);
        grade.setBorder(BorderFactory.createEmptyBorder(4, 0, 4, 0));

        for (Pokemon pokemon : pokemons) {
            grade.add(criarCartao(pokemon));
        }

        fundo.add(grade, BorderLayout.CENTER);

        JPanel rodape = new JPanel(new BorderLayout());
        rodape.setOpaque(false);
        JLabel dica = new JLabel("Selecione um Pokémon vivo para continuar");
        dica.setFont(Constantes.FONTE_RPG_TEXTO.deriveFont(11f));
        dica.setForeground(Constantes.MUTED);
        rodape.add(dica, BorderLayout.WEST);

        BotaoRPG cancelar = new BotaoRPG("Cancelar", new Color(224, 90, 90));
        cancelar.setPreferredSize(new Dimension(135, 40));
        cancelar.addActionListener(e -> dispose());
        rodape.add(cancelar, BorderLayout.EAST);
        fundo.add(rodape, BorderLayout.SOUTH);

        pack();
        setMinimumSize(new Dimension(Math.min(760, Math.max(420, getWidth())), getHeight()));
        setLocationRelativeTo(owner);
    }

    private JPanel criarCartao(Pokemon pokemon) {
        Color tipoCor = pokemon.getTipo().corPrincipal();

        JPanel card = new JPanel(new BorderLayout(0, 5));
        card.setPreferredSize(new Dimension(205, 245));
        card.setBackground(Constantes.HUD_BG);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(tipoCor, 2, true),
                BorderFactory.createEmptyBorder(7, 7, 7, 7)));

        JPanel topo = new JPanel(new BorderLayout());
        topo.setOpaque(false);
        JLabel nome = new JLabel(pokemon.getNome());
        nome.setFont(Constantes.FONTE_RPG_BOTAO.deriveFont(16f));
        nome.setForeground(Color.WHITE);
        JLabel tipo = new JLabel(pokemon.getTipo().nomeExibicao().toUpperCase());
        tipo.setFont(Constantes.FONTE_RPG_TEXTO.deriveFont(10f));
        tipo.setForeground(tipoCor);
        topo.add(nome, BorderLayout.WEST);
        topo.add(tipo, BorderLayout.EAST);
        card.add(topo, BorderLayout.NORTH);

        JPanel sprite = new JPanel() {
            private Image imagem;
            {
                SpriteCarregador.carregarAsync(SpriteCarregador.nomeParaSprite(pokemon), false,
                        img -> { imagem = img; repaint(); });
            }

            @Override
            protected void paintComponent(Graphics graphics) {
                super.paintComponent(graphics);
                Graphics2D g = (Graphics2D) graphics.create();
                g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
                g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

                if (imagem != null && imagem.getWidth(null) > 0) {
                    int tam = 105;
                    int w = imagem.getWidth(null);
                    int h = imagem.getHeight(null);
                    double escala = (double) tam / Math.max(w, h);
                    int dw = (int) (w * escala);
                    int dh = (int) (h * escala);
                    g.drawImage(imagem, getWidth() / 2 - dw / 2, getHeight() / 2 - dh / 2, dw, dh, this);
                } else {
                    CriaturaSprite.desenhar(g, pokemon.getTipo(), getWidth() / 2, getHeight() / 2, 95, false);
                }
                g.dispose();
            }
        };
        sprite.setOpaque(false);
        sprite.setPreferredSize(new Dimension(190, 115));
        card.add(sprite, BorderLayout.CENTER);

        JPanel inferior = new JPanel();
        inferior.setOpaque(false);
        inferior.setLayout(new BoxLayout(inferior, BoxLayout.Y_AXIS));

        JLabel hp = new JLabel("HP  " + pokemon.getVidaAtual() + " / " + pokemon.getVidaMaxima());
        hp.setAlignmentX(Component.CENTER_ALIGNMENT);
        hp.setFont(Constantes.FONTE_RPG_TEXTO);
        hp.setForeground(Color.WHITE);

        JProgressBar vida = new JProgressBar(0, pokemon.getVidaMaxima());
        vida.setValue(pokemon.getVidaAtual());
        vida.setStringPainted(false);
        vida.setPreferredSize(new Dimension(180, 9));
        vida.setMaximumSize(new Dimension(180, 9));
        vida.setBorderPainted(false);
        vida.setBackground(new Color(45, 45, 55));
        vida.setForeground(tipoCor);
        vida.setAlignmentX(Component.CENTER_ALIGNMENT);

        BotaoRPG escolher = new BotaoRPG("Escolher", tipoCor);
        escolher.setAlignmentX(Component.CENTER_ALIGNMENT);
        escolher.setMaximumSize(new Dimension(180, 38));
        escolher.setPreferredSize(new Dimension(180, 38));
        escolher.addActionListener(e -> {
            selecionado = pokemon;
            dispose();
        });

        inferior.add(hp);
        inferior.add(Box.createVerticalStrut(4));
        inferior.add(vida);
        inferior.add(Box.createVerticalStrut(7));
        inferior.add(escolher);
        card.add(inferior, BorderLayout.SOUTH);

        return card;
    }

    public static Pokemon mostrar(Component parent, List<Pokemon> pokemons, String titulo, String subtitulo) {
        if (pokemons == null || pokemons.isEmpty()) return null;

        Window owner = SwingUtilities.getWindowAncestor(parent);
        SeletorPokemonDialog dialog = new SeletorPokemonDialog(owner, pokemons, titulo, subtitulo);
        dialog.setVisible(true);
        return dialog.selecionado;
    }
}
