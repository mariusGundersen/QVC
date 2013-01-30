package qvc.validation.metadata;

import java.util.HashSet;
import java.util.Set;


public class ValidationConstraints {

	public final Set<ParameterConstraint> constraints;
	public final Exception exception;
	
	public ValidationConstraints(){
		this.constraints = new HashSet<ParameterConstraint>();
		this.exception = null;
	}

	public ValidationConstraints(Exception exception) {
		this.exception = exception;
		this.constraints = null;
	}

	public void addConstraint(ParameterConstraint constraint) {
		this.constraints.add(constraint);
	}

	
}
