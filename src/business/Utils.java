package business;

import java.util.List;

public class Utils {
	public static String retrieveAuthorText(List<Author> selectedAuthors) {	
		StringBuilder sb = new StringBuilder();
		if(selectedAuthors != null && selectedAuthors.size() > 0) {
			int cnt = 0;
			for(Author a: selectedAuthors) {				
				if(cnt != 0) sb.append("\r\n");
				sb.append(a.getFirstName()+" "+a.getLastName());
				cnt ++;
			}
		}
		return sb.toString();
	}
	
}
