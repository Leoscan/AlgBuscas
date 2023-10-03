package algoritmos;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Util {
    static void writeSolutionToHTML(List<int[][]> steps, String fileName) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            writer.write("<html><head><title>Solução do quebra-cabeça</title></head><body>");

            for (int step = 0; step < steps.size(); step++) {
                writer.write("<h2>Passo " + (step + 1) + ":</h2>");
                int[][] stepState = steps.get(step);

                writer.write("<table border='1'>");
                for (int[] row : stepState) {
                    writer.write("<tr>");
                    for (int value : row) {
                        writer.write("<td><img src='imgs/" + value + ".jpg' width='64' height='64'></td>");
                    }
                    writer.write("</tr>");
                }
                writer.write("</table>");
            }

            writer.write("</body></html>");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static int[][] generateRandomMatrix(int rows, int cols) {
        int totalElements = rows * cols;
        List<Integer> uniqueValues = new ArrayList<>();

        // Preencha a lista com valores únicos
        for (int i = 0; i < totalElements; i++) {
            uniqueValues.add(i);
        }

        Collections.shuffle(uniqueValues); // Embaralhar os valores

        int[][] matrix = new int[rows][cols];
        int index = 0;

        // Preencha a matriz com os valores embaralhados
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                matrix[i][j] = uniqueValues.get(index++);
            }
        }

        return matrix;
    }
}
