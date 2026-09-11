package arenaelemental.view;

import arenaelemental.modelo.Pokemon;

import javax.swing.*;
import java.awt.*;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public final class SpriteCarregador {

    private static final String URL_FRENTE = "https://play.pokemonshowdown.com/sprites/gen5/";
    private static final String URL_COSTAS = "https://play.pokemonshowdown.com/sprites/gen5-back/";

    private static final Map<String, ImageIcon> cache = new HashMap<>();

    private SpriteCarregador() {}


    public static String nomeParaSprite(Pokemon c) {
        if (c == null) return null;
        switch (c.getClass().getSimpleName()) {
            case "Braseiro": return "charmander";
            case "Marulho":  return "squirtle";
            case "Folharal": return "bulbasaur";
            default: return null;
        }
    }


    public static void carregarAsync(String nomePokemon, boolean deCostas, Consumer<Image> aoCarregar) {
        if (nomePokemon == null || nomePokemon.isBlank()) {
            aoCarregar.accept(null);
            return;
        }
        String chave = nomePokemon.toLowerCase() + (deCostas ? "-costas" : "-frente");
        ImageIcon jaCarregado = cache.get(chave);
        if (jaCarregado != null) {
            aoCarregar.accept(jaCarregado.getImage());
            return;
        }

        new SwingWorker<Image, Void>() {
            @Override
            protected Image doInBackground() {
                try {
                    String base = deCostas ? URL_COSTAS : URL_FRENTE;
                    String nomeUrl = nomePokemon.toLowerCase().replaceAll("[^a-z0-9]", "");
                    URL url = new URL(base + nomeUrl + ".png");
                    ImageIcon icone = new ImageIcon(url); // já estamos numa thread de fundo
                    return icone.getIconWidth() > 0 ? icone.getImage() : null;
                } catch (Exception e) {
                    return null; // sem internet, nome inexistente, etc.
                }
            }

            @Override
            protected void done() {
                Image imagem = null;
                try {
                    imagem = get();
                } catch (Exception ignorado) {
                    // mantém imagem = null, cai no fallback
                }
                if (imagem != null) {
                    cache.put(chave, new ImageIcon(imagem));
                }
                aoCarregar.accept(imagem);
            }
        }.execute();
    }
}
