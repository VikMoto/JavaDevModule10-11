package spacetravel.service;


import org.flywaydb.core.Flyway;

import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;


public class DatabaseInitService {

    private static String url = "";
    private static String urlCheck = "";
    private static String user = "";
    private static String pass = "";
      public void initDb(){

          readProperties();

       // First, create the database if it doesn't exist
          createDatabaseIfNotExist();

       // Next, run the Flyway migration
          runFlyWay();
      }

    private static void createDatabaseIfNotExist() {
        if (!databaseExists()) {
            String createDbSql = "CREATE DATABASE spacetravel;";
            try (Connection conn = DriverManager.getConnection(urlCheck, user, pass);
                 PreparedStatement pstmt = conn.prepareStatement(createDbSql)) {
                pstmt.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException("Error creating database: " + e.getMessage(), e);
            }
        }
    }

    private static void runFlyWay() {

        final Flyway flyway = Flyway.configure()
                .dataSource(url, user, pass)
                .baselineOnMigrate(true)
                .load();
        flyway.migrate();
    }

    private static void readProperties() {
        try (InputStream input = new FileInputStream("src/main/resources/hibernate.properties")) {
            Properties properties = new Properties();
            properties.load(input);

            urlCheck = properties.getProperty("hibernate.connection.urlCheck");
            url = properties.getProperty("hibernate.connection.url");
            user = properties.getProperty("hibernate.connection.username");
            pass = properties.getProperty("hibernate.connection.password");

        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean databaseExists() {
        try (Connection conn = DriverManager.getConnection(urlCheck, user, pass);
             PreparedStatement pstmt = conn.prepareStatement("SELECT datname FROM pg_database");
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                String dbName = rs.getString("datname");
                if (dbName.equals("spacetravel")) {
                    return true;
                }
            }
            return false;
        } catch (SQLException e) {
            throw new RuntimeException("Error checking database existence: " + e.getMessage(), e);
        }
    }

}
