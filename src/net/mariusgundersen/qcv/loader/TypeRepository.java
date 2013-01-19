package net.mariusgundersen.qcv.loader;

import java.util.HashSet;
import java.util.Set;

import org.reflections.Reflections;

import net.mariusgundersen.qcv.exceptions.*;
import net.mariusgundersen.qcv.executables.*;
import net.mariusgundersen.qcv.handlers.*;

public class TypeRepository {
	
	private Set<Reflections> packages = new HashSet<Reflections>();

	private ExecutableMap<Command> commands = new ExecutableMap<Command>();
	private ExecutableMap<Query> queries = new ExecutableMap<Query>();
	private HandlerMap<Command, CommandHandler> commandHandlers = new HandlerMap<Command, CommandHandler>();
	private HandlerMap<Query, QueryHandler> queryHandlers = new HandlerMap<Query, QueryHandler>();
	
	public void addPackage(String packageName){
		packages.add(new Reflections(packageName));
	}
	
	public void loadCommandsAndQueries(){
		for (Reflections reflection : packages) {
			queries.addClassesInPackage(reflection, Query.class);
			commands.addClassesInPackage(reflection, Command.class);
			commandHandlers.addClassesInPackage(reflection, CommandHandler.class, Command.class);
			queryHandlers.addClassesInPackage(reflection, QueryHandler.class, Query.class);
		}
	}

	public Class<? extends Command> findCommand(String name) throws CommandDoesNotExistException {
		if(commands.has(name)) {
			return commands.get(name);
		} else {
			throw new CommandDoesNotExistException(name);
		}
	}
	
	public Class<? extends Query> findQuery(String name) throws QueryDoesNotExistException {
		if (queries.has(name)) {
			return queries.get(name);
		} else {
			throw new QueryDoesNotExistException(name);
		}
	}
	
	public Class<? extends CommandHandler> findCommandHandler(Class<? extends Command> command) throws CommandHandlerDoesNotExistException{
		if (commandHandlers.has(command)) {
			return commandHandlers.get(command);
		} else {
			throw new CommandHandlerDoesNotExistException(command.getSimpleName());
		}
	}
	
	public Class<? extends QueryHandler> findQueryHandler(Class<? extends Query> query) throws QueryHandlerDoesNotExistException{
		if (queryHandlers.has(query)) {
			return queryHandlers.get(query);
		} else {
			throw new QueryHandlerDoesNotExistException(query.getSimpleName());
		}
	}

}
