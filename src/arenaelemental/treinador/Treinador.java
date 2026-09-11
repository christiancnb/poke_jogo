package arenaelemental.treinador;

import arenaelemental.modelo.Pokemon;
import java.util.List;
import java.util.ArrayList;

public class Treinador {
    private static final int TAMANHO_MAX_EQUIPE = 6;
    private String nome;
    private final List<Pokemon> equipe = new ArrayList<>();
    private Pokemon ativa;

    public Treinador(String nome) { this.nome = nome; }
    public String getNome() { return nome; }
    public List<Pokemon> getEquipe() { return equipe; }
    public static int getTamanhoMaxEquipe() { return TAMANHO_MAX_EQUIPE; }

    public boolean adicionarNaEquipe(Pokemon pokemon) {
        if (pokemon == null || equipe.size() >= TAMANHO_MAX_EQUIPE || equipe.contains(pokemon)) return false;
        equipe.add(pokemon);
        if (ativa == null && pokemon.estaViva()) ativa = pokemon;
        return true;
    }

    /** Retorna o Pokémon escolhido; se ele não estiver disponível, procura outro vivo. */
    public Pokemon getAtiva() {
        if (ativa != null && ativa.estaViva()) return ativa;
        for (Pokemon pokemon : equipe) {
            if (pokemon.estaViva()) {
                ativa = pokemon;
                return pokemon;
            }
        }
        ativa = null;
        return null;
    }

    /** Seleciona explicitamente um Pokémon da equipe para a próxima batalha. */
    public boolean escolherPokemon(Pokemon pokemon) {
        if (pokemon == null || !equipe.contains(pokemon) || !pokemon.estaViva()) return false;
        ativa = pokemon;
        return true;
    }
}
