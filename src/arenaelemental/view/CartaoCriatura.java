package arenaelemental.view;

import arenaelemental.modelo.TipoElemental;
import javax.swing.*;
import java.awt.*;

public class CartaoCriatura extends JPanel {
    /**
     * @param nomeSprite nome do Pokémon (em inglês) usado apenas para montar a
     *                   URL do sprite no Pokémon Showdown — puramente visual.
     */
    CartaoCriatura(TipoElemental tipo, String nome, String nomeSprite, String habilidade, Runnable aoEscolher) {
        setLayout(new BorderLayout());
        setBackground(new Color(28, 24, 38));
        setBorder(BorderFactory.createLineBorder(tipo.corPrincipal(), 2, true));
        setPreferredSize(new Dimension(230, 300));

        JPanel spritePanel = new JPanel() {
            private Image sprite;
            {
                SpriteCarregador.carregarAsync(nomeSprite, false, img -> { sprite = img; repaint(); });
            }
            @Override protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                if (sprite != null && sprite.getWidth(null) > 0) {
                    int tam = 120;
                    int w = sprite.getWidth(null), h = sprite.getHeight(null);
                    double escala = (double) tam / Math.max(w, h);
                    int dw = (int) (w * escala), dh = (int) (h * escala);
                    g2.drawImage(sprite, getWidth() / 2 - dw / 2, getHeight() / 2 + 10 - dh / 2, dw, dh, this);
                } else {
                    CriaturaSprite.desenhar(g2, tipo, getWidth() / 2, getHeight() / 2 + 10, 120, false);
                }
            }
        };
        spritePanel.setPreferredSize(new Dimension(230, 150));
        spritePanel.setOpaque(false);
        add(spritePanel, BorderLayout.NORTH);

        JPanel infoPanel = new JPanel();
        infoPanel.setOpaque(false);
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.setBorder(BorderFactory.createEmptyBorder(4, 16, 8, 16));

        JLabel lblNome = new JLabel(nome);
        lblNome.setFont(Constantes.FONTE_RPG_BOTAO.deriveFont(18f));
        lblNome.setForeground(Color.WHITE);
        lblNome.setAlignmentX(CENTER_ALIGNMENT);

        JLabel lblTipo = new JLabel("Tipo " + tipo.nomeExibicao());
        lblTipo.setFont(Constantes.FONTE_RPG_TEXTO.deriveFont(13f));
        lblTipo.setForeground(tipo.corPrincipal());
        lblTipo.setAlignmentX(CENTER_ALIGNMENT);

        JTextArea lblHabilidade = new JTextArea(habilidade);
        lblHabilidade.setFont(Constantes.FONTE_RPG_TEXTO.deriveFont(11f));
        lblHabilidade.setForeground(new Color(200, 200, 210));
        lblHabilidade.setLineWrap(true);
        lblHabilidade.setWrapStyleWord(true);
        lblHabilidade.setOpaque(false);
        lblHabilidade.setEditable(false);
        lblHabilidade.setFocusable(false);
        lblHabilidade.setAlignmentX(CENTER_ALIGNMENT);
        lblHabilidade.setMaximumSize(new Dimension(190, 60));

        infoPanel.add(lblNome);
        infoPanel.add(Box.createVerticalStrut(4));
        infoPanel.add(lblTipo);
        infoPanel.add(Box.createVerticalStrut(8));
        infoPanel.add(lblHabilidade);
        add(infoPanel, BorderLayout.CENTER);

        BotaoRPG btn = new BotaoRPG("Escolher", tipo.corPrincipal());
        btn.addActionListener(e -> aoEscolher.run());
        add(btn, BorderLayout.SOUTH);
    }
}
