package epn.gr6.cli;


import epn.gr6.modelo.logica.*;
import epn.gr6.modelo.persistencia.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MultipelisEjecutable {

    private static final String OPCION_SALIR = "0";
    private static final String OPCION_ALQUILAR = "1";
    private static final String OPCION_DEVOLVER = "2";
    private static final String OPCION_REGISTRAR_CLIENTE = "3";

    private static List<Cliente> clientes;
    private static List<Ejemplar> ejemplares;

    private static List<Alquiler> alquileres;

    private static List<Pelicula> peliculas;
    private static List<DescuentoTemporada> descuentos;


    public static void main(String[] args) throws Exception {
        Logger.getLogger("org.hibernate").setLevel(Level.OFF);
        clientes = PersistenciaCliente.consultarClientes();
        ejemplares = PersistenciaEjemplar.consultarEjemplares();
        peliculas = PersistenciaPelicula.consultarPeliculas();
        alquileres = PersistenciaAlquiler.consultarAlquileres();
        descuentos = PersistenciaDescuentoTemporada.consultarDescuentos();

        Scanner scanner = new Scanner(System.in);
        GestorCliente gestorCliente = new GestorCliente(clientes);
        Cliente cliente = new Cliente();
        GestorEjemplar gestorEjemplar = new GestorEjemplar();




        String opcion;
        do {
            mostrarMenu();
            opcion = scanner.nextLine();

            switch (opcion) {
                case OPCION_REGISTRAR_CLIENTE:
                    registrarCliente(gestorCliente,scanner);
                    break;
                case OPCION_ALQUILAR:
                    realizarAlquiler(gestorCliente, gestorEjemplar, scanner);
                    break;
                case OPCION_DEVOLVER:
                    realizarDevolucion(gestorCliente, gestorEjemplar, scanner);
                    break;
                case OPCION_SALIR:
                    System.out.println("¡Hasta luego!");
                    break;
                default:
                    System.out.println("Opción inválida. Por favor, selecciona una opción válida.");
                    break;
            }
        } while (!opcion.equals(OPCION_SALIR));
    }

    private static void mostrarMenu() {
        System.out.println("======= MENU PRINCIPAL =======");
        //System.out.println("3. Registrar Cliente");
        System.out.println("1. Realizar Alquiler");
        System.out.println("2. Devolver Película");
        System.out.println("0. Salir");
        System.out.println("==============================");
        System.out.print("Selecciona una opción: ");
    }

    private static void registrarCliente(GestorCliente gestorCliente, Scanner scanner) {
        System.out.println("======= REGISTRAR CLIENTE =======");

        System.out.print("Ingrese la cédula de identidad del cliente (10 dígitos): ");
        String cedula = scanner.nextLine();

        System.out.print("Ingrese el nombre del cliente: ");
        String nombre = scanner.nextLine();

        System.out.print("Ingrese el apellido del cliente: ");
        String apellido = scanner.nextLine();

        if (cedula.isEmpty() || nombre.isEmpty() || apellido.isEmpty()) {
            System.out.println("Error: Todos los campos deben ser completados.");
        } else {
            Cliente cliente = gestorCliente.registrarCliente(cedula, nombre, apellido);
            PersistenciaCliente.registrarCliente(cedula, cliente);
            System.out.println("¡Cliente registrado exitosamente!");
        }
    }

    private static void realizarAlquiler(GestorCliente gestorCliente, GestorEjemplar gestorEjemplar, Scanner scanner) {

        GestorAlquiler gestorAlquiler = new GestorAlquiler(gestorCliente);

        System.out.println("======= REALIZAR ALQUILER =======");

        System.out.print("Ingrese la cédula del cliente: ");
        String cedulaCliente = scanner.nextLine();

        Cliente cliente = gestorCliente.buscarCliente(cedulaCliente);
        if (cliente != null) {
            System.out.println("Cliente encontrado:");
            System.out.println("Nombre: " + cliente.getNombre());
            System.out.println("Apellido: " + cliente.getApellido());
            System.out.println("Puntos de Fidelidad: " + cliente.getPuntosPorFidelidad());
            if (gestorAlquiler.verificarPosicionDelCliente(cliente)){
                System.out.println("El cliente pertenece al grupo con mayores puntos de fidelidad");
            }

            List<Ejemplar> ejemplares = new ArrayList<>();
            boolean seguirAgregando = true;

            // Solicitar al menos una vez el código del ejemplar
            do {
                System.out.print("Ingrese el código del ejemplar (o 'q' para finalizar): ");
                String codigoEjemplar = scanner.nextLine();

                if (codigoEjemplar.equalsIgnoreCase("q")) {
                    if (ejemplares.isEmpty()) {
                        System.out.println("Debe ingresar al menos un código de ejemplar.");
                        continue; // Volver a solicitar el código del ejemplar
                    } else {
                        seguirAgregando = false;
                    }
                } else {
                    Ejemplar ejemplar = gestorEjemplar.buscarEjemplar(codigoEjemplar);
                    if (ejemplar != null) {
                        ejemplares.add(ejemplar);
                    } else {
                        System.out.println("Ejemplar no encontrado. Intente nuevamente.");
                    }
                }
            } while (seguirAgregando);

            int opcionAlquiler;
            do {
                System.out.println("======= TIPO DE ALQUILER =======");
                System.out.println("1. Alquiler Normal");
                System.out.println("2. Alquiler por Fidelidad");
                System.out.println("3. Volver al Menú Principal");
                System.out.print("Seleccione una opción: ");
                opcionAlquiler = scanner.nextInt();
                scanner.nextLine(); // Consumir el salto de línea
                Alquiler alquiler;

                switch (opcionAlquiler) {
                    case 1:
                        System.out.print("Ingrese la cantidad de días de alquiler: ");
                        int diasAlquiler = scanner.nextInt();
                        scanner.nextLine(); // Consumir el salto de línea
                        alquiler = gestorAlquiler.alquilar(diasAlquiler, ejemplares, cliente);
                        PersistenciaAlquiler.registrarAlquiler(alquiler);
                        PersistenciaCliente.actualizarCliente(cliente);
                        for(Ejemplar ejemplar: ejemplares){
                            PersistenciaEjemplar.actualizarEjemplar(ejemplar);
                        }
                        System.out.println("Alquiler Normal realizado exitosamente.");
                        System.out.println("Puntos de fidelidad del cliente: " + cliente.getPuntosPorFidelidad());
                        opcionAlquiler = 4; // Salir del submenú y volver al Menú Principal
                        break;
                    case 2:
                        System.out.print("Ingrese la cantidad de días de alquiler: ");
                        diasAlquiler = scanner.nextInt();
                        scanner.nextLine(); // Consumir el salto de línea
                        alquiler = gestorAlquiler.alquilarFidelidad(diasAlquiler, ejemplares, cliente);
                        PersistenciaCliente.actualizarCliente(cliente);
                        for(Ejemplar ejemplar: ejemplares){
                            PersistenciaEjemplar.actualizarEjemplar(ejemplar);
                        }
                        PersistenciaAlquiler.registrarAlquiler(alquiler);
                        System.out.println("Alquiler por Fidelidad realizado exitosamente.");
                        System.out.println("Puntos de fidelidad del cliente: " + cliente.getPuntosPorFidelidad());
                        opcionAlquiler = 4; // Salir del submenú y volver al Menú Principal
                        break;
                        /*
                    case 4:
                        System.out.print("Ingrese la cantidad de días de alquiler: ");
                        diasAlquiler = scanner.nextInt();
                        scanner.nextLine(); // Consumir el salto de línea
                        alquiler = gestorAlquiler.alquilarExclusivo(diasAlquiler, ejemplares, cliente);
                        PersistenciaAlquiler.registrarAlquiler(alquiler);
                        PersistenciaCliente.actualizarCliente(cliente);
                        for(Ejemplar ejemplar: ejemplares){
                            PersistenciaEjemplar.actualizarEjemplar(ejemplar);
                        }
                        System.out.println("Alquiler por Temporada realizado exitosamente.");
                        opcionAlquiler = 4; // Salir del submenú y volver al Menú Principal
                        break;*/
                    case 3:
                        System.out.println("Volviendo al Menú Principal...");
                        break;
                    default:
                        System.out.println("Opción inválida. Vuelva a intentar.");
                        break;
                }
            } while (opcionAlquiler != 4);
        } else {
            System.out.println("Cliente no encontrado. No se puede realizar el alquiler.");
        }

        System.out.println();
    }

    private static void realizarDevolucion(GestorCliente gestorCliente, GestorEjemplar gestorEjemplar, Scanner scanner) {

        GestorAlquiler gestorAlquiler = new GestorAlquiler(gestorCliente);

        System.out.println("======= DEVOLVER PELÍCULA =======");

        System.out.print("Ingrese la cédula del cliente: ");
        String cedulaCliente = scanner.nextLine();

        Cliente cliente = gestorCliente.buscarCliente(cedulaCliente);
        if (cliente != null) {
            System.out.println("Cliente encontrado:");
            System.out.println("Nombre: " + cliente.getNombre());
            System.out.println("Apellido: " + cliente.getApellido());
            System.out.println();

            List<Ejemplar> ejemplares = new ArrayList<>();
            boolean seguirAgregando = true;

            do {
                System.out.print("Ingrese el código del ejemplar a devolver (o 'q' para finalizar): ");
                String codigoEjemplar = scanner.nextLine();

                if (codigoEjemplar.equalsIgnoreCase("q")) {
                    System.out.println("Debe ingresar al menos un código de ejemplar.");
                    seguirAgregando = false;
                } else {
                    Ejemplar ejemplar = gestorEjemplar.buscarEjemplar(codigoEjemplar);
                    if (ejemplar != null) {
                        ejemplares.add(ejemplar);
                    } else {
                        System.out.println("Ejemplar no encontrado. Intente nuevamente.");
                    }
                }
            } while (seguirAgregando || ejemplares.isEmpty());

            if (!ejemplares.isEmpty()) {
                boolean tienePercance;
                boolean continuar;
                do {
                    System.out.print("¿Algún ejemplar tuvo un percance? (s/n): ");
                    String opcion = scanner.nextLine();
                    tienePercance = opcion.equalsIgnoreCase("s");
                    if (!opcion.equalsIgnoreCase("s") && !opcion.equalsIgnoreCase("n")) {
                        continuar = false;
                        System.out.println("Opción inválida. Intente nuevamente.");
                    } else{
                        continuar = true;
                    }
                } while (!continuar);

                gestorAlquiler.devolver(ejemplares, cliente, tienePercance);
                if (tienePercance){
                    System.out.println("Puntos de fidelidad del cliente: " + cliente.getPuntosPorFidelidad());
                }
                PersistenciaCliente.actualizarCliente(cliente);
                for (Ejemplar ejemplar : ejemplares) {
                    PersistenciaEjemplar.actualizarEjemplar(ejemplar);
                }
                System.out.println("Devolución realizada exitosamente.");
            } else {
                System.out.println("No se ingresó ningún código de ejemplar. No se puede realizar la devolución.");
            }
        } else {
            System.out.println("Cliente no encontrado. No se puede realizar la devolución.");
        }

        System.out.println();
    }



}

