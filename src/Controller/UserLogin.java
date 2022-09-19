package Controller;

import Database.Read;
import Model.Appointment;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ResourceBundle;

public class UserLogin implements Initializable {
    public TextField userIdTextField;
    public TextField userPassword;
    public Label userIDLabel;
    public Label userPasswordLabel;
    public Label userLoginHeader;
    public TextField userLocation;
    public Label userLocationLabel;
    public Button loginButton;
    private String language;
    public String username = "";
    public ObservableList<Appointment>  appointments = Read.getAppointments();

    //checking for user location to change language on display form
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        language = ZoneId.systemDefault().toString();
        if(!language.equals("America/New_York")){
            userIDLabel.setText("Identifiant d'utilisateur");
            userIdTextField.setPromptText("Identifiant d'utilisateur");
            userPasswordLabel.setText("Le mot de passe");
            userPassword.setPromptText("Le mot de passe");
            userLoginHeader.setText("Utilisateur en ligne");
            userLocationLabel.setText("Emplacement");
            loginButton.setText("Connexion");
        }
        userLocation.setText(ZoneId.systemDefault().toString());
        System.out.println(language);
    }
//login action sets username and password and matches to DB with query to/from
    public void loginAction(ActionEvent actionEvent) throws IOException {
        String username = userIdTextField.getText();
        String password = userPassword.getText();
        loggingLogginAttempt();
        passUsername(username);
        fifteenMinuteAlert();

        boolean loginValid = Helper.loginValidation(username, password);
        if (loginValid){
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/Main Screen.fxml"));
            Parent mainScreenParent = loader.load();
            MainScreen controller = loader.getController();
            controller.passLoggedInUser(username);
            System.out.println(username);
            Scene mainScreenScene = new Scene(mainScreenParent);
            Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            window.setScene(mainScreenScene);
            window.show();
            //else displays error message in different languages depending on where user is logged in
        }else {
            if(!language.equals("America/New_York")){
                Helper.AlertConfirmation(Alert.AlertType.CONFIRMATION,"ERREUR","identifiant ou mot de passe incorrect");
            }else{
                Helper.AlertConfirmation(Alert.AlertType.CONFIRMATION,"ERROR", "Incorrect username or password");
            }
        }
    }

    //Method that logs user login attempts and writes to a file
    //Method will display if login attempt was successful + the userName + timestamp the attempt occured
    public void loggingLogginAttempt() throws IOException{
        String username = userIdTextField.getText();
        String password = userPassword.getText();
        boolean loginValid = Helper.loginValidation(username, password);
        Path currPath = Paths.get("src");
        Path filePath = Path.of(currPath + "loggingactivity.txt");

        boolean existFile = Files.exists(filePath);
        //if / nested if else determines if login attempt was successful or not and displays correct message
        if(existFile){
            Writer out = new BufferedWriter(new FileWriter(String.valueOf(filePath), true));
            String logAttempt;
            if(loginValid){
                logAttempt = "Login successful! " + "\nUsername: " + userIdTextField.getText() + "Timestamp " + LocalDateTime.now();
            }else{
                logAttempt = "Login not successful! " + "\nUsername: " + userIdTextField.getText() + "Timestamp " + LocalDateTime.now();
            }
            out.append(logAttempt);
            out.close();
            //else check if the existfile is not created this will create the new file
        }else{
            String loggingAtempt = "Username: " + userIdTextField.getText() + "TimeStamp: " + LocalDateTime.now();
            Path newFile = Files.createFile(currPath.resolve("loggingactivity.txt"));
            Files.writeString(newFile, loggingAtempt);
        }
    }
//passing username
    public void passUsername(String username) {
        this.username = username;
    }
//used to check if app is within 15 minutes of login - will display alert if true
    public void fifteenMinuteAlert(){
        for (Appointment appointment : appointments){
            LocalDateTime start = appointment.getStartDateTime();
            if(start.isAfter(LocalDateTime.now()) && start.isBefore(LocalDateTime.now().plusMinutes(15))){
                Helper.AlertConfirmation(Alert.AlertType.CONFIRMATION, "Appointment within 15 minutes ", "Appointment ID: " + appointment.getAppointmentId() + " Date and time: " + start + "within 15 minutes");
            }else {
                Helper.AlertConfirmation(Alert.AlertType.CONFIRMATION, "No appointments within 15 minutes ", "No appointments within 15 minutes ");
            }
        }
    }
}

