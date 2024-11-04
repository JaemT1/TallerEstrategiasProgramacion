package ARM;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ArbolRecubrimientoMinimo {
    private List<Conexion> conexiones;
    private int numeroMunicipios;

    public ArbolRecubrimientoMinimo(int numeroMunicipios) {
        this.numeroMunicipios = numeroMunicipios;
        this.conexiones = new ArrayList<>();
    }

    public ArbolRecubrimientoMinimo(int numeroMunicipios, List<Conexion> conexiones) {
        this.numeroMunicipios = numeroMunicipios;
        this.conexiones = conexiones;
    }

    public void agregarConexion(int municipio1, int municipio2, int costo) {
        conexiones.add(new Conexion(municipio1, municipio2, costo));
    }

    public void encontrarMST() {
        Collections.sort(conexiones, Comparator.comparingInt(c -> c.costo));
        UnionFind unionFind = new UnionFind(numeroMunicipios);

        List<Conexion> mst = new ArrayList<>();
        int costoTotal = 0;

        for (Conexion conexion : conexiones) {
            if (unionFind.union(conexion.municipio1, conexion.municipio2)) {
                mst.add(conexion);
                costoTotal += conexion.costo;
            }
        }

        mostrarResultado(mst, costoTotal);
    }

    private void mostrarResultado(List<Conexion> mst, int costoTotal) {
        System.out.println("Conexiones en el Árbol de Recubrimiento Mínimo:");
        for (Conexion conexion : mst) {
            System.out.println("Municipio " + conexion.municipio1 + " - Municipio " + conexion.municipio2 + " con costo: " + conexion.costo);
        }
        System.out.println("Costo total mínimo: " + costoTotal + " pesos colombianos.");
    }



}