package arenaelemental.treinador;

import arenaelemental.modelo.Pokemon;
import arenaelemental.modelo.Marulho;
import arenaelemental.modelo.Braseiro;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TreinadorTest {

    @Test
    void naoAdicionaAlemDoLimiteDeSeis() {
        Treinador t = new Treinador("Ana");
        for (int i = 0; i < 6; i++) {
            assertTrue(t.adicionarNaEquipe(new Marulho("C" + i, 20, 5, 5)));
        }
        boolean adicionouSetima = t.adicionarNaEquipe(new Marulho("Setima", 20, 5, 5));
        assertFalse(adicionouSetima);
        assertEquals(6, t.getEquipe().size());
    }

    @Test
    void getAtivaRetornaPrimeiraCriaturaViva() {
        Treinador t = new Treinador("Ana");
        Pokemon desmaiada = new Marulho("Desmaiada", 20, 5, 5);
        desmaiada.receberDano(20);
        Pokemon viva = new Marulho("Viva", 20, 5, 5);
        t.adicionarNaEquipe(desmaiada);
        t.adicionarNaEquipe(viva);
        assertEquals(viva, t.getAtiva());
    }

    @Test
    void treinadorPodeEscolherUmPokemonVivoDaEquipe() {
        Treinador t = new Treinador("Ana");
        Pokemon fogo = new Braseiro("Fogo", 30, 10, 5);
        Pokemon agua = new Marulho("Agua", 30, 10, 5);
        t.adicionarNaEquipe(fogo);
        t.adicionarNaEquipe(agua);

        assertTrue(t.escolherPokemon(agua));
        assertEquals(agua, t.getAtiva());
    }

    @Test
    void treinadorNaoPodeEscolherPokemonForaDaEquipe() {
        Treinador t = new Treinador("Ana");
        Pokemon foraDaEquipe = new Marulho("Fora", 30, 10, 5);
        assertFalse(t.escolherPokemon(foraDaEquipe));
    }
}
