package controllers.rulesets;

import java.awt.Component;

import business.SystemController;
import controllers.BaseController;
import controllers.BookController;



/**
 * Rules:
 *  1. All fields must be nonempty 
 *  2. Duplicated isbn
 *
 */

public class BookControllerRuleSet implements RuleSet {
	private BookController bookController;

	@Override
	public void applyRules(BaseController ob) throws RuleException {
		bookController = (BookController) ob;
		nonemptyRule();
		checkDuplicateRule();
	}
	private void checkDuplicateRule() throws RuleException{
		SystemController sc = new SystemController();
		
		if(sc.getBookById(bookController.getIsbn())!=null)  {
				throw new RuleException("ISBN must be unique");
		}
	}
	private void nonemptyRule() throws RuleException {
		if (bookController.getIsbn().trim().isEmpty()
				|| bookController.getTitle().trim().isEmpty()
				|| bookController.getCopyCount().trim().isEmpty()
				|| bookController.getCheckoutDays().trim().isEmpty()) {
			throw new RuleException("All fields must be non-empty!");
		}
	}

}
