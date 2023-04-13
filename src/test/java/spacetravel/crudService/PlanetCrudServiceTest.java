package spacetravel.crudService;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import spacetravel.entity.Planet;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class PlanetCrudServiceTest {
    private static PlanetCrudService crudService;
    @BeforeAll
    public static void init() {
        log.info("start init()");
        crudService = new PlanetCrudService();
    }
    @Test
    @Order(1)
    public void create() {
        Planet planet = Planet.builder()
                .id("LUN")
                .name("Luna")
                .build();

        boolean deleted = crudService.delete("LUN");

        String id = crudService.create(planet);

        assertNotNull("planet should not be null", id);
        assertEquals( "LUN", id);
        log.info("finish create");
    }

    @Test
    @Order(2)
    public void read() {
        Planet planet = crudService.read("LUN");
        assertEquals("Luna", planet.getName());
        log.info("finish read");
    }

    @Test
    @Order(3)
    public void update() {

        Planet planet = crudService.read("LUN");
        planet.setName("Eye of God");
        crudService.update(planet);
        Planet actualPlanet = crudService.read("LUN");
        System.out.println("actualPlanet = " + actualPlanet);
        assertEquals("Eye of God", actualPlanet.getName());
        log.info("finish update");
    }

    @Test
    @Order(4)
    public void delete() {
        boolean deleted = crudService.delete("LUN");
        assertTrue(deleted);
        log.info("finish delete");
    }
}