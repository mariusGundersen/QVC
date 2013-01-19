package net.mariusgundersen.qcv.handlers;

import net.mariusgundersen.qcv.executables.Command;

public abstract class CommandHandler<C extends Command> implements Handler {
	public abstract void handle(C command);
}
