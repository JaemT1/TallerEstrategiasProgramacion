
import ARM.ArbolRecubrimientoMinimo;
import ARM.Conexion;
import ARM.UnionFind;
import com.sun.source.tree.NewArrayTree;

import java.sql.SQLOutput;
import java.util.*;

public class Main {
    public static void main (String[] args){
        int[] arr = new int[]{1, 2, 3, 4, 5, 6};
        System.out.println(suma(arr,0, arr.length - 1));
        atm(205000);

        //agregarObjetos(520);

        int[][] objetos = new int[][]{
                {1,2,3,4,5},{1,1,1,1,1},{10,20,30,40,50},{20,30,66,40,60}
        };

        int[][] objetos1 = new int[][]{
                {1, 2, 3, 4, 5},   // ID
                {2, 1, 3, 1, 1},   // Cantidad
                {5, 10, 15, 7, 20}, // Peso
                {30, 40, 25, 50, 70} // Valor
        };

        int[][] objetos2 = new int[][]{
                {1, 2, 3, 4, 5},   // ID
                {1, 2, 1, 3, 2},   // Cantidad
                {6, 12, 8, 15, 10}, // Peso
                {20, 35, 40, 25, 50} // Valor
        };

        //Mochila
        //imprimirArreglo(maximizandoValor(100,objetos));
        exportaciones(50, objetos1);

        int[][] ciudades1 = new int[][] {
                {0, 2, 0, 6, 0},
                {2, 0, 3, 8, 5},
                {0, 3, 0, 0, 7},
                {6, 8, 0, 0, 9},
                {0, 5, 7, 9, 0}
        };



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
                        System.out.println("Se nos acabó la plata socio");
                        hayPlata = false;
                    }else {
                        i++;
                    }
                }
            }else{
                throw new Exception("No le podemos rotar la plata sapo solo múltiplos de 10000");
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

    public static void exportaciones(int pesoMaximo, int[][] objetos){
        System.out.println("Maximizando Valor");
        double[] resultado = maximizandoValor(pesoMaximo, objetos);
        resultado(resultado, objetos);
        System.out.println("Minimizando Peso");
        double[] resultado2 = minimizandoPeso(pesoMaximo, objetos);
        resultado(resultado2, objetos);
        System.out.println("Valor por unidad de Peso");
        double[] resultado3 = valorPorUnidadDePeso(pesoMaximo, objetos);
        resultado(resultado3, objetos);
    }

    public static double[] maximizandoValor(int pesoMaximo, int[][] objetos){
        double[] objetosSeleccionados = new double[objetos[0].length + 1];
        int[] valores = Arrays.copyOf(objetos[3],objetos[0].length);
        while(pesoMaximo>0) {
            int mayor = 0;
            for (int i = 1; i < valores.length; i++) {
                if((valores[i] > valores[mayor]) && objetosSeleccionados[i] == 0){
                        mayor = i;
                }
            }

            if((objetos[2][mayor] * objetos[1][mayor]) < pesoMaximo){
                objetosSeleccionados[mayor] = objetos[1][mayor];
                valores[mayor] = 0;
            }else{
                objetosSeleccionados[mayor] = (double) pesoMaximo /objetos[2][mayor];
            }

            objetosSeleccionados[objetosSeleccionados.length-1] += objetos[3][mayor] * objetosSeleccionados[mayor];

            pesoMaximo -= (int) (objetos[2][mayor] * objetosSeleccionados[mayor]);
        }
        return objetosSeleccionados;
    }

    public static double[] minimizandoPeso(int pesoMaximo, int[][] objetos){
        double[] objetosSeleccionados = new double[objetos[0].length + 1];
        int[] pesos = Arrays.copyOf(objetos[2],objetos[0].length);

        while(pesoMaximo>0) {
            int menor = 0;
            for (int i = 1; i < pesos.length; i++) {
                if(objetosSeleccionados[menor] > 0){
                    menor+=1;
                }else{
                    if((pesos[i] < pesos[menor]) && pesos[i] > 0){
                        menor = i;
                    }
                }
            }

            if((objetos[2][menor] * objetos[1][menor]) < pesoMaximo){
                objetosSeleccionados[menor] = objetos[1][menor];
                pesos[menor] = -1;
            }else{
                objetosSeleccionados[menor] = (double) pesoMaximo / objetos[2][menor];
            }

            objetosSeleccionados[objetosSeleccionados.length-1] += objetos[3][menor] * objetosSeleccionados[menor];

            pesoMaximo -= (int) (objetos[2][menor] * objetosSeleccionados[menor]);
        }
        return objetosSeleccionados;
    }

    public static double[] valorPorUnidadDePeso(int pesoMaximo, int[][] objetos){
        double[] objetosSeleccionados = new double[objetos[0].length + 1];

        double[] valoresPorPeso = hallarValorPorUnidadDePeso(objetos);

        while(pesoMaximo>0) {
            int mayor = 0;
            for (int i = 1; i < valoresPorPeso.length; i++) {
                if((valoresPorPeso[i] > valoresPorPeso[mayor]) && objetosSeleccionados[i] == 0){
                    mayor = i;
                }
            }

            if((objetos[2][mayor] * objetos[1][mayor]) < pesoMaximo){
                objetosSeleccionados[mayor] = objetos[1][mayor];
                valoresPorPeso[mayor] = 0;
            }else{
                objetosSeleccionados[mayor] = (double) pesoMaximo /objetos[2][mayor];
            }

            objetosSeleccionados[objetosSeleccionados.length-1] += objetos[3][mayor] * objetosSeleccionados[mayor];

            pesoMaximo -= (int) (objetos[2][mayor] * objetosSeleccionados[mayor]);
        }
        return objetosSeleccionados;
    }

    private static double[] hallarValorPorUnidadDePeso(int[][] objetos) {
        double[] valorPorPeso = new double[objetos[0].length];
        for (int i = 0; i < objetos[0].length; i++) {
            valorPorPeso[i] = (double) objetos[3][i] / objetos[2][i];
        }
        return valorPorPeso;
    }


    public static void resultado(double[] resultado, int[][] objetos){
        System.out.println("Objetos seleccionados: ");
        for (int i = 0; i <= resultado.length-2; i++) {
            if (resultado[i] != 0) {
                System.out.println(objetos[0][i]);
            }
        }
        System.out.println("Valor total: " + resultado[resultado.length-1]);
    }

    public static void agregarObjetos(int pesoMaximo){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Digite la cantidad de objetos a añadir: ");
        int cantidad = scanner.nextInt();
        int[][] objetos = new int[4][cantidad];
        for(int i = 0; i < cantidad; i++){
            System.out.println("Digite el id del objeto: ");
            int objeto = scanner.nextInt();
            System.out.println("Digite la cantidad disponible del objeto: ");
            int cantObj = scanner.nextInt();
            System.out.println("Digite el peso del objeto: ");
            int peso = scanner.nextInt();
            System.out.println("Digite el valor del objeto: ");
            int valor = scanner.nextInt();
            objetos[0][i] = objeto;
            objetos[1][i] = cantObj;
            objetos[2][i] = peso;
            objetos[3][i] = valor;
        }

        imprimirArreglo(maximizandoValor(pesoMaximo, objetos));
    }

    public static void imprimirArreglo(double[] arreglo){
        for(double num : arreglo){
            System.out.print(num + ", ");
        }
    }

        //Arbol de recubrimiento minimo
    public static void ARM(){
        //Arbol de recubrimiento minimo
        Scanner scanner = new Scanner(System.in);

        System.out.print("Ingrese el número de municipios: ");
        int numeroMunicipios = scanner.nextInt();

        ArbolRecubrimientoMinimo arbol = new ArbolRecubrimientoMinimo(numeroMunicipios);

        System.out.print("Ingrese el número de conexiones posibles: ");
        int numeroConexiones = scanner.nextInt();

        for (int i = 0; i < numeroConexiones; i++) {
            System.out.print("Ingrese el municipio 1 de la conexión " + (i + 1) + ": ");
            int municipio1 = scanner.nextInt();
            System.out.print("Ingrese el municipio 2 de la conexión " + (i + 1) + ": ");
            int municipio2 = scanner.nextInt();
            System.out.print("Ingrese el costo de la conexión " + (i + 1) + " (en pesos colombianos): ");
            int costo = scanner.nextInt();

            arbol.agregarConexion(municipio1, municipio2, costo);
        }

        arbol.encontrarMST();
        scanner.close();
    }

    public static void ARM(List<Conexion> conexiones, int numeroMunicipios){
        ArbolRecubrimientoMinimo arbol = new ArbolRecubrimientoMinimo(numeroMunicipios, conexiones);
        arbol.encontrarMST();
    }

}
