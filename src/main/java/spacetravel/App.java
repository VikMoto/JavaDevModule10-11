package spacetravel;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import spacetravel.service.DatabaseInitService;
import spacetravel.util.HibernateUtil;

import java.util.List;
@Slf4j
public class App {
    public static void main(String[] args) {
        new DatabaseInitService().initDb();
    }

}
