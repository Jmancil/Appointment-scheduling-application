package Controller;

import Database.Read;
import Database.Update;
import Model.Appointment;
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
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.ResourceBundle;

public class ModifyAppointment implements Initializable {
    public TextField appointmentId;
    public TextField userId;
    public TextField customerId;
    public TextField title;
    public TextField description;
    public TextField location;
    public TextField type;
    public TextField start;
    public TextField end;
    public ComboBox contactCombo;
    private String loggedInUser;
    private static Appointment setAppPass;

    //Array list of all appointments
    public ObservableList<Appointment> appointments = Read.getAppointments();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            //Initialization of appointment being passed in
            appointmentPass(setAppPass);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
/*
Method for passing in the appointment to modify
@param Appointment getAppointment
 */
    public static void setAppPass(Appointment getAppointment) {
        ModifyAppointment.setAppPass = getAppointment;
    }
/*
@param Appointment appointment setting the text fields for the modify appointment view
@throws IOException Exception catches IO errors if exist
 */
    public void appointmentPass(Appointment appointment) throws IOException, SQLException {
        appointmentId.setText(String.valueOf(appointment.getAppointmentId()));
        userId.setText(String.valueOf(appointment.getUserId()));
        customerId.setText(String.valueOf(appointment.getUserId()));
        title.setText(String.valueOf(appointment.getTitle()));
        description.setText(String.valueOf(appointment.getDescription()));
        location.setText(String.valueOf(appointment.getLocation()));
        type.setText(String.valueOf(appointment.getType()));
        start.setText(dateFormat(appointment.getStartDateTime()));
        end.setText(dateFormat(appointment.getEndDateTime()));
        contactCombo.setValue(String.valueOf(appointment.getContactId()));
    }

    /*
    passing the logged in user around for DB update purposes
    @param String loggedInUser
     */
    public void passLoggedInUser(String loggedInUser) {
        this.loggedInUser = loggedInUser;
    }
/*
@param actionEvent used for button action
@throws IOException exceptions get caught if any errors exist
Saves and exits the modify appointment screen back to the main screen
Utilizes isAppoinmentOverlapped and isAppointmentBusiness hours to check for overlapping another appointment and to make sure the
meeting is during business hours.
Also passes back the logged in user
 */
    public void saveExit(ActionEvent actionEvent) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText("Press OK to submit");
        alert.setContentText("Exit to main screen?");
        alert.setTitle("Exit to main screen?");
        Optional<ButtonType> decision = alert.showAndWait();
        //if "OK" pressed triggers creation
        if (decision.get() == ButtonType.OK) {

            int appIdl = Integer.parseInt(appointmentId.getText());
            int userIdl = Integer.parseInt(userId.getText());
            int customerIdl = Integer.parseInt(customerId.getText());
            String titlel = title.getText();
            String descriptionl = description.getText();
            String locationl = location.getText();
            String typel = type.getText();
            LocalDateTime startl = dateRevert(start.getText());
            LocalDateTime endl = dateRevert(end.getText());
            int contact = Integer.parseInt(String.valueOf(contactCombo.getSelectionModel().getSelectedItem()));
            //appointment created/assigned with new values
            Appointment appointmentModified = new Appointment(typel, locationl, descriptionl, titlel, contact, customerIdl, userIdl, appIdl, endl, startl, loggedInUser);
            //checks for overlap time && business hour check
            if(!isAppointmnetOverlapped(appointmentModified) && isAppBusinessHours(appointmentModified)){
            Update.updateApp(appointmentModified);
            //Moves user to main screen and passes loggedInUser
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/Main Screen.fxml"));
            Parent mainScreen = loader.load();
            MainScreen controller = loader.getController();
            controller.passLoggedInUser(loggedInUser);
            System.out.println(loggedInUser);
            Scene mainScreenScene = new Scene(mainScreen);
            Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            window.setScene(mainScreenScene);
            window.show();
            }else{
                Helper.AlertError(Alert.AlertType.ERROR, "Invalid appointment hours", "Appointment time is overlapping or Appointment Start/End date is before or after business hours");
            }
        }
    }
