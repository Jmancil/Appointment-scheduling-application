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
import java.util.Locale;
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

    /**
     * Initialization used for checking user language and swaps display language if zoneId = France.
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        language = ZoneId.systemDefault().toString();
        if(Locale.getDefault().equals(Locale.FRANCE)){
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

    /**
     * Login action sets Strings for username and password, logs loggin attempts, passes username for loggedinuser.
     *
     * @param actionEvent
     * @throws IOException
     */
    public void loginAction(ActionEvent actionEvent) throws IOException {
        try {
        String username = userIdTextField.getText();
        String password = userPassword.getText();
        loggingLogginAttempt();
        passUsername(username);
    /**
    * if checks loginvalid true/false, runs fifteenminute check if true and pushes loggedInUser and changes screen to main.
    */
        boolean loginValid = Helper.loginValidation(username, password);
        if (loginValid){
            fifteenMinuteAlert();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/Main Screen.fxml"));
            Parent mainScreenParent = loader.load();
            MainScreen controller = loader.getController();
            controller.passLoggedInUser(username);
            System.out.println(username);
            Scene mainScreenScene = new Scene(mainScreenParent);
            Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            window.setScene(mainScreenScene);
            window.show();
            /**
             * else displays error message in different languages depending on where user is logged in
             */
        }else {
            if(Locale.getDefault().equals(Locale.FRANCE)){
                Helper.AlertConfirmation(Alert.AlertType.CONFIRMATION,"ERREUR","identifiant ou mot de passe incorrect");
            }else{
                Helper.AlertConfirmation(Alert.AlertType.CONFIRMATION,"ERROR", "Incorrect username or password");
            }
        }
        }catch (Exception e){
//            e.printStackTrace();
        }
    }

    /**
     * Method that logs user login attempts and writes to a file.
     * Method will display if login attempt was successful + the userName + timestamp the attempt occurred.
     * @throws IOException
     */
    public void loggingLogginAttempt() throws IOException{
        String username = userIdTextField.getText();
        String password = userPassword.getText();
        boolean loginValid = Helper.loginValidation(username, password);
        Path currPath = Paths.get("src");
        Path filePath = Path.of(currPath + "/login_activity.txt");
        String logAttempt;
        boolean existFile = Files.exists(filePath);

        /**
         * if / nested if else determines if login attempt was successful or not and displays correct message.
         */
        if(existFile){
            Writer out = new BufferedWriter(new FileWriter(String.valueOf(filePath), true));
            if(loginValid){
                logAttempt = " Login successful! " + "\nUsername: " + username + " Timestamp " + LocalDateTime.now();
                System.out.println(username);
            }else{
                logAttempt = " Login not successful! " + "\nUsername: " + username + " Timestamp " + LocalDateTime.now();
                System.out.println(username);
            }
            out.append(logAttempt);
            out.close();

            /**
             * else check if the log file is not created this will create the new file
             */
        }else{
            String loggingAtempt = " Username: " + username + "TimeStamp: " + LocalDateTime.now();
            Path newFile = Files.createFile(currPath.resolve("login_activity.txt"));
            Files.writeString(newFile, loggingAtempt);
        }
    }

    /**
     * Passing username
     * @param username
     */
    public void passUsername(String username) {
        this.username = username;
    }

    /**
     * Used to check if app is within 15 minutes of login - will display alert if true
     */
    public void fifteenMinuteAlert(){
        boolean fifteen = false;
        for (Appointment appointment : appointments){
            LocalDateTime start = appointment.getStartDateTime();
                if (start.isAfter(LocalDateTime.now()) && start.isBefore(LocalDateTime.now().plusMinutes(15))) {
                    Helper.AlertConfirmation(Alert.AlertType.CONFIRMATION, "Appointment within 15 minutes ", "Appointment ID: " + appointment.getAppointmentId() + " Date and time: " + start + "within 15 minutes");
                    fifteen = true;
                    break;
                }
            }
        if(!fifteen){
            Helper.AlertConfirmation(Alert.AlertType.CONFIRMATION, "No upcoming appointments", "No appointments within 15 minutes");
        }
        }
    }

