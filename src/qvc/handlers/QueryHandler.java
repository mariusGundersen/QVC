package qvc.handlers;

import qvc.executables.Query;


public interface QueryHandler extends Handler {
	public Object handle(Query query);
}
