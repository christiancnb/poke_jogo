# Arena Elemental - versão corrigida

Projeto Java Swing para a disciplina de Programação Orientada a Objetos.

## Como abrir no IntelliJ IDEA

1. Extraia o ZIP.
2. Abra a pasta `poke_jogo` no IntelliJ IDEA.
3. Se aparecer a opção de importar como Maven, aceite e aguarde o download do JUnit 5.
4. Use JDK 17.
5. Execute `src/arenaelemental/Main.java`.
6. Para os testes, abra a pasta `test` ou execute os testes JUnit pelo IntelliJ.

## Alterações desta versão

- Classe base renomeada de `Criatura` para `Pokemon`, conforme o requisito técnico.
- Atributos base `nome`, `tipo`, `vida` e `ataque` estão na classe `Pokemon`.
- Vida continua sendo alterada somente pelos métodos `receberDano()` e `curar()`.
- As subclasses `Braseiro`, `Marulho` e `Folharal` sobrescrevem `calcularDano()`.
- Vantagem elemental centralizada em `TipoElemental.multiplicadorContra()`.
- Treinador possui equipe com limite de 6 e seleção explícita do Pokémon ativo.
- Antes de cada batalha, o jogador escolhe qual Pokémon vivo da equipe vai lutar.
- Durante a batalha existe a opção `Trocar Pokémon`.
- Pokédex/Bestiário continua registrando espécies sem duplicação.
- Captura continua aumentando de chance conforme a vida do Pokémon selvagem diminui.
- Projeto configurado com Maven e JUnit 5.
- Testes existentes foram mantidos e foram adicionados testes para seleção de Pokémon.

## Estrutura principal

- `src/arenaelemental/modelo/Pokemon.java` - classe base.
- `src/arenaelemental/modelo/Braseiro.java` - tipo Fogo.
- `src/arenaelemental/modelo/Marulho.java` - tipo Água.
- `src/arenaelemental/modelo/Folharal.java` - tipo Planta.
- `src/arenaelemental/modelo/TipoElemental.java` - tabela central de vantagens.
- `src/arenaelemental/treinador/Treinador.java` - equipe e seleção.
- `src/arenaelemental/treinador/Bestiario.java` - Pokédex.
- `src/arenaelemental/batalha/Batalha.java` - batalha por turnos e troca.
- `src/arenaelemental/batalha/Captura.java` - cálculo/tentativa de captura.
- `src/arenaelemental/view/PainelBatalha.java` - interface de batalha e escolha do Pokémon.
- `test/` - testes JUnit 5.
