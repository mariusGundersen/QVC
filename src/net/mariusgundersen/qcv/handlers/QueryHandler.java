package net.mariusgundersen.qcv.handlers;

import net.mariusgundersen.qcv.executables.Query;

public abstract class QueryHandler <Q extends Query> implements Handler {
	public abstract Object handle(Q query);

}
