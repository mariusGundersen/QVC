package qvc.exceptions;

public class DuplicateQueryException extends Exception {
	private static final long serialVersionUID = -4453404892266381075L;

	public DuplicateQueryException(String name) {
		super(name);
	}
}
