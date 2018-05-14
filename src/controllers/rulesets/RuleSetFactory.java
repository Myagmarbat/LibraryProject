package controllers.rulesets;

import java.util.HashMap;

import controllers.AuthorController;
import controllers.BaseController;
import controllers.BookController;
import controllers.LibraryMemberController;


final public class RuleSetFactory {
	private RuleSetFactory(){}
	static HashMap<Class<? extends BaseController>, RuleSet> map = new HashMap<>();
	static {
		map.put(LibraryMemberController.class, new LibraryMemberControllerRuleSet());
		map.put(BookController.class, new BookControllerRuleSet());
		map.put(AuthorController.class, new AuthorControllerRuleSet());
	}
	public static RuleSet getRuleSet(BaseController c) {
		Class<? extends BaseController> cl = c.getClass();
		if(!map.containsKey(cl)) {
			throw new IllegalArgumentException(
					"No RuleSet found for this Component");
		}
		return map.get(cl);
	}
}
