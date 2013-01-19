package net.mariusgundersen.qcv.handlers.factory;

import net.mariusgundersen.qcv.handlers.QueryHandler;


public class DefaultQueryHandleFactory implements HandlerFactory<QueryHandler> {

	@Override
	public QueryHandler create(Class<? extends QueryHandler> classType) throws Exception {
		return classType.newInstance();
	}

}
