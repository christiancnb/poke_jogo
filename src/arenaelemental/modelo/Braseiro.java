package arenaelemental.modelo;

public class Braseiro extends Pokemon {
    public Braseiro(String nome, int vida, int ataque, int defesa) {
        super(nome, TipoElemental.FOGO, vida, ataque, defesa);
    }
    @Override
    public int calcularDano(Pokemon alvo) {
        return super.calcularDano(alvo);
    }

    protected double bonusEspecial(Pokemon alvo) {
        return alvo.percentualVida() < 0.3 ? 1.3 : 1.0;
    }
    public String descricaoHabilidade() {
        return "Labareda: +30% de dano contra alvos com vida abaixo de 30%";
    }
}
