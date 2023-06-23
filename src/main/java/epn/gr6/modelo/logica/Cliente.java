package epn.gr6.modelo.logica;

import java.util.Calendar;

public class Cliente {

    private IBuscadorCodigo iBuscadorCodigo;

    public void alquilar(String[] codigosEjemplares, int i, boolean b, Calendar fechaAlquiler) {


    }

    public int getPuntosPorFidelidad() {
        return 0;
    }


    public Alquiler getUltimoAlquiler() {
        return new Alquiler();
    }

    public void devolver(long l, boolean devolucionconPercance, IBuscadorCodigo iBuscadorCodigo) throws Exception {

        if (!iBuscadorCodigo.verificarExistenciaCodigo(l)) {
            throw new Exception();
        }
    }
}
