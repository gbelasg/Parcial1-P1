package model;

public class Habitacion {

    private int numeroHabitacion;
    private String tipo;          // Individual, Doble, Suite
    private byte piso;
    private byte capacidadMaxima;
    private float precioNoche;
    private String estadoActual;        //Disponible, Reservada, Ocupada, Mantenimiento

    public Habitacion(int numeroHabitacion, String tipo, byte piso, byte capacidadMaxima, float precioNoche, String estadoActual) {
        this.numeroHabitacion = numeroHabitacion;
        this.tipo = tipo;
        this.piso = piso;
        this.capacidadMaxima = capacidadMaxima;
        this.precioNoche = precioNoche;
        this.estadoActual = estadoActual;
    }

    public boolean estaDisponible() {

        return estadoActual.equalsIgnoreCase("Disponible");
    }

    public void cambiarEstado(String nuevoEstado) {

        this.estadoActual = nuevoEstado;
    }

    public int getNumeroHabitacion() {
        return numeroHabitacion;
    }

    public void setNumeroHabitacion(int numeroHabitacion) {
        this.numeroHabitacion = numeroHabitacion;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public byte getPiso() {
        return piso;
    }

    public void setPiso(byte piso) {
        this.piso = piso;
    }

    public byte getCapacidadMaxima() {
        return capacidadMaxima;
    }

    public void setCapacidadMaxima(byte capacidadMaxima) {
        this.capacidadMaxima = capacidadMaxima;
    }

    public float getPrecioNoche() {
        return precioNoche;
    }

    public void setPrecioNoche(float precioNoche) {
        this.precioNoche = precioNoche;
    }

    public String getEstadoActual() {
        return estadoActual;
    }

    public void setEstadoActual(String estadoActual) {
        this.estadoActual = estadoActual;
    }

    @Override
    public String toString() {
        return "Habitacion{" +
                "numeroHabitacion=" + numeroHabitacion +
                ", tipo='" + tipo + '\'' +
                ", piso=" + piso +
                ", capacidadMaxima=" + capacidadMaxima +
                ", precioNoche=" + precioNoche +
                ", estadoActual='" + estadoActual + '\'' +
                '}';
    }
}
