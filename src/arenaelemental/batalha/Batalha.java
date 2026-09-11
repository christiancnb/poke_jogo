package arenaelemental.batalha;

import arenaelemental.modelo.Pokemon;
import java.util.List;
import java.util.ArrayList;

public class Batalha {
    private Pokemon doJogador;
    private Pokemon selvagem;
    private final List<String> log = new ArrayList<>();

    public Batalha(Pokemon doJogador, Pokemon selvagem) {
        if (doJogador == null || selvagem == null) throw new IllegalArgumentException("Os dois Pokémon são obrigatórios");
        this.doJogador = doJogador;
        this.selvagem = selvagem;
        log.add("Um " + selvagem.getNome() + " selvagem apareceu!");
    }

    public Pokemon getDoJogador() { return doJogador; }
    public Pokemon getSelvagem() { return selvagem; }
    public List<String> getLog() { return log; }

    public boolean trocarPokemon(Pokemon novo) {
        if (terminou() || novo == null || !novo.estaViva()) return false;
        doJogador = novo;
        log.add("Você escolheu " + novo.getNome() + " para continuar a batalha!");
        int dano = selvagem.atacar(doJogador);
        log.add(selvagem.getNome() + " atacou durante a troca e causou " + dano + " de dano!");
        return true;
    }

    public void turno() {
        if (terminou()) return;
        int dano1 = doJogador.atacar(selvagem);
        log.add(doJogador.getNome() + " atacou e causou " + dano1 + " de dano!");
        if (!selvagem.estaViva()) {
            log.add(selvagem.getNome() + " selvagem foi derrotado!");
            return;
        }
        int dano2 = selvagem.atacar(doJogador);
        log.add(selvagem.getNome() + " revidou e causou " + dano2 + " de dano!");
        if (!doJogador.estaViva()) {
            log.add(doJogador.getNome() + " não pode continuar!");
        }
    }

    public boolean terminou() { return !doJogador.estaViva() || !selvagem.estaViva(); }
}
