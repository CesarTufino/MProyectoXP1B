package epn.gr6.modelo.logica;

import org.junit.Test;
import org.mockito.Mockito;

import java.util.Calendar;

import static org.junit.Assert.*;

public class AlquilerTest {

    @Test
    public void given_new_cliente_when_client_rents_then_increase_loyalty_points() {
        Cliente cliente = new Cliente();
        String[] codigosEjemplares = {"E0001","E0002","E0003"};

        cliente.alquilar(codigosEjemplares,3,false, Calendar.getInstance());
        assertEquals(15, cliente.getPuntosPorFidelidad());
    }

    @Test
    public void given_loyalty_points_when_client_uses_loyalty_points_then_decrease_loyalty_points_and_give_discount() {
        boolean seUsanPuntosDeFidelidad = true;
        Cliente cliente = new Cliente();
        Calendar fechaAlquiler = Calendar.getInstance();
        fechaAlquiler.set(Calendar.YEAR, 2023);
        fechaAlquiler.set(Calendar.MONTH, Calendar.OCTOBER);
        fechaAlquiler.set(Calendar.DAY_OF_MONTH, 5);
        String[] codigosEjemplares = {"E0001","E0002","E0003"};

        cliente.alquilar(codigosEjemplares,3,seUsanPuntosDeFidelidad, fechaAlquiler);
        Alquiler alquiler = cliente.getUltimoAlquiler();
        assertEquals(10, alquiler.getPrecioTotal());
        assertEquals(30,cliente.getPuntosPorFidelidad());
    }

    @Test
    public void given_best_client_when_client_rents_in_season_then_give_discount() {
        Calendar fechaAlquiler = Calendar.getInstance();
        fechaAlquiler.set(Calendar.YEAR, 2023);
        fechaAlquiler.set(Calendar.MONTH, Calendar.OCTOBER);
        fechaAlquiler.set(Calendar.DAY_OF_MONTH, 31);
        Cliente cliente = new Cliente();
        String[] codigosEjemplares = {"E0001","E0002","E0003"};

        cliente.alquilar(codigosEjemplares,3,false,fechaAlquiler);
        Alquiler alquiler = cliente.getUltimoAlquiler();
        assertEquals(11, alquiler.getPrecioTotal());
    }


    @Test
    public void given_client_with_max_loyalty_points_when_client_rents_then_not_increase_loyalty_points() {
        Cliente cliente = new Cliente();
        String[] codigosEjemplares = {"E0001","E0002","E0003"};
        cliente.alquilar(codigosEjemplares,3,false, Calendar.getInstance());
        assertEquals(100, cliente.getPuntosPorFidelidad());
    }

    @Test
    public void given_damaged_copy_when_client_returns_then_decrease_loyalty_points() throws Exception {
        boolean devolucionConPercance = true;
        Cliente cliente = new Cliente();
        IBuscadorCodigo iBuscadorCodigo = Mockito.mock(IBuscadorCodigo.class);
        Mockito.when(iBuscadorCodigo.verificarExistenciaCodigo(1L)).thenReturn(true);

        cliente.devolver(1L,devolucionConPercance, iBuscadorCodigo);
        assertEquals(100, cliente.getPuntosPorFidelidad());
    }


}