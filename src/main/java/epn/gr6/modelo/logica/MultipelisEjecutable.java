package epn.gr6.modelo.logica;


import java.util.ArrayList;
import java.util.List;

public class MultipelisEjecutable {


    public static void main(String[] args) throws Exception {

        //Registrar cliente
        GestorCliente gestorCliente = new GestorCliente();
        //gestorCliente.registrarCliente("5050963166", "Badbunny", "Albuja");

        //Realizar alquiler y devolver
        Cliente cliente = new Cliente();
        GestorEjemplar gestorEjemplar = new GestorEjemplar();
        List<Ejemplar> ejemplares = new ArrayList<>();
        Ejemplar ejemplar = gestorEjemplar.buscarEjemplar("E0004");
        Ejemplar ejemplar2 = gestorEjemplar.buscarEjemplar("E0005");
        ejemplares.add(ejemplar);
        ejemplares.add(ejemplar2);

        cliente = gestorCliente.buscarCliente("5050963166");


        //PRIMERA HISTORIA
        cliente.alquilar(5,ejemplares, cliente);
        //SEGUNDA HISTORIA
        cliente.alquilarFidelidad(5,ejemplares, cliente);
        //TERCERA HISTORIA
        cliente.alquilarTemporada(5, ejemplares,cliente, "D0001");
        //QUINTA HISTORIA
        cliente.devolver(ejemplares, cliente);




    }

}