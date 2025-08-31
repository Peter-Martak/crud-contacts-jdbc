package sk.peter.db;

import org.slf4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

public class DBContactService {
    private static final String READ_ALL = "SELECT * FROM contact";

    private static final String CREATE = "INSERT INTO contact(name, email, phone) VALUES ( ?, ?, ?)";

    private static final String DELETE = "DELETE FROM contact WHERE id = ?";

    private static final String EDIT = "UPDATE contact SET name = ?, email = ?, phone = ? WHERE id = ?";
    private static final String SEARCH = "SELECT * FROM contact WHERE email LIKE  ? ";

    private static final Logger logger = getLogger(DBContactService.class);

    public List<Contact> readAll() {
        try (Connection connection = HikariCPDataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(READ_ALL)) {

            ResultSet resultSet = statement.executeQuery();
            List<Contact> contacts = new ArrayList<>();
            while (resultSet.next()) {
                contacts.add(new Contact(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getString("email"),
                        resultSet.getString("phone")
                ));
            }
            return contacts;
        } catch (SQLException e) {
            logger.error("Error while reading all contacts!", e);
            return null;
        }
    }

    public int create(String name, String email, String phone){
        try(
                Connection conn = HikariCPDataSource.getConnection();
                PreparedStatement ps = conn.prepareStatement(CREATE);
                ) {
            ps.setString(1,name);
            ps.setString(2,email);
            ps.setString(3,phone);

            return ps.executeUpdate();

        } catch (SQLIntegrityConstraintViolationException e){
            System.out.println("Contact with this email or phone already exits");
            return 0;
        } catch (SQLException e){
            logger.error("Error while creating new contact", e);
            return 0;
        }
    }

    public int delete(int id){
        try(
                Connection conn = HikariCPDataSource.getConnection();
                PreparedStatement ps = conn.prepareStatement(DELETE);
                ) {
            ps.setInt(1, id);
            // returns number of affected rows
            return ps.executeUpdate();
        }catch (SQLException e){
            logger.error("Error while deleting contact", e);
            return 0;
        }
    }

    public int edit (int id, String name, String email, String phone){
        try(
                Connection conn = HikariCPDataSource.getConnection();
                PreparedStatement ps = conn.prepareStatement(EDIT);
                ){
                ps.setInt(4, id);
                ps.setString(1, name);
                ps.setString(2, email);
                ps.setString(3, phone);

                // return number of affected rows
                return ps.executeUpdate();
        }catch (SQLIntegrityConstraintViolationException e){
            logger.error("Contact with this email or phone already exists");
            return 0;
        }
        catch (SQLException e){
            logger.error("Error while editing contact", e);
            return 0;
        }
    }

    public void search(String mail){
        try(
                Connection conn = HikariCPDataSource.getConnection();
                PreparedStatement ps = conn.prepareStatement(SEARCH);
                ) {
            ps.setString(1,"%" + mail + "%");
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String email = rs.getString("email");
                String phone = rs.getString("phone");
                System.out.println("id: " +id + " name: " + name +" email: "+ email + " phone: " + phone );
            }
        }catch (SQLException e){
            logger.error("Contact not found", e);
        }
    }
}

