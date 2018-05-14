package controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import business.SystemController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

public class MainController extends BaseController implements Initializable {
	@FXML
	protected Text messageBox;
	@FXML
	protected Label mainRole;
	@FXML
	protected Label menuCheckOut;
	@FXML
	protected Label menuCheckIn;
	@FXML
	protected Label menuLibraryMembers;
	@FXML
	protected Label menuBook;
	@FXML
	protected Label menuOverdue;

	@FXML
	public void checkOutMenu() throws IOException {
		loader = new FXMLLoader(getClass().getResource("/ui/CheckOutBook.fxml"));
		Pane pane = (Pane) loader.load();
		bPane.setCenter(pane);
	}
	
	@FXML
	public void checkInMenu() throws IOException {
		loader = new FXMLLoader(getClass().getResource("/ui/CheckinBook.fxml"));
		Pane pane = (Pane) loader.load();
		bPane.setCenter(pane);
	}

	@FXML
	public void usersMenu() throws IOException {
		loader = new FXMLLoader(getClass().getResource("/ui/Users.fxml"));
		Pane pane = (Pane) loader.load();
		bPane.setCenter(pane);
	}

	@FXML
	public void booksMenu() throws IOException {
		loader = new FXMLLoader(getClass().getResource("/ui/Books.fxml"));
		Pane pane = (Pane) loader.load();
		bPane.setCenter(pane);
	}

	@FXML
	public void libraryMembersMenu() throws IOException {
		loader = new FXMLLoader(getClass().getResource("/ui/LibraryMembers.fxml"));
		Pane pane = (Pane) loader.load();
		bPane.setCenter(pane);
	}

	@FXML
	public void overDueMenu() throws IOException {
		loader = new FXMLLoader(getClass().getResource("/ui/Overdue.fxml"));
		Pane pane = (Pane) loader.load();
		bPane.setCenter(pane);
	}

	@FXML
	public void logOutMenu() throws IOException {
		loader = new FXMLLoader(getClass().getResource("/ui/Login.fxml"));
		Parent parent = loader.load();
		Scene scene = new Scene(parent);
		stage.setResizable(false);
		stage.setHeight(298);
		stage.setWidth(527);
		stage.setScene(scene);
		stage.show();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		switch (SystemController.currentAuth) {
		case ADMIN:
			menuLibraryMembers.setVisible(true);
			mainRole.setText("Administrator");
			menuCheckOut.setVisible(false);
			menuCheckIn.setVisible(false);
			menuBook.setVisible(true);
			menuOverdue.setVisible(false);
			break;
		case LIBRARIAN:
			menuLibraryMembers.setVisible(false);
			menuCheckOut.setVisible(true);
			menuCheckIn.setVisible(true);
			menuBook.setVisible(false);
			menuOverdue.setVisible(true);
			mainRole.setText("Librarian");
			break;
		default:
			menuCheckOut.setVisible(true);
			menuCheckIn.setVisible(true);
			menuLibraryMembers.setVisible(true);
			menuBook.setVisible(true);
			menuOverdue.setVisible(true);
			mainRole.setText("Super");
		}
	}
}
