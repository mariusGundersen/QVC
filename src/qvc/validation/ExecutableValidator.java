package qvc.validation;


import java.util.Map;
import java.util.Set;

import javax.validation.*;
import javax.validation.metadata.*;

import qvc.executables.Executable;
import qvc.validation.metadata.Parameter;
import qvc.validation.metadata.ValidationConstraints;
import qvc.validation.metadata.ParameterContsraint;

public class ExecutableValidator {
	
	
	private Validator validator;
	
	public ExecutableValidator() {
		ValidatorFactory vf = Validation.buildDefaultValidatorFactory();
		
		validator = vf.getValidator();
		
	}
	
	public ValidationConstraints validationConstraints(Class<? extends Executable> executable){
		BeanDescriptor beanDescriptor = validator.getConstraintsForClass(executable);
		
		
		Set<PropertyDescriptor> propertyDescriptors = beanDescriptor.getConstrainedProperties();
		
		ValidationConstraints validationConstraints = new ValidationConstraints();
		
		for(PropertyDescriptor propertyDescriptor: propertyDescriptors){
			String name = propertyDescriptor.getPropertyName();
			Parameter constraint = new Parameter(name);
			
			
			for(ConstraintDescriptor<?> constraintDescriptor : propertyDescriptor.getConstraintDescriptors()){
				String ruleName = constraintDescriptor.getAnnotation().annotationType().getSimpleName();
				Map<String, Object> attributes = constraintDescriptor.getAttributes();
				
				ParameterContsraint rule = new ParameterContsraint(ruleName, attributes);
				
				constraint.addRule(rule);
				
			}
			validationConstraints.addConstraint(constraint);
		}
		
		return validationConstraints;
	}

	public ValidationResult validate(Executable executable){
		
		Set<ConstraintViolation<Executable>> violations = validator.validate(executable);
		

		ValidationResult result = new ValidationResult(violations.size() == 0);
		
		
		for(ConstraintViolation<Executable> violation: violations){
			System.out.println(violation.getPropertyPath()+": "+violation.getMessage());
			result.violations.add(new Violation(violation.getPropertyPath(), violation.getMessage()));
		}
		
		
		return result;
	}
}
