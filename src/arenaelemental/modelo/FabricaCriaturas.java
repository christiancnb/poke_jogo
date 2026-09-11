package arenaelemental.modelo;

public class FabricaCriaturas {
    private static final String[] NOMES_BRASEIRO = {"Braseiro", "Ignivo", "Chamotim"};
    private static final String[] NOMES_MARULHO = {"Marulho", "Aquário", "Gotinho"};
    private static final String[] NOMES_FOLHARAL = {"Folharal", "Brotante", "Ramalho"};

    public static Pokemon novaInicial(TipoElemental tipo) {
        switch (tipo) {
            case FOGO: return new Braseiro("Braseiro", 42, 13, 8);
            case AGUA: return new Marulho("Marulho", 46, 11, 10);
            case PLANTA: return new Folharal("Folharal", 44, 11, 9);
        }
        throw new IllegalArgumentException("Tipo desconhecido");
    }

    public static Pokemon selvagemAleatorio() {
        TipoElemental[] tipos = TipoElemental.values();
        TipoElemental tipo = tipos[(int) (Math.random() * tipos.length)];
        int vida = 24 + (int) (Math.random() * 18);
        int ataque = 7 + (int) (Math.random() * 7);
        int defesa = 5 + (int) (Math.random() * 6);
        switch (tipo) {
            case FOGO: return new Braseiro(escolher(NOMES_BRASEIRO), vida, ataque, defesa);
            case AGUA: return new Marulho(escolher(NOMES_MARULHO), vida, ataque, defesa);
            default: return new Folharal(escolher(NOMES_FOLHARAL), vida, ataque, defesa);
        }
    }

    private static String escolher(String[] opcoes) {
        return opcoes[(int) (Math.random() * opcoes.length)];
    }
}

// =========================================================================
// TELAS
// =========================================================================
