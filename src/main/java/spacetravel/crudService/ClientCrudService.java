package spacetravel.crudService;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import spacetravel.entity.Client;
import spacetravel.entity.Planet;
import spacetravel.util.HibernateUtil;

public class ClientCrudService implements CrudService<Client, Long> {
    @Override
    public Long create(Client value) {
        Long  result;
        try (Session session = HibernateUtil.buildSessionFactory().openSession()){
            Transaction transaction = session.beginTransaction();
            session.persist(value);
            transaction.commit();

            Transaction transaction1 = session.beginTransaction();
            result = value.getId();
            transaction1.commit();
            return result;
        }
    }

    @Override
    public Client read(Long id) {
        Client result;
        try (Session session = HibernateUtil.buildSessionFactory().openSession()){
            Transaction transaction1 = session.beginTransaction();
            Query<Client> query = session.createQuery("from Client where id = : id", Client.class);
            result = query.setParameter("id", id).getSingleResult();
            transaction1.commit();
            return result;
        }
    }

    @Override
    public Client update(Client value) {
        Client resultUpdate;
        try (Session session = HibernateUtil.buildSessionFactory().openSession()){
            Transaction transaction = session.beginTransaction();
            resultUpdate = session.merge(value);
            transaction.commit();
        }
        return resultUpdate;
    }

    @Override
    public boolean delete(Long id) {
        try (Session session = HibernateUtil.buildSessionFactory().openSession()){
            Transaction transaction = session.beginTransaction();
            Client client = session.get(Client.class, id);
            session.remove(client);
            transaction.commit();
            return true;
        }
    }
}
