package arenaelemental.view;

import arenaelemental.modelo.Pokemon;
import javax.swing.*;
import java.awt.*;

public class BarraVida extends JPanel {
    private Pokemon criatura;

    public BarraVida(Pokemon criatura) {
        this.criatura = criatura;
        setOpaque(false);
        setPreferredSize(new Dimension(230, 64));
    }

    public void setCriatura(Pokemon c) { this.criatura = c; repaint(); }

    @Override
    protected void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        if (criatura == null) return;
        Graphics2D g = (Graphics2D) graphics;
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int w = getWidth() - 4;

        // placa de fundo estilo HUD (sem a borda padrão do Windows)
        g.setColor(new Color(0, 0, 0, 70));
        g.fillRoundRect(2, 2, w, 46, 12, 12);
        g.setColor(Constantes.HUD_BG);
        g.fillRoundRect(0, 0, w, 44, 12, 12);
        g.setStroke(new BasicStroke(1.6f));
        g.setColor(Constantes.HUD_BORDA_CLARA);
        g.drawRoundRect(0, 0, w, 44, 12, 12);

        g.setFont(Constantes.FONTE_RPG_BOTAO.deriveFont(13f));
        g.setColor(Color.WHITE);
        g.drawString(criatura.getNome(), 12, 18);

        g.setFont(Constantes.FONTE_RPG_TEXTO.deriveFont(11f));
        FontMetrics fm = g.getFontMetrics();
        String tipoTxt = criatura.getTipo().nomeExibicao();
        g.setColor(Constantes.CYAN);
        g.drawString(tipoTxt, w - fm.stringWidth(tipoTxt) - 10, 18);

        int barX = 12, barY = 24, barW = w - 24, barH = 12;
        g.setColor(new Color(40, 40, 46));
        g.fillRoundRect(barX, barY, barW, barH, 6, 6);

        double pct = Math.max(0, criatura.percentualVida());
        int fillW = (int) (barW * pct);
        // Verde > Amarelo > Vermelho conforme a vida diminui
        Color cor = pct > 0.5 ? new Color(76, 201, 105)
                : (pct > 0.2 ? new Color(240, 173, 61) : new Color(224, 68, 68));
        if (fillW > 0) {
            g.setColor(cor);
            g.fillRoundRect(barX, barY, fillW, barH, 6, 6);
            g.setColor(cor.brighter());
            g.fillRoundRect(barX, barY, fillW, barH / 2, 6, 6); // brilho na metade de cima
        }
        g.setColor(new Color(15, 15, 18));
        g.drawRoundRect(barX, barY, barW, barH, 6, 6);

        g.setFont(Constantes.FONTE_RPG_TEXTO.deriveFont(10f));
        g.setColor(new Color(220, 220, 225));
        String hpTxt = criatura.getVidaAtual() + "/" + criatura.getVidaMaxima() + " HP";
        g.drawString(hpTxt, barX + barW - g.getFontMetrics().stringWidth(hpTxt), barY + barH + 12);
    }
}
