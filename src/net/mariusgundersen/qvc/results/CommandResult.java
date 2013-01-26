package net.mariusgundersen.qvc.results;

import java.util.List;

import net.mariusgundersen.qvc.validation.ValidationResult;
import net.mariusgundersen.qvc.validation.Violation;

public class CommandResult {
	
	public final boolean success;
	public final boolean valid;
	public final Throwable exception;
	public final List<Violation> violations;
	
	public CommandResult(Throwable exception){
		this.exception = exception;
		this.success = false;
		this.valid = false;
		this.violations = null;
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
		this.violations = null;
	}
}
