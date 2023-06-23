package epn.gr6.modelo.persistencia;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {
    private static SessionFactory sessionFactory;
    public static void HibernateUtil(){ }
    public static synchronized SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
                Configuration configuration = new Configuration().configure();
                sessionFactory = configuration.buildSessionFactory();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return sessionFactory;
    }

    public static Session getSession (){
        return getSessionFactory().openSession();
    }

    public static void closeSessionFactory() {
        sessionFactory.close();
    }
}

