package net.mariusgundersen.qvc.validation;

import java.util.ArrayList;
import java.util.List;

public class ValidationResult {
	
	public boolean isValid;
	public List<Violation> violations = new ArrayList<Violation>();
	
	public ValidationResult(boolean isValid) {
		this.isValid = isValid;
	}
}
