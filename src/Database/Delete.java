package Database;

import Model.Appointment;
import Model.Customer;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.PropertyPermission;

public class Delete {

    public static void deleteApp(Appointment appointment){
        try {
            String sql = "DELETE FROM client_schedule.appointments WHERE appointment_Id = ?";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ps.setInt(1, appointment.getAppointmentId());
            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void deleteCustomer(Customer customer){
        try {
                String sql = "DELETE FROM client_schedule.customers WHERE customer_Id = ?";
                PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
                ps.setInt(1, customer.getId());
                ps.execute();
        } catch (SQLException e){

        }
    }
}
