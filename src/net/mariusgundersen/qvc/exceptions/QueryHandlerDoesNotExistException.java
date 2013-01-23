package net.mariusgundersen.qvc.exceptions;

public class QueryHandlerDoesNotExistException extends Exception {
	private static final long serialVersionUID = -4288154219685847896L;

	public QueryHandlerDoesNotExistException(String name) {
		super("Query handler for query " + name + " does not exist");
	}
}
