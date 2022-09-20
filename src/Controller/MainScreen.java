package Controller;
import Database.Delete;
import Database.Read;
import Model.Appointment;
import Model.Customer;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
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
import java.util.Optional;
import java.util.ResourceBundle;

/*
Main Screen controller
 */
public class MainScreen implements Initializable {
    @FXML
    public TableView<Customer> customerTable;
    @FXML
    public TableView<Appointment> appTable;
    @FXML
    public TableColumn appointment;
    @FXML
    public TableColumn title;
    @FXML
    public TableColumn description;
    @FXML
    public TableColumn location;
    @FXML
    public TableColumn contact;
    @FXML
    public TableColumn type;
    @FXML
    public TableColumn start;
    @FXML
    public TableColumn end;
    @FXML
    public TableColumn customerID;
    @FXML
    public TableColumn customerIdColumn;
    @FXML
    public TableColumn customerName;
    @FXML
    public TableColumn address;
    @FXML
    public TableColumn zip;
    @FXML
    public TableColumn phone;
    @FXML
    public TableColumn division;
    @FXML
    public Button modifyAppointment;
    @FXML
    public String loggedInUser;
    @FXML
    public RadioButton viewAllRadio;
    @FXML
    public RadioButton viewMonthlyRadio;
    @FXML
    public RadioButton viewWeeklyRadio;

    //Creating arraylists for customers, appointments, monthlyapps, and weeklyapps
    ObservableList<Customer> customers = Read.getAllCustomers();
    ObservableList<Appointment> appointments = Read.getAppointments();
    ObservableList<Appointment> monthlyAppointments = Read.getAppsByMonth() ;
    ObservableList<Appointment> weeklyAppointments = Read.getAppsByWeek();

    /*
    passing the logged in user around for DB update purposes
    @param String loggedInUser
     */
    public void passLoggedInUser(String loggedInUser) {
        this.loggedInUser = loggedInUser;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //list views population
        customers = Read.getAllCustomers();
        appointments = Read.getAppointments();

        //initialize tables
        customerTable.setItems(customers);
        appTable.setItems(appointments);
        viewAllRadio.setSelected(true);

//        Populates table views in program - string has to match model to work correctly
//        Customer table population
        customerIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        customerName.setCellValueFactory(new PropertyValueFactory<>("name"));
        address.setCellValueFactory(new PropertyValueFactory<>("address"));
        zip.setCellValueFactory(new PropertyValueFactory<>("postal_code"));
        phone.setCellValueFactory(new PropertyValueFactory<>("phone"));
        division.setCellValueFactory(new PropertyValueFactory<>("division_id"));

        //Appointments table population
        appointment.setCellValueFactory(new PropertyValueFactory<>("appointmentId"));
        title.setCellValueFactory(new PropertyValueFactory<>("title"));
        description.setCellValueFactory(new PropertyValueFactory<>("description"));
        location.setCellValueFactory(new PropertyValueFactory<>("location"));
        contact.setCellValueFactory(new PropertyValueFactory<>("contactId"));
        type.setCellValueFactory(new PropertyValueFactory<>("type"));
        start.setCellValueFactory(new PropertyValueFactory<>("StartDateTime"));
        end.setCellValueFactory(new PropertyValueFactory<>("endDateTime"));
        customerID.setCellValueFactory(new PropertyValueFactory<>("customerId"));

    }

    public MainScreen() throws SQLException {
    }
/*
AddCustomerAction points to new window with FXMLloader and passes logged in user from above
 */
    public void addCustomerAction(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/Add Customer.fxml"));
        Parent addCustomer = loader.load();
        AddCustomer controller = loader.getController();
        controller.passLoggedInUser(loggedInUser);
        System.out.println(loggedInUser);
        Scene mainScreenScene = new Scene(addCustomer);
        Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        window.setScene(mainScreenScene);
        window.show();
    }

