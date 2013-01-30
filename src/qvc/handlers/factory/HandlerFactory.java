package qvc.handlers.factory;

import qvc.handlers.Handler;

public interface HandlerFactory<T extends Handler> {
	public T create(Class<? extends T> classType, String sessionId) throws Exception;
}
