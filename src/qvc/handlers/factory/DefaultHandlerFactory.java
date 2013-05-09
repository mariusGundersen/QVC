package qvc.handlers.factory;

import qvc.handlers.Handler;


public class DefaultHandlerFactory implements HandlerFactory {

	@Override
	public Handler create(Class<? extends Handler> classType, String sessionId) throws Exception {
		try{
			Handler handler = classType.getConstructor(String.class).newInstance();
			handler.setSessionId(sessionId);
			return handler;
		}catch(NoSuchMethodException e){
			return classType.newInstance();
		}
	}

}
