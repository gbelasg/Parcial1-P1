package model;

public class Habitacion {

    private int numero;
    private String tipo;          // "Individual", "Doble", "Suite"
    private byte piso;
    private byte capacidadMaxima;
    private double precioNoche;
    private String estado;        // "Disponible", "Reservada", "Ocupada", "Mantenimiento"

    public Habitacion(int numero, String tipo, byte piso, byte capacidadMaxima, double precioNoche, String estado) {
        this.numero = numero;
        this.tipo = tipo;
        this.piso = piso;
        this.capacidadMaxima = capacidadMaxima;
        this.precioNoche = precioNoche;
        this.estado = estado;
    }

    public boolean estaDisponible() {
        return estado.equalsIgnoreCase("Disponible");
    }

    public void cambiarEstado(String nuevoEstado) {
        this.estado = nuevoEstado;
    }

    public int getNumero() {
        return numero;
    }

    public String getTipo() {
        return tipo;
    }

    public byte getPiso() {
        return piso;
    }

    public byte getCapacidadMaxima() {
        return capacidadMaxima;
    }

    public double getPrecioNoche() {
        return precioNoche;
    }

    public String getEstado() {
        return estado;
    }

    @Override
    public String toString() {
        return "model.Habitacion " + numero + " - " + tipo + " - piso " + piso
                + " - capacidad " + capacidadMaxima + " - $" + precioNoche + " - " + estado;
    }
}
