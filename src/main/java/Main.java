
import ARM.ArbolRecubrimientoMinimo;
import ARM.Conexion;
import ARM.UnionFind;
import Mochila.MochilaVoraz;
import Mochila.Objeto;
import com.sun.source.tree.NewArrayTree;

import java.sql.SQLOutput;
import java.util.*;

public class Main {
    public static void main (String[] args){
        int[] arr = new int[]{1, 2, 3, 4, 5, 6};
        System.out.println(suma(arr,0, arr.length - 1));
        atm(205000);

        //Mochila
        List<Objeto> objetos = new ArrayList<>();

        // Crear los objetos con los datos proporcionados
        objetos.add(new Objeto(1, 2, 5, 30));
        objetos.add(new Objeto(2, 1, 10, 40));
        objetos.add(new Objeto(3, 3, 15, 25));
        objetos.add(new Objeto(4, 1, 7, 50));
        objetos.add(new Objeto(5, 1, 20, 70));

        // Crear instancia de MochilaVoraz con peso máximo de 50
        MochilaVoraz mochila = new MochilaVoraz(objetos, 50);

        // Llamar a maximizarValor
        System.out.println("Maximizando valor:");
        mochila.maximizarValor();

        // Llamar a minimizarPeso
        System.out.println("\nMinimizando peso:");
        mochila.minimizarPeso();

        // Llamar a valorPorPeso
        System.out.println("\nMaximizando valor por peso:");
        mochila.valorPorPeso();

        List<Conexion> conexiones = new ArrayList<>();
        conexiones.add(new Conexion(1,2,1));
        conexiones.add(new Conexion(2,3,2));
        conexiones.add(new Conexion(4,5,3));
        conexiones.add(new Conexion(6,7,3));
        conexiones.add(new Conexion(1,4,4));
        conexiones.add(new Conexion(2,5,4));
        conexiones.add(new Conexion(4,7,4));
        conexiones.add(new Conexion(3,5,5));
        conexiones.add(new Conexion(2,4,6));
        conexiones.add(new Conexion(3,6,6));
        conexiones.add(new Conexion(5,7,7));
        conexiones.add(new Conexion(5,6,8));

        ARM(conexiones, 7);
    }

    //Divide y vencerás
    public static int suma(int[] array, int inicio, int fin){
        int mitad = (inicio + fin )/2;
        if(inicio==fin){
            return array[inicio];
        }else{
            return suma(array, inicio, mitad) + suma(array, mitad+1, fin);
        }
    }
    //Algoritmos Voraces
        //ATM
    public static void atm(int cantidad){
        Map<Integer,Integer> billetes = new HashMap<>();
        Map<Integer,Integer> billetesEntregados = new HashMap<>();
        boolean hayPlata = true;
        billetes.put(10000,3);
        billetes.put(20000,2);
        billetes.put(50000,1);
        billetes.put(100000,5);
        billetesEntregados.put(10000,0);
        billetesEntregados.put(20000,0);
        billetesEntregados.put(50000,0);
        billetesEntregados.put(100000,0);

        int [] denominaciones = new int[]{100000, 50000, 20000, 10000};
        int i = 0;
        try {
            if(cantidad % 10000 == 0){
                while(cantidad>0 && hayPlata){
                    if(cantidad >= denominaciones[i] && billetes.get(denominaciones[i]) >= billetesEntregados.get(denominaciones[i])){
                        cantidad -= denominaciones[i];
                        billetesEntregados.replace(denominaciones[i], billetesEntregados.get(denominaciones[i])+1);
                    }else if(i==3 && billetes.get(denominaciones[i]) < billetesEntregados.get(denominaciones[i])){
                        System.out.println("Se nos acabaron los billetes");
                        hayPlata = false;
                    }else {
                        i++;
                    }
                }
            }else{
                throw new Exception("No le podemos dar la plata, solo valores múltiplos de 10000");
            }
            if(hayPlata) {
                System.out.println("Plata entregada:");
                for (Map.Entry<Integer, Integer> entry : billetesEntregados.entrySet()) {
                    billetes.replace(entry.getKey(), billetes.get(entry.getKey()) - entry.getValue());
                    System.out.println("[Billete] : " + entry.getKey() + " [Cantidad] : " + entry.getValue());
                }
            }
            System.out.println("Plata restante en el cajero:");
            for(Map.Entry<Integer,Integer> entry: billetes.entrySet()){
                System.out.println("[Billete] : " + entry.getKey() + " [Cantidad] : " + entry.getValue());
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void ARM(List<Conexion> conexiones, int numeroMunicipios){
        ArbolRecubrimientoMinimo arbol = new ArbolRecubrimientoMinimo(numeroMunicipios, conexiones);
        arbol.encontrarMST();
    }

}
