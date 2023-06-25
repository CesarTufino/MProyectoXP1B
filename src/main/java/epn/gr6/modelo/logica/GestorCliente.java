package epn.gr6.modelo.logica;

import epn.gr6.modelo.persistencia.PersistenciaCliente;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class GestorCliente {
    //Cliente con mayor puntaje
    public static List<Cliente> obtenerClienteMayorPuntaje(){
        List<Cliente> clientes = PersistenciaCliente.consultarClientes();
        Collections.sort(clientes, new Comparador());
        for (int i=3; i<clientes.size();i++){
                clientes.remove(i);
        }
        return clientes;
    }

    public void registrarCliente(String cedula, String nombre, String apellido) {
        if (buscarCliente(cedula)==null){
            Cliente cliente = new Cliente(cedula, nombre, apellido);
            PersistenciaCliente.registrarCliente(cedula, cliente);
        }
    }

    public Cliente buscarCliente(String cedula) {
        Cliente cliente = PersistenciaCliente.consultarCliente(cedula);
        return cliente;
    }
}


