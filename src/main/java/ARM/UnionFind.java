package ARM;

public class UnionFind {
    private int[] padre;
    private int[] rango;

    public UnionFind(int n) {
        padre = new int[n];
        rango = new int[n];
        for (int i = 0; i < n; i++) {
            padre[i] = i;
            rango[i] = 0;
        }
    }

    public int find(int nodo) {
        if (padre[nodo] != nodo) {
            padre[nodo] = find(padre[nodo]); // Compresión de camino
        }
        return padre[nodo];
    }

    public boolean union(int nodo1, int nodo2) {
        int raiz1 = find(nodo1-1);
        int raiz2 = find(nodo2-1);

        if (raiz1 == raiz2) {
            return false; // Ya están conectados
        }

        if (rango[raiz1] > rango[raiz2]) {
            padre[raiz2] = raiz1;
        } else if (rango[raiz1] < rango[raiz2]) {
            padre[raiz1] = raiz2;
        } else {
            padre[raiz2] = raiz1;
            rango[raiz1]++;
        }
        return true;
    }
}
