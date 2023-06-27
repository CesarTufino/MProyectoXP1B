package epn.gr6.modelo.persistencia;

import epn.gr6.modelo.logica.Alquiler;
import epn.gr6.modelo.logica.Cliente;
import epn.gr6.modelo.logica.Ejemplar;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;

public class PersistenciaEjemplar {
    public static Ejemplar consultarEjemplar(String codigo) {
        Session session = HibernateUtil.getSession();
        session.beginTransaction();
        Ejemplar ejemplar = session.get(Ejemplar.class, codigo);
        session.close();
        return ejemplar;
    }

    public static List<Ejemplar> consultarEjemplares() {
        Session session = HibernateUtil.getSession();
        session.beginTransaction();
        String hql = "FROM Ejemplar";
        Query query = session.createQuery(hql);
        List<Ejemplar> ejemplares = query.list();
        session.getTransaction().commit();
        session.close();
        return ejemplares;
    }

    public static void actualizarEjemplar(Ejemplar ejemplar) {
        Session session = HibernateUtil.getSession();
        session.beginTransaction();
        session.update(ejemplar);
        session.getTransaction().commit();
        session.close();
    }
}
