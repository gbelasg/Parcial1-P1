package app;
import model.Habitacion;
import model.Hotel;
import model.Huesped;
import model.Reserva;
import javax.swing.JOptionPane;
public class Main {
        public static void main(String[] args) {
            Hotel hotel = new Hotel("StayPlus", "900123456-7", "Calle 10 #20-30, Armenia", "6067450000", 20, 50);
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
            hotel.registrarHuesped(new Huesped(documento, nombre, edad, telefono, ciudad));
            JOptionPane.showMessageDialog(null, "Huesped registrado.");
        }

        public static void registrarHabitacion(Hotel hotel) {
            int numero = leerEntero("Numero de habitacion:");
            String[] tipos = {"Individual", "Doble", "Suite"};
            String tipo = elegirOpcion("Tipo de habitacion:", tipos);
            byte piso = (byte) leerEntero("Piso:");
            byte capacidad = (byte) leerEntero("Capacidad maxima:");
            float precio = leerFloat("Precio por noche:");
            String[] estados = {"Disponible", "Reservada", "Ocupada", "Mantenimiento"};
            String estado = elegirOpcion("Estado de la habitacion:", estados);

            if (hotel.registrarHabitacion(new Habitacion(numero, tipo, piso, capacidad, precio, estado))) {
                JOptionPane.showMessageDialog(null, "Habitacion registrada.");
            } else {
                JOptionPane.showMessageDialog(null, "No hay espacio para mas habitaciones.");
            }
        }

        public static void registrarReserva(Hotel hotel) {
            String telefono = leerTexto("Telefono del huesped que reserva:");
            Huesped huesped = hotel.buscarHuespedPorTelefono(telefono);
            if (huesped == null) {
                JOptionPane.showMessageDialog(null, "El huesped no existe");
            } else {
                crearReserva(hotel, huesped);
            }
        }

        public static void crearReserva(Hotel hotel, Huesped huesped) {
            int codigo = leerEntero("Codigo de reserva:");
            String fecha = leerTexto("Fecha de reserva (dd/mm/aaaa):");
            int noches = leerEntero("Numero de noches:");
            int cantidad = leerEntero("Cantidad de huespedes:");
            String[] estados = {"Pendiente", "Confirmada", "Finalizada"};
            String estado = elegirOpcion("Estado de la reserva:", estados);
            String[] metodos = {"Efectivo", "Tarjeta", "Transferencia"};
            String metodoPago = elegirOpcion("Metodo de pago:", metodos);

            Reserva reserva = new Reserva(codigo, fecha, noches, cantidad, estado, metodoPago, huesped);

            int respuesta;
            do {
                int numeroHabitacion = leerEntero("Numero de habitacion a agregar:");
                Habitacion habitacion = hotel.buscarHabitacion(numeroHabitacion);
                if (habitacion == null) {
                    JOptionPane.showMessageDialog(null, "La habitacion no existe.");
                } else if (!habitacion.estaDisponible()) {
                    JOptionPane.showMessageDialog(null, "La habitacion no esta disponible (" + habitacion.getEstadoActual() + ").");
                } else {
                    reserva.agregarHabitacion(habitacion);
                    JOptionPane.showMessageDialog(null, "Habitacion agregada.");
                }
                respuesta = JOptionPane.showConfirmDialog(null, "Desea agregar otra habitacion?");
            } while (respuesta == JOptionPane.YES_OPTION);

            if (reserva.getListaHabitaciones().size() == 0) {
                JOptionPane.showMessageDialog(null, "La reserva no tiene habitaciones. No se registro.");
            } else if (hotel.registrarReserva(reserva)) {
                JOptionPane.showMessageDialog(null, "Reserva registrada. Valor total: $" + reserva.getValorTotal());
            } else {
                JOptionPane.showMessageDialog(null, "No hay espacio para mas reservas.");
            }
        }

        public static void mostrarDisponibilidad(Hotel hotel) {
            String mensaje = "Habitaciones disponibles: " + hotel.contarHabitacionesPorEstado("Disponible")
                    + "\nHabitaciones reservadas: " + hotel.contarHabitacionesPorEstado("Reservada")
                    + "\nHabitaciones ocupadas: " + hotel.contarHabitacionesPorEstado("Ocupada")
                    + "\nHabitaciones en mantenimiento: " + hotel.contarHabitacionesPorEstado("Mantenimiento");

            Habitacion mayor = hotel.buscarHabitacionMayorPrecio();
            Habitacion menor = hotel.buscarHabitacionMenorPrecio();
            if (mayor != null) {
                mensaje += "\n\nMayor precio: habitacion " + mayor.getNumeroHabitacion() + " - $" + mayor.getPrecioNoche()
                        + "\nMenor precio: habitacion " + menor.getNumeroHabitacion() + " - $" + menor.getPrecioNoche();
            }
            JOptionPane.showMessageDialog(null, mensaje);
        }

