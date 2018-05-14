package controllers;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;

import business.Address;
import business.Author;
import business.Book;
import business.ControllerInterface;
import business.SystemController;
import business.Utils;
import controllers.rulesets.RuleException;
import controllers.rulesets.RuleSet;
import controllers.rulesets.RuleSetFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TableView.TableViewSelectionModel;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.stage.Stage;

public class AuthorController extends BaseController implements Initializable {
	@FXML private TableColumn colFirstName;
	@FXML private TableColumn colLastName;
	@FXML private TableColumn colBio;
	@FXML private TableView<Author> table;
	@FXML private TextField tFirstName;
	@FXML private TextField tLastName;
	@FXML private TextField tTelephone;
	@FXML private TextArea tBio;
	@FXML private TextField tStreet;
	@FXML private TextField tCity;
	@FXML private TextField tState;
	@FXML private TextField tZip;
	@FXML private Label messageBox;
	@FXML private Button btnCreate;
	
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {		
		createTable();
		table.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
	}
	
	
	private void createTable(){
		ControllerInterface ci = new SystemController();
		List<Author> authors = ci.getAuthors();
		ObservableList<Author> data = FXCollections.observableList(authors);
		
		table.setItems(data);
		colFirstName.setCellValueFactory(
			    new PropertyValueFactory<Author,String>("firstName")
			);
		colLastName.setCellValueFactory(
			    new PropertyValueFactory<Author,String>("lastName")
			);
		colBio.setCellValueFactory(
			    new PropertyValueFactory<Author,String>("bio")
			);
		table.setRowFactory(tv -> {
		    TableRow<Author> row = new TableRow<>();
		    row.setOnMouseClicked(event -> {
		        if (! row.isEmpty() && event.getButton()==MouseButton.PRIMARY
		             && event.getClickCount() == 2) {

		        	Author m = row.getItem();
		        	tFirstName.setText(m.getFirstName());
		    		tLastName.setText(m.getLastName());
		    		tTelephone.setText(m.getTelephone());
		    		tBio.setText(m.getBio());
		    		tStreet.setText(m.getAddress().getStreet());
		    		tCity.setText(m.getAddress().getCity());
		    		tState.setText(m.getAddress().getState());
		    		tZip.setText(m.getAddress().getZip());
			    	table.getSelectionModel().clearSelection();
			    	btnCreate.setDisable(true);
		        }
		    });
		    return row ;
		});
	}
	
	@FXML
	protected void createNewAuthor(ActionEvent e) {
		RuleSet rules = RuleSetFactory.getRuleSet(AuthorController.this);
		try {
			rules.applyRules(AuthorController.this);
			
			Address address = new Address(tStreet.getText(),tCity.getText(),tState.getText(),tZip.getText());
			Author newAuthor = new Author(tFirstName.getText(), tLastName.getText(), tTelephone.getText(), address, tBio.getText());			
			newAuthor = sc.createNewAuthor(newAuthor);
			clear();
			createTable();
			
		} catch (RuleException e1) {
			messageBox.setText(e1.getMessage());
			// e1.printStackTrace();
		}
	}
	
	void clear(){
		tFirstName.clear();;
		tLastName.clear();
		tTelephone.clear();
		tBio.clear();
		tStreet.clear();
		tCity.clear();
		tState.clear();
		tZip.clear();
	}
	
	
	@FXML protected void cancel(ActionEvent e){		
		secondStage.hide();
	}
	
	@FXML protected void done(ActionEvent e){		
		BookController controller = (BookController)loader.<BookController>getController();
		controller.setSelectedAuthors(new ArrayList<Author>(table.getSelectionModel().getSelectedItems()));
		controller.setAuthorsTextArea(Utils.retrieveAuthorText(table.getSelectionModel().getSelectedItems()));
		secondStage.hide();
	}

	
	
	public String getFirstName() {
		return tFirstName.getText();
	}


	public String getLastName() {
		return tLastName.getText();
	}


	public String getTelephone() {
		return tTelephone.getText();
	}


	public String getBio() {
		return tBio.getText();
	}


	public String getStreet() {
		return tStreet.getText();
	}


	public String getCity() {
		return tCity.getText();
	}


	public String getState() {
		return tState.getText();
	}


	public String getZip() {
		return tZip.getText();
	}

	
}
