package net.mariusgundersen.qcv.results;

public class QueryResult {
	
	public boolean success;
	public boolean valid;
	public Object result;
	public String commandName;
	public Throwable exception;
	
	public QueryResult(Object result){
		this.success = true;
		this.valid = true;
		this.result = result;
	}
	
	public QueryResult(Throwable exception){
		this.exception = exception;
		this.success = false;
		this.valid = false;
	}
	
	public QueryResult(){
		this.success = true;
		this.exception = null;
		this.valid = false;
	}
}
