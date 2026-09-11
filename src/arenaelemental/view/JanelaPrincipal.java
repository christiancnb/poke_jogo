package arenaelemental.view;

import arenaelemental.modelo.Pokemon;
import arenaelemental.treinador.Treinador;
import arenaelemental.treinador.Bestiario;
import javax.swing.*;
import java.awt.*;

public class JanelaPrincipal extends JFrame {
    private CardLayout cardLayout = new CardLayout();
    private JPanel cartas = new JPanel(cardLayout);
    private Treinador treinador;
    private Bestiario bestiario = new Bestiario();
    private PainelBatalha painelBatalha;
    private PainelEquipe painelEquipe;

    public JanelaPrincipal() {
        setTitle("Arena Elemental");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 650);
        setLocationRelativeTo(null);
        setResizable(false);

        painelBatalha = new PainelBatalha(this);
        painelEquipe = new PainelEquipe(this);

        cartas.add(new PainelMenu(this), "MENU");
        cartas.add(new PainelEscolha(this), "ESCOLHA");
        cartas.add(painelBatalha, "BATALHA");
        cartas.add(painelEquipe, "EQUIPE");

        add(cartas);
        mostrar("MENU");
    }

    void mostrar(String nome) {
        if (nome.equals("EQUIPE")) painelEquipe.atualizar();
        cardLayout.show(cartas, nome);
    }

    void iniciarJornada(Pokemon inicial) {
        treinador = new Treinador("Treinador");
        treinador.adicionarNaEquipe(inicial);
        bestiario.registrar(inicial);
        painelBatalha.iniciar(treinador);
        mostrar("BATALHA");
    }

    Treinador getTreinador() { return treinador; }
    Bestiario getBestiario() { return bestiario; }
}

// =========================================================================
