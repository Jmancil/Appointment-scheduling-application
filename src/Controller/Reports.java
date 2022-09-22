package Controller;

import Database.Read;
import Model.Appointment;
import Model.Contact;
import Model.ReportMonthandType;
import Model.SharedZip;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

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

    /*
    Creation of monthAndTypes sharedZips, and contacts array lists
     */
    public ObservableList<ReportMonthandType> monthandTypes = Read.getAppsByTypeAndMonth();
    public ObservableList<SharedZip> sharedZips = Read.getZips();
    public ObservableList<Contact> contacts = Read.getallContacts();
    private String loggedInUser;

    //reports initialization
    public Reports() throws SQLException {
    }

    //Initializations
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            //Read method calls for monthandTypes, sharedZips, and contacts Views
            monthandTypes = Read.getAppsByTypeAndMonth();
            sharedZips = Read.getZips();
            contacts = Read.getallContacts();
            //setting lists
            appsMonthAndType.setItems(monthandTypes);
            customersSharedZip.setItems(sharedZips);
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

    //@param ActionEvent actionEvent contactCombo initializing combo box
    public void contactComboAction(ActionEvent actionEvent) {
        int contactId = Read.contactIdFromName(contactCombo.getSelectionModel().getSelectedItem().toString());
        //creating appointment array for population, calling Read method to fill array
        ObservableList<Appointment> appsByContactName = Read.getAppsByContact(contactId);
        //Setting items after creation
        appsByContact.setItems(appsByContactName);
    }
    //population of contact list
    public void popContactList(){
        for(Contact contact : contacts){
            contactCombo.getItems().add(contact.getContactNa());
        }
    }
    /*
    @param ActionEvent actionEvent exits reports screen and passes back logged in user
    @throws IOException Exception catches IO errors if exist
     */
    public void exitAction(ActionEvent actionEvent) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Exit to main screen?");
        alert.setHeaderText("Exit to main screen?");
        Optional<ButtonType> result = alert.showAndWait();
         if(result.get() == ButtonType.OK){
             FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/Main Screen.fxml"));
             Parent mainScreen = loader.load();
             MainScreen controller = loader.getController();
             controller.passLoggedInUser(loggedInUser);
             Scene mainScreenScene = new Scene(mainScreen);
             Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
             window.setScene(mainScreenScene);
             window.show();
         }
    }
//@param String loggInUser catching loggedInuser
    public void passLoggedInUser(String loggedInUser) {
        this.loggedInUser = loggedInUser;
    }
}
