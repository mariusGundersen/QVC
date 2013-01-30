package qvc.handlers.factory;

import java.util.HashMap;
import java.util.Map;

import qvc.exceptions.CommandHandlerDoesNotExistException;
import qvc.handlers.CommandHandler;


public class SingletonCommandHandlerFactory implements HandlerFactory<CommandHandler>{

	private final Map<Class<? extends CommandHandler>, CommandHandler> handlerMap = new HashMap<Class<? extends CommandHandler>, CommandHandler>();
	
	@Override
	public CommandHandler create(Class<? extends CommandHandler> classType, String sessionId) throws Exception {
		if(handlerMap.containsKey(classType)){
			return handlerMap.get(classType);
		}else{
			throw new CommandHandlerDoesNotExistException(classType.getSimpleName());
		}
	}
	
	
	public void addHandler(CommandHandler handler){
		handlerMap.put(handler.getClass(), handler);
	}

}
