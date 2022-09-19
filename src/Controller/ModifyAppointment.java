package Controller;

import Database.Read;
import Database.Update;
import Model.Appointment;
import javafx.collections.FXCollections;
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
import java.time.temporal.TemporalAmount;
import java.util.Optional;
import java.util.ResourceBundle;
import static java.time.format.DateTimeFormatter.ofLocalizedDateTime;


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
    public ObservableList<Appointment> appointments = Read.getAppointments();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            appointmentPass(setAppPass);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void setAppPass(Appointment getAppointment) {
        ModifyAppointment.setAppPass = getAppointment;
    }

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

    public void passLoggedInUser(String loggedInUser) {
        this.loggedInUser = loggedInUser;
    }

    public void saveExit(ActionEvent actionEvent) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText("Press OK to submit");
        alert.setContentText("Exit to main screen?");
        alert.setTitle("Exit to main screen?");
        Optional<ButtonType> decision = alert.showAndWait();
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

            Appointment appointmentModified = new Appointment(typel, locationl, descriptionl, titlel, contact, customerIdl, userIdl, appIdl, endl, startl, loggedInUser);

            if(!isAppointmnetOverlapped(appointmentModified) && isAppBusinessHours(appointmentModified)){
            Update.updateApp(appointmentModified);

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

    //Formats data read from DB to be local time
//yyyy-MM-dd h:mm:ss
    public static String dateFormat(LocalDateTime localDateTime) {
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        String parseDateTime = localDateTime.format(format);
        return parseDateTime;
    }

    //Formats data back to update DB to UTC
//yyyy-MM-dd HH:mm
    public static LocalDateTime dateRevert(String string) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime test = LocalDateTime.parse(string, formatter);
        return test;
    }

    /*Checks appointment times against other appointments to see if times overlap
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
                if(appointment.getStartDateTime().getDayOfWeek().equals(DayOfWeek.SATURDAY)){
                    isOverlapped = true;
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("Appointment can not be on Saturday");
                    alert.setTitle("Appointment can not be on Saturday");
                    alert.showAndWait();
                    break;
                }
                if(appointment.getStartDateTime().getDayOfWeek().equals(DayOfWeek.SUNDAY)){
                    isOverlapped = true;
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("Appointment can not be on Sunday");
                    alert.setTitle("Appointment can not be on Sunday");
                    alert.showAndWait();
                    break;
                }
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
/*Checks appointment times against business hours
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
