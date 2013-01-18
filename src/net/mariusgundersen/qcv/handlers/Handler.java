package net.mariusgundersen.qcv.handlers;

import net.mariusgundersen.qcv.executables.Command;

public interface Handler {
	public CommandHandler<Command> handler();
}
