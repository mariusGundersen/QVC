package qvc.handlers;

import qvc.executables.Command;


public interface CommandHandler extends Handler {
	public void handle(Command command);
}
