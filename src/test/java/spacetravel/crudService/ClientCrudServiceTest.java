package spacetravel.crudService;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import spacetravel.entity.Client;
import spacetravel.entity.Planet;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ClientCrudServiceTest {

    private static ClientCrudService crudService;
    @BeforeAll
    public static void init() {
        log.info("start init()");
        crudService = new ClientCrudService();
    }
    @Test
    @Order(1)
    void create() {
        Client testClient = Client.builder()
                .name("Test Client")
                .build();


        Long id = crudService.create(testClient);

        assertEquals( crudService.read(id).getId(), id);
        log.info("finish create");
    }

    @Test
    @Order(2)
    void read() {
        Client client = crudService.read(5L);
        assertEquals("Mykola Melnichuk", client.getName());
        log.info("finish read");
    }

    @Test
    @Order(3)
    void update() {
        Client client = crudService.read(5L);
        client.setName("Eye of God");
        crudService.update(client);
        Client actualClient = crudService.read(5L);
        System.out.println("actualClient = " + actualClient);
        assertEquals("Eye of God", actualClient.getName());
        log.info("finish update");
    }

    @Test
    @Order(4)
    void delete() {
        boolean deleted = crudService.delete(10L);
        assertTrue(deleted);
        log.info("finish delete");
    }
}