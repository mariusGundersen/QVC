package qvc.validation.metadata;

import java.util.HashSet;
import java.util.Set;


public class ValidationConstraints {

	public final Set<Parameter> parameters;
	public final Exception exception;
	
	public ValidationConstraints(){
		this.parameters = new HashSet<Parameter>();
		this.exception = null;
	}

	public ValidationConstraints(Exception exception) {
		this.exception = exception;
		this.parameters = null;
	}

	public void addConstraint(Parameter constraint) {
		this.parameters.add(constraint);
	}

	
}
