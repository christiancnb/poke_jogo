package arenaelemental.modelo;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CriaturaTest {

    @Test
    void danoSemVantagemDeTipo() {
        Criatura a = new Braseiro("A", 50, 20, 10);
        Criatura b = new Braseiro("B", 50, 20, 10); // fogo vs fogo = neutro (1.0x)
        int dano = a.calcularDano(b);
        int base = Math.max(1, a.getAtaque() - b.getDefesa() / 2); // 20 - 5 = 15
        assertEquals(base, dano, "Sem vantagem de tipo, o dano deve ser igual ao valor base");
    }

    @Test
    void danoComVantagemDeTipo() {
        Criatura fogo = new Braseiro("Fogo", 50, 20, 10);
        Criatura planta = new Folharal("Planta", 50, 20, 20); // vida cheia: nao aciona o bonus especial do fogo
        int dano = fogo.calcularDano(planta);
        int base = Math.max(1, fogo.getAtaque() - planta.getDefesa() / 2); // 20 - 10 = 10
        int esperado = (int) Math.round(base * 1.5); // fogo vence planta
        assertEquals(esperado, dano, "Fogo contra Planta deve aplicar 1.5x de dano");
    }

    @Test
    void danoComDesvantagemDeTipo() {
        Criatura planta = new Folharal("Planta", 50, 20, 10);
        Criatura fogo = new Braseiro("Fogo", 50, 20, 20);
        int dano = planta.calcularDano(fogo);
        int base = Math.max(1, planta.getAtaque() - fogo.getDefesa() / 2); // 20 - 10 = 10
        int esperado = (int) Math.round(base * 0.67); // planta perde para fogo
        assertEquals(esperado, dano, "Planta contra Fogo deve aplicar o multiplicador de desvantagem (0.67x)");
    }

    @Test
    void danoNuncaFicaAbaixoDeUm() {
        Criatura fraca = new Marulho("Fraca", 30, 1, 5);
        Criatura tanque = new Braseiro("Tanque", 100, 5, 100);
        int dano = fraca.calcularDano(tanque);
        assertTrue(dano >= 1, "O dano minimo garantido deve ser 1, mesmo contra defesa muito alta");
    }

    @Test
    void criaturaMorreQuandoVidaChegaAZero() {
        Criatura c = new Marulho("Teste", 20, 10, 5);
        assertTrue(c.estaViva());
        c.receberDano(30);
        assertEquals(0, c.getVidaAtual());
        assertFalse(c.estaViva());
    }

    @Test
    void vidaNuncaUltrapassaOMaximoAoCurar() {
        Criatura c = new Folharal("Teste", 50, 10, 5);
        c.receberDano(5); // vida: 45/50
        c.curar(100);
        assertEquals(50, c.getVidaAtual(), "curar() nao pode deixar a vida atual acima da vida maxima");
    }

    @Test
    void braseiroCausaBonusContraAlvoEnfraquecido() {
        Criatura braseiro = new Braseiro("Braseiro", 50, 20, 0);
        Criatura alvoFraco = new Folharal("AlvoFraco", 100, 10, 0);
        alvoFraco.receberDano(80); // vida: 20/100 = 20%, abaixo de 30%
        Criatura alvoCheio = new Folharal("AlvoCheio", 100, 10, 0);
        int danoContraFraco = braseiro.calcularDano(alvoFraco);
        int danoContraCheio = braseiro.calcularDano(alvoCheio);
        assertTrue(danoContraFraco > danoContraCheio,
                "Labareda deve causar mais dano contra um alvo com vida abaixo de 30%");
    }

    @Test
    void folharalCuraAoAtacar() {
        Criatura folharal = new Folharal("Folharal", 50, 15, 5);
        folharal.receberDano(20); // vida: 30/50
        Criatura alvo = new Marulho("Alvo", 50, 5, 5);
        int vidaAntes = folharal.getVidaAtual();
        folharal.atacar(alvo);
        assertTrue(folharal.getVidaAtual() > vidaAntes,
                "Sugar Seiva deve curar o Folharal apos ele atacar");
    }
}
