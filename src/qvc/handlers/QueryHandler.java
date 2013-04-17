package qvc.handlers;

import qvc.executables.Query;


public interface QueryHandler extends ExecutableHandler {
	public Object handle(Query query);
}
