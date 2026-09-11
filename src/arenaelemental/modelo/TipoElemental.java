package arenaelemental.modelo;

import java.awt.Color;

/** Tabela central de relações entre tipos. */
public enum TipoElemental {
    FOGO, AGUA, PLANTA;

    public static double multiplicadorContra(TipoElemental atacante, TipoElemental defensor) {
        if (atacante == FOGO && defensor == PLANTA) return 1.5;
        if (atacante == PLANTA && defensor == AGUA) return 1.5;
        if (atacante == AGUA && defensor == FOGO) return 1.5;
        if (atacante == PLANTA && defensor == FOGO) return 0.67;
        if (atacante == AGUA && defensor == PLANTA) return 0.67;
        if (atacante == FOGO && defensor == AGUA) return 0.67;
        return 1.0;
    }

    public double vantagemSobre(TipoElemental outro) {
        return multiplicadorContra(this, outro);
    }

    public Color corPrincipal() {
        switch (this) {
            case FOGO: return new Color(230, 92, 46);
            case AGUA: return new Color(58, 130, 214);
            case PLANTA: return new Color(87, 168, 74);
            default: return Color.GRAY;
        }
    }

    public String nomeExibicao() {
        switch (this) {
            case FOGO: return "Fogo";
            case AGUA: return "Água";
            case PLANTA: return "Planta";
            default: return "?";
        }
    }
}
