package Controller;

import Database.JDBC;
import Database.Read;
import Model.Appointment;
import Model.Customer;
import Model.Users;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Optional;

/*
Functions to help with error checking, login validation, and alerts
 */
public class Helper {
    /*
    Validates user login by parsing DB with Read.getUSersInfo
    Iterates user list for username and password match, returns true if found
    Returns false if not found
    @param String username user username for comparison
    @param String password user password for comparison
     */
    public static boolean loginValidation(String username, String password) {

        ObservableList<Users> user = Read.getUsersInfo();
        boolean loginValid = false;

        for (Users users : user) {
            if (users.getUserName().equals(username)) {
                if (users.getPassword().equals(password)) {
                    loginValid = true;
                }
            }
        }

        return loginValid;
    }
    /*
    A simple confirmation alert that is used throughout program to alert user to confirmation messages
    @param AlertType Confirmation
    @param String title
    @param String header
     */

    public static void AlertConfirmation(Alert.AlertType Confirmation, String title, String header) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        Optional<ButtonType> result = alert.showAndWait();

    }
    /*
        A simple confirmation alert error that is used throughout program to alert user to confirmation error messages
        @param AlertError error
        @param String title
        @param String header
         */
    public static void AlertError(Alert.AlertType Error, String title, String header) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(header);
        Optional<ButtonType> result = alert.showAndWait();
    }
/*
used to check date conversion for localdatetime
Reads LocalDateTime to a string
 */
    public static void checkDateConversion() {
        System.out.println("Create DATE TEST");
        String sql = "Select Create_date from countries";
        try {
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Timestamp ts = rs.getTimestamp("Create_Date");
                System.out.println("CD: " + ts.toLocalDateTime().toString());
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
/*
Screen change method used throughout program to more easily change screens
@throws IOException Exception catches IO errors if exist
@param ActionEvent actionEvent
@param String resourceName
@param String title
@param boolean isResize
 */
    public static FXMLLoader screenChange(ActionEvent actionEvent, String resourceName, String title, boolean isResize) throws IOException {
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        Parent scene = FXMLLoader.load(Helper.class.getResource(resourceName));
        stage.setScene(new Scene(scene, 1200, 900));
        stage.setTitle(title);
        stage.setResizable(isResize);
        stage.show();
        return null;
    }
/*
customer input validation
checks if data entry is null returns boolean if true/false
@param Customer customer
 */
    public static boolean validateCustomer(Customer customer){
        boolean isFieldEmpty = true;
        if(customer.getName() == null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Please input customer name");
            alert.setHeaderText("Please input customer name");
            alert.showAndWait();
            isFieldEmpty = false;
        }
        if(customer.getAddress() == null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Please input customer address");
            alert.setHeaderText("Please input customer address");
            alert.showAndWait();
            isFieldEmpty = false;
        }
        if(customer.getPostal_code() == null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Please input customer zip code");
            alert.setHeaderText("Please input customer zip code");
            alert.showAndWait();
            isFieldEmpty = false;
        }
        if(customer.getPhone() == null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Please input customer phone number");
            alert.setHeaderText("Please input customer phone number");
            alert.showAndWait();
            isFieldEmpty = false;
        }
        return isFieldEmpty;
    }

    public static boolean validateAppointment(Appointment appointment){
        String headerText = "";
        boolean isValid = true;
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Invalid data input");
        if(appointment.getUserId() <= 0){
            headerText = "Please input user ID";
            alert.setHeaderText(headerText);
        }
        if(appointment.getCustomerId() <= 0){
            headerText = ("Please input customer ID");
            alert.setHeaderText(headerText);
        }
        if(appointment.getTitle().contains("")){
            headerText = ("Please input title");
            alert.setHeaderText(headerText);
        }
        if(appointment.getDescription() == null){
            headerText = ("Please input description");
            alert.setHeaderText(headerText);
        }
        if(appointment.getLocation() == null){
            headerText = ("Please input location");
            alert.setHeaderText(headerText);
        }
        if(appointment.getType() == null){
            headerText = ("Please input type");
            alert.setHeaderText(headerText);
        }
        if(appointment.getType() == null) {
            headerText = ("Please input type");
            alert.setHeaderText(headerText);
        }
        if(appointment.getStartDateTime() == null) {
            headerText = " Please input Start Date time with format of yyyy-MM-dd HH:mm";
            alert.setHeaderText(headerText);
        }
        if(appointment.getEndDateTime() == null) {
            headerText = "Please input End Date time with format of yyyy-MM-dd HH:mm";
            alert.setHeaderText(headerText);
        }
        if(!headerText.isEmpty()){
            alert.showAndWait();
            isValid = false;
        }
        return isValid;
    }
}



