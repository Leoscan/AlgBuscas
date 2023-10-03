package main;
import java.util.*;
import algoritmos.*;
import java.util.Scanner;


public class Main {

	public static void main(String[] args) {
		//int[][] initial = {{5, 4, 2}, {3, 0, 1}, {7, 6, 8}};
		int[][] initial = Util.generateRandomMatrix(3, 3);
        
		Algoritmo a1;
		Busca busca;
		int[][] solution;
		
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Selecione um algoritmo:");
            System.out.println("1 - Busca Horizontal");
            System.out.println("2 - Busca Heurística 1");
            System.out.println("3 - Busca Heurística 2");
            System.out.println("0 - Sair");

            int escolha = scanner.nextInt();

            switch (escolha) {
                case 1:
                    System.out.println("Você escolheu Busca Horizontal.");
                    a1 = new BuscaHorizontal();
                    busca = new Busca(a1);
            		solution = busca.FazBusca(initial);
            		if (solution != null) {
                        for (int[] row : solution) {
                            System.out.println(Arrays.toString(row));
                        }
                    } else {
                        System.out.println("Não foi encontrada uma solução.");
                    }
                    break;
                case 2:
                    System.out.println("Você escolheu Busca Heurística 1.");
                    a1 = new BuscaEstrela();
                    busca = new Busca(a1);
            		solution = busca.FazBusca(initial);
            		if (solution != null) {
                        for (int[] row : solution) {
                            System.out.println(Arrays.toString(row));
                        }
                    } else {
                        System.out.println("Não foi encontrada uma solução.");
                    }
                    break;
                case 3:
                    System.out.println("Você escolheu Busca Heurística 2.");
                    a1 = new BuscaEstrelaV2();
                    busca = new Busca(a1);
            		solution = busca.FazBusca(initial);
            		if (solution != null) {
                        for (int[] row : solution) {
                            System.out.println(Arrays.toString(row));
                        }
                    } else {
                        System.out.println("Não foi encontrada uma solução.");
                    }
                    break;
                case 0:
                    System.out.println("Saindo do programa.");
                    scanner.close();
                    System.exit(0);
                default:
                    System.out.println("Escolha inválida. Tente novamente.");
            }
        }
        
	}
}
