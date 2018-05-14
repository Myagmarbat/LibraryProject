package controllers;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ResourceBundle;

import business.Book;
import business.BookCopy;

import business.CheckoutRecordEntry;
import business.LibraryMember;
import business.SystemController;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.util.Callback;
import javafx.util.StringConverter;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class CheckOutController extends BaseController implements Initializable {
	@FXML
	private TextField txtMemberID;
	@FXML
	private Label lblMemberInfo;
	@FXML
	private TextField txtISBN;
	@FXML
	private Label lblBookInfo;
	@FXML
	private TextField txtDueDate;
	@FXML
	private TableView<CheckoutRecordEntry> table;
	@FXML
	private Button btnSearchMember;
	@FXML
	private Button btnRenew;
	@FXML
	private TableColumn colISBN;
	@FXML
	private TableColumn colCopyNum;
	@FXML
	private TableColumn colCheckOutDate;
	@FXML
	private TableColumn colDueDate;
	@FXML
	private TableColumn colReturnDate;
	int i = 0;
	private String tmpISBN = "";
	private int bookCopyNum = 0;

	@FXML
	protected void searchMember(ActionEvent e) {
		SystemController daf = new SystemController();
		LibraryMember lb = daf.getLibraryMemberById(txtMemberID.getText());
		if (lb == null) {
			lblMemberInfo.setText("No Member!");
		} else {
			i++;
			lblMemberInfo.setText(lb.getFirstName() + " " + lb.getLastName());
			List<CheckoutRecordEntry> c = sc.getCheckoutRecordEntries(txtMemberID.getText());
			ObservableList<CheckoutRecordEntry> data = FXCollections.observableList(c);
			table.setItems(data);

			colISBN.setCellValueFactory(new PropertyValueFactory<CheckoutRecordEntry, String>("isbn"));

			colCopyNum.setCellValueFactory(
					new Callback<TableColumn.CellDataFeatures<CheckoutRecordEntry, String>, ObservableValue<String>>() {
						@Override
						public ObservableValue<String> call(
								TableColumn.CellDataFeatures<CheckoutRecordEntry, String> p) {
							if (p.getValue() != null) {
								return new SimpleStringProperty(p.getValue().getBookCopy().getCopyNum() + "");
							} else {
								return new SimpleStringProperty("");
							}
						}
					});

			colCheckOutDate.setCellValueFactory(new PropertyValueFactory<CheckoutRecordEntry, String>("checkoutDate"));
			colDueDate.setCellValueFactory(new PropertyValueFactory<CheckoutRecordEntry, String>("dueDate"));
			colReturnDate.setCellValueFactory(new PropertyValueFactory<CheckoutRecordEntry, String>("returnDate"));
			table.setRowFactory(tv -> {
				TableRow<CheckoutRecordEntry> row = new TableRow<>();
				row.setOnMouseClicked(event -> {
					if (!row.isEmpty() && event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2) {

						CheckoutRecordEntry m = row.getItem();
						if (m.getReturnDate() == null) {
							txtDueDate.setText(LocalDate.now()
									.plusDays(m.getBookCopy().getBook().getMaxCheckoutLength()).toString());
							tmpISBN = m.getIsbn();
							bookCopyNum = m.getBookCopy().getCopyNum();
							btnRenew.setDisable(false);
						} else {
							tmpISBN = "";
							bookCopyNum = 0;
							txtDueDate.setText("");
							btnRenew.setDisable(true);
						}
					}
				});
				return row;
			});
		}

	}

	@FXML
	protected void searchBook(ActionEvent e) {
		SystemController daf = new SystemController();
		Book b = daf.getBookById(txtISBN.getText());
		if (b == null) {
			lblBookInfo.setText("Book Not Found!");
			return;
		} else {
			lblBookInfo.setText(b.getTitle());
		}
		BookCopy bc = daf.getNextAvailableCopy(txtISBN.getText());
		if (bc == null) {
			lblBookInfo.setText("No Available Book!");
		} else {
			i++;
		}
	}

	@FXML
	protected void createCheckOut(ActionEvent e) {
		if (i < 2) {
			lblBookInfo.setText("Find Book and Member!");
			return;
		}
		Boolean b = sc.addCheckoutRecordEntry(txtMemberID.getText(), txtISBN.getText());
		if (b)
			i--;
		List<CheckoutRecordEntry> c = sc.getCheckoutRecordEntries(txtMemberID.getText());
		ObservableList<CheckoutRecordEntry> data = FXCollections.observableList(c);
		table.setItems(data);

		colISBN.setCellValueFactory(new PropertyValueFactory<CheckoutRecordEntry, String>("isbn"));

		colCopyNum.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<CheckoutRecordEntry, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(TableColumn.CellDataFeatures<CheckoutRecordEntry, String> p) {
						if (p.getValue() != null) {
							return new SimpleStringProperty(p.getValue().getBookCopy().getCopyNum() + "");
						} else {
							return new SimpleStringProperty("");
						}
					}
				});

		colCheckOutDate.setCellValueFactory(new PropertyValueFactory<CheckoutRecordEntry, String>("checkoutDate"));
		colDueDate.setCellValueFactory(new PropertyValueFactory<CheckoutRecordEntry, String>("dueDate"));
		colReturnDate.setCellValueFactory(new PropertyValueFactory<CheckoutRecordEntry, String>("returnDate"));
	}

	@FXML
	protected void printCheckout(ActionEvent e) {
		String memberId = txtMemberID.getText();
		if (!memberId.trim().equals("")) {
			lblMemberInfo.setText("");
			sc.printCheckoutRecordEntry(memberId);

		} else {
			lblMemberInfo.setText("No Member!");
		}

	}

	@FXML
	protected void renewDueDate(ActionEvent e) {
		if (tmpISBN.equals("") || bookCopyNum == 0 || txtDueDate.getText() == null || txtDueDate.getText().isEmpty()) {
			lblBookInfo.setText("Invalid argument");
			return;
		}
		if (sc.renewCheckoutRecordEntry(txtMemberID.getText(), tmpISBN, bookCopyNum,
				LocalDate.parse(txtDueDate.getText())))
			lblBookInfo.setText("Successful");
		else
			lblBookInfo.setText("Error occured");
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		txtDueDate.setDisable(true);
		btnRenew.setDisable(true);
	}
}
