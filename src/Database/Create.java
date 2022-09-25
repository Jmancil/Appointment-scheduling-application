package Database;

import Model.Appointment;
import Model.Customer;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDateTime;
/*
Create class for database create operations in mysql
 */
public class Create {

    /**
    @param customer method creates a string with an insert SQL operation to create a new customer in the database
     */
    public static void createCustomer(Customer customer){
        try{
            String createCustomer = "INSERT INTO client_schedule.customers (customer_name, address, postal_code, phone, create_date, division_id, created_by, last_update, last_updated_by) \n" +
                    "values (?,?,?,?,?,?,?,?,?)";
            //Feeds string to MYSQL
            PreparedStatement ps = JDBC.openConnection().prepareStatement(createCustomer);
            //assignment for data values
            ps.setString(1, customer.getName());
            ps.setString(2, customer.getAddress());
            ps.setString(3, customer.getPostal_code());
            ps.setString(4, customer.getPhone());
            ps.setString(5, String.valueOf(LocalDateTime.now()));
            ps.setInt(6, customer.getDivision_id());
            ps.setString(7, customer.getLoggedUser());
            ps.setString(8, String.valueOf(LocalDateTime.now()));
            ps.setString(9, customer.getLoggedUser());
            //Statement execution
            ps.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
    @param appointment creates new appointment with String statement
     */
    public static void createAppointment(Appointment appointment) throws SQLException {
        try{
            String sql = "INSERT INTO client_schedule.appointments (customer_id, contact_id, user_id, title, description, location, type, start, end, create_date, created_by, last_update, last_updated_by)\n" +
                    "values (?,?,?,?,?,?,?,?,?,?,?,?,?);";
            //Feeds string to MYSQL
            PreparedStatement ps = JDBC.openConnection().prepareStatement(sql);
            //assignment for data values
            ps.setInt(1, appointment.getCustomerId());
            ps.setInt(2, appointment.getContactId());
            ps.setInt(3, appointment.getUserId());
            ps.setString(4, appointment.getTitle());
            ps.setString(5, appointment.getDescription());
            ps.setString(6, appointment.getLocation());
            ps.setString(7, appointment.getType());
            ps.setTimestamp(8, Timestamp.valueOf(appointment.getStartDateTime()));
            ps.setTimestamp(9, Timestamp.valueOf(appointment.getEndDateTime()));
            ps.setString(10, String.valueOf(LocalDateTime.now()));
            ps.setString(11, appointment.getLoggedInUser());
            ps.setString(12, String.valueOf(LocalDateTime.now()));
            ps.setString(13, appointment.getLoggedInUser());
            //Statement execution
            ps.execute();

        } catch (SQLException e){
            e.printStackTrace();
        }
    }
}