        public static void registrarOcupacion(Hotel hotel) {
            int numero = leerEntero("Numero de habitacion:");
            int dia = leerEntero("Dia de la semana:\n1. Lunes\n2. Martes\n3. Miercoles\n4. Jueves\n5. Viernes\n6. Sabado\n7. Domingo");
            String[] estados = {"O", "D"};
            String estado = elegirOpcion("Estado ese dia (O = ocupada, D = disponible):", estados);

            if (hotel.registrarOcupacion(numero, dia - 1, estado.charAt(0))) {
                JOptionPane.showMessageDialog(null, "Ocupacion registrada.");
            } else {
                JOptionPane.showMessageDialog(null, "Habitacion o dia no valido.");
            }
        }

        //  Datos de prueba

        public static void cargarDatosDePrueba(Hotel hotel) {
            Huesped h1 = new Huesped("1094000111", "Ana Maria Lopez", (byte) 28, "3001112233", "Armenia");
            Huesped h2 = new Huesped("1094000222", "Carlos Perez", (byte) 35, "3104445566", "Pereira");
            hotel.registrarHuesped(h1);
            hotel.registrarHuesped(h2);

            hotel.registrarHabitacion(new Habitacion((short) 101, "Individual", (byte) 1, (byte) 1, 120000, "Disponible"));
            hotel.registrarHabitacion(new Habitacion((short)102, "Doble", (byte) 1, (byte) 2, 180000, "Disponible"));
            hotel.registrarHabitacion(new Habitacion((short)201, "Suite", (byte) 2, (byte) 4, 350000, "Disponible"));
            hotel.registrarHabitacion(new Habitacion((short)202, "Doble", (byte) 2, (byte) 2, 190000, "Ocupada"));
            hotel.registrarHabitacion(new Habitacion((short)301, "Individual", (byte) 3, (byte) 1, 110000, "Mantenimiento"));

            Reserva r1 = new Reserva(1221, "22/09/2026", 2, 2, "Confirmada", "Tarjeta", h1);
            r1.agregarHabitacion(hotel.buscarHabitacion(102));
            hotel.registrarReserva(r1);

            Reserva r2 = new Reserva(4567, "22/09/2026", 3, 1, "Pendiente", "Efectivo", h2);
            r2.agregarHabitacion(hotel.buscarHabitacion(101));
            hotel.registrarReserva(r2);

            // Ocupacion de ejemplo: 101 O O D, 102 D O O (lunes, martes, miercoles)
            hotel.registrarOcupacion(101, 0, 'O');
            hotel.registrarOcupacion(101, 1, 'O');
            hotel.registrarOcupacion(102, 1, 'O');
            hotel.registrarOcupacion(102, 2, 'O');
        }

        //  Lectura de datos

        public static String leerTexto(String mensaje) {
            String texto = JOptionPane.showInputDialog(mensaje);
            while (texto == null || texto.equals("")) {
                texto = JOptionPane.showInputDialog("Este dato es obligatorio.\n" + mensaje);
            }
            return texto;
        }

        public static int leerEntero(String mensaje) {
            int numero = 0;
            boolean valido = false;
            while (!valido) {
                try {
                    numero = Integer.parseInt(leerTexto(mensaje));
                    valido = true;
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(null, "Debe digitar un numero entero.");
                }
            }
            return numero;
        }

        public static double leerDouble(String mensaje) {
            double numero = 0;
            boolean valido = false;
            while (!valido) {
                try {
                    numero = Double.parseDouble(leerTexto(mensaje));
                    valido = true;
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(null, "Debe digitar un numero.");
                }
            }
            return numero;
        }
    public static float leerFloat(String mensaje) {
        float numero = 0;
        boolean valido = false;
        while (!valido) {
            try {
                numero = Float.parseFloat(leerTexto(mensaje));
                valido = true;
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Debe digitar un numero.");
            }
        }
        return numero;
    }
        // Muestra las opciones como botones y devuelve la que se presiono
        public static String elegirOpcion(String mensaje, String[] opciones) {
            int indice = JOptionPane.showOptionDialog(null, mensaje, "Seleccione",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, opciones, opciones[0]);
            while (indice == -1) {
                indice = JOptionPane.showOptionDialog(null, "Debe seleccionar una opcion.\n" + mensaje, "Seleccione",
                        JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, opciones, opciones[0]);
            }
            return opciones[indice];
        }
    }