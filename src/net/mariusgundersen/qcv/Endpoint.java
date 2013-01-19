package net.mariusgundersen.qcv;

import net.mariusgundersen.qcv.executables.*;
import net.mariusgundersen.qcv.handlers.*;
import net.mariusgundersen.qcv.loader.HandlerFactory;
import net.mariusgundersen.qcv.loader.DefaultCommandHandlerFactory;
import net.mariusgundersen.qcv.loader.DefaultQueryHandleFactory;
import net.mariusgundersen.qcv.loader.TypeRepository;
import net.mariusgundersen.qcv.results.*;

@SuppressWarnings("rawtypes")
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

	@SuppressWarnings("unchecked")
	public CommandResult command(Command command) {
		try {
			CommandHandler handler = commandHandlerFromCommand(command.getClass());
			handler.handle(command);
			return new CommandResult();
		} catch (Throwable e) {
			return new CommandResult(e);
		}

	}

	@SuppressWarnings("unchecked")
	public QueryResult query(Query query) {
		try {
			QueryHandler handler = queryHandlerFromQuery(query.getClass());
			Object result = handler.handle(query);
			return new QueryResult(result);
		} catch (Throwable e) {
			return new QueryResult(e);
		}

	}

	private CommandHandler commandHandlerFromCommand(Class<? extends Command> commandClass) throws Exception {
		Class<? extends CommandHandler> commandHandler = loader.findCommandHandler(commandClass);
		return (CommandHandler) commandHandleCreater.create(commandHandler);
	}

	private QueryHandler queryHandlerFromQuery(Class<? extends Query> queryClass) throws Exception {
		Class<? extends QueryHandler> queryHandler = loader.findQueryHandler(queryClass);
		return (QueryHandler) queryHandleCreater.create(queryHandler);
	}
}
