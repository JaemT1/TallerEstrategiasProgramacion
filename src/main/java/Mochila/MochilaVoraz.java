package Mochila;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class MochilaVoraz {

    List<Objeto> objetos = new ArrayList<>();
    int pesoMaximo = 0;
    double valor = 0;

    public MochilaVoraz(List<Objeto> objetos, int pesoMaximo) {
        this.objetos = objetos;
        this.pesoMaximo = pesoMaximo;
    }

    public void maximizarValor(){
        objetos.sort(Comparator.comparingInt(Objeto::getValor).reversed());
        List<Objeto> objetosMochila = agregarObjetos();
        imprimirResultado(objetosMochila);
    }

    public void minimizarPeso(){
        objetos.sort(Comparator.comparingInt(o -> o.peso));
        List<Objeto> objetosMochila = agregarObjetos();
        imprimirResultado(objetosMochila);
    }

    public void valorPorPeso(){
        objetos.sort(Comparator.comparingInt(Objeto::getValorPorPeso).reversed());
        List<Objeto> objetosMochila = agregarObjetos();
        imprimirResultado(objetosMochila);
    }

    public void imprimirResultado(List<Objeto> objetosSeleccionados){
        System.out.println("Elementos seleccionados");
        for(Objeto objeto : objetosSeleccionados){
            System.out.println("Cantidad seleccionada del objeto " + objeto.getId() + " es: " + objeto.getPorcentaje() );
        }
        System.out.println("Valor total: " + this.valor);
    }

    public List<Objeto> agregarObjetos() {
        // Ordenar por valor en orden descendente
        int pesoAca = pesoMaximo;
        List<Objeto> objetosEnMochila = new ArrayList<>();
        double valorMochila = 0;

        for (Objeto objeto : objetos) {
            if (pesoAca <= 0) break;

            if (objeto.getPeso() * objeto.getCantidad() <= pesoAca) {
                // Agregar todo el objeto si cabe completamente en la mochila
                objeto.setPorcentaje(objeto.getCantidad());
                objetosEnMochila.add(objeto);
                pesoAca -= objeto.getPeso() * objeto.getCantidad();
            } else {
                // Calcular el porcentaje de fracción del objeto que cabe en la mochila
                double porcentaje = (double) pesoAca / objeto.getPeso();
                objeto.setPorcentaje(porcentaje); // Guardar el porcentaje fraccional
                objetosEnMochila.add(objeto);
                pesoAca = 0; // La mochila se llena, no cabe nada más
            }
            valorMochila += objeto.getValor() * objeto.getPorcentaje();
        }

        this.valor = valorMochila;

        return objetosEnMochila;
    }
}
