package net.mariusgundersen.qcv.loader;

import net.mariusgundersen.qcv.handlers.QueryHandler;


@SuppressWarnings("rawtypes")
public class DefaultQueryHandleFactory implements HandlerFactory<QueryHandler> {

	@Override
	public QueryHandler create(Class<? extends QueryHandler> classType) throws Exception {
		return classType.newInstance();
	}

}
