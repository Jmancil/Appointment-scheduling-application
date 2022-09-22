package Controller;

import Database.Create;
import Database.Read;
import Model.*;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.ResourceBundle;

/*
Add appointment class
 */
public class AddAppointment implements Initializable {
    public TextField appointmentId;
    public TextField userId;
    public TextField customerId;
    public TextField title;
    public TextField description;
    public TextField location;
    public TextField date;
    public TextField start;
    public TextField end;
    public ComboBox contactCombo;
    public TextField type;
    private String loggedInUser;
    private int appNumbers;
    /*
    Creation of appointment and contact arrays
     */
    public ObservableList<Appointment> appointments = Read.getAppointments();
    public ObservableList<Contact> contacts = Read.getallContacts();

    /*
    Save and exit action for a new appointment
    @throws IOException - catches exceptions if thrown
    @param ActionEvent actionEvent button press trigger for creation of Appointment
     */
    public void saveAction(ActionEvent actionEvent) throws IOException, SQLException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText("Press OK to submit");
        alert.setContentText("Create a new Appointment?");
        alert.setTitle("Create a new Appointment");
        Optional<ButtonType> decision = alert.showAndWait();
/*
 Alert created above and used below as trigger to assign data from fields to new appointment object
*/
        if (decision.get() == ButtonType.OK) {
            int idl = Integer.parseInt(appointmentId.getText());
            int userIdl = Integer.parseInt(userId.getText());
            int customerID = Integer.parseInt(customerId.getText());
            String titlel = title.getText();
            String descriptionl = description.getText();
            String locationl = location.getText();
            LocalDateTime startl = dateRevert(start.getText());
            LocalDateTime endl = dateRevert(end.getText());
            String typel = type.getText();
            String contactName = contactCombo.getSelectionModel().getSelectedItem().toString();
            int contactId = 0;
            /*
            Contact for loop to select correct contact ID
             */
            for (Contact contact : contacts) {
                if (contactName.equalsIgnoreCase(contact.getContactNa())) {
                    contactId = contact.getContactId();
                }
            }
            /*
            New appointment created and checked with isAppointmentOverLapped method & isAppBusinessHours method to check for overlapping
            appointment times and that the appointment falls within the weekday
             */

                Appointment newAppointment = new Appointment(typel, locationl, descriptionl, titlel, contactId, customerID, userIdl, idl, endl, startl, loggedInUser);
                if (!isAppointmnetOverlapped(newAppointment) && isAppBusinessHours(newAppointment)) {
                    if (!startl.isAfter(endl)) {
                        Create.createAppointment(newAppointment);
                    /*
                    Loader object created to move user to main screen and pass back loggedInUser
                    */
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/Main Screen.fxml"));
                        Parent mainScreenParent = loader.load();
                        MainScreen controller = loader.getController();
                        controller.passLoggedInUser(loggedInUser);
                        System.out.println(loggedInUser);
                        Scene mainScreenScene = new Scene(mainScreenParent);
                        Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                        window.setScene(mainScreenScene);
                        window.show();
                    } else{
                        Helper.AlertError(Alert.AlertType.ERROR, "Error", "Start time must be before appointment end time");
                    }
                }else {
                    Helper.AlertError(Alert.AlertType.ERROR, "Invalid appointment hours", "Appointment time is overlapping or Appointment Start/End date is before or after business hours");
            }
        }
    }

/*
@param ActionEvent actionEvent
@throws IOException Exception catches IO errors if exist
creates Alert used for trigger to switch screens
passes logged in user
 */
    public void exitAction(ActionEvent actionEvent) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText("Exit and return to main screen?");
        alert.setTitle("Exit and return to main screen?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/Main Screen.fxml"));
            Parent mainScreenParent = loader.load();
            MainScreen controller = loader.getController();
            controller.passLoggedInUser(loggedInUser);
            Scene mainScreenScene = new Scene(mainScreenParent);
            Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            window.setScene(mainScreenScene);
            window.show();
        }
    }
