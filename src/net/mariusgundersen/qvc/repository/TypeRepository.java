package net.mariusgundersen.qvc.repository;

import java.util.HashSet;
import java.util.Set;

import org.reflections.Reflections;

import net.mariusgundersen.qvc.exceptions.*;
import net.mariusgundersen.qvc.executables.*;
import net.mariusgundersen.qvc.handlers.*;

public class TypeRepository {
	
	private Set<Package> packages = new HashSet<Package>();

	private ExecutableMap<Command> commands = new ExecutableMap<Command>();
	private ExecutableMap<Query> queries = new ExecutableMap<Query>();
	private HandlerMap<Command, CommandHandler> commandHandlers = new HandlerMap<Command, CommandHandler>();
	private HandlerMap<Query, QueryHandler> queryHandlers = new HandlerMap<Query, QueryHandler>();
	
	public void addPackage(String packageName){
		packages.add(new Package(packageName));
	}
	
	public void loadCommandsAndQueries(){
		for (Package packageObject : packages) {
			queries.addClassesInPackage(packageObject, Query.class);
			commands.addClassesInPackage(packageObject, Command.class);
			commandHandlers.addClassesInPackage(packageObject, CommandHandler.class, Command.class);
			queryHandlers.addClassesInPackage(packageObject, QueryHandler.class, Query.class);
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
	
	class Package{
		public Reflections reflections;
		public String packageName;
		
		public Package(String packageName){
			this.packageName = packageName;
			this.reflections = new Reflections(packageName);
		}
		
		@Override
		public int hashCode() {
			return packageName.hashCode();
		}
				
		
	}

}
