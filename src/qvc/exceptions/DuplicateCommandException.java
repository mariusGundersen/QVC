package qvc.exceptions;

public class DuplicateCommandException extends Exception {
	private static final long serialVersionUID = -4453404892266381075L;

	public DuplicateCommandException(String name) {
		super(name);
	}
}
