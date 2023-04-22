package spacetravel.crudService;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import spacetravel.entity.Client;
import spacetravel.entity.Planet;
import spacetravel.entity.Ticket;
import spacetravel.util.HibernateUtil;

import java.util.Optional;

public class TicketCrudService implements CrudService<Ticket, Long > {

    @Override
    public Long create(Ticket value) {
        Long  result;
        Optional<Long> clientId;
        Optional<Planet> fromPlanetId;
        Optional<Planet> toPlanetId;
        try (Session session = HibernateUtil.buildSessionFactory().openSession()){
            Transaction transaction1 = session.beginTransaction();

            ClientCrudService clientCrudService = new ClientCrudService();
            clientId = Optional.ofNullable(value.getClient().getId());
            Optional<Client> client = Optional.ofNullable(clientCrudService.read(value.getClient().getId()));

            PlanetCrudService planetCrudService = new PlanetCrudService();
            fromPlanetId = Optional.ofNullable(value.getFromPlanetId());
            toPlanetId = Optional.ofNullable(value.getToPlanetId());

            Optional<Planet> planetFrom = Optional.ofNullable(planetCrudService.read(fromPlanetId.get().getId()));
            Optional<Planet> planetTo = Optional.ofNullable(planetCrudService.read(toPlanetId.get().getId()));

            transaction1.commit();
            if (client.isPresent() && planetFrom.isPresent() && planetTo.isPresent() ) {
                Transaction transaction = session.beginTransaction();
                session.persist(value);
                transaction.commit();
                Transaction transaction2 = session.beginTransaction();
                result = value.getId();
                transaction2.commit();
                return result;
            }else {
                return -1L;
            }
        }
    }

    @Override
    public Ticket read(Long  id) {
        Ticket result;
        try (Session session = HibernateUtil.buildSessionFactory().openSession()){
            Transaction transaction1 = session.beginTransaction();
            Query<Ticket> query = session.createQuery("from Ticket where id = : id", Ticket.class);
            result = query.setParameter("id", id).getSingleResult();
            transaction1.commit();
            return result;
        }
    }

    @Override
    public Ticket update(Ticket value) {
        Ticket resultUpdate;
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
            Optional<Ticket> ticket;
            Transaction transaction = session.beginTransaction();
            ticket = Optional.ofNullable(session.get(Ticket.class, id));
            transaction.commit();
            if (ticket.isPresent()) {
                Transaction transaction2 = session.beginTransaction();
                session.remove(ticket.get());
                transaction2.commit();
                return true;
            } else {
                return false;
            }

        }
    }
}
