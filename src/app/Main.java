package app;
import javax.swing.JOptionPane;
public class Main {

    public static void main(String[] args) {
        Hotel hotel = new Hotel("StayPlus", "9001234567", "Calle 10 #20-30, Armenia", "6067450000", 20, 50);
        cargarDatosDePrueba(hotel);
        String menu = "Hotel " + hotel.getNombre() + "\n\n"
                + "1. Registrar huesped\n"
                + "2. Registrar habitacion\n"
                + "3. Registrar reserva\n"
                + "4. Consultar huesped por telefono\n"
                + "5. Disponibilidad de habitaciones\n"
                + "6. Registrar ocupacion en la matriz\n"
                + "7. Analisis de la matriz de ocupacion\n"
                + "8. Reservas especiales (capicua)\n"
                + "9. Ingresos por fecha\n"
                + "0. Salir\n\n"
                + "Digite una opcion:";

        int opcion;
        do {
            opcion = leerEntero(menu);

            if (opcion == 1) {
                registrarHuesped(hotel);
            } else if (opcion == 2) {
                registrarHabitacion(hotel);
            } else if (opcion == 3) {
                registrarReserva(hotel);
            } else if (opcion == 4) {
                String telefono = leerTexto("Telefono del huesped:");
                JOptionPane.showMessageDialog(null, hotel.consultarHuesped(telefono));
            } else if (opcion == 5) {
                mostrarDisponibilidad(hotel);
            } else if (opcion == 6) {
                registrarOcupacion(hotel);
            } else if (opcion == 7) {
                String resultado = hotel.mostrarMatrizOcupacion()
                        + "\nDia con mayor ocupacion: " + hotel.calcularDiaMayorOcupacion()
                        + "\nDia con menor ocupacion: " + hotel.calcularDiaMenorOcupacion()
                        + "\nTotal habitaciones ocupadas en la semana: " + hotel.calcularTotalOcupadasSemana();
                JOptionPane.showMessageDialog(null, resultado);
            } else if (opcion == 8) {
                JOptionPane.showMessageDialog(null, hotel.consultarReservasEspeciales());
            } else if (opcion == 9) {
                String fecha = leerTexto("Fecha a consultar (dd/mm/aaaa):");
                JOptionPane.showMessageDialog(null, "Ingresos del " + fecha + ": $" + hotel.calcularIngresosPorFecha(fecha));
            } else if (opcion == 0) {
                JOptionPane.showMessageDialog(null, "Hasta luego.");
            } else {
                JOptionPane.showMessageDialog(null, "Opcion no valida.");
            }
        } while (opcion != 0);
    }

    //  Opciones del menu
    public static void registrarHuesped(Hotel hotel) {
        String documento = leerTexto("Documento:");
        String nombre = leerTexto("Nombre completo:");
        byte edad = (byte) leerEntero("Edad:");
        String telefono = leerTexto("Telefono:");
        String ciudad = leerTexto("Ciudad de procedencia:");
        Huesped huesped = new Huesped(documento, nombre, edad, telefono, ciudad);
        hotel.registrarHuesped(huesped);
        JOptionPane.showMessageDialog(null, "Huesped registrado.");
    }

    public static void registrarHabitacion(Hotel hotel) {
        int numero = leerEntero("Numero habitacion:");
        String tipo = leerTexto("Tipo (Individual/Doble/Suite):");
        byte piso = (byte) leerEntero("Piso:");
        byte capacidad = (byte) leerEntero("Capacidad maxima:");
        double precio = leerDouble("Precio por noche:");
        String estado = leerTexto("Estado (Disponible/Reservada/Ocupada/Mantenimiento):");
        Habitacion habitacion = new Habitacion(numero, tipo, piso, capacidad, precio, estado);
        if (hotel.registrarHabitacion(habitacion)) {
            JOptionPane.showMessageDialog(null, "Habitacion registrada.");
        } else {
            JOptionPane.showMessageDialog(null, "No hay espacio para mas habitaciones.");
        }
    }