    /*
AddAppointment action points to new window with FXMLloader and passes logged in user from above
 */
    public void addAppointAction(javafx.event.ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/Add Appointment.fxml"));
        Parent addAppointment = loader.load();
        AddAppointment controller = loader.getController();
        controller.passLoggedInUser(loggedInUser);
        System.out.println(loggedInUser);
        Scene mainScreenScene = new Scene(addAppointment);
        Stage window =(Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        window.setScene(mainScreenScene);
        window.show();
    }

    /*
modifyCustomerAction action points to new window with FXMLloader and passes logged in user from above
Utilizes .getSelection and .getSelectedItem to create a new Customer from the highlighted item to pass to the next screen
*/
    public void modifyCustomerAction(javafx.event.ActionEvent actionEvent) throws IOException {
        Customer highLightedCustomer = customerTable.getSelectionModel().getSelectedItem();
        ModifyCustomer.setCustomerPass(highLightedCustomer);
        System.out.println(highLightedCustomer);
        if(highLightedCustomer == null){
            Helper.AlertError(Alert.AlertType.ERROR,"Error", "Please select a customer to modify");
        }
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/Modify Customer.fxml"));
        Parent modifyCustomer = loader.load();
        ModifyCustomer controller = loader.getController();
        controller.passLoggedInUser(loggedInUser);
        System.out.println(loggedInUser);
        Scene mainScreenScene = new Scene(modifyCustomer);
        Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        window.setScene(mainScreenScene);
        window.show();
    }

    /*
    modifyAppointment action points to new window with FXMLloader and passes logged in user from above
    Utilizes .getSelection and .getSelectedItem to create a new customer from the highlighted item to pass to the next screen
    */
    public void ModifyAppointmentAction(javafx.event.ActionEvent actionEvent) throws IOException {
        Appointment highLightedAppointment = appTable.getSelectionModel().getSelectedItem();
        ModifyAppointment.setAppPass(highLightedAppointment);
        if(highLightedAppointment == null){
            Helper.AlertError(Alert.AlertType.ERROR,"Error","Please select an appointment to modify");
        }
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/Modify Appointment.fxml"));
        Parent modifyAppointment = loader.load();
        ModifyAppointment controller = loader.getController();
        controller.passLoggedInUser(loggedInUser);
        System.out.println(loggedInUser);
        Scene mainScreenScene = new Scene(modifyAppointment);
        Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        window.setScene(mainScreenScene);
        window.show();
    }

    /*
    deletes the highlighted Customer - also displays error message if no customer is highlighted
     */
    public void deleteCustomerAction(javafx.event.ActionEvent actionEvent) {
        Customer deleteHighLightedCustomer = customerTable.getSelectionModel().getSelectedItem();

        if(deleteHighLightedCustomer == null){
            Helper.AlertError(Alert.AlertType.ERROR, "Please select a customer to delete", "Please select Customer");
        }

        int customerAppointments = Read.customerApps(deleteHighLightedCustomer.getId());
        if(customerAppointments == 0){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Delete Customer?");
            alert.setHeaderText("Delete Customer?");
            Optional<ButtonType> result = alert.showAndWait();

            if(result.get() == ButtonType.OK){
                Delete.deleteCustomer(deleteHighLightedCustomer);
                customers = Read.getAllCustomers();
                customerTable.setItems(customers);
                Helper.AlertConfirmation(Alert.AlertType.CONFIRMATION, "Delete customer", "Customer ID: " + deleteHighLightedCustomer.getId() + "Has been deleted");
            }
        }else{
            Helper.AlertError(Alert.AlertType.ERROR, "Selected customer must not have associated appointments", "Please delete associated appointments");
        }
    }
    /*
        deletes the highlighted appointment - also displays error message if no appointment is highlighted
         */
    public void deleteAppointmentAction(ActionEvent actionEvent) {
        Appointment deleteHighlightedAppointment = appTable.getSelectionModel().getSelectedItem();

        if(deleteHighlightedAppointment == null){
            Helper.AlertError(Alert.AlertType.ERROR, "Please select an appointment to delete", "Please select appointment");
        }

        if(deleteHighlightedAppointment != null){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Delete Appointment?");
            alert.setHeaderText("Delete Appointment?");
            Optional<ButtonType> result = alert.showAndWait();

            if(result.get() == ButtonType.OK) {
                Delete.deleteApp(deleteHighlightedAppointment);
                appointments = Read.getAppointments();
                appTable.setItems(appointments);
                Helper.AlertConfirmation(Alert.AlertType.CONFIRMATION, "Appointment delete", "Appointment ID: " + deleteHighlightedAppointment.getAppointmentId() + " Type: " +deleteHighlightedAppointment.getType());
            }
        }else{
            return;
        }
    }

    /*
        reportsAction action points to new window with FXMLloader and passes logged in user from above
        */
    public void reportsAction(javafx.event.ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/Reports.fxml"));
        Parent reports = loader.load();
        Reports controller = loader.getController();
        controller.passLoggedInUser(loggedInUser);
        System.out.println(loggedInUser);
        Scene mainScreenScene = new Scene(reports);
        Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        window.setScene(mainScreenScene);
        window.show();
    }
/*
sets the view for appointments to display all appointments regardless of when the appointment is
this is also the default setting when a user first enters the application
 */
    public void viewAllAction(ActionEvent actionEvent) {
        viewWeeklyRadio.setSelected(false);
        viewMonthlyRadio.setSelected(false);
        appTable.setItems(null);

        appTable.setItems(appointments);
    }
    /*
    Sets the appointments displayed to a monthly timeframe
     */

    public void viewMonthlyAction(ActionEvent actionEvent) {
        viewWeeklyRadio.setSelected(false);
        viewAllRadio.setSelected(false);
        appTable.setItems(null);

        appTable.setItems(monthlyAppointments);
    }
    /*
    Sets the appointments displayed to a weekly timeframe
     */

    public void viewWeeklyAction(ActionEvent actionEvent) {
        viewMonthlyRadio.setSelected(false);
        viewAllRadio.setSelected(false);
        appTable.setItems(null);

        appTable.setItems(weeklyAppointments);
    }
}