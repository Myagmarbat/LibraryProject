package business;

import java.io.Serializable;
import java.time.LocalDate;

public final class CheckoutRecordEntry implements Serializable{
	private static final long serialVersionUID = 2200053462678672007L;

	private BookCopy bookCopy;
	private LocalDate checkoutDate;
	private LocalDate dueDate;
	private LocalDate returnDate;
	// this is not association it's only for print overdue list purpose
	private String memberId;

	CheckoutRecordEntry(BookCopy bookCopy, LocalDate checkoutDate, LocalDate dueDate) {
		this.bookCopy = bookCopy;
		this.checkoutDate = checkoutDate;
		this.dueDate = dueDate;
	}

	public BookCopy getBookCopy() {
		return bookCopy;
	}

	public LocalDate getCheckoutDate() {
		return checkoutDate;
	}

	public LocalDate getDueDate() {
		return dueDate;
	}
	
	public LocalDate getReturnDate() {
		return returnDate;
	}
	
	public void setReturnDate(LocalDate rDate) {
		returnDate = rDate;
	}
	
	public String getIsbn() {
		if(bookCopy != null && bookCopy.getBook() != null && !bookCopy.getBook().getIsbn().equals("")) {
			return bookCopy.getBook().getIsbn();
		}
		return "";		
	}

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}
	
	public void setDueDate(LocalDate date) {
		this.dueDate = date;
	}

}
