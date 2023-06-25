package epn.gr6.modelo.persistencia;


import epn.gr6.modelo.logica.DescuentoTemporada;

import org.hibernate.Session;

public class PersistenciaDescuentoTemporada {
    public static DescuentoTemporada consultarDescuento(String codigo) {
        Session session = HibernateUtil.getSession();
        session.beginTransaction();
        DescuentoTemporada descuentoTemporada = session.get(DescuentoTemporada.class, codigo);
        session.close();
        return descuentoTemporada;
    }

}