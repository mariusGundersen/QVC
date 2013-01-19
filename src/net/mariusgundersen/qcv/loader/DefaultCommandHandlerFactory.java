package net.mariusgundersen.qcv.loader;

import net.mariusgundersen.qcv.handlers.CommandHandler;


@SuppressWarnings("rawtypes")
public class DefaultCommandHandlerFactory implements HandlerFactory<CommandHandler> {

	@Override
	public CommandHandler create(Class<? extends CommandHandler> classType) throws Exception {
		return classType.newInstance();
	}

}
