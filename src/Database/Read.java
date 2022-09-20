package Database;

import Model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.*;
import java.time.LocalDateTime;

public class Read {
//    public static ObservableList<Appointment> getAppsByMonth;

    //Observable list of users to compare user login information to database
    public static ObservableList<Users> getUsersInfo() {
        ObservableList<Users> user = FXCollections.observableArrayList();

        try {
            //SQL string to select id, username, and password
            String sql = "SELECT User_ID, User_Name, Password FROM client_schedule.users;";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            //executes query and assigns items to resultset
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int userId = rs.getInt("User_ID");
                String userName = rs.getString("User_Name");
                String password = rs.getString("Password");
                //Creating new user and then adding with .add method
                Users users = new Users(userId, userName, password);
                user.add(users);
            }
        } catch (
                SQLException exception) {
            exception.printStackTrace();
        }
        return user;
    }

//Observable list of all countries
    public static ObservableList<Country> getAllCountries() {
        ObservableList<Country> clist = FXCollections.observableArrayList();

        try {
            //setting a string with SQL query
            String sql = "Select * from countries";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            //executes query
            ResultSet rs = ps.executeQuery();
            //assigns results to an RS to store items
            while (rs.next()) {
                int countryId = rs.getInt("Country_ID");
                String countryName = rs.getString("Country");
                //creates new country
                Country c = new Country(countryId, countryName);
                //adds country to list
                clist.add(c);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        //returns country list
        return clist;
    }

    //gets all customers and returns them in an array
    public static ObservableList<Customer> getAllCustomers() {
        //array creation
        ObservableList<Customer> allCustomers = FXCollections.observableArrayList();
        try {
            //sql string query
            String get = "SELECT * FROM client_schedule.customers;";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(get);
            //returns results to result set
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                //assignment of data values to object
                int id = rs.getInt("Customer_ID");
                String name = rs.getString("Customer_Name");
                String address = rs.getString("Address");
                String zip = rs.getString("Postal_Code");
                String phone = rs.getString("Phone");
                int divisionId = rs.getInt("Division_ID");
                //creation of customer
                Customer n = new Customer(id, name, address, zip, phone, divisionId);
                //adding customer to list
                allCustomers.add(n);
            }
        } catch (Exception e) {
            System.out.println("getCustomers Error: " + e.getMessage());
        }
        //returning customerlist
        return allCustomers;
    }

    //Gets all existing appointments in database
    public static ObservableList<Appointment> getAppointments() {
        ObservableList<Appointment> appointments = FXCollections.observableArrayList();
        try {
            //string sql query
            String get = "SELECT * FROM client_schedule.appointments;";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(get);
            //executes query
            ResultSet rs = ps.executeQuery();
            //assigns results to resultset
            while (rs.next()) {
                //assignment of data values to object
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
                //start end timestamps to LocalDateTime
                LocalDateTime start = start_time.toLocalDateTime();
                LocalDateTime end = end_time.toLocalDateTime();
                //creation of appointment
                Appointment n = new Appointment(type, location, description, title, contactId, customerId, userId, appointmentId, end, start);
                //adding appointment to list
                appointments.add(n);
            }
        } catch (SQLException e) {
            System.out.println("getAppointments Error: " + e);
        }
        //returning appointment
        return appointments;
    }

    /*
    @param int countryId gets divisions buy countryId - creates array list of divisions and returns it
    @throws SQLException catches exception if thrown
     */
    public static ObservableList<Division> getAllDivisionsByCountryId(int countryId) throws SQLException {
        ObservableList<Division> divisions = FXCollections.observableArrayList();

        try {
            //sql query string
            String sql = "SELECT Division_Id, Division, Country_Id FROM client_schedule.first_level_divisions WHERE Country_Id = ?;";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            //sets countryId
            ps.setInt(1, countryId);
            //executes query
            ResultSet rs = ps.executeQuery();
            //sets results to result set
            while (rs.next()) {
                Division division = new Division(rs.getInt("Division_Id"), rs.getString("Division"), rs.getInt("Country_Id"));
                divisions.add(division);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        //returns divisions list
        return divisions;
    }
    //method to retrieve all divisions with observable list
    public static ObservableList<Division> getAllDivisions() throws SQLException {
        ObservableList<Division> divisions = FXCollections.observableArrayList();
        try {
            //sql query string
            String sql = "SELECT * FROM client_schedule.first_level_divisions";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            //executes query and assigns results
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Division division = new Division(rs.getInt("division_id"), rs.getString("division"), rs.getInt("country_id"));
                divisions.add(division);
            }
        } catch (SQLException e) {

            }
        //returns divisions array list
        return divisions;
    }

    //gets all contacts - creates observable list and returns in
    public static ObservableList<Contact> getallContacts(){
        ObservableList<Contact> contacts = FXCollections.observableArrayList();
        try{
            //sql query String
            String sql = "SELECT * FROM client_schedule.contacts;";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            //executes query and assigns to result set
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
        //returns contacts list
        return contacts;
    }

//    public static ObservableList<Appointment> getAllContactsByContactId(int contactId) throws SQLException {
//        ObservableList<Appointment> appointments = FXCollections.observableArrayList();
//
//        try {
//            String sql = "SELECT contact_id, contact_name, email FROM client_schedule.contacts WHERE contact_id = ?;";
//            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
//            ps.setInt(1, contactId);
//
//            ResultSet rs = ps.executeQuery();
//            while (rs.next()) {
//                int appointmentId = rs.getInt("Appointment_ID");
//                String title = rs.getString("Title");
//                String description = rs.getString("Description");
//                String location = rs.getString("Location");
//                String type = rs.getString("Type");
//                Timestamp start_time = rs.getTimestamp("Start");
//                Timestamp end_time = rs.getTimestamp("End");
//                int customerId = rs.getInt("Customer_ID");
//                int userId = rs.getInt("User_ID");
//                int contactIdl = rs.getInt("Contact_ID");
//
//                LocalDateTime start = start_time.toLocalDateTime();
//                LocalDateTime end = end_time.toLocalDateTime();
//
//                Appointment n = new Appointment(type, location, description, title, contactIdl, customerId, userId, appointmentId, end, start);
//                appointments.add(n);
//
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return appointments;
//    }

    /*
    @param int specificCountryId gets country by specific ID and returns country list
     */
    public static Country getCounryById(int specificCountryId) {
        Country specificCountry = null;
        try {
            //sql query string
            String sql = "select c.country_id, c.country, fd.division_id from countries as c join first_level_divisions as fd on fd.country_id = c.country_id where division_id = ?";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            //sets countryId
            ps.setInt(1, specificCountryId);
            //executes query and assigns to result set
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int countryId = rs.getInt("Country_ID");
                String countryName = rs.getString("Country");
                specificCountry = new Country(countryId, countryName);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        //returns specificCountry list
        return specificCountry;
    }

//    public static int getCustomerDivision(String contactName) throws SQLException {
//        int contactId = 0;
//
//        try {
//            String getContact = "select contact_id from contacts where email = ?;";
//            PreparedStatement ps = JDBC.getConnection().prepareStatement(getContact);
//            ps.setString(1, contactName);
//            ResultSet rs = ps.executeQuery();
//
//            while (rs.next()){
//                contactId = rs.getInt("Contact_ID");
//            }
//        }catch (SQLException e){
//            e.printStackTrace();
//        }
//        return contactId;
//    }

    /*
    @param int customerId accepts customerId selects all appointments where customerId matches
     */
    public static int customerApps(int customerId){
        int customerAppointments = 0;
        try {
            //sql query string
            String appsFromCustomerId = "SELECT * FROM client_schedule.appointments WHERE customer_Id = ?";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(appsFromCustomerId);
            //assigns customerId
            ps.setInt(1, customerId);
            //executes query and assigns to result set
            ResultSet rs = ps.executeQuery();

            while (rs.next()){
                customerAppointments = customerAppointments + 1;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        //returns customer appointments list
        return customerAppointments;
    }
    /*
    Reports by type and month method - creates and returns an array of reports
     */
    public static ObservableList<ReportMonthandType> getAppsByTypeAndMonth() throws SQLException {
        ObservableList<ReportMonthandType> typeAndMonth = FXCollections.observableArrayList();
        //sql query string
        String sql = "SELECT DATE_FORMAT(start, '%M') AS month, COUNT(start) AS count, type FROM client_schedule.appointments GROUP BY month, type";
        try {
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            //executes and assigns to result set
            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                String month = rs.getString("month");
                int count = rs.getInt("count");
                String type = rs.getString("type");
                //creation of report
                ReportMonthandType report = new ReportMonthandType(count, month, type);
                //adding report to array of reports
                typeAndMonth.add(report);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        //returns reports
        return typeAndMonth;
    }

    /*method for gathering similar zip codes grouped by count
    creates array list of zips
     */
    public static ObservableList<SharedZip> getZips() {
        ObservableList<SharedZip> zipReports = FXCollections.observableArrayList();
        //sql query string for postal code counting and grouping
        String sql = "SELECT postal_code, COUNT(Postal_Code) AS count FROM client_schedule.customers GROUP BY Postal_Code";
        try {
            PreparedStatement ps = JDBC.openConnection().prepareStatement(sql);
            //query execute and assigns to resultset
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
        //returns zip code reports
        return zipReports;
    }

    /*
    @param String contactName gets contact ID from contact name that user passes
     */
    public static int contactIdFromName(String contactName){
        int contactId = 0;
        try {
            //sql query string
            String sql = "SELECT contact_id from client_schedule.contacts WHERE contact_name = ?";
            PreparedStatement  ps = JDBC.getConnection().prepareStatement(sql);
            ps.setString(1, contactName);
            ResultSet rs = ps.executeQuery();
            //query execute and assigns to resultset
            while(rs.next()){
                contactId = rs.getInt("Contact_Id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        //returns list of ids from names
        return contactId;
    }

    /*
    @param int contactId gets apps by contactId
    Creates list of appointments and returns the list
     */
    public static ObservableList<Appointment> getAppsByContact(int contactId) {
        ObservableList<Appointment> apps = FXCollections.observableArrayList();
        //sql query string
        String sql = "SELECT * FROM client_schedule.appointments WHERE contact_id = ?";
        try {
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            //setting fed contactId
            ps.setInt(1,  contactId);
            //executing and assigning to result set
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
                //timestamp to LocalDateTime conversion for object creation
                LocalDateTime start = start_time.toLocalDateTime();
                LocalDateTime end = end_time.toLocalDateTime();
                //creation of appointment object
                Appointment appointment = new Appointment(typel, locationl, descriptionl, titlel, contactIdl, customerIdl, userIdl, appointmentIdl, start, end);
                //adding appointment to app list
                apps.add(appointment);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        //returns list
        return apps;
    }

    /*
    appointments by month method - creates appointment array and returns it
     */
    public static ObservableList<Appointment> getAppsByMonth() {
        ObservableList<Appointment> monthApps = FXCollections.observableArrayList();
        //sql query string
        String sql = "SELECT * FROM client_schedule.appointments WHERE Start BETWEEN date_add(now(), interval 1 week) AND date_add(now(), interval 3 week);";
        try {
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            //executes and assigns to result set
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
                //Timestamp to LocalDataTime conversion
                LocalDateTime start = start_time.toLocalDateTime();
                LocalDateTime end = end_time.toLocalDateTime();
                //creation of new appointment
                Appointment n = new Appointment(type, location, description, title, contactId, customerId, userId, appointmentId, end, start);
                //adding appointment to list
                monthApps.add(n);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        //returning appointment array
        return monthApps;
    }

    /*
    creates and returns list of weekly appointments
     */
    public static ObservableList<Appointment> getAppsByWeek() {
        ObservableList<Appointment> weekApps = FXCollections.observableArrayList();
        //sql query string
        String sql = "SELECT * FROM client_schedule.appointments WHERE Start BETWEEN NOW() AND date_add(now(), interval 7 day);";
        try {
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            //query execute and assigns to result set
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
                //timestamp to LocalDateTime conversion
                LocalDateTime start = start_time.toLocalDateTime();
                LocalDateTime end = end_time.toLocalDateTime();
                //appointment creation
                Appointment n = new Appointment(type, location, description, title, contactId, customerId, userId, appointmentId, end, start);
                //adding appointment to list
                weekApps.add(n);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        //returning list
        return weekApps;
    }
}
