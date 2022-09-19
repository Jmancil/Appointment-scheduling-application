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
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

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
    private int numberOfAppointments;
    public ObservableList<Appointment> appointments = Read.getAppointments();
    public ObservableList<Contact> contacts = Read.getallContacts();

    public void saveAction(ActionEvent actionEvent) throws IOException, SQLException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText("Press OK to submit");
        alert.setContentText("Create a new Customer?");
        alert.setTitle("Create a new Customer");
        Optional<ButtonType> decision = alert.showAndWait();
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
            for (Contact contact : contacts) {
                if (contactName.equalsIgnoreCase(contact.getContactNa())) {
                    contactId = contact.getContactId();
                }
            }
                Appointment newAppointment = new Appointment(typel, locationl, descriptionl, titlel, contactId, customerID, userIdl, idl, endl, startl, loggedInUser);
                if (!isAppointmnetOverlapped(newAppointment) && isAppBusinessHours(newAppointment)) {
                    Create.createAppointment(newAppointment);

                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/Main Screen.fxml"));
                    Parent mainScreenParent = loader.load();
                    MainScreen controller = loader.getController();
                    controller.passLoggedInUser(loggedInUser);
                    System.out.println(loggedInUser);
                    Scene mainScreenScene = new Scene(mainScreenParent);
                    Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                    window.setScene(mainScreenScene);
                    window.show();
                } else {
                    Helper.AlertError(Alert.AlertType.ERROR, "Invalid appointment hours", "Appointment time is overlapping or Appointment Start/End date is before or after business hours");
                }
            }
            }


    public void exitAction(ActionEvent actionEvent) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText("Exit and return to main screen?");
        alert.setTitle("Exit and return to main screen?");
        System.out.println(loggedInUser);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            Helper.screenChange(actionEvent, "/View/Main Screen.fxml", "Main Screen", true);
        }
    }

    public void passLoggedInUser(String loggedInUser) {
        this.loggedInUser = loggedInUser;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        passNumberOfAppointments(appointments.size());
        contactComboPopulate();
    }

    public void passNumberOfAppointments(int numberOfAppointments) {
        this.numberOfAppointments = numberOfAppointments;
        getNextIdNumber(numberOfAppointments);
    }

    public void getNextIdNumber(int numberOfAppointments) {
        int size = numberOfAppointments; // Set the size
        int i = 1;  // Iterator

        // If no customers in database
        if (size == 0) {
            appointmentId.setText("1");
        } else {
            for (Appointment appointment : appointments) {
                if (appointment.getAppointmentId() == i) {
                    if (i == size) {
                        appointmentId.setText(String.valueOf(i + 1));
                    }
                    i += 1;
                    continue;
                } else {
                    appointmentId.setText(String.valueOf(i));
                    break;
                }
            }
        }
    }

    public void contactComboAction(ActionEvent actionEvent) throws SQLException {
    }

/*Lambda express to replace for loop
Still cycles through contacts to assign correct display to contact combo box drop down selectable
 */
    public void contactComboPopulate(){
        contacts.forEach(contact -> contactCombo.getItems().add(contact.getContactNa()));
    }

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

    public static LocalDateTime dateRevert(String string) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime test = LocalDateTime.parse(string, formatter);
        return test;
    }

    public boolean isAppBusinessHours(Appointment appointment){
        boolean testValid = true;
        LocalDate test = appointment.getStartDateTime().toLocalDate();
        LocalDateTime dayStart = LocalDateTime.of(test, LocalTime.MIDNIGHT.plusHours(8));
        LocalDateTime dayEnd = LocalDateTime.of(test, LocalTime.MIDNIGHT.plusHours(22));
        if(appointment.getStartDateTime().isBefore(dayStart) || appointment.getEndDateTime().isAfter(dayEnd)){
            testValid = false;
        }
        return  testValid;
    }


}


