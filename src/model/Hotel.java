package model;

import java.util.ArrayList;

public class Hotel {

    private String nombre;
    private String nit;
    private String direccion;
    private String telefono;

    // Rol: listaHuespedes
    private ArrayList<Huesped> listaHuespedes;
    // Rol: arregloHabitaciones
    private Habitacion[] arregloHabitaciones;
    private int cantidadHabitaciones;
    // Rol: arregloReservas
    private Reserva[] arregloReservas;
    private int cantidadReservas;

    private char[][] matrizOcupacion;
    private String[] diasSemana = {"Lunes", "Martes", "Miercoles", "Jueves", "Viernes", "Sabado", "Domingo"};

    public Hotel(String nombre, String nit, String direccion, String telefono,
                 int maxHabitaciones, int maxReservas) {
        this.nombre = nombre;
        this.nit = nit;
        this.direccion = direccion;
        this.telefono = telefono;
        this.listaHuespedes = new ArrayList<>();
        this.arregloHabitaciones = new Habitacion[maxHabitaciones];
        this.cantidadHabitaciones = 0;
        this.arregloReservas = new Reserva[maxReservas];
        this.cantidadReservas = 0;
        this.matrizOcupacion = new char[maxHabitaciones][diasSemana.length];
        // Al inicio todas las habitaciones estan disponibles todos los dias
        for (int i = 0; i < matrizOcupacion.length; i++) {
            for (int j = 0; j < matrizOcupacion[i].length; j++) {
                matrizOcupacion[i][j] = 'D';
            }
        }
    }

    //  Registro 

    public void registrarHuesped(Huesped huesped) {
        listaHuespedes.add(huesped);
    }

    public boolean registrarHabitacion(Habitacion habitacion) {
        if (cantidadHabitaciones >= arregloHabitaciones.length) {
            return false;
        }
        arregloHabitaciones[cantidadHabitaciones] = habitacion;
        cantidadHabitaciones++;
        return true;
    }

    public boolean registrarReserva(Reserva reserva) {
        if (cantidadReservas >= arregloReservas.length) {
            return false;
        }
        arregloReservas[cantidadReservas] = reserva;
        cantidadReservas++;
        reserva.getHuesped().agregarReserva(reserva);
        return true;
    }

    public Habitacion buscarHabitacion(int numero) {
        Habitacion encontrada = null;
        int i = 0;
        while (i < cantidadHabitaciones && encontrada == null) {
            if (arregloHabitaciones[i].getNumero() == numero) {
                encontrada = arregloHabitaciones[i];
            }
            i++;
        }
        return encontrada;
    }

    //  1. Consultar huesped por telefono 

    public Huesped buscarHuespedPorTelefono(String telefono) {
        Huesped encontrado = null;
        int i = 0;
        while (i < listaHuespedes.size() && encontrado == null) {
            if (listaHuespedes.get(i).getTelefono().equals(telefono)) {
                encontrado = listaHuespedes.get(i);
            }
            i++;
        }
        return encontrado;
    }

    public String consultarHuesped(String telefono) {
        Huesped huesped = buscarHuespedPorTelefono(telefono);
        if (huesped == null) {
            return "No existe un huesped con el telefono " + telefono;
        }
        return "Nombre: " + huesped.getNombreCompleto() + "\n"
                + "Documento: " + huesped.getDocumento() + "\n"
                + "Ciudad: " + huesped.getCiudad() + "\n"
                + "Reservas realizadas:\n" + huesped.consultarReservas();
    }

    //  2. Disponibilidad de habitaciones 

    public int contarHabitacionesPorEstado(String estado) {
        int contador = 0;
        for (int i = 0; i < cantidadHabitaciones; i++) {
            if (arregloHabitaciones[i].getEstado().equalsIgnoreCase(estado)) {
                contador++;
            }
        }
        return contador;
    }

    public Habitacion buscarHabitacionMayorPrecio() {
        if (cantidadHabitaciones == 0) {
            return null;
        }
        Habitacion mayor = arregloHabitaciones[0];
        for (int i = 1; i < cantidadHabitaciones; i++) {
            if (arregloHabitaciones[i].getPrecioNoche() > mayor.getPrecioNoche()) {
                mayor = arregloHabitaciones[i];
            }
        }
        return mayor;
    }

