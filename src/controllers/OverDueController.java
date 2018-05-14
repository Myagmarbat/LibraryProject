package controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.stream.Collectors;

import business.Address;
import business.Author;
import business.Book;
import business.BookCopy;
import business.CheckoutRecordEntry;
import business.ControllerInterface;
import business.LibraryMember;
import business.SystemController;
import business.Utils;
import controllers.rulesets.RuleException;
import controllers.rulesets.RuleSet;
import controllers.rulesets.RuleSetFactory;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.stage.Stage;
import javafx.util.Callback;

public class OverDueController extends BaseController{

	@FXML private TableColumn colTitle;
	@FXML private TableColumn colIsbn;
	@FXML private TableColumn colCheckoutDays;
	@FXML private TableColumn colAuthors;
	
	@FXML private TextField txtISBN;
		
	@FXML private Label messageBox;

	@FXML private TableView<CheckoutRecordEntry> tableOverdue;

	@FXML private Button btnAuthor;
	@FXML private Button btnAdd;

	@FXML protected void search(ActionEvent e){
		createTable();
	}

	private void createTable(){
		ControllerInterface ci = new SystemController();
		List<CheckoutRecordEntry> co = ci.getOverdueMemberList(txtISBN.getText());
		ObservableList<CheckoutRecordEntry> data = FXCollections.observableList(co);
		tableOverdue.setItems(data);
		colTitle.setCellValueFactory(
			    new PropertyValueFactory<CheckoutRecordEntry,String>("bookCopy")
			);
		colIsbn.setCellValueFactory(
			    new PropertyValueFactory<CheckoutRecordEntry,String>("checkoutDate")
			);
		colCheckoutDays.setCellValueFactory(
			    new PropertyValueFactory<CheckoutRecordEntry,String>("dueDate")
			);
		colAuthors.setCellValueFactory(
			    new PropertyValueFactory<CheckoutRecordEntry,String>("memberId")
			);
	}

	
}

