package epn.gr6.modelo.logica;

import epn.gr6.modelo.persistencia.PersistenciaCliente;
import epn.gr6.modelo.persistencia.PersistenciaEjemplar;

public class GestorAlquiler {
    public static void sumarPuntajeAlquiler(Cliente cliente) {
        int puntajeActual = cliente.getPuntosPorFidelidad();
        int puntajeSumado = 5;
        int nuevoPuntaje = puntajeActual + puntajeSumado;
        cliente.setPuntosPorFidelidad(nuevoPuntaje);
        PersistenciaCliente.actualizarCliente(cliente);
    }

    public static double aplicarDescuentoTemporada( double precioTotal, String codigoDescuento) {
        DescuentoTemporada descuentoTemporada = new DescuentoTemporada();
        double descuento = descuentoTemporada.buscarDescuento(codigoDescuento).getPorcentajeDescuento();
        double precioTemporada = precioTotal - (precioTotal*descuento) ;
        return precioTemporada;
    }

    public static double aplicarDescuento(double precioActual, Cliente cliente) {
        double precioNuevo = precioActual - (precioActual*0.05) ;
        cliente.setPuntosPorFidelidad(cliente.getPuntosPorFidelidad()-50);
        PersistenciaCliente.actualizarCliente(cliente);
        return precioNuevo;
    }





}
