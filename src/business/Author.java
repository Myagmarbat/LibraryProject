package business;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

final public class Author extends Person implements Serializable {
	private static final long serialVersionUID = 7508481940058530471L;	 
	
	private String bio;
	private List<Book> books;
	
	public String getBio() {
		return bio;
	}
	
	public Author(String f, String l, String t, Address a, String bio) {
		super(f, l, t, a);
		this.bio = bio;
		books = new ArrayList<Book>();		
	}
	
	public List<Book> getBooks() {
		return books;
	}

	@Override
	public String toString() {
		return super.getLastName() + ", " + super.getFirstName() + ", " + super.getTelephone() + ", " + super.getAddress();
	}

}