    public static void registrarReserva(Hotel hotel) {
        String telefono = leerTexto("Telefono del huesped que reserva:");
        Huesped huesped = hotel.buscarHuespedPorTelefono(telefono);
        if (huesped == null) {
            JOptionPane.showMessageDialog(null, "El huesped no existe.");
        } else {
            crearReserva(hotel, huesped);
        }
    }
    public static void crearReserva(Hotel hotel, Huesped huesped) {

        int codigo = leerEntero("Codigo de reserva:");
        String fecha = leerTexto("Fecha de reserva (dd/mm/aaaa):");
        int noches = leerEntero("Numero de noches:");
        int cantidad = leerEntero("Cantidad de huespedes:");

        String estado = leerTexto(
                "Estado de reserva (Pendiente/Confirmada/Finalizada):"
        );

        String metodoPago = leerTexto(
                "Metodo de pago (Efectivo/Tarjeta/Transferencia):"
        );


        Reserva reserva = new Reserva(codigo, fecha, noches, cantidad, estado, metodoPago, huesped);
        int respuesta;
        do {
            int numeroHabitacion = leerEntero("Numero de habitacion a agregar:");
            Habitacion habitacion = hotel.buscarHabitacion(numeroHabitacion);
            if (habitacion == null) {
                JOptionPane.showMessageDialog(
                        null,
                        "La habitacion no existe."
                );

            } else if (!habitacion.estaDisponible()) {
                JOptionPane.showMessageDialog(null,
                        "La habitacion no esta disponible. Estado: "
                                + habitacion.getEstado());
            } else {
                reserva.agregarHabitacion(habitacion);
                JOptionPane.showMessageDialog(null, "Habitacion agregada.");
            }
            respuesta = JOptionPane.showConfirmDialog(
                    null,
                    "Desea agregar otra habitacion?"
            );

        } while (respuesta == JOptionPane.YES_OPTION);

        if (reserva.getListaHabitaciones().size() == 0) {
            JOptionPane.showMessageDialog(
                    null,
                    "La reserva no tiene habitaciones."
            );
        } else if (hotel.registrarReserva(reserva)) {
            JOptionPane.showMessageDialog(
                    null,
                    "Reserva registrada. Valor total: $"
                            + reserva.getValorTotal()
            );
        } else {
            JOptionPane.showMessageDialog(
                    null,
                    "No hay espacio para mas reservas."
            );
        }
    }
    public static void mostrarDisponibilidad(Hotel hotel) {
        String mensaje =
                "Habitaciones disponibles: "
                        + hotel.contarHabitacionesPorEstado("Disponible")
                        + "\nHabitaciones reservadas: "
                        + hotel.contarHabitacionesPorEstado("Reservada")
                        + "\nHabitaciones ocupadas: "
                        + hotel.contarHabitacionesPorEstado("Ocupada")
                        + "\nHabitaciones en mantenimiento: "
                        + hotel.contarHabitacionesPorEstado("Mantenimiento");

        Habitacion mayor = hotel.buscarHabitacionMayorPrecio();
        Habitacion menor = hotel.buscarHabitacionMenorPrecio();

        if (mayor != null) {
            mensaje +=
                    "\n\nHabitacion mayor precio: "
                            + mayor.getNumero()
                            + " - $"
                            + mayor.getPrecioNoche();

            mensaje +=
                    "\nHabitacion menor precio: "
                            + menor.getNumero()
                            + " - $"
                            + menor.getPrecioNoche();
        }
        JOptionPane.showMessageDialog(null, mensaje);
    }

    public static void registrarOcupacion(Hotel hotel) {
        int numero = leerEntero(
                "Numero de habitacion:"
        );
        int dia = leerEntero(
                "Dia de la semana:\n"
                        + "1. Lunes\n"
                        + "2. Martes\n"
                        + "3. Miercoles\n"
                        + "4. Jueves\n"
                        + "5. Viernes\n"
                        + "6. Sabado\n"
                        + "7. Domingo"
        );

        String estado = leerTexto(
                "Estado (O = Ocupada, D = Disponible):"
        );

        if (hotel.registrarOcupacion(
                numero,
                dia - 1,
                estado.charAt(0)
        )) {
            JOptionPane.showMessageDialog(
                    null,
                    "Ocupacion registrada."
            );
        } else {
            JOptionPane.showMessageDialog(
                    null,
                    "Habitacion o dia no valido."
            );
        }
        public static void cargarDatosDePrueba(Hotel hotel) {
            Huesped h1 = new Huesped("1094000111", "Ana Maria Lopez", (byte) 28, "3001112233", "Armenia");
            Huesped h2 = new Huesped("1094000222", "Carlos Perez", (byte) 35, "3104445566", "Pereira");
            hotel.registrarHuesped(h1);
            hotel.registrarHuesped(h2);
            hotel.registrarHabitacion(
                    new Habitacion(101, "Individual", (byte)1, (byte)1, 120000, "Disponible"));
            hotel.registrarHabitacion(
                    new Habitacion(102, "Doble", (byte)1, (byte)2, 180000, "Disponible"));
            hotel.registrarHabitacion(
                    new Habitacion(201, "Suite", (byte)2, (byte)4, 350000, "Disponible"));
            hotel.registrarHabitacion(
                    new Habitacion(202, "Doble", (byte)2, (byte)2, 190000,"Ocupada"));
            hotel.registrarHabitacion(
                    new Habitacion(301, "Individual", (byte)3, (byte)1, 110000, "Mantenimiento"));
            Reserva r1 = new Reserva(1221, "22/09/2026", 2, 2, "Confirmada", "Tarjeta", h1);
            r1.agregarHabitacion(hotel.buscarHabitacion(102));
            hotel.registrarReserva(r1);
            Reserva r2 = new Reserva(4567, "22/09/2026", 3, 1, "Pendiente", "Efectivo", h2);
            r2.agregarHabitacion(hotel.buscarHabitacion(101));
            hotel.registrarReserva(r2);
            // Matriz de ocupacion
            hotel.registrarOcupacion(101,0,'O');
            hotel.registrarOcupacion(101,1,'O');
            hotel.registrarOcupacion(102,1,'O');
            hotel.registrarOcupacion(102,2,'O');

        }
    }
}