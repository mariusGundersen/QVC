package net.mariusgundersen.qvc.exceptions;

public class ExecutableDoesNotExistException extends Exception{
	private static final long serialVersionUID = -6227893508230917604L;

	public ExecutableDoesNotExistException(String name) {
		super(name+" is not a Command or a Query");
	}
}
