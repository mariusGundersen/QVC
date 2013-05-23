package qvc.validation;

import javax.validation.MessageInterpolator.Context;
import javax.validation.metadata.ConstraintDescriptor;

public class MetaDataContext implements Context {

	private ConstraintDescriptor<?> constraintDescriptor;

	public MetaDataContext(ConstraintDescriptor<?> constraintDescriptor) {
		this.constraintDescriptor = constraintDescriptor;
	}

	@Override
	public ConstraintDescriptor<?> getConstraintDescriptor() {
		return constraintDescriptor;
	}

	@Override
	public Object getValidatedValue() {
		return "{value}";
	}

}