    public Habitacion buscarHabitacionMenorPrecio() {
        if (cantidadHabitaciones == 0) {
            return null;
        }
        Habitacion menor = arregloHabitaciones[0];
        for (int i = 1; i < cantidadHabitaciones; i++) {
            if (arregloHabitaciones[i].getPrecioNoche() < menor.getPrecioNoche()) {
                menor = arregloHabitaciones[i];
            }
        }
        return menor;
    }

    //  3. Matriz de ocupacion 

    // dia: 0 = Lunes ... 6 = Domingo. estado: 'O' ocupada, 'D' disponible
    public boolean registrarOcupacion(int numeroHabitacion, int dia, char estado) {
        boolean registrado = false;
        if (dia >= 0 && dia < diasSemana.length) {
            for (int i = 0; i < cantidadHabitaciones; i++) {
                if (arregloHabitaciones[i].getNumero() == numeroHabitacion) {
                    matrizOcupacion[i][dia] = estado;
                    registrado = true;
                }
            }
        }
        return registrado;
    }

    private int contarOcupadasDia(int dia) {
        int contador = 0;
        for (int i = 0; i < cantidadHabitaciones; i++) {
            if (matrizOcupacion[i][dia] == 'O') {
                contador++;
            }
        }
        return contador;
    }

    public String calcularDiaMayorOcupacion() {
        int diaMayor = 0;
        for (int j = 1; j < diasSemana.length; j++) {
            if (contarOcupadasDia(j) > contarOcupadasDia(diaMayor)) {
                diaMayor = j;
            }
        }
        return diasSemana[diaMayor] + " (" + contarOcupadasDia(diaMayor) + " ocupadas)";
    }

    public String calcularDiaMenorOcupacion() {
        int diaMenor = 0;
        for (int j = 1; j < diasSemana.length; j++) {
            if (contarOcupadasDia(j) < contarOcupadasDia(diaMenor)) {
                diaMenor = j;
            }
        }
        return diasSemana[diaMenor] + " (" + contarOcupadasDia(diaMenor) + " ocupadas)";
    }

    public int calcularTotalOcupadasSemana() {
        int total = 0;
        for (int j = 0; j < diasSemana.length; j++) {
            total += contarOcupadasDia(j);
        }
        return total;
    }

    public String mostrarMatrizOcupacion() {
        String texto = "Hab.   L   M   X   J   V   S   D\n";
        for (int i = 0; i < cantidadHabitaciones; i++) {
            texto += arregloHabitaciones[i].getNumero() + "   ";
            for (int j = 0; j < diasSemana.length; j++) {
                texto += matrizOcupacion[i][j] + "   ";
            }
            texto += "\n";
        }
        return texto;
    }

    //  4. Reservas especiales 

    public String consultarReservasEspeciales() {
        String texto = "";
        for (int i = 0; i < cantidadReservas; i++) {
            if (arregloReservas[i].esCapicua()) {
                texto += arregloReservas[i].toString() + "\n";
            }
        }
        if (texto.isEmpty()) {
            return "No hay reservas especiales.";
        }
        return texto;
    }

    //  5. Ingresos por fecha 

    public double calcularIngresosPorFecha(String fecha) {
        double total = 0;
        for (int i = 0; i < cantidadReservas; i++) {
            if (arregloReservas[i].getFechaReserva().equals(fecha)) {
                total += arregloReservas[i].getValorTotal();
            }
        }
        return total;
    }

    //  Getters 

    public String getNombre() {
        return nombre;
    }

    public String getNit() {
        return nit;
    }

    public String getDireccion() {
        return direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public ArrayList<Huesped> getListaHuespedes() {
        return listaHuespedes;
    }

    public int getCantidadHabitaciones() {
        return cantidadHabitaciones;
    }

    public Habitacion[] getArregloHabitaciones() {
        return arregloHabitaciones;
    }
}
