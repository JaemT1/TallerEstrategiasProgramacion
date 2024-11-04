import java.util.HashMap;
import java.util.Map;

public class MainDinamico {
    static int nk = 5;

    public static void main(String[] args){
        int n = 5;
        int[] datos = new int[n+1];
        Map<Integer, Integer> memo = new HashMap<>();
        System.out.println(lucasTabulado(n, datos));
        System.out.println(lucasMemorizado(n, memo));
        printMatrix(coeficientesTabulados(10));

        printMatrix(misterio(nk,nk));
        System.out.println("Matriz parcial");
        printMatrix(coeficientesTabuladosParcial(nk));
    }

    public static int lucasTabulado(int n, int[] datos){
        datos[0]=2;
        if(n==0){
            return datos[0];
        }
        datos[1] = 1;

        for (int i=2; i <= n; i++){
            datos[i] = datos[i-1] + datos[i-2];
        }

        return datos[n];
    }

    public static int lucasMemorizado(int n, Map<Integer, Integer> memo){
        if(n==0){
            return 2;
        }
        if(n==1){
            return n;
        }

        if (memo.containsKey(n)) {
            return memo.get(n);
        } else {
            int fibValue = lucasMemorizado(n - 1, memo) + lucasMemorizado(n - 2, memo);
            memo.put(n, fibValue);
            return fibValue;
        }

    }

    public static int[][] coeficientesTabulados(int n){
        int[][] coeficientes = new int[n + 1][n + 1];
        for (int i = 0; i <= n; i++) {
            coeficientes[i][0] = 4;
            coeficientes[i][i] = 4;
        }
        for (int i = 2; i <= n; i++) {
            for (int j = 1; j < i; j++) {
                coeficientes[i][j] = coeficientes[i][j - 1] + coeficientes[i - 1][j];
            }
        }
        return coeficientes;
    }

    public static int coeficientesMemorizados(int n, int k, int[][]coeficientes){

        if(coeficientes[n][k] != 0){
            return coeficientes[n][k];
        }
        if((k==0 || n==k)){
            coeficientes[n][k] = 4;
            return coeficientes[n][k];
        }
        coeficientes[n][k] = coeficientesMemorizados(n, k - 1, coeficientes) + coeficientesMemorizados(n - 1, k, coeficientes);
        return coeficientes[n][k];


    }

    public static int[][] misterio(int n, int k){
        int[][] coeficientes = new int[nk + 1][nk + 1];
        for(int i = 0; i<= n; i++){
            for(int j = 0; j <= Math.min(i,k);j++){
                coeficientesMemorizados(i,j,coeficientes);
            }
        }
        return coeficientes;
    }

    public static void printMatrix(int[][] matrix) {
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                System.out.print("["+matrix[i][j]+"]" + "\t");
            }
            System.out.println();
        }
    }

    //Punto 4 del parcial del semestre pasado
    //Relación de Recurrencia: (n,k) = (n-1,k-1) + 4 * (n, k - 1) 0<k<n ; k=n n^k ; k=0 3*n
    public static int[][] coeficientesTabuladosParcial(int n){
        int[][] coeficientes = new int[n + 1][n + 1];
        for (int i = 0; i <= n; i++) {
            if(i == 0){
                coeficientes[i][0] = 0;
            }else{
                coeficientes[i][0] = 3 * i;
                coeficientes[i][i] = (int)Math.pow(i,i);
            }

        }
        for (int i = 2; i <= n; i++) {
            for (int j = 1; j < i; j++) {
                coeficientes[i][j] = coeficientes[i-1][j - 1] + 4 * coeficientes[i][j-1];
            }
        }
        return coeficientes;
    }
}
