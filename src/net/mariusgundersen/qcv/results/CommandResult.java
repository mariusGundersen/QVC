package net.mariusgundersen.qcv.results;

public class CommandResult {
	
	public boolean success;
	public boolean valid;
	public String commandName;
	public Throwable exception;
	
	public CommandResult(Throwable exception){
		this.exception = exception;
		this.success = false;
		this.valid = false;
	}
	
	public CommandResult(){
		this.success = true;
		this.exception = null;
		this.valid = false;
	}
}
