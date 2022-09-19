package Database;

import Model.*;
import com.mysql.cj.protocol.Resultset;
import com.sun.source.tree.WhileLoopTree;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.*;
import java.time.LocalDateTime;

public class Read {
    public static ObservableList<Appointment> getAppsByMonth;

    //
    public static ObservableList<Users> getUsersInfo() {
        ObservableList<Users> user = FXCollections.observableArrayList();

        try {
            String sql = "SELECT User_ID, User_Name, Password FROM client_schedule.users;";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int userId = rs.getInt("User_ID");
                String userName = rs.getString("User_Name");
                String password = rs.getString("Password");
                Users users = new Users(userId, userName, password);
                user.add(users);
            }
        } catch (
                SQLException exception) {
            exception.printStackTrace();
        }
        return user;
    }


    public static ObservableList<Country> getAllCountries() {

        ObservableList<Country> clist = FXCollections.observableArrayList();

        try {
            String sql = "Select * from countries";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int countryId = rs.getInt("Country_ID");
                String countryName = rs.getString("Country");
                Country c = new Country(countryId, countryName);
                clist.add(c);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return clist;
    }

    public static ObservableList<Customer> getAllCustomers() {
        ObservableList<Customer> allCustomers = FXCollections.observableArrayList();
        try {
            String get = "SELECT * FROM client_schedule.customers;";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(get);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("Customer_ID");
                String name = rs.getString("Customer_Name");
                String address = rs.getString("Address");
                String zip = rs.getString("Postal_Code");
                String phone = rs.getString("Phone");
                int divisionId = rs.getInt("Division_ID");

                Customer n = new Customer(id, name, address, zip, phone, divisionId);
                allCustomers.add(n);
            }
        } catch (Exception e) {
            System.out.println("getCustomers Error: " + e.getMessage());
        }
        return allCustomers;
    }

    //Gets all existing appointments in database
    public static ObservableList<Appointment> getAppointments() {
        ObservableList<Appointment> appointments = FXCollections.observableArrayList();
        try {
            String get = "SELECT * FROM client_schedule.appointments;";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(get);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int appointmentId = rs.getInt("Appointment_ID");
                String title = rs.getString("Title");
                String description = rs.getString("Description");
                String location = rs.getString("Location");
                String type = rs.getString("Type");
                Timestamp start_time = rs.getTimestamp("Start");
                Timestamp end_time = rs.getTimestamp("End");
                int customerId = rs.getInt("Customer_ID");
                int userId = rs.getInt("User_ID");
                int contactId = rs.getInt("Contact_ID");

                LocalDateTime start = start_time.toLocalDateTime();
                LocalDateTime end = end_time.toLocalDateTime();

                Appointment n = new Appointment(type, location, description, title, contactId, customerId, userId, appointmentId, end, start);
                appointments.add(n);
            }
        } catch (SQLException e) {
            System.out.println("getAppointments Error: " + e);
        }
        return appointments;
    }

    public static ObservableList<Division> getAllDivisionsByCountryId(int countryId) throws SQLException {
        ObservableList<Division> divisions = FXCollections.observableArrayList();

        try {
            String sql = "SELECT Division_Id, Division, Country_Id FROM client_schedule.first_level_divisions WHERE Country_Id = ?;";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ps.setInt(1, countryId);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Division division = new Division(rs.getInt("Division_Id"), rs.getString("Division"), rs.getInt("Country_Id"));
                divisions.add(division);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return divisions;
    }

    public static ObservableList<Division> getAllDivisions() throws SQLException {
        ObservableList<Division> divisions = FXCollections.observableArrayList();
        try {
            String sql = "SELECT * FROM client_schedule.first_level_divisions";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Division division = new Division(rs.getInt("division_id"), rs.getString("division"), rs.getInt("country_id"));
                divisions.add(division);
            }
        } catch (SQLException e) {

            }
        return divisions;
    }

    public static ObservableList<Contact> getallContacts(){
        ObservableList<Contact> contacts = FXCollections.observableArrayList();
        try{
            String sql = "SELECT * FROM client_schedule.contacts;";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()){
                String emaill = rs.getString("Email");
                String namel = rs.getString("Contact_name");
                int contactId = rs.getInt("Contact_Id");
                Contact contact = new Contact(emaill, namel, contactId);
                contacts.add(contact);
            }
        }catch (SQLException e){
            System.out.println("getAppointments Error: " + e);
        }
        return contacts;
    }

    public static ObservableList<Appointment> getAllContactsByContactId(int contactId) throws SQLException {
        ObservableList<Appointment> appointments = FXCollections.observableArrayList();

        try {
            String sql = "SELECT contact_id, contact_name, email FROM client_schedule.contacts WHERE contact_id = ?;";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ps.setInt(1, contactId);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int appointmentId = rs.getInt("Appointment_ID");
                String title = rs.getString("Title");
                String description = rs.getString("Description");
                String location = rs.getString("Location");
                String type = rs.getString("Type");
                Timestamp start_time = rs.getTimestamp("Start");
                Timestamp end_time = rs.getTimestamp("End");
                int customerId = rs.getInt("Customer_ID");
                int userId = rs.getInt("User_ID");
                int contactIdl = rs.getInt("Contact_ID");

                LocalDateTime start = start_time.toLocalDateTime();
                LocalDateTime end = end_time.toLocalDateTime();

                Appointment n = new Appointment(type, location, description, title, contactIdl, customerId, userId, appointmentId, end, start);
                appointments.add(n);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return appointments;
    }

    public static Country getCounryById(int specificCountryId) {
        Country specificCountry = null;
        try {
            String sql = "select c.country_id, c.country, fd.division_id from countries as c\n" +
                    "join first_level_divisions as fd \n" +
                    "on fd.country_id = c.country_id\n" +
                    "where division_id = ?";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ps.setInt(1, specificCountryId);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int countryId = rs.getInt("Country_ID");
                String countryName = rs.getString("Country");
                specificCountry = new Country(countryId, countryName);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return specificCountry;
    }

    public static int getCustomerDivision(String contactName) throws SQLException {
        int contactId = 0;

        try {
            String getContact = "select contact_id from contacts where email = ?;";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(getContact);
            ps.setString(1, contactName);
            ResultSet rs = ps.executeQuery();

            while (rs.next()){
                contactId = rs.getInt("Contact_ID");
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return contactId;
    }

    public static int customerApps(int customerId){
        int customerAppointments = 0;
        try {
            String appsFromCustomerId = "SELECT * FROM client_schedule.appointments WHERE customer_Id = ?";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(appsFromCustomerId);
            ps.setInt(1, customerId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()){
                customerAppointments = customerAppointments + 1;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return customerAppointments;
    }

    public static ObservableList<ReportMonthandType> getAppsByTypeAndMonth() throws SQLException {
        ObservableList<ReportMonthandType> reports = FXCollections.observableArrayList();

        String sql = "SELECT DATE_FORMAT(start, '%M') AS month, COUNT(start) AS count, type FROM client_schedule.appointments GROUP BY month, type";
        try {
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                String month = rs.getString("month");
                int count = rs.getInt("count");
                String type = rs.getString("type");

                ReportMonthandType report = new ReportMonthandType(count, month, type);
                reports.add(report);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return reports;
    }

    public static ObservableList<SharedZip> getZips() {
        ObservableList<SharedZip> zipReports = FXCollections.observableArrayList();

        String sql = "SELECT postal_code, COUNT(Postal_Code) AS count FROM client_schedule.customers GROUP BY Postal_Code";
        try {
            PreparedStatement ps = JDBC.openConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                String zip = rs.getString("postal_code");
                int count = rs.getInt("count");

                SharedZip report = new SharedZip(count, zip);
                zipReports.add(report);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return zipReports;
    }

    public static int contactIdFromName(String contactName){
        int contactId = 0;
        try {
            String sql = "SELECT contact_id from client_schedule.contacts WHERE contact_name = ?";
            PreparedStatement  ps = JDBC.getConnection().prepareStatement(sql);
            ps.setString(1, contactName);
            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                contactId = rs.getInt("Contact_Id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return contactId;
    }

    public static ObservableList<Appointment> getAppsByContact(int contactId) {
        ObservableList<Appointment> apps = FXCollections.observableArrayList();
        String sql = "SELECT * FROM client_schedule.appointments WHERE contact_id = ?";
        try {
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ps.setInt(1,  contactId);
            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                String typel = rs.getString("type");
                String locationl = rs.getString("location");
                String descriptionl = rs.getString("description");
                String titlel = rs.getString("title");
                int contactIdl = rs.getInt("Contact_Id");
                int customerIdl = rs.getInt("Customer_Id");
                int userIdl = rs.getInt("user_Id");
                int appointmentIdl = rs.getInt("Appointment_Id");
                Timestamp start_time = rs.getTimestamp("Start");
                Timestamp end_time = rs.getTimestamp("End");

                LocalDateTime start = start_time.toLocalDateTime();
                LocalDateTime end = end_time.toLocalDateTime();

                Appointment appointment = new Appointment(typel, locationl, descriptionl, titlel, contactIdl, customerIdl, userIdl, appointmentIdl, start, end);
                apps.add(appointment);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return apps;
    }

    public static ObservableList<Appointment> getAppsByMonth() {
        ObservableList<Appointment> monthApps = FXCollections.observableArrayList();
        String sql = "SELECT * FROM client_schedule.appointments WHERE Start BETWEEN date_add(now(), interval 1 week) AND date_add(now(), interval 3 week);";

        try {
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                int appointmentId = rs.getInt("Appointment_ID");
                String title = rs.getString("Title");
                String description = rs.getString("Description");
                String location = rs.getString("Location");
                String type = rs.getString("Type");
                Timestamp start_time = rs.getTimestamp("Start");
                Timestamp end_time = rs.getTimestamp("End");
                int customerId = rs.getInt("Customer_ID");
                int userId = rs.getInt("User_ID");
                int contactId = rs.getInt("Contact_ID");

                LocalDateTime start = start_time.toLocalDateTime();
                LocalDateTime end = end_time.toLocalDateTime();

                Appointment n = new Appointment(type, location, description, title, contactId, customerId, userId, appointmentId, end, start);
                monthApps.add(n);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return monthApps;
    }

    public static ObservableList<Appointment> getAppsByWeek() {
        ObservableList<Appointment> weekApps = FXCollections.observableArrayList();
        String sql = "SELECT * FROM client_schedule.appointments WHERE Start BETWEEN NOW() AND date_add(now(), interval 7 day);";

        try {
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                int appointmentId = rs.getInt("Appointment_ID");
                String title = rs.getString("Title");
                String description = rs.getString("Description");
                String location = rs.getString("Location");
                String type = rs.getString("Type");
                Timestamp start_time = rs.getTimestamp("Start");
                Timestamp end_time = rs.getTimestamp("End");
                int customerId = rs.getInt("Customer_ID");
                int userId = rs.getInt("User_ID");
                int contactId = rs.getInt("Contact_ID");

                LocalDateTime start = start_time.toLocalDateTime();
                LocalDateTime end = end_time.toLocalDateTime();

                Appointment n = new Appointment(type, location, description, title, contactId, customerId, userId, appointmentId, end, start);
                weekApps.add(n);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return weekApps;
    }
}
