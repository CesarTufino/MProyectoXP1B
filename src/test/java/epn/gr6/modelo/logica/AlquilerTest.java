package epn.gr6.modelo.logica;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static org.junit.Assert.*;

public class AlquilerTest {
    private ArrayList<Cliente> listaClientes;
    private GestorCliente gestorCliente;
    private GestorAlquiler gestorAlquiler;
    private List<Ejemplar> ejemplaresAlquilados;
    private List<Ejemplar> ejemplaresDevueltos;

    @Before
    public void setUp(){
        Cliente cliente1 = new Cliente("1752995819","Jhon","Maiza");
        Cliente cliente2 = new Cliente("1752445678","Cesar","Tufiño");
        Cliente cliente3 = new Cliente("1754656345","David","Albuja");
        Cliente cliente4 = new Cliente("1723423857","Ismael","Toaquiza");
        listaClientes = new ArrayList<>();
        listaClientes.add(cliente1);
        listaClientes.add(cliente2);
        listaClientes.add(cliente3);
        listaClientes.add(cliente4);

        gestorCliente = new GestorCliente(listaClientes);

        Pelicula pelicula = new Pelicula("P0001","El Padrino");
        Ejemplar ejemplar1 =new Ejemplar("E0001",true,10,pelicula);

        ejemplaresAlquilados = new ArrayList<>();
        ejemplaresAlquilados.add(ejemplar1);

        ejemplaresDevueltos = new ArrayList<>();
        ejemplaresDevueltos.add(ejemplar1);

        gestorAlquiler = new GestorAlquiler(gestorCliente);
    }

    @Test
    public void given_new_cliente_when_client_rents_then_increase_loyalty_points() {
        Cliente clienteQueAlquila = listaClientes.get(0);
        gestorAlquiler.alquilar(3,ejemplaresAlquilados,clienteQueAlquila);
        assertEquals(5, clienteQueAlquila.getPuntosPorFidelidad());
    }

    @Test
    public void given_loyalty_points_when_client_uses_loyalty_points_then_decrease_loyalty_points_and_give_discount() {
        listaClientes.get(0).setPuntosPorFidelidad(100);
        Cliente clienteQueAlquila = listaClientes.get(0);
        Alquiler alquiler = gestorAlquiler.alquilarFidelidad(1,ejemplaresAlquilados,clienteQueAlquila);
        assertEquals(9.5, alquiler.getPrecioTotal(),0);
        assertEquals(55, clienteQueAlquila.getPuntosPorFidelidad());
    }

    @Test
    public void given_best_client_when_client_rents_in_season_then_give_discount() {
        listaClientes.get(0).setPuntosPorFidelidad(100);
        listaClientes.get(1).setPuntosPorFidelidad(95);
        listaClientes.get(2).setPuntosPorFidelidad(60);
        listaClientes.get(3).setPuntosPorFidelidad(70);
        Cliente clienteQueAlquila = listaClientes.get(3);
        Alquiler alquiler = gestorAlquiler.alquilarExclusivo(1,ejemplaresAlquilados,clienteQueAlquila);
        assertEquals(9, alquiler.getPrecioTotal(),0);
    }


    @Test
    public void given_client_with_max_loyalty_points_when_client_rents_then_not_increase_loyalty_points() {
        listaClientes.get(0).setPuntosPorFidelidad(100);
        Cliente clienteQueAlquila = listaClientes.get(0);
        gestorAlquiler.alquilarExclusivo(1,ejemplaresAlquilados,clienteQueAlquila);
        assertEquals(100, clienteQueAlquila .getPuntosPorFidelidad());
    }

    @Test
    public void given_damaged_copy_when_client_returns_then_decrease_loyalty_points() {
        Cliente clienteQueDevuelve = listaClientes.get(3);
        gestorAlquiler.alquilar(1,ejemplaresAlquilados,clienteQueDevuelve);
        gestorAlquiler.devolver(ejemplaresDevueltos,clienteQueDevuelve,true);
        assertEquals(0, clienteQueDevuelve.getPuntosPorFidelidad());
    }

}