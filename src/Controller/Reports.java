package Controller;

import Database.Read;
import Model.Appointment;
import Model.Contact;
import Model.ReportMonthandType;
import Model.SharedZip;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.ResourceBundle;

public class Reports implements Initializable {
    public Button Exit;
    public TableView appsMonthAndType;
    public TableView appsByContact;
    public TableView customersSharedZip;
    public TableColumn monthCol;
    public TableColumn typeCol;
    public TableColumn countCol;
    public TableColumn sharedZip;
    public TableColumn zipCount;
    public TableColumn appointmentId;
    public TableColumn appType;
    public TableColumn title;
    public TableColumn description;
    public TableColumn startDatetime;
    public TableColumn endDateTime;
    public TableColumn division;
    public ComboBox contactCombo;
    public TableColumn customerId;

    public ObservableList<ReportMonthandType> monthandTypes = Read.getAppsByTypeAndMonth();
    public ObservableList<SharedZip> sharedZips = Read.getZips();
    public ObservableList<Contact> contacts = Read.getallContacts();
    private String loggedInUser;


    public Reports() throws SQLException {
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            monthandTypes = Read.getAppsByTypeAndMonth();
            sharedZips = Read.getZips();
            contacts = Read.getallContacts();

            appsMonthAndType.setItems(monthandTypes);
            customersSharedZip.setItems(sharedZips);
//            appsByContact.setItems(contacts);
            popContactList();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        //Setting cell values for Month and Types table
        monthCol.setCellValueFactory(new PropertyValueFactory<>("month"));
        typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        countCol.setCellValueFactory(new PropertyValueFactory<>("count"));

        //Setting cell values for shared zip code customers
        sharedZip.setCellValueFactory(new PropertyValueFactory<>("zip"));
        zipCount.setCellValueFactory(new PropertyValueFactory<>("count"));

        //Setting cell values for appointments by contact
        appointmentId.setCellValueFactory(new PropertyValueFactory<Appointment, Integer>("appointmentId"));
        appType.setCellValueFactory(new PropertyValueFactory<Appointment, String>("type"));
        title.setCellValueFactory(new PropertyValueFactory<Appointment, String>("title"));
        description.setCellValueFactory(new PropertyValueFactory<Appointment, String>("description"));
        startDatetime.setCellValueFactory(new PropertyValueFactory<Appointment, LocalDateTime>("startDateTime"));
        endDateTime.setCellValueFactory(new PropertyValueFactory<Appointment, LocalDateTime>("EndDateTime"));
        customerId.setCellValueFactory(new PropertyValueFactory<Appointment, Integer>("customerId"));
    }

    public void contactComboAction(ActionEvent actionEvent) {
        int contactId = Read.contactIdFromName(contactCombo.getSelectionModel().getSelectedItem().toString());

        ObservableList<Appointment> appsByContactName = Read.getAppsByContact(contactId);
        appsByContact.setItems(appsByContactName);
    }

    public void popContactList(){
        for(Contact contact : contacts){
            contactCombo.getItems().add(contact.getContactNa());
        }
    }

    public void exitAction(ActionEvent actionEvent) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Exit to main screen?");
        alert.setHeaderText("Exit to main screen?");
        Optional<ButtonType> result = alert.showAndWait();
         if(result.get() == ButtonType.OK){
             Helper.screenChange(actionEvent, "/View/Main Screen.fxml", "Main Screen", true);
         }
    }

    public void passLoggedInUser(String loggedInUser) {
        this.loggedInUser = loggedInUser;
    }
}
