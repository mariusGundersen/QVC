package qvc.validation.metadata;

import java.util.HashSet;
import java.util.Set;


public class Parameter {
	public final String name;
	public final Set<ParameterContsraint> constraints = new HashSet<ParameterContsraint>();
	
	public Parameter(String name) {
		this.name = name;
	}

	public void addRule(ParameterContsraint rule) {
		constraints.add(rule);
	}
	
}
