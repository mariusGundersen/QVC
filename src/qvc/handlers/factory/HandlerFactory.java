package qvc.handlers.factory;

import qvc.handlers.Handler;

public interface HandlerFactory {
	public Handler create(Class<? extends Handler> classType, String sessionId) throws Exception;
}
