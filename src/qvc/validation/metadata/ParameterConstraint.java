package qvc.validation.metadata;

import java.util.HashSet;
import java.util.Set;


public class ParameterConstraint {
	public final String name;
	public final Set<ValidationRule> rules = new HashSet<ValidationRule>();
	
	public ParameterConstraint(String name) {
		this.name = name;
	}

	public void addRule(ValidationRule rule) {
		rules.add(rule);
	}
	
}
