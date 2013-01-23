package net.mariusgundersen.qvc.handlers.factory;

import net.mariusgundersen.qvc.handlers.QueryHandler;


public class DefaultQueryHandleFactory implements HandlerFactory<QueryHandler> {

	@Override
	public QueryHandler create(Class<? extends QueryHandler> classType) throws Exception {
		return classType.newInstance();
	}

}
