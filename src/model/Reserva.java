package model;

import java.util.ArrayList;

public class Reserva {

    private int codigo;
    private String fechaReserva;      // formato "dd/mm/aaaa"
    private int numeroNoches;
    private int cantidadHuespedes;
    private String estado;            // "Pendiente", "Confirmada", "Finalizada"
    private String metodoPago;        // "Efectivo", "Tarjeta", "Transferencia"
    private double valorTotal;

    // Rol: huesped
    private Huesped huesped;
    // Rol: listaHabitaciones
    private ArrayList<Habitacion> listaHabitaciones;

    public Reserva(int codigo, String fechaReserva, int numeroNoches, int cantidadHuespedes,
                   String estado, String metodoPago, Huesped huesped) {
        this.codigo = codigo;
        this.fechaReserva = fechaReserva;
        this.numeroNoches = numeroNoches;
        this.cantidadHuespedes = cantidadHuespedes;
        this.estado = estado;
        this.metodoPago = metodoPago;
        this.huesped = huesped;
        this.listaHabitaciones = new ArrayList<>();
        this.valorTotal = 0;
    }

    public void agregarHabitacion(Habitacion habitacion) {
        listaHabitaciones.add(habitacion);
        // Si la reserva esta confirmada, la habitacion deja de estar disponible
        if (estado.equalsIgnoreCase("Confirmada")) {
            habitacion.cambiarEstado("Reservada");
        }
        calcularValorTotal();
    }

    // valorTotal = (suma de precios por noche de las habitaciones) * numeroNoches
    public double calcularValorTotal() {
        double sumaPrecios = 0;
        for (int i = 0; i < listaHabitaciones.size(); i++) {
            sumaPrecios += listaHabitaciones.get(i).getPrecioNoche();
        }
        valorTotal = sumaPrecios * numeroNoches;
        return valorTotal;
    }

    // Un codigo es capicua si al invertirlo queda el mismo numero
    public boolean esCapicua() {
        int original = codigo;
        int invertido = 0;
        int aux = codigo;
        while (aux > 0) {
            int digito = aux % 10;
            invertido = invertido * 10 + digito;
            aux = aux / 10;
        }
        return original == invertido;
    }

    public int getCodigo() {
        return codigo;
    }

    public String getFechaReserva() {
        return fechaReserva;
    }

    public int getNumeroNoches() {
        return numeroNoches;
    }

    public int getCantidadHuespedes() {
        return cantidadHuespedes;
    }

    public String getEstado() {
        return estado;
    }

    public String getMetodoPago() {
        return metodoPago;
    }

    public double getValorTotal() {
        return valorTotal;
    }

    public Huesped getHuesped() {
        return huesped;
    }

    public ArrayList<Habitacion> getListaHabitaciones() {
        return listaHabitaciones;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public void setFechaReserva(String fechaReserva) {
        this.fechaReserva = fechaReserva;
    }

    public void setNumeroNoches(int numeroNoches) {
        this.numeroNoches = numeroNoches;
    }

    public void setCantidadHuespedes(int cantidadHuespedes) {
        this.cantidadHuespedes = cantidadHuespedes;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public void setMetodoPago(String metodoPago) {
        this.metodoPago = metodoPago;
    }

    public void setValorTotal(double valorTotal) {
        this.valorTotal = valorTotal;
    }

    public void setHuesped(Huesped huesped) {
        this.huesped = huesped;
    }

    public void setListaHabitaciones(ArrayList<Habitacion> listaHabitaciones) {
        this.listaHabitaciones = listaHabitaciones;
    }

    @Override
    public String toString() {
        String habitaciones = "";
        for (int i = 0; i < listaHabitaciones.size(); i++) {
            habitaciones += listaHabitaciones.get(i).getNumeroHabitacion() + " ";
        }
        return "model.Reserva " + codigo + " | fecha " + fechaReserva + " | " + numeroNoches + " noches | "
                + cantidadHuespedes + " huespedes | " + estado + " | " + metodoPago
                + " | habitaciones: " + habitaciones + "| total $" + valorTotal;
    }
}
