package algoritmos;


public class Busca {
	private Algoritmo algoritmo;
	private int[][] resultado = {{1, 2, 3}, {4, 5, 6}, {7, 8, 0}};
	
	public Busca(Algoritmo algoritmo) {
		this.algoritmo = algoritmo;
	}
	
	public int[][] FazBusca(int[][] array) {
		return algoritmo.Busca(array, resultado);
	}
}
