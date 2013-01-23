package net.mariusgundersen.qvc.handlers.factory;

import net.mariusgundersen.qvc.handlers.Handler;

public interface HandlerFactory<T extends Handler> {
	public T create(Class<? extends T> classType) throws Exception;
}
