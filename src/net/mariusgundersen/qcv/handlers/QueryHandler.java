package net.mariusgundersen.qcv.handlers;

import net.mariusgundersen.qcv.executables.Query;

public abstract class QueryHandler <Q extends Query> {
	public abstract Object Handle(Q query);

}
