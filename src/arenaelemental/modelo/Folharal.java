package arenaelemental.modelo;

public class Folharal extends Pokemon {
    public Folharal(String nome, int vida, int ataque, int defesa) {
        super(nome, TipoElemental.PLANTA, vida, ataque, defesa);
    }
    @Override
    public int calcularDano(Pokemon alvo) {
        return super.calcularDano(alvo);
    }

    protected double bonusEspecial(Pokemon alvo) { return 1.0; }
    protected void efeitoPosAtaque(Pokemon alvo, int danoAplicado) {
        curar((int) Math.round(danoAplicado * 0.2));
    }
    public String descricaoHabilidade() {
        return "Sugar Seiva: recupera 20% do dano causado como vida";
    }
}

// =========================================================================
// TREINADOR / BESTIARIO
// =========================================================================
