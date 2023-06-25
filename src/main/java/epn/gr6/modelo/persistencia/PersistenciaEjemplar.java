package epn.gr6.modelo.persistencia;

import epn.gr6.modelo.logica.Alquiler;
import epn.gr6.modelo.logica.Cliente;
import epn.gr6.modelo.logica.Ejemplar;
import org.hibernate.Session;
public class PersistenciaEjemplar {
    public static Ejemplar consultarEjemplar(String codigo) {
        Session session = HibernateUtil.getSession();
        session.beginTransaction();
        Ejemplar ejemplar = session.get(Ejemplar.class, codigo);
        session.close();
        return ejemplar;
    }

    public static void actualizarEjemplar(Ejemplar ejemplar) {
        Session session = HibernateUtil.getSession();
        session.beginTransaction();
        session.update(ejemplar);
        session.getTransaction().commit();
        session.close();
    }
}
