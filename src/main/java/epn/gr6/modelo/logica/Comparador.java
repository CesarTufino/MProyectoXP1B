package epn.gr6.modelo.logica;
import java.util.Comparator;
public class Comparador implements Comparator<Cliente> {
    @Override
    public int compare(Cliente o1, Cliente o2) {
        double puntaje1 = o1.getPuntosPorFidelidad();
        double puntaje2 = o2.getPuntosPorFidelidad();
        return (puntaje2-puntaje1 > 0) ? 1:-1;

    }
}
