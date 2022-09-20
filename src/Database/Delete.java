package Database;

import Model.Appointment;
import Model.Customer;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.PropertyPermission;
/*
delete class for database delete operations in mysql
 */
public class Delete {
/*
@param Appointment appointment appointment deletion
appointment selected from specific appointmentId of appointment that gets passed in
 */
    public static void deleteApp(Appointment appointment){
        try {
            //MySQL String assigned with mysql operation
            String sql = "DELETE FROM client_schedule.appointments WHERE appointment_Id = ?";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            //sets specific appId
            ps.setInt(1, appointment.getAppointmentId());
            //Executes query
            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /*
    @param Customer customer customer deletion
    customer selected from specific customerId of customer that gets passed in
     */
    public static void deleteCustomer(Customer customer){
        try {
            //MySQL String assigned with mysql operation
                String sql = "DELETE FROM client_schedule.customers WHERE customer_Id = ?";
                PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
                //sets specific appId
                ps.setInt(1, customer.getId());
                //executes query
                ps.execute();
        } catch (SQLException e){

        }
    }
}
