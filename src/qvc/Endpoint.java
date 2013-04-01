package qvc;

import qvc.executables.*;
import qvc.handlers.*;
import qvc.handlers.factory.DefaultCommandHandlerFactory;
import qvc.handlers.factory.DefaultQueryHandlerFactory;
import qvc.handlers.factory.HandlerFactory;
import qvc.repository.TypeRepository;
import qvc.results.*;
import qvc.validation.ExecutableValidator;
import qvc.validation.ValidationResult;
import qvc.validation.metadata.ValidationConstraints;

public class Endpoint {
	
	private TypeRepository loader;
	private HandlerFactory<CommandHandler> commandHandlerFactory;
	private HandlerFactory<QueryHandler> queryHandlerFactory;
	private ExecutableValidator validator;

	public Endpoint(TypeRepository loader) {
		this.loader = loader;
		this.commandHandlerFactory = new DefaultCommandHandlerFactory();
		this.queryHandlerFactory = new DefaultQueryHandlerFactory();
		this.validator = new ExecutableValidator();
	}

	public void SetCommandHandlerCreater(HandlerFactory<CommandHandler> creater){
		this.commandHandlerFactory = creater;
	}
	
	public void SetQueryHandlerCreater(HandlerFactory<QueryHandler> creater){
		this.queryHandlerFactory = creater;
	}
	
	public ValidationConstraints constraints(Class<? extends Executable> executable){
		return validator.validationConstraints(executable);
	}

	public CommandResult command(Command command, String sessionId) {
		try {
			ValidationResult validationResult = validate(command);
			if(validationResult.isValid){
				return handle(command, sessionId);
			}else{
				return new CommandResult(validationResult);
			}
		} catch (Throwable e) {
			return new CommandResult(e);
		}
	}

	public QueryResult query(Query query, String sessionId) {
		try {
			ValidationResult validationResult = validate(query);
			if(validationResult.isValid){
				return handle(query, sessionId);
			}else{
				return new QueryResult(validationResult);
			}
		} catch (Throwable e) {
			return new QueryResult(e);
		}
	}
	
	private ValidationResult validate(Executable executable){
		return validator.validate(executable);
	}
	
	private CommandResult handle(Command command, String sessionId) throws Exception{
		CommandHandler handler = commandHandlerFromCommand(command.getClass(), sessionId);
		handler.getClass().getMethod("handle", command.getClass()).invoke(handler, command);
		return new CommandResult();
	}

	private QueryResult handle(Query query, String sessionId) throws Exception{
		QueryHandler handler = queryHandlerFromQuery(query.getClass(), sessionId);
		Object result = handler.getClass().getMethod("handle", query.getClass()).invoke(handler, query);
		return new QueryResult(result);		
	}

	private CommandHandler commandHandlerFromCommand(Class<? extends Command> commandClass, String sessionId) throws Exception {
		Class<? extends CommandHandler> commandHandler = loader.findCommandHandler(commandClass);
		return commandHandlerFactory.create(commandHandler, sessionId);
	}

	private QueryHandler queryHandlerFromQuery(Class<? extends Query> queryClass, String sessionId) throws Exception {
		Class<? extends QueryHandler> queryHandler = loader.findQueryHandler(queryClass);
		return queryHandlerFactory.create(queryHandler, sessionId);
	}
}
