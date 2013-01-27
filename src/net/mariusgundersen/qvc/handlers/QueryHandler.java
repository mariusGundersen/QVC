package net.mariusgundersen.qvc.handlers;

import net.mariusgundersen.qvc.executables.Query;


public interface QueryHandler extends Handler {
	public Object handle(Query query);
}