/*
catches loggedInUser
 */
    public void passLoggedInUser(String loggedInUser) {
        this.loggedInUser = loggedInUser;
    }

    /*
    Initializing contactcombo and passing number of appointments for ID auto assignment
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        passAppNumbers(appointments.size());
        contactComboPopulate();
    }

    /*
    @param int numberOfAppointments
    passing appointment numbers between screens
     */
    public void passAppNumbers(int appNumbers) {
        this.appNumbers = appNumbers;
        getAppNext(appNumbers);
    }

    public void getAppNext(int numberOfAppointments) {
        int size = numberOfAppointments; // Setting number of appointments size
        int n = 1;  // Iterator for check and setting values

        // Checks if DB has customers, if not sets to 1
        if (size <= 0) {
            appointmentId.setText("1");
        } else { //Iterates appointments checking if appId = current n value
            for (Appointment appointment : appointments) {
                if (appointment.getAppointmentId() == n) {
                    if (n == size) {
                        appointmentId.setText(String.valueOf(n + 1));
                    }
                    n += 1;
                    continue;
                } else {
                    appointmentId.setText(String.valueOf(n));
                    break;
                }
            }
        }
    }
/*
@param ActionEvent actionEvent Initializes contactCombo
@throws IOException Exception catches IO errors if exist
 */
    public void contactComboAction(ActionEvent actionEvent) throws SQLException {
    }

/*Lambda expression to replace for loop
Still cycles through contacts to assign correct display to contact combo box drop down selectable
 */
    public void contactComboPopulate(){
        contacts.forEach(contact -> contactCombo.getItems().add(contact.getContactNa()));
    }
/*
@param Appointment appointment Checks if appointment being added is overlapped with another appointment
Receives appointment that gets compared to all other appointments
Comparison is handled by finding matching customerId's
 */
    public boolean isAppointmnetOverlapped(Appointment appointment) {
        boolean isOverlapped = false;
        ObservableList<Appointment> appointments;
        appointments = Read.getAppointments();
        for (Appointment appointmentl : appointments) {
            if(appointment.getCustomerId() == appointmentl.getCustomerId()){
                //if customerId matches checks day of appointment to see if it falls on Saturday, throws alert if true
                if(appointment.getStartDateTime().getDayOfWeek().equals(DayOfWeek.SATURDAY)){
                    isOverlapped = true;
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("Appointment can not be on Saturday");
                    alert.setTitle("Appointment can not be on Saturday");
                    alert.showAndWait();
                    break;
                }
                //if customerId matches checks day of appointment to see if it falls on Sunday, throws alert if true
                if(appointment.getStartDateTime().getDayOfWeek().equals(DayOfWeek.SUNDAY)){
                    isOverlapped = true;
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("Appointment can not be on Sunday");
                    alert.setTitle("Appointment can not be on Sunday");
                    alert.showAndWait();
                    break;
                }
                //checks endDateTime and startDateTime of appointment returns true or false
                if(appointment.getEndDateTime().isAfter(appointmentl.getStartDateTime()) && appointment.getAppointmentId() != appointmentl.getAppointmentId()){
                    if(appointment.getStartDateTime().isBefore(appointmentl.getEndDateTime())){
                        isOverlapped = true;
                        break;
                    }
                }
            }
        }
        return isOverlapped;
    }
/*
@param String string Date revert for start and end times
 */
    public static LocalDateTime dateRevert(String string) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime test = LocalDateTime.parse(string, formatter);
        return test;
    }

    /*
    @param Appointment appointment converts time of app to localDate and compares to LocalDateTime start and end of business hours
    returns true or false
     */
    public boolean isAppBusinessHours(Appointment appointment){
        boolean testValid = true;
        LocalDate test = appointment.getStartDateTime().toLocalDate();
        //dayStart is localDateTime + 8 hours from midnight
        LocalDateTime dayStart = LocalDateTime.of(test, LocalTime.MIDNIGHT.plusHours(8));
        //dayEnd is localDateTime + 22 hours from midnight
        LocalDateTime dayEnd = LocalDateTime.of(test, LocalTime.MIDNIGHT.plusHours(22));
        if(appointment.getStartDateTime().isBefore(dayStart) || appointment.getEndDateTime().isAfter(dayEnd)){
            testValid = false;
        }
        return  testValid;
    }
}


