package net.mariusgundersen.qvc.results;

import java.util.List;

import net.mariusgundersen.qvc.validation.ValidationResult;
import net.mariusgundersen.qvc.validation.Violation;

public class QueryResult {
	
	public boolean success;
	public boolean valid;
	public Object result;
	public String commandName;
	public Throwable exception;
	public List<Violation> violations;
	
	public QueryResult(Object result){
		this.success = true;
		this.valid = true;
		this.result = result;
	}
	
	public QueryResult(ValidationResult validationResult){
		this.valid = validationResult.isValid;
		this.exception = null;
		this.success = this.valid;
		this.violations = validationResult.violations;
	}
	
	public QueryResult(Throwable exception){
		this.exception = exception;
		this.success = false;
		this.valid = false;
	}
	
	public QueryResult(){
		this.success = true;
		this.exception = null;
		this.valid = false;
	}
}
