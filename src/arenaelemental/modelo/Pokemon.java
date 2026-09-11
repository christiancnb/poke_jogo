package arenaelemental.modelo;

/**
 * Classe base de todas as criaturas do jogo.
 *
 * Os atributos exigidos pelo projeto ficam protegidos para que as subclasses
 * possam especializar o comportamento, enquanto a vida é alterada somente
 * por métodos públicos (receberDano/curar), mantendo o encapsulamento da
 * regra de negócio.
 */
public abstract class Pokemon {
    protected String nome;
    protected TipoElemental tipo;
    protected int vida;
    protected int ataque;
    protected int defesa;
    private final int vidaMaxima;

    protected Pokemon(String nome, TipoElemental tipo, int vida, int ataque, int defesa) {
        if (vida <= 0) throw new IllegalArgumentException("A vida deve ser maior que zero");
        this.nome = nome;
        this.tipo = tipo;
        this.vidaMaxima = vida;
        this.vida = vida;
        this.ataque = ataque;
        this.defesa = defesa;
    }

    public String getNome() { return nome; }
    public TipoElemental getTipo() { return tipo; }
    public int getVidaMaxima() { return vidaMaxima; }
    public int getVidaAtual() { return vida; }
    public int getAtaque() { return ataque; }
    public int getDefesa() { return defesa; }
    public boolean estaViva() { return vida > 0; }
    public double percentualVida() { return (double) vida / vidaMaxima; }

    /** Vida nunca é alterada diretamente por classes externas. */
    public void receberDano(int dano) {
        if (dano < 0) throw new IllegalArgumentException("O dano não pode ser negativo");
        vida = Math.max(0, vida - dano);
    }

    public void curar(int quantidade) {
        if (quantidade < 0) throw new IllegalArgumentException("A cura não pode ser negativa");
        vida = Math.min(vidaMaxima, vida + quantidade);
    }

    /**
     * Cálculo comum de dano. A vantagem elemental fica centralizada em
     * TipoElemental, portanto a batalha não precisa conhecer a tabela de tipos.
     */
    public int calcularDano(Pokemon alvo) {
        int base = Math.max(1, ataque - alvo.defesa / 2);
        double vantagem = TipoElemental.multiplicadorContra(tipo, alvo.tipo);
        double bonus = bonusEspecial(alvo);
        return Math.max(1, (int) Math.round(base * vantagem * bonus));
    }

    public int atacar(Pokemon alvo) {
        int dano = calcularDano(alvo);
        alvo.receberDano(dano);
        efeitoPosAtaque(alvo, dano);
        return dano;
    }

    protected abstract double bonusEspecial(Pokemon alvo);
    protected void efeitoPosAtaque(Pokemon alvo, int danoAplicado) { }
    public abstract String descricaoHabilidade();
}
