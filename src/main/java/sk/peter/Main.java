package sk.peter;

import sk.peter.db.DBContactService;
import sk.peter.db.HikariCPDataSource;

import java.sql.*;

public class Main {
    public static void main(String[] args) {
        DBContactService service = new DBContactService();
        service.readAll().forEach(System.out::println);
    }
}
