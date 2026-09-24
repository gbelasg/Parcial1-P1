package model;

import java.util.ArrayList;

public class Huesped {

    private String documento;
    private String nombreCompleto;
    private byte edad;
    private String telefono;
    private String ciudad;

    // Rol: listaReservas
    private ArrayList<Reserva> listaReservas;

    public Huesped(String documento, String nombreCompleto, byte edad, String telefono, String ciudad) {
        this.documento = documento;
        this.nombreCompleto = nombreCompleto;
        this.edad = edad;
        this.telefono = telefono;
        this.ciudad = ciudad;
        this.listaReservas = new ArrayList<>();
    }

    public void agregarReserva(Reserva reserva) {
        listaReservas.add(reserva);
    }

    public String consultarReservas() {
        if (listaReservas.isEmpty()) {
            return "  (no tiene reservas)\n";
        }
        String texto = "";
        for (int i = 0; i < listaReservas.size(); i++) {
            texto += "  " + listaReservas.get(i).toString() + "\n";
        }
        return texto;
    }

    public String getDocumento() {
        return documento;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public byte getEdad() {
        return edad;
    }

    public String getTelefono() {
        return telefono;
    }

    public String getCiudad() {
        return ciudad;
    }

    public ArrayList<Reserva> getListaReservas() {
        return listaReservas;
    }
}
