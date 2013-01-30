package qvc.handlers.factory;

import qvc.handlers.CommandHandler;


public class DefaultCommandHandlerFactory implements HandlerFactory<CommandHandler> {

	@Override
	public CommandHandler create(Class<? extends CommandHandler> classType, String sessionId) throws Exception {
		try{
			return classType.getConstructor(String.class).newInstance(sessionId);
		}catch(NoSuchMethodException e){
			return classType.newInstance();
		}
	}

}