package net.mariusgundersen.qcv.loader;

import net.mariusgundersen.qcv.handlers.Handler;

public interface HandlerFactory<T extends Handler> {
	public T create(Class<? extends T> classType) throws Exception;
}
