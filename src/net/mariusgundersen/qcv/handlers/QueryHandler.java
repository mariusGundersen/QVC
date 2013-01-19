package net.mariusgundersen.qcv.handlers;

import net.mariusgundersen.qcv.executables.Query;


public abstract class QueryHandler implements Handler {
	public Object handle(Query query){
		return "default";
	}
}
