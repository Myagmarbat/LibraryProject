package business;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import business.Book;
import dataaccess.Auth;
import dataaccess.DataAccess;
import dataaccess.DataAccessFacade;

public class SystemController implements ControllerInterface {
	public static Auth currentAuth = null;
	public static DataAccess dataAccess = new DataAccessFacade();

	@Override
	public void login(String id, String password) throws LoginException {
		HashMap<String, User> map = dataAccess.readUserMap();
		if (!map.containsKey(id)) {
			throw new LoginException("ID " + id + " not found");
		}
		String passwordFound = map.get(id).getPassword();
		if (!passwordFound.equals(password)) {
			throw new LoginException("Password incorrect");
		}
		currentAuth = map.get(id).getAuthorization();
	}

	@Override
	public List<String> allMemberIds() {
		List<String> retval = new ArrayList<>();
		retval.addAll(dataAccess.readMemberMap().keySet());
		return retval;
	}

	@Override
	public List<String> allBookIds() {
		List<String> retval = new ArrayList<>();
		retval.addAll(dataAccess.readBooksMap().keySet());
		return retval;
	}

	@Override
	public LibraryMember getLibraryMemberById(String id) {
		LibraryMember retval = null;
		HashMap<String, LibraryMember> map = dataAccess.readMemberMap();
		if (id != null && !id.trim().equals("") && map.containsKey(id)) {
			retval = map.get(id);
		}
		return retval;
	}

	@Override
	public List<LibraryMember> getLibraryMembers() {
		List<LibraryMember> retval = new ArrayList<>();
		HashMap<String, LibraryMember> map = dataAccess.readMemberMap();
		retval.addAll(map.values());
		return retval;
	}

	@Override
	public void createNewMember(LibraryMember member) {
		dataAccess.saveNewMember(member);
	}

	@Override
	public void editMember(String id, LibraryMember editMember) {
		LibraryMember existingMember = this.getLibraryMemberById(id);
		Address addr = editMember.getAddress();
		existingMember.setAddress(new Address(addr.getStreet(), addr.getCity(), addr.getState(), addr.getZip()));
		existingMember.setFirstName(editMember.getFirstName());
		existingMember.setLastName(editMember.getLastName());
		existingMember.setTelephone(editMember.getTelephone());
		existingMember.setCheckoutRecord(editMember.getCheckoutRecord());
		dataAccess.saveEditMember(id, existingMember);
	}

	@Override
	public List<User> getUsers() {
		List<User> retval = new ArrayList<>();
		HashMap<String, User> map = dataAccess.readUserMap();
		retval.addAll(map.values());
		return retval;
	}

	@Override
	public User getUserById(String id) {
		User retval = null;
		HashMap<String, User> map = dataAccess.readUserMap();
		if (id != null && map.containsKey(id)) {
			retval = map.get(id);
		}
		return retval;
	}

	@Override
	public void createNewUser(User user) {
		dataAccess.saveNewUser(user);
	}

	@Override
	public List<Book> getBooks() {
		List<Book> retval = new ArrayList<>();
		HashMap<String, Book> map = dataAccess.readBooksMap();
		retval.addAll(map.values());
		return retval;
	}

	@Override
	public Book getBookById(String isbn) {
		Book retval = null;
		HashMap<String, Book> map = dataAccess.readBooksMap();
		if (isbn != null && !isbn.trim().equals("") && map.containsKey(isbn)) {
			retval = map.get(isbn);
		}
		return retval;
	}

	@Override
	public Book createNewBook(Book book) {
		return dataAccess.saveNewBook(book);
	}

	@Override
	public void addBookCopy(String isbn, int numCopies) {
		Book book = getBookById(isbn);
		for (int i = 0; i < numCopies; i++) {
			book.addCopy();
		}
		dataAccess.saveEditBook(isbn, book);
	}

	@Override
	public List<Author> getAuthors() {
		List<Author> retval = new ArrayList<>();
		HashMap<String, Author> map = dataAccess.readAuthorMap();
		retval.addAll(map.values());
		return retval;
	}

	@Override
	public Author createNewAuthor(Author author) {
		return dataAccess.saveNewAuthor(author);
	}

	@Override
	public BookCopy getNextAvailableCopy(String isbn) {
		return this.getBookById(isbn).getNextAvailableCopy();
	}

	@Override
	public List<CheckoutRecordEntry> getCheckoutRecordEntries(String memberId) {
		LibraryMember member = this.getLibraryMemberById(memberId);
		return member.getCheckoutRecord().getCheckoutRecordEntries();
	}

