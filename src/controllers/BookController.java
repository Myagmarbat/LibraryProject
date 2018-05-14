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

public class BookController extends BaseController implements Initializable {

	@FXML
	private TableColumn colTitle;
	@FXML
	private TableColumn colIsbn;
	@FXML
	private TableColumn colCheckoutDays;
	@FXML
	private TableColumn colAuthors;
	@FXML
	private TableColumn colCopyCount;

	@FXML
	private TextField tTitle;
	@FXML
	private TextField tIsbn;
	@FXML
	private TextField tCopyCount;
	@FXML
	private ComboBox tCheckoutDays;
	@FXML
	private TextArea tAuthors;
	@FXML
	private TextField tAddCopyCount;
	@FXML
	private TextField tSearchIsbn;
	@FXML
	private Label messageBox;

	@FXML
	private TableView<Book> table;

	@FXML
	private Button btnAuthor;
	@FXML
	private Button btnAdd;
	private List<Author> selectedAuthors;

	@FXML
	protected void createBook(ActionEvent e) {
		RuleSet rules = RuleSetFactory.getRuleSet(BookController.this);
		try {
			rules.applyRules(BookController.this);
			Book book = new Book(tIsbn.getText(), tTitle.getText(),
					Integer.parseInt(tCheckoutDays.getValue().toString()), selectedAuthors);
			for (int i = 1; i <= Integer.parseInt(tCopyCount.getText()); i++) {
				book.addCopy();
			}
			book = sc.createNewBook(book);
			createTable();
		} catch (RuleException e1) {
			messageBox.setText(e1.getMessage());
			// e1.printStackTrace();

		}
	}

	@FXML
	protected void addBookCopy(ActionEvent e) {
		Book book = sc.getBookById(tSearchIsbn.getText());
		if (tSearchIsbn.getText().trim().equals("")) {
			messageBox.setText("Input book ispn");
		} else if (tAddCopyCount.getText().trim().equals("")) {
			messageBox.setText("Input book number of copy");
		}
		try {
			sc.addBookCopy(tSearchIsbn.getText(), Integer.parseInt(tAddCopyCount.getText()));
			messageBox.setText("Successfully added");
		} catch (NumberFormatException ne) {
			messageBox.setText("Book not found");
		}
		createTable();
	}

	@FXML
	protected void addAuthors(ActionEvent e) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("/ui/Author.fxml"));
		secondStage.setTitle("Add authors");
		secondStage.setScene(new Scene(root, 900, 400));
		secondStage.show();
	}

	@FXML
	protected void cancel(ActionEvent e) {
		clear();
	}

	private void createTable() {
		ControllerInterface ci = new SystemController();
		List<Book> books = ci.getBooks();
		ObservableList<Book> data = FXCollections.observableList(books);
		table.setItems(data);
		colTitle.setCellValueFactory(new PropertyValueFactory<Book, String>("title"));
		colIsbn.setCellValueFactory(new PropertyValueFactory<Book, String>("isbn"));
		colCheckoutDays.setCellValueFactory(new PropertyValueFactory<Book, String>("maxCheckoutLength"));

		colAuthors.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Book, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(TableColumn.CellDataFeatures<Book, String> p) {
						if (p.getValue() != null) {
							return new SimpleStringProperty(Utils.retrieveAuthorText(p.getValue().getAuthors()));
						} else {
							return new SimpleStringProperty("");
						}
					}
				});

		colCopyCount.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Book, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(TableColumn.CellDataFeatures<Book, String> p) {
						if (p.getValue() != null) {
							return new SimpleStringProperty(p.getValue().getNumCopies() + "");
						} else {
							return new SimpleStringProperty("");
						}
					}
				});

		table.setRowFactory(tv -> {
			TableRow<Book> row = new TableRow<>();
			row.setOnMouseClicked(event -> {
				if (!row.isEmpty() && event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2) {

					Book m = row.getItem();
					tTitle.setText(m.getTitle());
					tIsbn.setText(m.getIsbn());
					tCheckoutDays.setValue(m.getMaxCheckoutLength());
					int count = m.getNumCopies();
					tCopyCount.setText(Integer.toString(count));
					String s = "";
					for (int i = 0; i < m.getAuthors().size(); i++)
						s = s + m.getAuthors().get(i).getFirstName() + " " + m.getAuthors().get(i).getLastName()
								+ "\r\n";
					tAuthors.setText(s);
					table.getSelectionModel().clearSelection();
					btnAdd.setDisable(true);
					btnAuthor.setDisable(true);
				}
			});
			return row;
		});
	}

	void clear() {
		tTitle.clear();
		;
		tIsbn.clear();
		tCheckoutDays.setValue("");
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		tCheckoutDays.getItems().removeAll(tCheckoutDays.getItems());
		tCheckoutDays.getItems().addAll(21, 7);
		tCheckoutDays.getSelectionModel().selectFirst();
		tAuthors.setDisable(true);
		createTable();

	}

	public List<Author> getSelectedAuthors() {
		return selectedAuthors;
	}

	public void setSelectedAuthors(List<Author> selectedAuthors) {
		this.selectedAuthors = selectedAuthors;
	}

	public TextArea getAuthors() {
		return tAuthors;
	}

	public void setAuthorsTextArea(String authors) {
		this.tAuthors.setText(authors);
	}

	public String getTitle() {
		return tTitle.getText();
	}

	public String getIsbn() {
		return tIsbn.getText();
	}

	public String getCopyCount() {
		return tCopyCount.getText();
	}

	public String getCheckoutDays() {
		return tCheckoutDays.getValue().toString();
	}

}
