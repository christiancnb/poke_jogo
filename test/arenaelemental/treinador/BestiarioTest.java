package arenaelemental.treinador;

import arenaelemental.modelo.Braseiro;
import arenaelemental.modelo.Marulho;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class BestiarioTest {

    @Test
    void naoRegistraAMesmaEspecieDuasVezes() {
        Bestiario b = new Bestiario();
        assertTrue(b.registrar(new Braseiro("Um", 30, 10, 5)));
        assertFalse(b.registrar(new Braseiro("Outro", 40, 12, 6)),
                "Duas criaturas da mesma especie (mesmo com nomes diferentes) nao devem ser registradas duas vezes");
        assertEquals(1, b.total());
    }

    @Test
    void registraEspeciesDiferentesSeparadamente() {
        Bestiario b = new Bestiario();
        b.registrar(new Braseiro("Fogo", 30, 10, 5));
        b.registrar(new Marulho("Agua", 30, 10, 5));
        assertEquals(2, b.total());
    }
}
