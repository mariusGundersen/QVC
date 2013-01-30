package qvc.validation;

import javax.validation.Path;
import javax.validation.Path.Node;


public class Violation {
	public String fieldName;
	public String message;

	public Violation(Path path, String message) {
		
		
		for(Node property: path){
			this.fieldName = property.getName();
		}
		this.message = message;
	}

}
