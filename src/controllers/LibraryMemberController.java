package controllers;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import business.Address;
import business.ControllerInterface;
import business.LibraryMember;
import business.SystemController;
import controllers.rulesets.RuleException;
import controllers.rulesets.RuleSet;
import controllers.rulesets.RuleSetFactory;
import javafx.collections.FXCollections;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class LibraryMemberController extends BaseController implements Initializable {
	@FXML
	private Label messageBox;
	@FXML
	private TextField tMemberId;
	@FXML
	private TextField tFirstName;
	@FXML
	private TextField tLastName;
	@FXML
	private TextField tPhoneNumber;
	@FXML
	private TextField tStreet;
	@FXML
	private TextField tCity;
	@FXML
	private TextField tState;
	@FXML
	private TextField tZip;
	@FXML
	private TableView<LibraryMember> table;
	private boolean createNewMember = true;
	@FXML
	private TableColumn colMemberId;
	@FXML
	private TableColumn colFirstName;
	@FXML
	private TableColumn colLastName;
	@FXML
	private TableColumn colPhone;

	@FXML
	protected void createNewMember(ActionEvent e) {
		createNewMember = true;
		clear();
		tMemberId.setEditable(true);
	}

	@FXML
	protected void saveMember(ActionEvent e) {

		RuleSet rules = RuleSetFactory.getRuleSet(LibraryMemberController.this);
		try {
			rules.applyRules(LibraryMemberController.this);

			if (createNewMember == true) {
				SystemController sc = new SystemController();
				if (sc.getLibraryMemberById(tMemberId.getText()) != null) {
					messageBox.setText("Member ID must be unique");
					return;
				}

				Address address = new Address(tStreet.getText(), tCity.getText(), tState.getText(), tZip.getText());
				LibraryMember member = new LibraryMember(tMemberId.getText(), tFirstName.getText(), tLastName.getText(),
						tPhoneNumber.getText(), address);
				
				sc.createNewMember(member);
				messageBox.setText("Successfully Created");

			} else {

				LibraryMember member = sc.getLibraryMemberById(tMemberId.getText());
				member.setFirstName(tFirstName.getText());
				member.setLastName(tLastName.getText());
				member.setTelephone(tPhoneNumber.getText());
				Address address = member.getAddress();
				address.setCity(tCity.getText());
				address.setState(tState.getText());
				address.setStreet(tStreet.getText());
				address.setZip(tZip.getText());
				member.setAddress(address);
				sc.editMember(member.getMemberId(), member);
				messageBox.setText("Successfully Updated");

			}
			clear();
			createTable();

		} catch (RuleException e1) {
			messageBox.setText(e1.getMessage());
			// e1.printStackTrace();
		}

	}

	@FXML
	protected void cancel(ActionEvent e) {
		clear();
	}

	private void createTable() {
		ControllerInterface ci = new SystemController();
		List<LibraryMember> libMembers = ci.getLibraryMembers();
		ObservableList<LibraryMember> data = FXCollections.observableList(libMembers);
		table.setItems(data);
		colMemberId.setCellValueFactory(new PropertyValueFactory<LibraryMember, String>("memberId"));
		colFirstName.setCellValueFactory(new PropertyValueFactory<LibraryMember, String>("firstName"));
		colLastName.setCellValueFactory(new PropertyValueFactory<LibraryMember, String>("lastName"));
		colPhone.setCellValueFactory(new PropertyValueFactory<LibraryMember, String>("telephone"));

		table.setRowFactory(tv -> {
			TableRow<LibraryMember> row = new TableRow<>();
			row.setOnMouseClicked(event -> {
				if (!row.isEmpty() && event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2) {

					LibraryMember m = row.getItem();
					tMemberId.setText(m.getMemberId());
					tFirstName.setText(m.getFirstName());
					tLastName.setText(m.getLastName());
					tPhoneNumber.setText(m.getTelephone());
					tStreet.setText(m.getAddress().getStreet());
					tCity.setText(m.getAddress().getCity());
					tState.setText(m.getAddress().getState());
					tZip.setText(m.getAddress().getZip());
					table.getSelectionModel().clearSelection();
					createNewMember = false;
					tMemberId.setEditable(false);
				}
			});
			return row;
		});
	}

	void clear() {
		tMemberId.clear();
		;
		tFirstName.clear();
		tLastName.clear();
		tPhoneNumber.clear();
		tStreet.clear();
		tCity.clear();
		tState.clear();
		tZip.clear();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		createTable();
	}

	public Label getStatus() {
		return messageBox;
	}

	public String getMemberId() {
		return tMemberId.getText();
	}

	public String getFirstName() {
		return tFirstName.getText();
	}

	public String getLastName() {
		return tLastName.getText();
	}

	public String getPhoneNumber() {
		return tPhoneNumber.getText();
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
