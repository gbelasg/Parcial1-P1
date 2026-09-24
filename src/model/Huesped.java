package model;

import java.util.ArrayList;

/**
 * Esta clase representa un huesped del hotel
 * @version 1.0
 * @author Gabriela Sabogal García y Mariana Arias Aristizabal
 * @fecha : 23/09/26
 */

public class Huesped {

    private String documento;
    private String nombreCompleto;
    private byte edad;
    private String telefono;
    private String ciudadPresedencia ;

    //  listaReservas
    private ArrayList<Reserva> listaReservas;

    public Huesped(String documento, String nombreCompleto, byte edad, String telefono, String ciudadPresedencia) {
        this.documento = documento;
        this.nombreCompleto = nombreCompleto;
        this.edad = edad;
        this.telefono = telefono;
        this.ciudadPresedencia = ciudadPresedencia;
        this.listaReservas = new ArrayList<>();
    }

    public void agregarReserva(Reserva reserva) {
        listaReservas.add(reserva); //añadir reserva con el .add
    }

    public String consultarReservas() {
        if (listaReservas==null) {
            return "  (no tiene reservas)\n";
        }
        String mensaje = "";
        for (int i = 0; i < listaReservas.size(); i++) { //size es para listas
            mensaje+= "  " + listaReservas.get(i).toString() + "\n"; //  toString para que aparezca mejor el numero o letra
        }
        return mensaje;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public byte getEdad() {
        return edad;
    }

    public void setEdad(byte edad) {
        this.edad = edad;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCiudad() {
        return ciudadPresedencia;
    }

    public void setCiudad(String ciudad) {
        this.ciudadPresedencia = ciudad;
    }

    public ArrayList<Reserva> getListaReservas() {
        return listaReservas;
    }

    public void setListaReservas(ArrayList<Reserva> listaReservas) {
        this.listaReservas = listaReservas;
    }

    @Override
    public String toString() {
        return "Huesped{" +
                "documento='" + documento + '\'' +
                ", nombreCompleto='" + nombreCompleto + '\'' +
                ", edad=" + edad +
                ", telefono='" + telefono + '\'' +
                ", ciudad='" + ciudadPresedencia + '\'' +
                ", listaReservas=" + listaReservas +
                '}';
    }
}
