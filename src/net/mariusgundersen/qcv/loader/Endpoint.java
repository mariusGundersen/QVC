package net.mariusgundersen.qcv.loader;

import net.mariusgundersen.qcv.executables.*;
import net.mariusgundersen.qcv.handlers.*;
import net.mariusgundersen.qcv.results.*;

@SuppressWarnings("rawtypes")
public class Endpoint {
	
	private Loader loader;
	private Instancer<CommandHandler> instancer;

	public Endpoint(Loader loader, Instancer<CommandHandler> instancer) {
		this.loader = loader;
		this.instancer = instancer;
	}

	@SuppressWarnings("unchecked")
	public CommandResult command(Command command) {
		try {
			CommandHandler handler = commandHandlerFromCommand(command.getClass());
			handler.Handle(command);
			return new CommandResult();
		} catch (Throwable e) {
			return new CommandResult(e);
		}

	}

	@SuppressWarnings("unchecked")
	public QueryResult query(Query query) {
		try {
			QueryHandler handler = queryHandlerFromQuery(query.getClass());
			Object result = handler.Handle(query);
			return new QueryResult(result);
		} catch (Throwable e) {
			return new QueryResult(e);
		}

	}

	private CommandHandler commandHandlerFromCommand(Class<? extends Command> commandClass) throws InstantiationException, IllegalAccessException {
		Class<? extends CommandHandler> commandHandler = loader.findCommandHandler(commandClass);
		return (CommandHandler) instancer.create(commandHandler);
	}

	private QueryHandler queryHandlerFromQuery(Class<? extends Query> queryClass) throws InstantiationException, IllegalAccessException {
		Class<? extends QueryHandler> queryHandler = loader.findQueryHandler(queryClass);
		return queryHandler.newInstance();
	}
}
