package Controller;

import Database.Read;
import Database.Update;
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
Modify Customer class controller
 */

public class ModifyCustomer implements Initializable {

    public TextField customerId;
    public TextField customerName;
    public TextField address;
    public TextField zip;
    public TextField phone;
    public Button save;
    public Button exit;
    public ComboBox countryCombo;
    public ComboBox divisionCombo;
    public String loggedInUser;
    private static Customer setCustomerPass;
    private ObservableList<Division> divisions = Read.getAllDivisions();

    public ModifyCustomer() throws SQLException {
    }
/*
Catching logged in user from main screen
@param String loggedInUser
 */
    public void passLoggedInUser(String loggedInUser) {
        this.loggedInUser = loggedInUser;
    }

/*
catching customer to be modified
@param Customer getCustomer
*/
    public static void setCustomerPass(Customer getCustomer) {
        ModifyCustomer.setCustomerPass = getCustomer;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
/*
Initializing customerPass
*/
            customerPass(setCustomerPass);
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
    }
/*
customer pass method used for passing in highlighted customer from main screen
sets text fields with correct data from passed customer
Creates country object by using specific country ID
@param Customer customer
 */
    public void customerPass(Customer customer) throws IOException, SQLException {
        Country country = Read.getCounryById(customer.getId());
        customerId.setText(String.valueOf(customer.getId()));
        customerName.setText(String.valueOf(customer.getName()));
        address.setText(String.valueOf(customer.getAddress()));
        zip.setText(String.valueOf(customer.getPostal_code()));
        phone.setText(String.valueOf(customer.getPhone()));
        divisionCombo.setValue(String.valueOf(customer.getDivision_id()));
        countryCombo.setValue(country.getCountryName());
    }

/*
Saves and exits modify customer to main screen
 */
    public void saveExit(ActionEvent actionEvent) throws IOException {
/*
creation of alert object used below as trigger to save data
 */
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText("Press OK to submit");
        alert.setContentText("Create a new Customer?");
        alert.setTitle("Create a new Customer");
        Optional<ButtonType> decision = alert.showAndWait();
/*
If Ok is clicked from alert above data saved from text field and new customer object is saved
 */
        if (decision.get() == ButtonType.OK) {
            int idl = Integer.parseInt(customerId.getText());
            String namel = customerName.getText();
            String addressl = address.getText();
            String zipl = zip.getText();
            String phonel = phone.getText();
            int customerDivision = Integer.parseInt(String.valueOf(divisionCombo.getSelectionModel().getSelectedItem()));
/*
customer created - Helper.validateCustomer checks data input fields for correct type
Update.updateCustomer feeds created customer to update method in Update class
 */
            Customer updatedCustomer = new Customer(idl, namel, addressl, zipl, phonel, customerDivision, loggedInUser);
            if (Helper.validateCustomer(updatedCustomer)) {
                Update.updateCustomer(updatedCustomer);
/*
FXMLLoader object creation to point back to main screen
also passes back logged in user that is used for DB updates.
 */
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
                    Alert alertCustomer = new Alert(Alert.AlertType.ERROR);
                    alertCustomer.setHeaderText("Please input data into all customer data fields");
                    alertCustomer.setTitle("Missing customer data");
                    alertCustomer.showAndWait();
            }
        }
    }
/*Exit action from modify customer to main screen
No data is saved
 */
        public void exitAction (ActionEvent actionEvent) throws IOException {
/*
Alert creating for returning to main screen with confirmation
*/
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setContentText("Return to main screen?");
            alert.setTitle("Return to main screen?");
            Optional<ButtonType> decision = alert.showAndWait();
/*
FXMLLoader object creation to point back to main screen
also passes back logged in user that is used for DB updates.
 */
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
    }

