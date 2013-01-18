package net.mariusgundersen.qcv.loader;

import net.mariusgundersen.qcv.handlers.CommandHandler;


@SuppressWarnings("rawtypes")
public class DefaultCommandHandlerInstancer implements Instancer<CommandHandler> {

	@Override
	public CommandHandler create(Class<? extends CommandHandler> classType) {
		try {
			return classType.newInstance();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return null;
	}

}
