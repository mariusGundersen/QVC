package qvc.handlers.factory;

import qvc.handlers.ExecutableHandler;

public interface HandlerFactory<T extends ExecutableHandler> {
	public T create(Class<? extends T> classType, String sessionId) throws Exception;
}
