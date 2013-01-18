package net.mariusgundersen.qcv.handlers;

import net.mariusgundersen.qcv.executables.Command;

public abstract class CommandHandler<C extends Command> {
	public abstract void Handle(C command);
}
