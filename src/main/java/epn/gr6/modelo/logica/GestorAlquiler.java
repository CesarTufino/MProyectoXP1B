package epn.gr6.modelo.logica;

import epn.gr6.modelo.persistencia.PersistenciaAlquiler;
import epn.gr6.modelo.persistencia.PersistenciaCliente;
import epn.gr6.modelo.persistencia.PersistenciaEjemplar;

import java.util.List;

public class GestorAlquiler {
    private GestorCliente gestorCliente;

    public GestorAlquiler(GestorCliente gestorCliente) {
        this.gestorCliente = gestorCliente;
    }

    public Alquiler alquilarFidelidad(int diasAlquiler, List<Ejemplar> ejemplares, Cliente cliente) {
        double precioTotal = calcularPrecioTotal(ejemplares, diasAlquiler);
        if (verificarPuntosFidelidad(cliente.getPuntosPorFidelidad())) {
            precioTotal = GestorAlquiler.aplicarDescuento(precioTotal, cliente);
        }
        cambiarDisponibilidadEjemplares(ejemplares);
        if (cliente.getPuntosPorFidelidad() < 100) {
            GestorAlquiler.sumarPuntajeAlquiler(cliente);
        }

        System.out.println(precioTotal);

        return new Alquiler(diasAlquiler, precioTotal, cliente, ejemplares);

    }

    public Alquiler alquilarExclusivo(int diasAlquiler, List<Ejemplar> ejemplares, Cliente cliente) {
        double precioTotal = calcularPrecioTotal(ejemplares, diasAlquiler);
        if (verificarFidelidadCliente(cliente)) {
            precioTotal = precioTotal * (1 - 0.1);
        }
        cambiarDisponibilidadEjemplares(ejemplares);
        if (cliente.getPuntosPorFidelidad() < 100) {
            GestorAlquiler.sumarPuntajeAlquiler(cliente);
        } else {
            //CUARTA HISTORIA
            System.out.println("\n Cantidad de puntos maximos");
        }
        System.out.println(precioTotal);
        return new Alquiler(diasAlquiler, precioTotal, cliente, ejemplares);
    }

    public boolean verificarFidelidadCliente(Cliente cliente) {
        List<Cliente> clientes = gestorCliente.obtenerClientesMayorPuntaje();
        System.out.println(cliente.getCedula());
        for (Cliente cliente1 : clientes) {
            System.out.println(cliente1.getCedula());
            if (cliente1.getCedula().equals(cliente.getCedula())) {
                return true;
            }
        }
        System.out.println("No puede aplicar descuento por temporada");
        return false;
    }

    private boolean verificarPuntosFidelidad(int puntosPorFidelidad) {
        if (puntosPorFidelidad < 50) {
            return false;
        }
        return true;
    }

    public void devolver(List<Ejemplar> ejemplares, Cliente cliente, boolean estado) {
        for (Ejemplar ejemplar : ejemplares) {
            ejemplar.setEstadoDisponibilidad(true);
        }
        if (estado) {
            cliente.setPuntosPorFidelidad(0);
            System.out.println("Los puntos de fidelidad del cliente se han reducido a cero debido a los daños presentados en los ejemplares");
        }
    }

    public Alquiler alquilar(int diasAlquiler, List<Ejemplar> ejemplares, Cliente cliente) {

        double precioTotal = calcularPrecioTotal(ejemplares, diasAlquiler);
        cambiarDisponibilidadEjemplares(ejemplares);
        if (cliente.getPuntosPorFidelidad() < 100) {
            GestorAlquiler.sumarPuntajeAlquiler(cliente);
        } else {
            //CUARTA HISTORIA
            System.out.println("Cantidad de puntos maximos");
        }
        System.out.println(precioTotal);
        return new Alquiler(diasAlquiler, precioTotal, cliente, ejemplares);
    }


    private void cambiarDisponibilidadEjemplares(List<Ejemplar> ejemplares) {
        for (Ejemplar ejemplar : ejemplares) {
            ejemplar.setEstadoDisponibilidad(false);
        }
    }

    private double calcularPrecioTotal(List<Ejemplar> ejemplares, int diasAlquiler) {
        double precioPorDia = 0;
        double precioFinal = 0;
        for (Ejemplar ejemplar : ejemplares) {
            precioPorDia = precioFinal + ejemplar.getCostoPorDia();
        }
        return precioPorDia * diasAlquiler;
    }


    public static void sumarPuntajeAlquiler(Cliente cliente) {
        int puntajeActual = cliente.getPuntosPorFidelidad();
        int puntajeSumado = 5;
        int nuevoPuntaje = puntajeActual + puntajeSumado;
        cliente.setPuntosPorFidelidad(nuevoPuntaje);
    }

    public static double aplicarDescuento(double precioActual, Cliente cliente) {
        double precioNuevo = precioActual - (precioActual * 0.05);
        cliente.setPuntosPorFidelidad(cliente.getPuntosPorFidelidad() - 50);

        return precioNuevo;
    }
}
