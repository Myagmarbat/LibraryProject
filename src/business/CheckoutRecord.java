package business;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

final public class CheckoutRecord implements Serializable {
	private static final long serialVersionUID = -1984035285805331755L;

	private List<CheckoutRecordEntry> entries;

	CheckoutRecord() {
		entries = new ArrayList<CheckoutRecordEntry>();
	}

	public void addCheckoutRecordEntry(CheckoutRecordEntry entry) {
		entries.add(entry);
	}

	public void setCheckoutRecordEntry(CheckoutRecordEntry entry, LocalDate date, boolean bReturn) {
		for (int i = 0; i < entries.size(); i++) {
			if (entries.get(i).getBookCopy().getBook().getIsbn().equals(entry.getIsbn())
					&& entries.get(i).getBookCopy().getCopyNum() == entry.getBookCopy().getCopyNum())
				if (bReturn)
					entries.get(i).setReturnDate(date);
				else
					entries.get(i).setDueDate(date);
		}
	}

	public List<CheckoutRecordEntry> getCheckoutRecordEntries() {
		return entries;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		if (entries != null && entries.size() > 0) {
			for (CheckoutRecordEntry entry : entries) {
				sb.append("(");
				sb.append("checkoutDate: ").append(entry.getCheckoutDate());
				sb.append(", dueDate: ").append(entry.getDueDate());
				sb.append(", number of bookcopy: ").append(entry.getBookCopy().getCopyNum());
				sb.append(", book: ").append(entry.getBookCopy().getBook().getIsbn());

				sb.append(") ");
			}
		}
		return sb.toString();
	}

}
