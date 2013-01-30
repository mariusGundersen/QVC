package qvc.exceptions;

public class CommandHandlerDoesNotExistException extends Exception {
	private static final long serialVersionUID = -2028996817373590797L;

	public CommandHandlerDoesNotExistException(String name) {
		super("Command handler for command " + name + " does not exist");
	}
}
