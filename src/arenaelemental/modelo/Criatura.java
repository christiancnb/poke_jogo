package arenaelemental.modelo;

public abstract class Criatura {
    protected String nome;
    protected TipoElemental tipo;
    private int vidaMaxima;
    private int vidaAtual;
    protected int ataque;
    protected int defesa;

    protected Criatura(String nome, TipoElemental tipo, int vidaMaxima, int ataque, int defesa) {
        this.nome = nome;
        this.tipo = tipo;
        this.vidaMaxima = vidaMaxima;
        this.vidaAtual = vidaMaxima;
        this.ataque = ataque;
        this.defesa = defesa;
    }

    public String getNome() { return nome; }
    public TipoElemental getTipo() { return tipo; }
    public int getVidaMaxima() { return vidaMaxima; }
    public int getVidaAtual() { return vidaAtual; }
    public int getAtaque() { return ataque; }
    public int getDefesa() { return defesa; }
    public boolean estaViva() { return vidaAtual > 0; }
    public double percentualVida() { return (double) vidaAtual / vidaMaxima; }

    public void receberDano(int dano) { vidaAtual = Math.max(0, vidaAtual - dano); }
    public void curar(int quantidade) { vidaAtual = Math.min(vidaMaxima, vidaAtual + quantidade); }

    public int calcularDano(Criatura alvo) {
        int base = Math.max(1, ataque - alvo.defesa / 2);
        double vantagem = tipo.vantagemSobre(alvo.tipo);
        double bonus = bonusEspecial(alvo);
        return Math.max(1, (int) Math.round(base * vantagem * bonus));
    }

    public int atacar(Criatura alvo) {
        int dano = calcularDano(alvo);
        alvo.receberDano(dano);
        efeitoPosAtaque(alvo, dano);
        return dano;
    }

    protected abstract double bonusEspecial(Criatura alvo);
    protected void efeitoPosAtaque(Criatura alvo, int danoAplicado) { /* no-op por padrao */ }
    public abstract String descricaoHabilidade();
}
