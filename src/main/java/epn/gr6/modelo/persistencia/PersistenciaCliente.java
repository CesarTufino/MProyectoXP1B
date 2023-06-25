package epn.gr6.modelo.persistencia;

import epn.gr6.modelo.logica.Alquiler;
import epn.gr6.modelo.logica.Cliente;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;

public class PersistenciaCliente {

    public static boolean verificarExistenciaCliente(Session session, String cedulaAVerificar){

        Query<Long> query = session.createQuery("SELECT COUNT(*) FROM modelo.Cliente WHERE cedula = :cedulaAVerificar", Long.class);
        query.setParameter("cedulaAVerificar", cedulaAVerificar);

        Long count = query.uniqueResult();

        return count > 0;
    }

    public static void registrarCliente(String cedula, Cliente cliente) {
        Session session = HibernateUtil.getSession();
        session.beginTransaction();
        /*if(PersistenciaCliente.verificarExistenciaCliente(session, cedula)){
            //System.out.println("Si existe");
            session.getTransaction().rollback();
            return;
        }*/
        session.save(cliente);
        session.getTransaction().commit();
        session.close();
    }

    public static void actualizarCliente(Cliente cliente) {
        Session session = HibernateUtil.getSession();
        session.beginTransaction();
        session.update(cliente);
        session.getTransaction().commit();
        session.close();
    }

    public static Cliente consultarCliente(String cedula) {
        Session session = HibernateUtil.getSession();
        session.beginTransaction();
        Cliente cliente = session.get(Cliente.class, cedula);
        session.close();
        return cliente;
    }

    public static List<Cliente> consultarClientes (){
        Session session = HibernateUtil.getSession();
        session.beginTransaction();
        String hql = "FROM Cliente";
        Query query = session.createQuery(hql);
        List<Cliente> clientes = query.list();
        session.getTransaction().commit();
        session.close();
        return clientes;
    }
}
