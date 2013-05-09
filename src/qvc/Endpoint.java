package qvc;

import java.lang.reflect.InvocationTargetException;

import qvc.executables.*;
import qvc.handlers.Handler;
import qvc.handlers.factory.DefaultHandlerFactory;
import qvc.handlers.factory.HandlerFactory;
import qvc.repository.TypeRepository;
import qvc.results.*;
import qvc.validation.ExecutableValidator;
import qvc.validation.ValidationResult;
import qvc.validation.metadata.ValidationConstraints;

public class Endpoint {
	
	private TypeRepository loader;
	private HandlerFactory handlerFactory;
	private ExecutableValidator validator;

	public Endpoint(TypeRepository loader) {
		this.loader = loader;
		this.handlerFactory = new DefaultHandlerFactory();
		this.validator = new ExecutableValidator();
	}

	public void setHandlerFactory(HandlerFactory factory){
		this.handlerFactory = factory;
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
		} catch(InvocationTargetException e){
			return new CommandResult(e.getCause());
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
		} catch(InvocationTargetException e){
			return new QueryResult(e.getCause());
		} catch (Throwable e) {
			return new QueryResult(e);
		}
	}
	
	private ValidationResult validate(Executable executable){
		return validator.validate(executable);
	}
	
	private CommandResult handle(Command command, String sessionId) throws Exception{
		Handler handler = createHandlerFromCommand(command.getClass(), sessionId);
		handler.getClass().getMethod("handle", command.getClass()).invoke(handler, command);
		return new CommandResult();
	}

	private QueryResult handle(Query query, String sessionId) throws Exception{
		Handler handler = createHandlerFromQuery(query.getClass(), sessionId);
		Object result = handler.getClass().getMethod("handle", query.getClass()).invoke(handler, query);
		return new QueryResult(result);		
	}

	private Handler createHandlerFromCommand(Class<? extends Command> commandClass, String sessionId) throws Exception {
		Class<? extends Handler> handler = loader.findCommandHandler(commandClass);
		return handlerFactory.create(handler, sessionId);
	}

	private Handler createHandlerFromQuery(Class<? extends Query> queryClass, String sessionId) throws Exception {
		Class<? extends Handler> handler = loader.findQueryHandler(queryClass);
		return handlerFactory.create(handler, sessionId);
	}
}
