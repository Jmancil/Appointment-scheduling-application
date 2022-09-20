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
/*
Add customer class controller
 */
public class AddCustomer implements Initializable {
    /*
    Creation of country, customers, and division arrays for use
     */
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
    private int CustomerNumbers;
    public String loggedUser;
    public String userName = "";
    private String loggedInUser;

    //catching loggedInUser for DB updates
    public void passLoggedInUser(String loggedInUser) {
        this.loggedInUser = loggedInUser;
    }

    //Initializer for addCustomer
    public AddCustomer() throws SQLException {
    }

//Save and exit to main screen
    public void saveExit(ActionEvent actionEvent) throws IOException {
//Alert creation to use as trigger to create and add a customer
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText("Press OK to submit");
        alert.setContentText("Create a new Customer?");
        alert.setTitle("Create a new Customer");
        Optional<ButtonType> decision = alert.showAndWait();
        //if ok pressed customer is assigned values from text fields
        if (decision.get() == ButtonType.OK) {
            int idl = Integer.parseInt(customerId.getText());
            String namel = name.getText();
            String addressl = address.getText();
            String zipl = zip.getText();
            String phonel = phone.getText();
            String customerDivision = divisionInfoCombo.getSelectionModel().getSelectedItem().toString();
            int customerDivisionId = 0;
        //Division for loop to assign divisionId
            for (Division division : divisions) {
                if (division.getDivisionName().equals(customerDivision)) {
                    customerDivisionId = division.getDivisionId();
                    break;
                }
            }
        //Customer crreation
            Customer newCustomer = new Customer(idl, namel, addressl, zipl, phonel, customerDivisionId, loggedInUser);
        //Customer input validation used
            if (Helper.validateCustomer(newCustomer)) {
                Create.createCustomer(newCustomer);
        //FXMLLoader object created to move user back to main screen, passes loggedInUser
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

    //Initialization of countryCombo
    //customerSize being passed and set for customer Id automation
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        countryComboPopulate();
        passCustomerNumbers(customers.size());
    }
/*
CountryComboAction used to populate country drop down and then display available list of Divsions for specific country
 */
    public void countryComboAction(ActionEvent actionEvent) throws SQLException {
        divisionInfoCombo.getItems().clear();
        //Country for loop sets up division combo
        for (Country country : countries) {
            if (country.getCountryName().equals(countryCombo.getSelectionModel().getSelectedItem())) {
                //arraylist for getting divisions by countryId
                ObservableList<Division> test = Read.getAllDivisionsByCountryId(country.getId());
                for (Division division : test) {
                    divisionInfoCombo.getItems().add(division.getDivisionName());
                }
                break;
            }
        }
    }
/*
@param int numberOfCustomers setting number of customers for CustomerId incrementing
 */
    public void passCustomerNumbers(int CustomerNumbers) {
        this.CustomerNumbers = CustomerNumbers;
        getNextIdNumber(CustomerNumbers);
    }
/*
@param int customerCount used for finding next customerId or assigning one if 0 customers exist
 */
    public void getNextIdNumber(int customerCount) {
        int size = customerCount; // Set the size of customerCount
        int n = 1;  // Iterator set for comparison

        // If no customers in database sets to 1
        if (size <= 0) {
            customerId.setText("1");
        } else {
            //for loop on customers to check customerIds
            for (Customer customer : customers) {
                if (customer.getId() == n) {
                    if (n == size) {
                        customerId.setText(String.valueOf(n + 1));
                    }
                    n += 1;
                    continue;
                } else {
                    customerId.setText(String.valueOf(n));
                    break;
                }
            }
        }
    }
}
