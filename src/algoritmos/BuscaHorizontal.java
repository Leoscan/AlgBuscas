package algoritmos;

import java.util.*;
import java.util.concurrent.TimeUnit;

public class BuscaHorizontal extends Algoritmo {

	public int[][] Busca(int[][] array, int[][] resultado) {
	    Queue<int[][]> queue = new LinkedList<>();
	    Map<String, Integer> visited = new HashMap<>();
	    Map<String, int[][]> parent = new HashMap<>(); // Mapa para rastrear o pai de cada estado.

	    int nodosExplorados = 0; // Variável para contar o número de nós explorados.
	    long startTime = System.nanoTime(); // Variável para registrar o tempo de início.

	    // Inicializa a fila com o estado inicial e o nível 0
	    queue.offer(array);
	    visited.put(Arrays.deepToString(array), 0);

	    int[][] moves = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
	    int[][] goalState = new int[3][3];
	    int goalValue = 1;

	    // Define o estado objetivo
	    for (int i = 0; i < 3; i++) {
	        for (int j = 0; j < 3; j++) {
	            goalState[i][j] = goalValue++;
	        }
	    }
	    goalState[2][2] = 0;

	    while (!queue.isEmpty()) {
	        int[][] current = queue.poll();
	        nodosExplorados++; // Incrementa o número de nós explorados.

	        if (Arrays.deepEquals(current, goalState)) {
	            // Constrói a lista de passos da solução retrocedendo dos estados finais para o estado inicial
	            List<int[][]> solutionSteps = new ArrayList<>();
	            solutionSteps.add(current);
	            String stateString = Arrays.deepToString(current);

	            while (parent.containsKey(stateString)) {
	                int[][] parentState = parent.get(stateString);
	                solutionSteps.add(parentState);
	                stateString = Arrays.deepToString(parentState);
	            }

	            Collections.reverse(solutionSteps);

	            // Chame o método para escrever a solução em um arquivo HTML
	            Util.writeSolutionToHTML(solutionSteps, "Solucao_BuscaHorizontal.html");

	            long endTime = System.nanoTime(); // Registra o tempo de término.
	            long duration = TimeUnit.NANOSECONDS.toMillis(endTime - startTime); // Calcula a duração em milissegundos.
	            System.out.println("Número de nós explorados: " + nodosExplorados);
	            System.out.println("Tempo de busca: " + duration + " ms");
	            return current;
	        }

	        int[] zeroPos = findZero(current);

	        for (int[] move : moves) {
	            int newRow = zeroPos[0] + move[0];
	            int newCol = zeroPos[1] + move[1];

	            if (isValid(newRow, newCol)) {
	                int[][] newState = cloneArray(current);
	                newState[zeroPos[0]][zeroPos[1]] = current[newRow][newCol];
	                newState[newRow][newCol] = 0;

	                String stateString = Arrays.deepToString(newState);

	                if (!visited.containsKey(stateString)) {
	                    queue.offer(newState);
	                    visited.put(stateString, visited.get(Arrays.deepToString(current)) + 1);
	                    parent.put(stateString, current); // Registra o pai deste novo estado.
	                }
	            }
	        }
	    }

	    return null; // Solução não encontrada
	}

     private static int[] findZero(int[][] puzzle) {
        for (int i = 0; i < puzzle.length; i++) {
            for (int j = 0; j < puzzle[0].length; j++) {
                if (puzzle[i][j] == 0) {
                    return new int[]{i, j};
                }
            }
        }
        return null;
    }

    private static boolean isValid(int row, int col) {
        return row >= 0 && row < 3 && col >= 0 && col < 3;
    }

    private static int[][] cloneArray(int[][] array) {
        int[][] clone = new int[array.length][];
        for (int i = 0; i < array.length; i++) {
            clone[i] = array[i].clone();
        }
        return clone;
    }
    
}
