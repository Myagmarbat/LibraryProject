package controllers;

import java.io.IOException;

import business.LoginException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

public class LoginController extends BaseController {
	@FXML
	protected Text messageBox;
	@FXML
	protected TextField userTextField;
	@FXML
	protected PasswordField userPwField;
	
	@FXML
	public void onEnter(ActionEvent ae) throws IOException{
		loginLogInAction();
	}

	@FXML
	public void loginLogInAction() throws IOException {
		try {
			sc.login(userTextField.getText().trim(), userPwField.getText().trim());

			Parent parent = (Parent)FXMLLoader.load(getClass().getResource("/ui/Main.fxml"));
			Scene scene = new Scene(parent);
			stage.setResizable(false);
			stage.setHeight(400);
			stage.setWidth(900);
			stage.setScene(scene);
			stage.show();
		} catch (LoginException e1) {
			messageBox.setText(e1.getMessage());
		}
	}
}
