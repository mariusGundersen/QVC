package qvc.handlers.factory;

import java.util.HashMap;
import java.util.Map;

import qvc.exceptions.QueryHandlerDoesNotExistException;
import qvc.handlers.QueryHandler;

public class SingletonQueryHandlerFactory implements HandlerFactory<QueryHandler>{

	private final Map<Class<? extends QueryHandler>, QueryHandler> handlerMap = new HashMap<Class<? extends QueryHandler>, QueryHandler>();
	
	@Override
	public QueryHandler create(Class<? extends QueryHandler> classType, String sessionId) throws Exception {
		if(handlerMap.containsKey(classType)){
			return handlerMap.get(classType);
		}else{
			throw new QueryHandlerDoesNotExistException(classType.getSimpleName());
		}
	}
	
	
	public void addHandler(QueryHandler handler){
		handlerMap.put(handler.getClass(), handler);
	}

}
