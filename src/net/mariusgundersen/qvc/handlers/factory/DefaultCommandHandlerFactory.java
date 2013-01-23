package net.mariusgundersen.qvc.handlers.factory;

import net.mariusgundersen.qvc.handlers.CommandHandler;


public class DefaultCommandHandlerFactory implements HandlerFactory<CommandHandler> {

	@Override
	public CommandHandler create(Class<? extends CommandHandler> classType) throws Exception {
		return classType.newInstance();
	}

}
