package Mochila;

public class Objeto {

    int id = 0;
    int peso = 0;
    int cantidad = 0;
    int valor = 0;
    int valorPorPeso = 0;
    double porcentaje = 0;
    public Objeto(int id, int cantidad, int peso, int valor){
        this.id = id;
        this.cantidad = cantidad;
        this.peso = peso;
        this.valor = valor;
        valorPorPeso = valor/peso;
    }

    public int getId() {
        return id;
    }

    public int getPeso() {
        return peso;
    }

    public int getCantidad() {
        return cantidad;
    }

    public int getValor() {
        return valor;
    }

    public int getValorPorPeso() {
        return valorPorPeso;
    }

    public double getPorcentaje() {
        return porcentaje;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setPeso(int peso) {
        this.peso = peso;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public void setValor(int valor) {
        this.valor = valor;
    }

    public void setValorPorPeso(int valorPorPeso) {
        this.valorPorPeso = valorPorPeso;
    }

    public void setPorcentaje(double porcentaje) {
        this.porcentaje = porcentaje;
    }
}
