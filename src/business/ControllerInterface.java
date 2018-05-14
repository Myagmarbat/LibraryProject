package business;

import java.time.LocalDate;
import java.util.List;

import business.Book;

public interface ControllerInterface {
	public void login(String id, String password) throws LoginException;
	public List<String> allMemberIds();
	public List<String> allBookIds();

	public LibraryMember getLibraryMemberById(String id);
	public List<LibraryMember> getLibraryMembers();
	public void createNewMember(LibraryMember member);
	public void editMember(String id, LibraryMember editMember);

	public List<User> getUsers();
	public User getUserById(String id);
	public void createNewUser(User user);

	public List<Book> getBooks();
	public Book getBookById(String isbn);
	public Book createNewBook(Book book);
	public void addBookCopy(String isbn, int numCopies);
	
	public List<Author> getAuthors();
	public Author createNewAuthor(Author author);

	public BookCopy getNextAvailableCopy(String isbn);
	public List<CheckoutRecordEntry> getCheckoutRecordEntries(String memberId);
	public CheckoutRecordEntry getCheckoutRecordEntry(String memberId, String ISBN, int bookCopy);
	public boolean addCheckoutRecordEntry(String memberId, String isbn);
	public boolean renewCheckoutRecordEntry(String memberId, String isbn, int bookCopyNum, LocalDate date);
	public boolean setReturnCheckoutRecordEntry(String memberId, String isbn, int bookCopyNum);
	public void printCheckoutRecordEntry(String memberId);
	public List<CheckoutRecordEntry> getOverdueMemberList(String isbn);
	
}
