package epn.gr6.modelo.logica;

import java.util.List;

public class GestorAlquiler {
    private final GestorCliente gestorCliente;

    public GestorAlquiler(GestorCliente gestorCliente) {
        this.gestorCliente = gestorCliente;
    }

    public Alquiler alquilar(int diasAlquiler, List<Ejemplar> ejemplares, Cliente cliente) {
        double precioTotal = calcularPrecioTotal(ejemplares, diasAlquiler);
        precioTotal = calcularPrecioDescuentoExlusivo(cliente, precioTotal);
        cambiarDisponibilidadEjemplares(ejemplares);
        aumentarPuntosFidelidad(ejemplares, cliente);
        precioTotal = redondearPrecioDosDecimales(precioTotal);
        System.out.println("$" + precioTotal);
        return new Alquiler(diasAlquiler, precioTotal, cliente, ejemplares);
    }

    private static double redondearPrecioDosDecimales(double precioTotal) {
        precioTotal = precioTotal *100;
        precioTotal = Math.round(precioTotal);
        precioTotal = precioTotal /100;
        return precioTotal;
    }

    private double calcularPrecioDescuentoExlusivo(Cliente cliente, double precioTotal) {
        if (verificarPosicionDelCliente(cliente)) {
            precioTotal = precioTotal * (1 - 0.1);
        }
        return precioTotal;
    }

    private static void aumentarPuntosFidelidad(List<Ejemplar> ejemplares, Cliente cliente) {
        for (Ejemplar ignored : ejemplares){
            if (cliente.getPuntosPorFidelidad() < 100) {
                GestorAlquiler.sumarPuntajeAlquiler(cliente);
            } else {
                System.out.println("Cantidad de puntos maximos");
                break;
            }
        }
    }

    public Alquiler alquilarFidelidad(int diasAlquiler, List<Ejemplar> ejemplares, Cliente cliente) {
        double precioTotal = calcularPrecioTotal(ejemplares, diasAlquiler);
        if (verificarPuntosFidelidadSuficientes(cliente.getPuntosPorFidelidad())) {
            precioTotal = GestorAlquiler.aplicarDescuento(precioTotal, cliente);
        }
        precioTotal = calcularPrecioDescuentoExlusivo(cliente, precioTotal);
        cambiarDisponibilidadEjemplares(ejemplares);
        aumentarPuntosFidelidad(ejemplares, cliente);
        precioTotal = redondearPrecioDosDecimales(precioTotal);
        System.out.println("$" + precioTotal);
        return new Alquiler(diasAlquiler, precioTotal, cliente, ejemplares);
    }

    public boolean verificarPosicionDelCliente(Cliente cliente) {
        List<Cliente> clientes = gestorCliente.obtenerClientesMayorPuntaje();
        for (Cliente cliente1 : clientes) {
            if (cliente1.getCedula().equals(cliente.getCedula())) {
                return true;
            }
        }
        return false;
    }

    private boolean verificarPuntosFidelidadSuficientes(int puntosPorFidelidad) {
        return puntosPorFidelidad >= 50;
    }

    public void devolver(List<Ejemplar> ejemplares, Cliente cliente, boolean estado) {
        for (Ejemplar ejemplar : ejemplares) {
            ejemplar.setEstadoDisponibilidad(true);
        }
        if (estado) {
            cliente.setPuntosPorFidelidad(0);
            System.out.println("Los puntos de fidelidad del cliente se han reducido debido a los daños presentados en los ejemplares");
        }
    }



    private void cambiarDisponibilidadEjemplares(List<Ejemplar> ejemplares) {
        for (Ejemplar ejemplar : ejemplares) {
            ejemplar.setEstadoDisponibilidad(false);
        }
    }

    private double calcularPrecioTotal(List<Ejemplar> ejemplares, int diasAlquiler) {
        double precioPorDia = 0;
        for (Ejemplar ejemplar : ejemplares) {
            precioPorDia += ejemplar.getCostoPorDia();
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
        double precioNuevo = precioActual - (precioActual * 0.20);
        cliente.setPuntosPorFidelidad(cliente.getPuntosPorFidelidad() - 50);

        return precioNuevo;
    }
}