	@Override
	public CheckoutRecordEntry getCheckoutRecordEntry(String memberId, String ISBN, int bookCopyNum) {
		LibraryMember member = this.getLibraryMemberById(memberId);
		List<CheckoutRecordEntry> lcre = member.getCheckoutRecord().getCheckoutRecordEntries();
		for (CheckoutRecordEntry ce : lcre) {
			if (ce.getIsbn().equals(ISBN) && ce.getBookCopy().getCopyNum() == bookCopyNum
					&& ce.getReturnDate() == null) {
				return ce;
			}
		}
		return null;
	}

	@Override
	public boolean addCheckoutRecordEntry(String memberId, String isbn) {
		LibraryMember member = this.getLibraryMemberById(memberId);
		Book book = this.getBookById(isbn);

		BookCopy bookCopy = book.getNextAvailableCopy();
		if (bookCopy != null) {
			CheckoutRecordEntry newEntry = new CheckoutRecordEntry(bookCopy, LocalDate.now(),
					LocalDate.now().plusDays(book.getMaxCheckoutLength()));
			bookCopy.changeAvailability();
			member.getCheckoutRecord().addCheckoutRecordEntry(newEntry);
			dataAccess.saveEditMember(memberId, member);
			dataAccess.saveEditBook(isbn, book);
			return true;
		}
		return false;
	}

	@Override
	public boolean renewCheckoutRecordEntry(String memberId, String isbn, int bookCopyNum, LocalDate date) {
		CheckoutRecordEntry entry = this.getCheckoutRecordEntry(memberId, isbn, bookCopyNum);
		if (entry != null) {
			LibraryMember member = this.getLibraryMemberById(memberId);
			member.getCheckoutRecord().setCheckoutRecordEntry(entry, date, false);
			dataAccess.saveEditMember(memberId, member);
			return true;
		} else
			return false;
	}

	@Override
	public boolean setReturnCheckoutRecordEntry(String memberId, String isbn, int bookCopyNum) {

		CheckoutRecordEntry entry = this.getCheckoutRecordEntry(memberId, isbn, bookCopyNum);
		if (entry != null) {
			LibraryMember member = this.getLibraryMemberById(memberId);
			member.getCheckoutRecord().setCheckoutRecordEntry(entry, LocalDate.now(), true);
			dataAccess.saveEditMember(memberId, member);
			Book book = this.getBookById(isbn);
			for (int i = 0; i < book.getCopies().length; i++) {
				if (book.getCopies()[i].getCopyNum() == bookCopyNum) {
					book.getCopies()[i].changeAvailability();
					break;
				}
			}
			dataAccess.saveEditBook(isbn, book);
			return true;
		} else
			return false;

	}

	@Override
	public void printCheckoutRecordEntry(String memberId) {
		LibraryMember member = this.getLibraryMemberById(memberId);
		System.out.printf("%-20s %-20s %-20s %-20s\n", "isbn", "copyNum", "checkoutDate", "dueDate");
		if (member != null) {
			List<CheckoutRecordEntry> entries = member.getCheckoutRecord().getCheckoutRecordEntries();
			if (entries != null && entries.size() > 0) {
				for (CheckoutRecordEntry entry : entries) {
					System.out.printf("%-20s %-20s %-20s %-20s\n", entry.getIsbn(), entry.getBookCopy().getCopyNum(),
							entry.getCheckoutDate(), entry.getDueDate());
				}
			}
		} else {
			System.out.printf("No data found\n\n");
		}

	}

	@Override
	public List<CheckoutRecordEntry> getOverdueMemberList(String isbn) {
		List<CheckoutRecordEntry> resultList = new ArrayList<CheckoutRecordEntry>();
		List<LibraryMember> members = this.getLibraryMembers();
		if (members != null && members.size() > 0) {
			for (LibraryMember member : members) {
				List<CheckoutRecordEntry> entries = member.getCheckoutRecord().getCheckoutRecordEntries();
				if (entries != null && entries.size() > 0) {
					for (CheckoutRecordEntry entry : entries) {
						if (entry.getCheckoutDate().isBefore(LocalDate.now())
								&& entry.getDueDate().isBefore(LocalDate.now()) && !entry.getBookCopy().isAvailable()
								&& entry.getReturnDate() == null) {
							entry.setMemberId(member.getMemberId());
							resultList.add(entry);
						}
					}
				}
			}
		}
		return resultList;
	}

}
