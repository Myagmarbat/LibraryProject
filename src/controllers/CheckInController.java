package controllers;

import java.time.LocalDate;

import business.Book;
import business.CheckoutRecordEntry;
import business.LibraryMember;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class CheckInController extends BaseController {
	@FXML
	private TextField txtMemberID;
	@FXML
	private TextField txtISBN;
	@FXML
	private TextField txtBookCopyNum;
	@FXML
	private TextField txtMember;
	@FXML
	private TextField txtBook;
	@FXML
	private TextField txtCheckoutDate;
	@FXML
	private TextField txtDueDate;
	@FXML
	private TextField txtReturnDate;
	@FXML
	private Label txtMsg;
	@FXML
	private Button btnSearchMember;
	@FXML
	private Button btnReturn;

	int i = 0;

	@FXML
	protected void searchCheckOutRecord(ActionEvent e) {
		CheckoutRecordEntry ce = sc.getCheckoutRecordEntry(txtMemberID.getText(), txtISBN.getText(),
				Integer.parseInt(txtBookCopyNum.getText()));
		if (ce != null) {
			Book b = sc.getBookById(ce.getIsbn());
			if (b != null)
				txtBook.setText(b.getTitle() + " - " + b.getAuthors().toString());
			LibraryMember mb = sc.getLibraryMemberById(txtMemberID.getText());
			if (mb != null)
				txtMember.setText(mb.getFirstName() + " " + mb.getLastName());
			txtDueDate.setText(ce.getDueDate().toString());
			txtCheckoutDate.setText(ce.getCheckoutDate().toString());
			if (ce.getReturnDate() != null) {
				txtReturnDate.setText(ce.getReturnDate().toString());
				txtMsg.setText("Already returned!");
				btnReturn.setDisable(true);
			} else
				btnReturn.setDisable(false);
		} else {
			txtMsg.setText("Record not found!");
			txtMember.setText("");
			txtBook.setText("");
			txtDueDate.setText("");
			txtCheckoutDate.setText("");
			txtReturnDate.setText("");
		}
	}

	@FXML
	protected void returnBookCopy(ActionEvent e) {
		boolean b = sc.setReturnCheckoutRecordEntry(txtMemberID.getText(), txtISBN.getText(),
				Integer.parseInt(txtBookCopyNum.getText()));
		if (b) {
			txtReturnDate.setText(LocalDate.now().toString());
			txtMsg.setText("Successfull");
			btnReturn.setDisable(true);
		} else
			txtMsg.setText("Error occured");
	}

}
