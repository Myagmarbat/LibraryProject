package dataaccess;

import java.util.HashMap;
import java.util.List;

import business.Author;
import business.Book;
import business.LibraryMember;
import business.User;

public interface DataAccess {
	public HashMap<String,User> readUserMap();
	public HashMap<String, LibraryMember> readMemberMap();
	public HashMap<String, Author> readAuthorMap();
	public void saveNewMember(LibraryMember member);
	public void saveEditMember(String id, LibraryMember editMember);
	public void saveNewUser(User user);
	public void saveEditUser(String id, User editUser);

	public HashMap<String,Book> readBooksMap();
	public Book saveNewBook(Book book);
	public void saveEditBook(String id, Book editBook);

	public Author saveNewAuthor(Author author);
}
