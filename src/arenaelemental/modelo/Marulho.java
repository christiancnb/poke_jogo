package arenaelemental.modelo;

public class Marulho extends Pokemon {
    public Marulho(String nome, int vida, int ataque, int defesa) {
        super(nome, TipoElemental.AGUA, vida, ataque, defesa);
    }
    @Override
    public int calcularDano(Pokemon alvo) {
        return super.calcularDano(alvo);
    }

    protected double bonusEspecial(Pokemon alvo) {
        return percentualVida() > 0.5 ? 1.15 : 1.0;
    }
    public String descricaoHabilidade() {
        return "Maré Cheia: +15% de dano enquanto a própria vida estiver acima de 50%";
    }
}
