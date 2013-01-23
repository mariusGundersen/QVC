package net.mariusgundersen.qvc.exceptions;

public class QueryDoesNotExistException extends Exception {
	private static final long serialVersionUID = -7703975481588313882L;

	public QueryDoesNotExistException(String name) {
		super("Query "+name+" does not exist");
	}
}
