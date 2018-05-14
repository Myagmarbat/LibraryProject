package controllers;

import business.ControllerInterface;
import business.SystemController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class BaseController {
	protected static ControllerInterface sc = new SystemController();
	protected static FXMLLoader loader;	
	protected static Stage stage = new Stage();
	protected static Stage secondStage = new Stage();
	
	@FXML
	protected BorderPane bPane;

	public static ControllerInterface getSc() {
		return sc;
	}

	public static Stage getStage() {
		return stage;
	}

	public static void setStage(Stage stage) {
		BaseController.stage = stage;
	}

	public void setbPane(BorderPane bPane) {
		this.bPane = bPane;
	}

	

}
