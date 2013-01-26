package net.mariusgundersen.qvc.results;

import java.util.List;

import net.mariusgundersen.qvc.validation.ValidationResult;
import net.mariusgundersen.qvc.validation.Violation;

public class QueryResult {
	
	public final boolean success;
	public final boolean valid;
	public final Object result;
	public final Throwable exception;
	public final List<Violation> violations;
	
	public QueryResult(Object result){
		this.success = true;
		this.valid = true;
		this.result = result;
		this.exception = null;
		this.violations = null;
	}
	
	public QueryResult(ValidationResult validationResult){
		this.valid = validationResult.isValid;
		this.exception = null;
		this.success = this.valid;
		this.violations = validationResult.violations;
		this.result = null;
	}
	
	public QueryResult(Throwable exception){
		this.exception = exception;
		this.success = false;
		this.valid = false;
		this.result = null;
		this.violations = null;
	}
	
	public QueryResult(){
		this.success = true;
		this.exception = null;
		this.valid = false;
		this.violations = null;
		this.result = null;
	}
}
