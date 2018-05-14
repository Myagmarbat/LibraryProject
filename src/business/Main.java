package business;

import java.util.*;

import dataaccess.DataAccess;
import dataaccess.DataAccessFacade;

public class Main {

	public static void main(String[] args) {
		System.out.println(allWhoseZipContains3());
		System.out.println(allHavingOverdueBook());
		System.out.println(allHavingMultipleAuthors());
		SystemController sc = new SystemController();
		System.out.println(sc.getLibraryMemberById("1003"));

		System.out.println("sc = " + sc.getLibraryMembers());
		System.out.println("sc = " + sc.getBooks());
		System.out.println( sc.getCheckoutRecordEntries("1003") );
		
		sc.addCheckoutRecordEntry("1003", "48-56882");
		sc.addCheckoutRecordEntry("1003", "99-22223");		
		LibraryMember lm = sc.getLibraryMemberById("1003");		
		System.out.println( lm.getCheckoutRecord());
		

	}
	//Returns a list of all ids of LibraryMembers whose zipcode contains the digit 3
	public static List<String> allWhoseZipContains3() {
		DataAccess da = new DataAccessFacade();
		Collection<LibraryMember> members = da.readMemberMap().values();
		List<LibraryMember> mems = new ArrayList<>();
		mems.addAll(members);
		//implement
		return null;

	}
	//Returns a list of all ids of  LibraryMembers that have an overdue book
	public static List<String> allHavingOverdueBook() {
		DataAccess da = new DataAccessFacade();
		Collection<LibraryMember> members = da.readMemberMap().values();
		List<LibraryMember> mems = new ArrayList<>();
		mems.addAll(members);
		//implement
		return null;

	}

	//Returns a list of all isbns of  Books that have multiple authors
	public static List<String> allHavingMultipleAuthors() {
		DataAccess da = new DataAccessFacade();
		Collection<Book> books = da.readBooksMap().values();
		List<Book> bs = new ArrayList<>();
		bs.addAll(books);
		//implement
		return null;
	}



}
