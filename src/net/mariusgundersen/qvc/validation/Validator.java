package net.mariusgundersen.qvc.validation;


import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.ValidatorFactory;

import net.mariusgundersen.qvc.executables.Executable;
public class Validator {

	public static ValidationResult validate(Executable executable){
		
		ValidatorFactory vf = Validation.buildDefaultValidatorFactory();
		
		javax.validation.Validator v = vf.getValidator();
		
		Set<ConstraintViolation<Executable>> violations = v.validate(executable);
		

		ValidationResult result = new ValidationResult(violations.size() == 0);
		
		
		for(ConstraintViolation<Executable> violation: violations){
			System.out.println(violation.getPropertyPath()+": "+violation.getMessage());
			result.violations.add(new Violation(violation.getPropertyPath(), violation.getMessage()));
		}
		
		
		return result;
	}
}
