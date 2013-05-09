package qvc.handlers.factory;

import java.util.HashMap;
import java.util.Map;

import qvc.exceptions.CommandHandlerDoesNotExistException;
import qvc.handlers.Handler;


public class SingletonCommandHandlerFactory implements HandlerFactory{

	private final Map<Class<? extends Handler>, Handler> handlerMap = new HashMap<Class<? extends Handler>, Handler>();
	
	@Override
	public Handler create(Class<? extends Handler> classType, String sessionId) throws Exception {
		if(handlerMap.containsKey(classType)){
			return handlerMap.get(classType);
		}else{
			throw new CommandHandlerDoesNotExistException(classType.getSimpleName());
		}
	}
	
	
	public void addHandler(Handler handler){
		handlerMap.put(handler.getClass(), handler);
	}

}
