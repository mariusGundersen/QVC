package net.mariusgundersen.qcv.handlers.factory;

import net.mariusgundersen.qcv.handlers.CommandHandler;


public class DefaultCommandHandlerFactory implements HandlerFactory<CommandHandler> {

	@Override
	public CommandHandler create(Class<? extends CommandHandler> classType) throws Exception {
		return classType.newInstance();
	}

}
