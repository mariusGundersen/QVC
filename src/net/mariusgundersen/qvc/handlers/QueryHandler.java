package net.mariusgundersen.qvc.handlers;

import net.mariusgundersen.qvc.executables.Query;


public abstract class QueryHandler implements Handler {
	public Object handle(Query query){
		return "default";
	}
}
