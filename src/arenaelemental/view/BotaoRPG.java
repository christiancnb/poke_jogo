package arenaelemental.view;

import javax.swing.*;
import java.awt.*;

/**
 * Botão com visual "RPG clássico": fundo escuro, borda dupla estilo caixa de
 * menu de jogo 16-bit, fonte monoespaçada, sombra e destaque ao passar o
 * mouse / clicar.
 *
 * Não há nenhuma mudança de comportamento em relação a um JButton comum —
 * ele continua funcionando com addActionListener normalmente. A única
 * diferença é que a pintura padrão do Look and Feel do Swing (aquele
 * retângulo cinza "de formulário") é desligada e substituída pela pintura
 * manual feita em paintComponent().
 */
public class BotaoRPG extends JButton {

    private final Color corDestaque;

    public BotaoRPG(String texto, Color corDestaque) {
        super(texto.toUpperCase());
        this.corDestaque = corDestaque;
        setFont(Constantes.FONTE_RPG_BOTAO);
        setForeground(Color.WHITE);
        setFocusPainted(false);
        setContentAreaFilled(false);
        setBorderPainted(false);
        setOpaque(false);
        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        setBorder(BorderFactory.createEmptyBorder(10, 18, 10, 18));
        setPreferredSize(new Dimension(200, 44));
    }

    @Override
    protected void paintComponent(Graphics graphics) {
        int w = getWidth(), h = getHeight();
        boolean pressionado = getModel().isArmed() && getModel().isPressed();
        boolean sobreMouse = getModel().isRollover();
        int desloc = pressionado ? 2 : 0;

        Graphics2D g = (Graphics2D) graphics.create();
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        Color fundo = sobreMouse ? clarear(Constantes.HUD_BG, 24) : Constantes.HUD_BG;

        // sombra (dá profundidade / efeito "3D" de caixa de menu)
        g.setColor(new Color(0, 0, 0, 90));
        g.fillRoundRect(3, 5, w - 6, h - 6, 10, 10);

        // corpo do botão
        g.setColor(fundo);
        g.fillRoundRect(desloc, desloc, w - 6, h - 6, 10, 10);

        // borda dupla: clara por fora, cor de destaque por dentro
        g.setStroke(new BasicStroke(2f));
        g.setColor(Constantes.HUD_BORDA_CLARA);
        g.drawRoundRect(desloc + 1, desloc + 1, w - 8, h - 8, 10, 10);
        g.setColor(corDestaque);
        g.drawRoundRect(desloc + 4, desloc + 4, w - 14, h - 14, 6, 6);

        g.dispose();

        // desenha o texto/ícone padrão do botão, deslocado junto quando pressionado
        Graphics textoG = graphics.create();
        textoG.translate(desloc, desloc);
        super.paintComponent(textoG);
        textoG.dispose();
    }

    private Color clarear(Color c, int quantidade) {
        return new Color(
                Math.min(255, c.getRed() + quantidade),
                Math.min(255, c.getGreen() + quantidade),
                Math.min(255, c.getBlue() + quantidade));
    }
}
