package controllers.rulesets;

import controllers.BaseController;

public interface RuleSet {
	public void applyRules(BaseController ob) throws RuleException;
}
