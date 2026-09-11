package arenaelemental.view;

import javax.swing.*;
import java.awt.*;

public class PainelMenu extends JPanel {
    PainelMenu(JanelaPrincipal janela) {
        setLayout(new GridBagLayout());
        setBackground(Constantes.NAVY);
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0; c.insets = new Insets(10, 0, 10, 0);

        JLabel titulo = new JLabel("ARENA ELEMENTAL");
        titulo.setFont(Constantes.FONTE_RPG_TITULO);
        titulo.setForeground(Color.WHITE);
        c.gridy = 0; add(titulo, c);

        JLabel subtitulo = new JLabel("> Capture. Treine. Batalhe. <");
        subtitulo.setFont(Constantes.FONTE_RPG_TEXTO.deriveFont(15f));
        subtitulo.setForeground(Constantes.CYAN);
        c.gridy = 1; add(subtitulo, c);

        BotaoRPG btnJogar = new BotaoRPG("Nova Jornada", Constantes.CYAN);
        btnJogar.setPreferredSize(new Dimension(240, 50));
        btnJogar.addActionListener(e -> janela.mostrar("ESCOLHA"));
        c.gridy = 2; c.insets = new Insets(50, 0, 12, 0); add(btnJogar, c);

        BotaoRPG btnSair = new BotaoRPG("Sair", Constantes.AMBER);
        btnSair.setPreferredSize(new Dimension(240, 50));
        btnSair.addActionListener(e -> System.exit(0));
        c.gridy = 3; c.insets = new Insets(10, 0, 10, 0); add(btnSair, c);
    }

    @Override
    protected void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        // leve efeito de "scanline" de tela retrô sobre o fundo navy
        Graphics2D g = (Graphics2D) graphics;
        g.setColor(new Color(255, 255, 255, 10));
        for (int y = 0; y < getHeight(); y += 4) {
            g.drawLine(0, y, getWidth(), y);
        }
    }
}
