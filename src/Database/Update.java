package Database;

import Model.Appointment;
import Model.Customer;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class Update {

    /*
    @param Customer customerUpdate updates the customer when called
     */
    public static void updateCustomer(Customer customerUpdate){
        try {
            //SQL query string
            String sql = "UPDATE client_schedule.customers SET customer_name = ?, address = ?, postal_code = ?, phone = ?, last_updated_by = ?, division_id = ?, last_update = ? WHERE customer_id = ?;";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);

             ps.setString(1, customerUpdate.getName());
             ps.setString(2, customerUpdate.getAddress());
             ps.setString(3, customerUpdate.getPostal_code());
             ps.setString(4, customerUpdate.getPhone());
             ps.setString(5, customerUpdate.getLoggedUser());
             ps.setInt(6, customerUpdate.getDivision_id());
             ps.setString(7, String.valueOf(LocalDateTime.now()));
             ps.setInt(8, customerUpdate.getId());
             //executes SQL
             ps.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /*
    @param Appointment appointmentModified updates current selected appointment when called
     */
    public static void updateApp(Appointment appointmentModified) {
        try {
            //sql query string
            String sql = "UPDATE client_schedule.appointments SET title = ?, description = ?, location = ?, type = ?, start = ?, end = ?, last_update = ?, last_updated_by = ?, customer_id = ?, user_id = ?, contact_id = ? WHERE appointment_id = ?;";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);

            ps.setString(1, appointmentModified.getTitle());
            ps.setString(2, appointmentModified.getDescription());
            ps.setString(3, appointmentModified.getLocation());
            ps.setString(4, appointmentModified.getType());
            ps.setString(5, String.valueOf(appointmentModified.getStartDateTime()));
            ps.setString(6, String.valueOf(appointmentModified.getEndDateTime()));
            ps.setString(7, String.valueOf(LocalDateTime.now()));
            ps.setString(8, String.valueOf(appointmentModified.getLoggedInUser()));
            ps.setInt(9, appointmentModified.getCustomerId());
            ps.setInt(10, appointmentModified.getUserId());
            ps.setInt(11, appointmentModified.getContactId());
            ps.setInt(12, appointmentModified.getAppointmentId());
            //executes SQL string
            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
