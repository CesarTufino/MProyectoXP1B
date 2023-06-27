package epn.gr6.modelo.logica;

import epn.gr6.modelo.persistencia.PersistenciaCliente;

import java.util.Collections;
import java.util.List;


public class GestorCliente {
    private static List<Cliente> clientes;

    public GestorCliente(List<Cliente> clientes) {
        this.clientes = clientes;
    }

    public static List<Cliente> obtenerClientesMayorPuntaje(){
        Collections.sort(clientes, new Comparador());
        for (int i=3; i<clientes.size();i++){
                clientes.remove(i);
        }
        return clientes;
    }

    public Cliente registrarCliente(String cedula, String nombre, String apellido) {
        if (buscarCliente(cedula)==null){
            Cliente cliente = new Cliente(cedula, nombre, apellido);
            return cliente;
        }
        return null;
    }

    public Cliente buscarCliente(String cedula) {
        Cliente cliente = PersistenciaCliente.consultarCliente(cedula);
        return cliente;
    }
}


