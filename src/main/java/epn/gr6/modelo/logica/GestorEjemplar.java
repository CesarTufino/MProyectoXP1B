package epn.gr6.modelo.logica;

import epn.gr6.modelo.persistencia.PersistenciaEjemplar;

public class GestorEjemplar {
    public Ejemplar buscarEjemplar(String codigoEjemplar) {
        Ejemplar ejemplar = PersistenciaEjemplar.consultarEjemplar(codigoEjemplar);
        return ejemplar;
    }
}