/*
@param actionEvent used for button action
@throws IOException exceptions get caught if any errors exist
Exits the modify appointment screen without saving any data
Also passes back the loggedInUser
 */
    public void exitAction(ActionEvent actionEvent) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText("Exit to main screen?");
        alert.setTitle("Exit to main screen?");
        Optional<ButtonType> decision = alert.showAndWait();
        if (decision.get() == ButtonType.OK) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/Main Screen.fxml"));
            Parent mainScreen = loader.load();
            MainScreen controller = loader.getController();
            controller.passLoggedInUser(loggedInUser);
            System.out.println(loggedInUser);
            Scene mainScreenScene = new Scene(mainScreen);
            Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            window.setScene(mainScreenScene);
            window.show();
        }
    }

    /*
    @paoram LocalDateTime localDateTime used for time conversion for DB and display changes
    Formats data read from DB to be local time
    yyyy-MM-dd h:mm:ss
     */
    public static String dateFormat(LocalDateTime localDateTime) {
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        String parseDateTime = localDateTime.format(format);
        return parseDateTime;
    }

    /*
        @paoram LocalDateTime localDateTime used for time conversion for DB and display changes
        Formats data read from DB to be local time
        yyyy-MM-dd h:mm:ss
         */
    public static LocalDateTime dateRevert(String string) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime test = LocalDateTime.parse(string, formatter);
        return test;
    }

    /*
     @param Appointment appointment used for time checks against other appointments && weekend check
     *Checks appointment times against other appointments to see if times overlap
     *creates list of appointments to iterate through for comparing stored apps to the app fed to the method
     *compares by customerId then compares start and end date time to DayofWeek Saturday + Sunday
     * If its not Saturday or Sunday it compares start and end date times with all other appointments
     * returns boolean for if statement
     */
    public boolean isAppointmnetOverlapped(Appointment appointment) {
        boolean isOverlapped = false;
        ObservableList<Appointment> appointments;
        appointments = Read.getAppointments();
        for (Appointment appointmentl : appointments) {
            if(appointment.getCustomerId() == appointmentl.getCustomerId()){
                //if customerId match check for day of week against Saturday
                if(appointment.getStartDateTime().getDayOfWeek().equals(DayOfWeek.SATURDAY)){
                    isOverlapped = true;
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("Appointment can not be on Saturday");
                    alert.setTitle("Appointment can not be on Saturday");
                    alert.showAndWait();
                    break;
                }
                //if customerId match check for day of week against Sunday
                if(appointment.getStartDateTime().getDayOfWeek().equals(DayOfWeek.SUNDAY)){
                    isOverlapped = true;
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("Appointment can not be on Sunday");
                    alert.setTitle("Appointment can not be on Sunday");
                    alert.showAndWait();
                    break;
                }
                //Appointment start and end time check
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
 *@param Appointment appointment used for appointment to make sure times are within business hours
 *Checks appointment times against business hours
 *test set to selected appointments startdatetime.local time
 * then fed into LocalDateTime.of to set hours for comparison
 * passed in appointment then compared to dayStart and dayEnd
 * returns boolean for if statement
 */

    public boolean isAppBusinessHours(Appointment appointment){
        boolean isAppBusinessHours = true;
        LocalDate test = appointment.getStartDateTime().toLocalDate();
        LocalDateTime dayStart = LocalDateTime.of(test, LocalTime.MIDNIGHT.plusHours(8));
        LocalDateTime dayEnd = LocalDateTime.of(test, LocalTime.MIDNIGHT.plusHours(22));
        if(appointment.getStartDateTime().isBefore(dayStart) || appointment.getEndDateTime().isAfter(dayEnd)){
            isAppBusinessHours = false;
        }
        return  isAppBusinessHours;
    }
}
