package qvc.validation.metadata;

import java.util.Map;

public class ParameterContsraint {

	
	public final String name;
	public final Map<String, Object> attributes;
	
	public ParameterContsraint(String ruleName, Map<String, Object> attributes) {
		this.name = ruleName;
		this.attributes = attributes;
	}
}
