package spacetravel;

import lombok.extern.slf4j.Slf4j;
import spacetravel.service.DatabaseInitService;

@Slf4j
public class App {
    public static void main(String[] args) {
        new DatabaseInitService().initDb();
    }

}
