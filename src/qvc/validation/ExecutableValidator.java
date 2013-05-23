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
	private MessageInterpolator messageInterpolator;
	
	public ExecutableValidator() {
		ValidatorFactory vf = Validation.buildDefaultValidatorFactory();
		messageInterpolator = vf.getMessageInterpolator();
		validator = vf.getValidator();
		
	}
	
	public ValidationConstraints validationConstraints(Class<? extends Executable> executable){
		BeanDescriptor beanDescriptor = validator.getConstraintsForClass(executable);
		
		
		Set<PropertyDescriptor> propertyDescriptors = beanDescriptor.getConstrainedProperties();
		
		ValidationConstraints validationConstraints = new ValidationConstraints();
		
		for(PropertyDescriptor propertyDescriptor: propertyDescriptors){
			String name = propertyDescriptor.getPropertyName();
			Parameter constraint = new Parameter(name);
			
			
			for(final ConstraintDescriptor<?> constraintDescriptor : propertyDescriptor.getConstraintDescriptors()){
				String ruleName = constraintDescriptor.getAnnotation().annotationType().getSimpleName();
				Map<String, Object> attributes = constraintDescriptor.getAttributes();
				attributes.put("message", messageInterpolator.interpolate((String) attributes.get("message"), new MetaDataContext(constraintDescriptor)));
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
