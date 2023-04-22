package spacetravel.crudService;

import jakarta.persistence.NoResultException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import spacetravel.entity.Client;
import spacetravel.entity.Planet;
import spacetravel.entity.Ticket;

import java.sql.Timestamp;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class TicketCrudServiceTest {
    private static TicketCrudService crudService;
    @BeforeAll
    public static void init() {
        log.info("start init()");
        crudService = new TicketCrudService();
    }
    @Test
    @Order(1)
    public void create() {
        Ticket ticket = Ticket.builder()
                .createdAt(Timestamp.valueOf("2023-04-13 12:00:00"))
                .client(Client.builder().id(9L).build())
                .fromPlanetId(Planet.builder().id("MARS").build())
                .toPlanetId(Planet.builder().id("EAR").build())
                .build();

        Long id = crudService.create(ticket);
        assertNotNull(id);
        log.info("finish create");
    }

    @Test
    @Order(2)
    public void createNullClient() {
        Ticket ticket = Ticket.builder()
                .createdAt(Timestamp.valueOf("2023-04-13 12:00:00"))
                .client(Client.builder().id(15L).build())
                .fromPlanetId(Planet.builder().id("MARS").build())
                .toPlanetId(Planet.builder().id("EAR").build())
                .build();

        assertThrows(NoResultException.class, () -> crudService.create(ticket));
        log.info("finish createNullClient()");
    }
    @Test
    @Order(3)
    public void createNullPlanet() {
        Ticket ticket = Ticket.builder()
                .createdAt(Timestamp.valueOf("2023-04-13 12:00:00"))
                .client(Client.builder().id(9L).build())
                .fromPlanetId(Planet.builder().id("FARS").build())
                .toPlanetId(Planet.builder().id("EAR").build())
                .build();


        assertThrows(NoResultException.class, () -> crudService.create(ticket));
        log.info("finish createNullClient()");
    }

    @Test
    @Order(4)
    public void read() {
        Ticket ticket = crudService.read(2L);
        assertEquals(2L, ticket.getId());
        log.info("finish read");
    }

    @Test
    @Order(5)
    public void update() {

        Ticket ticket = crudService.read(2L);
        ticket.setToPlanetId(Planet.builder().id("JUP").build());
        crudService.update(ticket);
        Ticket actualTicket = crudService.read(2L);
        System.out.println("actualTicket = " + actualTicket);
        assertEquals("JUP", actualTicket.getToPlanetId().getId());
        log.info("finish update");
    }

    @Test
    @Order(6)
    public void delete() {
        boolean deleted = crudService.delete(3L);
        assertTrue(deleted);
        log.info("finish delete");
    }
}