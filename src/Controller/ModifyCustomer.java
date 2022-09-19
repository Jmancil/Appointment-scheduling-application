package Controller;

import Database.Create;
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

    public void passLoggedInUser(String loggedInUser) {
        this.loggedInUser = loggedInUser;
    }

    public static void setCustomerPass(Customer getCustomer) {
        ModifyCustomer.setCustomerPass = getCustomer;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            customerPass(setCustomerPass);
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
    }

    public void customerPass(Customer customer) throws IOException, SQLException {
        Country preAssociatedCountry = Read.getCounryById(customer.getId());
        customerId.setText(String.valueOf(customer.getId()));
        customerName.setText(String.valueOf(customer.getName()));
        address.setText(String.valueOf(customer.getAddress()));
        zip.setText(String.valueOf(customer.getPostal_code()));
        phone.setText(String.valueOf(customer.getPhone()));
        divisionCombo.setValue(String.valueOf(customer.getDivision_id()));
        countryCombo.setValue(preAssociatedCountry.getCountryName());
    }


    public void saveExit(ActionEvent actionEvent) throws IOException {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText("Press OK to submit");
        alert.setContentText("Create a new Customer?");
        alert.setTitle("Create a new Customer");
        Optional<ButtonType> decision = alert.showAndWait();

        if (decision.get() == ButtonType.OK) {
            int idl = Integer.parseInt(customerId.getText());
            String namel = customerName.getText();
            String addressl = address.getText();
            String zipl = zip.getText();
            String phonel = phone.getText();
            int customerDivision = Integer.parseInt(String.valueOf(divisionCombo.getSelectionModel().getSelectedItem()));

            Customer updatedCustomer = new Customer(idl, namel, addressl, zipl, phonel, customerDivision, loggedInUser);
            if (Helper.validateCustomer(updatedCustomer)) {
                Update.updateCustomer(updatedCustomer);

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

        public void exitAction (ActionEvent actionEvent) throws IOException {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setContentText("Return to main screen?");
            alert.setTitle("Return to main screen?");
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
    }

