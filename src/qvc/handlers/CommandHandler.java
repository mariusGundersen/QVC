package qvc.handlers;

import qvc.executables.Command;


public interface CommandHandler extends ExecutableHandler {
	public void handle(Command command);
}
