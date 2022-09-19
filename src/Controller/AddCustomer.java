package Controller;

import Database.Create;
import Database.Read;
import Model.Country;
import Model.Customer;
import Model.Division;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

public class AddCustomer implements Initializable {
    private final ObservableList<Country> countries = Read.getAllCountries();
    private final ObservableList<Customer> customers = Read.getAllCustomers();
    private ObservableList<Division> divisions = Read.getAllDivisions();


    public TextField customerId;
    public TextField name;
    public TextField address;
    public TextField zip;
    public TextField phone;
    public Button save;
    public Button exit;
    public TextField divisionId;
    public ComboBox countryCombo;
    public ComboBox divisionInfoCombo;
    private int numberOfCustomers;
    public String loggedUser;
    public String userName = "";
    private String loggedInUser;

    public void passLoggedInUser(String loggedInUser) {
        this.loggedInUser = loggedInUser;
    }

    public AddCustomer() throws SQLException {
    }


    public void saveExit(ActionEvent actionEvent) throws IOException {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText("Press OK to submit");
        alert.setContentText("Create a new Customer?");
        alert.setTitle("Create a new Customer");
        Optional<ButtonType> decision = alert.showAndWait();


        if (decision.get() == ButtonType.OK) {
            int idl = Integer.parseInt(customerId.getText());
            String namel = name.getText();
            String addressl = address.getText();
            String zipl = zip.getText();
            String phonel = phone.getText();
            String customerDivision = divisionInfoCombo.getSelectionModel().getSelectedItem().toString();
            int customerDivisionId = 0;

            for (Division division : divisions) {
                if (division.getDivisionName().equals(customerDivision)) {
                    customerDivisionId = division.getDivisionId();
                    break;
                }
            }
            Customer newCustomer = new Customer(idl, namel, addressl, zipl, phonel, customerDivisionId, loggedInUser);
            if (Helper.validateCustomer(newCustomer)) {
                Create.createCustomer(newCustomer);

                FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/Main Screen.fxml"));
                Parent mainScreenParent = loader.load();
                MainScreen controller = loader.getController();
                controller.passLoggedInUser(loggedInUser);
                System.out.println(loggedInUser);
                Scene mainScreenScene = new Scene(mainScreenParent);
                Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                window.setScene(mainScreenScene);
                window.show();
            }else{
                Alert alertCustomer = new Alert(Alert.AlertType.ERROR);
                alertCustomer.setHeaderText("Please input data into all customer data fields");
                alertCustomer.setTitle("Missing customer data");
                alertCustomer.showAndWait();
            }
        }
    }

//    public static ObservableList<Customer> addCustomer(){
//
//    }

    //Exits the Add Customer screen with user prompt - does not save input data
    public void customerExit(ActionEvent actionEvent) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Go to main menu?");
        alert.setHeaderText("Go to main menu");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            Helper.screenChange(actionEvent, "/view/Main Screen.fxml", "Main Screen", true);
        }
    }

    /*
    Lambda to replace for loop
    Iterates countries to fill country combo box
     */
    public void countryComboPopulate(){
        countries.forEach(country -> countryCombo.getItems().add(country.getCountryName()));
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        countryComboPopulate();
        passNumberOfCustomers(customers.size());
    }

    public void countryComboAction(ActionEvent actionEvent) throws SQLException {
        divisionInfoCombo.getItems().clear();

        for (Country country : countries) {
            if (country.getCountryName().equals(countryCombo.getSelectionModel().getSelectedItem())) {
                ObservableList<Division> test = Read.getAllDivisionsByCountryId(country.getId());
                for (Division division : test) {
                    divisionInfoCombo.getItems().add(division.getDivisionName());
                }
                break;
            }
        }
    }

    public void passNumberOfCustomers(int numberOfCustomers) {
        this.numberOfCustomers = numberOfCustomers;
        getNextIdNumber(numberOfCustomers);
    }

    public void getNextIdNumber(int numberOfCustomers) {
        int size = numberOfCustomers; // Set the size
        int i = 1;  // Iterator

        // If no customers in database
        if (size == 0) {
            customerId.setText("1");
        } else {
            for (Customer customer : customers) {
                if (customer.getId() == i) {
                    if (i == size) {
                        customerId.setText(String.valueOf(i + 1));
                    }
                    i += 1;
                    continue;
                } else {
                    customerId.setText(String.valueOf(i));
                    break;
                }
            }
        }
    }
}
