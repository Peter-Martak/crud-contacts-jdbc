package sk.peter;

import java.sql.*;

public class Main {
    public static void main(String[] args) {
        String select = "SELECT * FROM contact";
        String connectionURL = "jdbc:mysql://localhost:3306/contacts";
        
        try {
            Connection conn = DriverManager.getConnection(connectionURL, "root", "password");
            PreparedStatement ps = conn.prepareStatement(select);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                long id = rs.getLong("id");
                String name = rs.getString("name");
                String email = rs.getString("email");
                String phone = rs.getString("phone");

                System.out.println("ID: " + id + ", name: " + name + ", email: " + email + ", phone: " + phone);

            }
        } catch (SQLException e){
            System.out.println("Error while connecting to database");
        }
    }
}
