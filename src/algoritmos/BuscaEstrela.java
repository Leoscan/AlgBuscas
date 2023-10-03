package algoritmos;

import java.util.*;
import java.util.concurrent.TimeUnit;

public class BuscaEstrela extends Algoritmo {

    @Override
    public int[][] Busca(int[][] array, int[][] resultado) {
        PriorityQueue<Estado> filaPrioridade = new PriorityQueue<>(new ComparadorEstado());
        Set<String> visitados = new HashSet<>();
        Map<String, int[][]> parent = new HashMap<>();

        int nodosExplorados = 0; // Variável para contar o número de nós explorados.
        long startTime = System.nanoTime(); // Variável para registrar o tempo de início.

        Estado estadoAtual = new Estado(array, 0, calcularHeuristica(array));
        filaPrioridade.add(estadoAtual);

        while (!filaPrioridade.isEmpty()) {
            estadoAtual = filaPrioridade.poll();
            nodosExplorados++; // Incrementa o número de nós explorados.

            if (Arrays.deepEquals(estadoAtual.estado, resultado)) {
                // Constrói a lista de passos da solução retrocedendo dos estados finais para o estado inicial
                List<int[][]> solutionSteps = new ArrayList<>();
                solutionSteps.add(estadoAtual.estado);
                String stateString = Arrays.deepToString(estadoAtual.estado);

                while (parent.containsKey(stateString)) {
                    int[][] parentState = parent.get(stateString);
                    solutionSteps.add(parentState);
                    stateString = Arrays.deepToString(parentState);
                }

                Collections.reverse(solutionSteps);

                // Chame o método para escrever a solução em um arquivo HTML
                Util.writeSolutionToHTML(solutionSteps, "Solucao_BuscaEstrela.html");

                long endTime = System.nanoTime(); // Registra o tempo de término.
                long duration = TimeUnit.NANOSECONDS.toMillis(endTime - startTime); // Calcula a duração em milissegundos.
                System.out.println("Número de nós explorados: " + nodosExplorados);
                System.out.println("Tempo de busca: " + duration + " ms");
                return estadoAtual.estado;
            }

            visitados.add(Arrays.deepToString(estadoAtual.estado));

            for (int[][] sucessor : gerarSucessores(estadoAtual.estado)) {
                if (!visitados.contains(Arrays.deepToString(sucessor))) {
                    int g = estadoAtual.g + 1;
                    int h = calcularHeuristica(sucessor);

                    Estado novoEstado = new Estado(sucessor, g, h);
                    filaPrioridade.add(novoEstado);

                    parent.put(Arrays.deepToString(sucessor), estadoAtual.estado);
                }
            }
        }

        return null; // Solução não encontrada
    }
    private static int[][][] gerarSucessores(int[][] estado) {
        List<int[][]> sucessores = new ArrayList<>();
        int[] posicaoVazia = encontrarPosicaoVazia(estado);

        // Possíveis movimentos: cima, baixo, esquerda, direita
        int[][] movimentos = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};

        for (int[] movimento : movimentos) {
            int novaLinha = posicaoVazia[0] + movimento[0];
            int novaColuna = posicaoVazia[1] + movimento[1];

            if (novaLinha >= 0 && novaLinha < estado.length && novaColuna >= 0 && novaColuna < estado[0].length) {
                int[][] novoEstado = cloneArray(estado);
                novoEstado[posicaoVazia[0]][posicaoVazia[1]] = estado[novaLinha][novaColuna];
                novoEstado[novaLinha][novaColuna] = 0;
                sucessores.add(novoEstado);
            }
        }

        return sucessores.toArray(new int[0][0][0]);
    }

    private static int[][] cloneArray(int[][] array) {
        int[][] clone = new int[array.length][];
        for (int i = 0; i < array.length; i++) {
            clone[i] = array[i].clone();
        }
        return clone;
    }

    private static int[] encontrarPosicaoVazia(int[][] estado) {
        for (int i = 0; i < estado.length; i++) {
            for (int j = 0; j < estado[i].length; j++) {
                if (estado[i][j] == 0) {
                    return new int[]{i, j};
                }
            }
        }
        return null;
    }

    private static int calcularHeuristica(int[][] estado) {
        int heuristica = 0;
        int objetivo = 1;

        for (int i = 0; i < estado.length; i++) {
            for (int j = 0; j < estado[i].length; j++) {
                if (estado[i][j] != objetivo) {
                    heuristica++;
                }
                objetivo++;
            }
        }

        return heuristica;
    }

    private static class Estado {
        int[][] estado;
        int g; // Custo acumulado
        int h; // Heurística

        Estado(int[][] estado, int g, int h) {
            this.estado = estado;
            this.g = g;
            this.h = h;
        }
    }

    private static class ComparadorEstado implements Comparator<Estado> {
        @Override
        public int compare(Estado e1, Estado e2) {
            // Comparação com base em f(x) = g(x) + h(x)
            int f1 = e1.g + e1.h;
            int f2 = e2.g + e2.h;
            return Integer.compare(f1, f2);
        }
    }
}
