package ui;

import controllers.MainController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Login extends Application {
	public static void main(String[] args) {
        Application.launch(Login.class, args);
    }

    @Override
    public void start(Stage stage) throws Exception {
    	MainController.setStage(stage);
        Parent root = FXMLLoader.load(getClass().getResource("Login.fxml"));

        stage.setTitle("Purchase Management");
        stage.setScene(new Scene(root, 527, 298));
        stage.show();
    }
}
