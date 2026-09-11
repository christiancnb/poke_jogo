package arenaelemental.view;

import arenaelemental.modelo.Pokemon;
import arenaelemental.modelo.TipoElemental;
import javax.swing.*;
import java.awt.*;

public class PainelArena extends JPanel {
    private Pokemon jogador, selvagem;
    private Image spriteJogador, spriteSelvagem;

    PainelArena() {
        setOpaque(false);
        setPreferredSize(new Dimension(760, 230));
    }

    /**
     * Recebe as próprias criaturas (em vez de só o tipo elemental) porque
     * agora precisamos saber QUAL criatura é, para pedir o sprite certo.
     * Qualquer um dos dois parâmetros pode ser null.
     */
    void configurar(Pokemon jogador, Pokemon selvagem) {
        this.jogador = jogador;
        this.selvagem = selvagem;
        this.spriteJogador = null;
        this.spriteSelvagem = null;
        repaint();

        if (jogador != null) {
            SpriteCarregador.carregarAsync(SpriteCarregador.nomeParaSprite(jogador), true,
                    img -> { spriteJogador = img; repaint(); });
        }
        if (selvagem != null) {
            SpriteCarregador.carregarAsync(SpriteCarregador.nomeParaSprite(selvagem), false,
                    img -> { spriteSelvagem = img; repaint(); });
        }
    }

    @Override
    protected void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        Graphics2D g = (Graphics2D) graphics;
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        desenharCenario(g);

        int w = getWidth(), h = getHeight();
        if (selvagem != null) {
            desenharCriatura(g, spriteSelvagem, selvagem.getTipo(), !selvagem.estaViva(), w - 150, 110, 110);
        }
        if (jogador != null) {
            desenharCriatura(g, spriteJogador, jogador.getTipo(), !jogador.estaViva(), 150, h - 90, 150);
        }
    }

    /** Cenário básico de arena: céu com gradiente, nuvens e um gramado com duas plataformas. */
    private void desenharCenario(Graphics2D g) {
        int w = getWidth(), h = getHeight();

        GradientPaint ceu = new GradientPaint(0, 0, new Color(140, 200, 235), 0, h * 0.72f, new Color(210, 235, 245));
        g.setPaint(ceu);
        g.fillRect(0, 0, w, (int) (h * 0.72));

        g.setColor(new Color(255, 255, 255, 210));
        g.fillOval(w - 130, 24, 70, 34);
        g.fillOval(w - 90, 14, 60, 30);
        g.fillOval(40, 40, 60, 26);

        GradientPaint chao = new GradientPaint(0, (int) (h * 0.7), new Color(150, 205, 120), 0, h, new Color(100, 160, 88));
        g.setPaint(chao);
        g.fillRect(0, (int) (h * 0.7), w, (int) (h * 0.3));

        g.setColor(new Color(85, 140, 75, 170));
        g.fillOval(w - 250, h - 62, 220, 46);   // plataforma do adversário
        g.fillOval(-40, h - 32, 260, 50);       // plataforma do jogador
    }

    /** Desenha o sprite baixado se ele já chegou; senão cai no desenho vetorial de reserva. */
    private void desenharCriatura(Graphics2D g, Image sprite, TipoElemental tipo, boolean desmaiado,
                                   int cx, int cy, int tamanho) {
        if (sprite == null || sprite.getWidth(null) <= 0) {
            CriaturaSprite.desenhar(g, tipo, cx, cy, tamanho, desmaiado);
            return;
        }
        int larguraOrig = sprite.getWidth(null), alturaOrig = sprite.getHeight(null);
        double escala = (double) tamanho / Math.max(larguraOrig, alturaOrig);
        int dw = (int) (larguraOrig * escala), dh = (int) (alturaOrig * escala);

        Composite antigo = g.getComposite();
        if (desmaiado) {
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.35f));
        }
        g.drawImage(sprite, cx - dw / 2, cy - dh + dh / 6, dw, dh, this);
        g.setComposite(antigo);
    }
}
