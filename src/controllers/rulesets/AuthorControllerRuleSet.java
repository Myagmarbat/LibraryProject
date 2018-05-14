package controllers.rulesets;

import java.awt.Component;

import business.SystemController;
import controllers.AuthorController;
import controllers.BaseController;



/**
 * Rules:
 *  1. All fields must be nonempty 
 *  2. Zip must be numeric with exactly 5 digits 
 *
 */

public class AuthorControllerRuleSet implements RuleSet {
	private AuthorController authorController;

	@Override
	public void applyRules(BaseController ob) throws RuleException {
		authorController = (AuthorController) ob;
		nonemptyRule();
		zipNumericRule();
	}
	
	private void nonemptyRule() throws RuleException {
		if (authorController.getFirstName().trim().isEmpty()
				|| authorController.getLastName().trim().isEmpty()
				|| authorController.getTelephone().trim().isEmpty()
				|| authorController.getBio().trim().isEmpty()
				|| authorController.getState().trim().isEmpty()
				|| authorController.getCity().trim().isEmpty()
				|| authorController.getStreet().trim().isEmpty()
				|| authorController.getZip().trim().isEmpty()
				) {
			throw new RuleException("All fields must be non-empty!");
		}
	}
	
	private void zipNumericRule() throws RuleException {
		String val = authorController.getZip().trim();
		try {
			// if exceed Integer.MAX_VALUE it also should display right message
			Long.parseLong(val);
			//val is numeric
		} catch(NumberFormatException e) {
			throw new RuleException("Zipcode must be numeric");
		}
		if(val.length() != 5) throw new RuleException("Zipcode must have 5 digits");
	}

}
