package net.mariusgundersen.qvc.results;

import java.util.List;

import net.mariusgundersen.qvc.validation.ValidationResult;
import net.mariusgundersen.qvc.validation.Violation;

public class CommandResult {
	
	public boolean success;
	public boolean valid;
	public String commandName;
	public Throwable exception;
	public List<Violation> violations;
	
	public CommandResult(Throwable exception){
		this.exception = exception;
		this.success = false;
		this.valid = false;
	}
	
	public CommandResult(ValidationResult validationResult){
		this.valid = validationResult.isValid;
		this.exception = null;
		this.success = this.valid;
		this.violations = validationResult.violations;
	}
	
	public CommandResult(){
		this.success = true;
		this.exception = null;
		this.valid = true;
	}
}
