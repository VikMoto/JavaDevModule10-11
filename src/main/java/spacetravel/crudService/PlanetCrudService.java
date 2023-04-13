package spacetravel.crudService;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import spacetravel.entity.Planet;
import spacetravel.util.HibernateUtil;

import java.util.Optional;

public class PlanetCrudService implements CrudService<Planet, String > {

    @Override
    public String create(Planet value) {
        String  result;
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
    public Planet read(String  id) {
        Planet result;
        try (Session session = HibernateUtil.buildSessionFactory().openSession()){
            Transaction transaction1 = session.beginTransaction();
            Query<Planet> query = session.createQuery("from Planet where id = : id", Planet.class);
            result = query.setParameter("id", id).getSingleResult();
            transaction1.commit();
            return result;
        }
    }

    @Override
    public Planet update(Planet value) {
        Planet resultUpdate;
        try (Session session = HibernateUtil.buildSessionFactory().openSession()){
            Transaction transaction = session.beginTransaction();
            resultUpdate = session.merge(value);
            transaction.commit();
        }
        return resultUpdate;
    }

    @Override
    public boolean delete(String id) {
        try (Session session = HibernateUtil.buildSessionFactory().openSession()){
            Optional<Planet> planet;
            Transaction transaction = session.beginTransaction();
            planet = Optional.ofNullable(session.get(Planet.class, id));
            transaction.commit();
            if (planet.isPresent()) {
                Transaction transaction2 = session.beginTransaction();
                session.remove(planet.get());
                transaction2.commit();
                return true;
            } else {
                return false;
            }

        }
    }
}
