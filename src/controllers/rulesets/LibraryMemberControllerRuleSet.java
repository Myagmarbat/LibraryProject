package controllers.rulesets;

import java.awt.Component;

import business.SystemController;
import controllers.BaseController;
import controllers.LibraryMemberController;

/**
 * Rules: 1. All fields must be nonempty 2. ID field must be numeric 3. Zip must
 * be numeric with exactly 5 digits
 *
 */

public class LibraryMemberControllerRuleSet implements RuleSet {
	private LibraryMemberController libMemberController;

	@Override
	public void applyRules(BaseController ob) throws RuleException {
		libMemberController = (LibraryMemberController) ob;

		nonemptyRule();
		stateRule();
		idNumericRule();
		zipNumericRule();
	}

	private void nonemptyRule() throws RuleException {
		if (libMemberController.getMemberId().trim().isEmpty() || libMemberController.getFirstName().trim().isEmpty()
				|| libMemberController.getLastName().trim().isEmpty()
				|| libMemberController.getPhoneNumber().trim().isEmpty()
				|| libMemberController.getState().trim().isEmpty() || libMemberController.getCity().trim().isEmpty()
				|| libMemberController.getStreet().trim().isEmpty() || libMemberController.getZip().trim().isEmpty()) {
			throw new RuleException("All fields must be non-empty!");
		}
	}

	private void stateRule() throws RuleException {
		String val = libMemberController.getState().trim();
		try {
			if (val.length() != 2)
				throw new RuleException("State must consists 2 alphabet!");
		} catch (NumberFormatException e) {
			throw new RuleException("State must consists 2 alphabet!");
		}
	}

	private void idNumericRule() throws RuleException {
		String val = libMemberController.getMemberId().trim();
		try {
			// if exceed Integer.MAX_VALUE it also should display right message
			Long.parseLong(val);
			// val is numeric
		} catch (NumberFormatException e) {
			throw new RuleException("ID must be numeric");
		}
	}

	private void zipNumericRule() throws RuleException {
		String val = libMemberController.getZip().trim();
		try {
			// if exceed Integer.MAX_VALUE it also should display right message
			Long.parseLong(val);
			// val is numeric
		} catch (NumberFormatException e) {
			throw new RuleException("Zipcode must be numeric");
		}
		if (val.length() != 5)
			throw new RuleException("Zipcode must have 5 digits");
	}

}
