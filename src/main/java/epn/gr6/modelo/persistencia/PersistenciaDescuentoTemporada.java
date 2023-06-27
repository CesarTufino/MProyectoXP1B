package epn.gr6.modelo.persistencia;


import epn.gr6.modelo.logica.DescuentoTemporada;

import epn.gr6.modelo.logica.Ejemplar;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;

public class PersistenciaDescuentoTemporada {
    public static DescuentoTemporada consultarDescuento(String codigo) {
        Session session = HibernateUtil.getSession();
        session.beginTransaction();
        DescuentoTemporada descuentoTemporada = session.get(DescuentoTemporada.class, codigo);
        session.close();
        return descuentoTemporada;
    }

    public static List<DescuentoTemporada> consultarDescuentos() {
        Session session = HibernateUtil.getSession();
        session.beginTransaction();
        String hql = "FROM DescuentoTemporada";
        Query query = session.createQuery(hql);
        List<DescuentoTemporada> descuentos = query.list();
        session.getTransaction().commit();
        session.close();
        return descuentos;
    }

}