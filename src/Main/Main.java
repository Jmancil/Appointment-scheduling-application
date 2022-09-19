package Main;

import Controller.Helper;
import Database.JDBC;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import java.sql.SQLException;

public class Main extends Application {

    public static void main(String[] args) throws SQLException {
        JDBC.openConnection();
        Helper.checkDateConversion();
        launch(args);
    }

    @Override
    public void start(Stage mainStage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/View/User Login.fxml"));
        mainStage.setTitle("Schedule App");
        mainStage.setScene(new Scene(root));
        mainStage.show();
    }
}
