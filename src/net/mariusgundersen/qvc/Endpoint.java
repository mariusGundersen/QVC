package net.mariusgundersen.qvc;

import net.mariusgundersen.qvc.executables.*;
import net.mariusgundersen.qvc.handlers.*;
import net.mariusgundersen.qvc.handlers.factory.DefaultCommandHandlerFactory;
import net.mariusgundersen.qvc.handlers.factory.DefaultQueryHandleFactory;
import net.mariusgundersen.qvc.handlers.factory.HandlerFactory;
import net.mariusgundersen.qvc.repository.TypeRepository;
import net.mariusgundersen.qvc.results.*;
import net.mariusgundersen.qvc.validation.ValidationResult;
import net.mariusgundersen.qvc.validation.Validator;

public class Endpoint {
	
	private TypeRepository loader;
	private HandlerFactory<CommandHandler> commandHandleCreater;
	private HandlerFactory<QueryHandler> queryHandleCreater;

	public Endpoint(TypeRepository loader) {
		this.loader = loader;
		this.commandHandleCreater = new DefaultCommandHandlerFactory();
		this.queryHandleCreater = new DefaultQueryHandleFactory();
	}

	public void SetCommandHandlerCreater(HandlerFactory<CommandHandler> creater){
		this.commandHandleCreater = creater;
	}
	
	public void SetQueryHandlerCreater(HandlerFactory<QueryHandler> creater){
		this.queryHandleCreater = creater;
	}

	public CommandResult command(Command command) {
		try {
			ValidationResult validationResult = validate(command);
			if(validationResult.isValid){
				return handle(command);
			}else{
				return new CommandResult(validationResult);
			}
		} catch (Throwable e) {
			return new CommandResult(e);
		}
	}

	public QueryResult query(Query query) {
		try {
			ValidationResult validationResult = validate(query);
			if(validationResult.isValid){
				return handle(query);
			}else{
				return new QueryResult(validationResult);
			}
		} catch (Throwable e) {
			return new QueryResult(e);
		}
	}
	
	private ValidationResult validate(Executable executable){
		return Validator.validate(executable);
	}
	
	private CommandResult handle(Command command) throws Exception{
		CommandHandler handler = commandHandlerFromCommand(command.getClass());
		handler.getClass().getMethod("handle", command.getClass()).invoke(handler, command);
		return new CommandResult();
	}
	
	private QueryResult handle(Query query) throws Exception{
		QueryHandler handler = queryHandlerFromQuery(query.getClass());
		Object result = handler.getClass().getMethod("handle", query.getClass()).invoke(handler, query);
		return new QueryResult(result);		
	}

	private CommandHandler commandHandlerFromCommand(Class<? extends Command> commandClass) throws Exception {
		Class<? extends CommandHandler> commandHandler = loader.findCommandHandler(commandClass);
		return commandHandleCreater.create(commandHandler);
	}

	private QueryHandler queryHandlerFromQuery(Class<? extends Query> queryClass) throws Exception {
		Class<? extends QueryHandler> queryHandler = loader.findQueryHandler(queryClass);
		return queryHandleCreater.create(queryHandler);
	}
}
