package net.mariusgundersen.qvc.exceptions;

public class CommandDoesNotExistException extends Exception {
	private static final long serialVersionUID = 7801233566963345754L;

	public CommandDoesNotExistException(String name) {
		super("Command "+name+" does not exist");
	}
}
