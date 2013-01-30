package qvc.handlers.factory;

import qvc.handlers.QueryHandler;


public class DefaultQueryHandlerFactory implements HandlerFactory<QueryHandler> {

	@Override
	public QueryHandler create(Class<? extends QueryHandler> classType, String sessionId) throws Exception {
		try{
			return classType.getConstructor(String.class).newInstance(sessionId);
		}catch(NoSuchMethodException e){
			return classType.newInstance();
		}
	}

}
