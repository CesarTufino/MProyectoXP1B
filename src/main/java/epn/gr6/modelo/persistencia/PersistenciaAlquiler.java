package epn.gr6.modelo.persistencia;

import epn.gr6.modelo.logica.Alquiler;
import epn.gr6.modelo.logica.Ejemplar;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;

public class PersistenciaAlquiler {

    public static void registrarAlquiler(Alquiler alquiler) {
        Session session = HibernateUtil.getSession();
        session.beginTransaction();
        session.save(alquiler);
        session.getTransaction().commit();
        session.close();
    }

    public static Alquiler consultarAlquiler(Long numeroAlquiler) {
        Session session = HibernateUtil.getSession();
        session.beginTransaction();
        Alquiler alquiler = session.get(Alquiler.class, numeroAlquiler);
        session.getTransaction().commit();
        session.close();
        return alquiler;
    }

    public static List<Alquiler> consultarAlquileres() {
        Session session = HibernateUtil.getSession();
        session.beginTransaction();
        String hql = "FROM Alquiler";
        Query query = session.createQuery(hql);
        List<Alquiler> alquileres = query.list();
        session.getTransaction().commit();
        session.close();
        return alquileres;
    }

    public static void actualizarAlquiler(Alquiler alquiler) {
        Session session = HibernateUtil.getSession();
        session.beginTransaction();
        session.update(alquiler);
        session.getTransaction().commit();
        session.close();
    }

    public static Ejemplar consultarEjemplar(String codigo) {
        Session session = HibernateUtil.getSession();
        session.beginTransaction();
        Ejemplar ejemplar = session.get(Ejemplar.class, codigo);
        session.close();
        return ejemplar;
    }

}
