package arenaelemental.view;

import arenaelemental.modelo.TipoElemental;
import arenaelemental.modelo.FabricaCriaturas;
import javax.swing.*;
import java.awt.*;

public class PainelEscolha extends JPanel {
    PainelEscolha(JanelaPrincipal janela) {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        JLabel titulo = new JLabel("Escolha sua primeira criatura", SwingConstants.CENTER);
        titulo.setFont(Constantes.FONTE_RPG_TITULO.deriveFont(24f));
        titulo.setForeground(Color.WHITE);
        add(titulo, BorderLayout.NORTH);

        JPanel cartas = new JPanel(new FlowLayout(FlowLayout.CENTER, 24, 20));
        cartas.setOpaque(false);

        // terceiro argumento novo: nome do Pokémon usado só para buscar o sprite
        // (ajuste livremente — não afeta nenhuma regra de jogo)
        cartas.add(new CartaoCriatura(TipoElemental.FOGO, "Braseiro", "charmander",
                "Labareda: dano extra contra alvos enfraquecidos",
                () -> janela.iniciarJornada(FabricaCriaturas.novaInicial(TipoElemental.FOGO))));
        cartas.add(new CartaoCriatura(TipoElemental.AGUA, "Marulho", "squirtle",
                "Maré Cheia: dano extra com a vida acima de 50%",
                () -> janela.iniciarJornada(FabricaCriaturas.novaInicial(TipoElemental.AGUA))));
        cartas.add(new CartaoCriatura(TipoElemental.PLANTA, "Folharal", "bulbasaur",
                "Sugar Seiva: recupera vida a cada ataque",
                () -> janela.iniciarJornada(FabricaCriaturas.novaInicial(TipoElemental.PLANTA))));
        add(cartas, BorderLayout.CENTER);
    }

    @Override
    protected void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        Graphics2D g = (Graphics2D) graphics;
        GradientPaint gp = new GradientPaint(0, 0, new Color(30, 26, 46), 0, getHeight(), new Color(15, 13, 24));
        g.setPaint(gp);
        g.fillRect(0, 0, getWidth(), getHeight());
    }
}
