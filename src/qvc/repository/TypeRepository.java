package qvc.repository;

import java.util.HashSet;
import java.util.Set;

import org.reflections.Reflections;

import qvc.exceptions.*;
import qvc.executables.*;
import qvc.handlers.*;


public class TypeRepository {
	
	private Set<Package> packages = new HashSet<Package>();

	private ExecutableMap<Command> commands = new ExecutableMap<Command>();
	private ExecutableMap<Query> queries = new ExecutableMap<Query>();
	private HandlerMap<Command> commandHandlers = new HandlerMap<Command>();
	private HandlerMap<Query> queryHandlers = new HandlerMap<Query>();
	
	public void addPackage(String packageName){
		packages.add(new Package(packageName));
	}
	
	public void loadCommandsAndQueries() throws DuplicateExecutableException{
		for (Package packageObject : packages) {
			queries.addClassesInPackage(packageObject, Query.class);
			commands.addClassesInPackage(packageObject, Command.class);
			commandHandlers.addClassesInPackage(packageObject, Command.class);
			queryHandlers.addClassesInPackage(packageObject, Query.class);
		}
	}

	public Class<? extends Command> findCommand(String name) throws CommandDoesNotExistException, DuplicateCommandException {
		try {
			return commands.get(name);
		} catch(ExecutableDoesNotExistException e) {
			throw new CommandDoesNotExistException(name);
		} catch (DuplicateExecutableException e) {
			throw new DuplicateCommandException(name);
		}
	}
	
	public Class<? extends Query> findQuery(String name) throws QueryDoesNotExistException, DuplicateQueryException {
		try {
			return queries.get(name);
		} catch(ExecutableDoesNotExistException e) {
			throw new QueryDoesNotExistException(name);
		} catch (DuplicateExecutableException e) {
			throw new DuplicateQueryException(name);
		}
	}
	
	public Class<? extends Handler> findCommandHandler(Class<? extends Command> command) throws CommandHandlerDoesNotExistException{
		if (commandHandlers.has(command)) {
			return commandHandlers.get(command);
		} else {
			throw new CommandHandlerDoesNotExistException(command.getSimpleName());
		}
	}
	
	public Class<? extends Handler> findQueryHandler(Class<? extends Query> query) throws QueryHandlerDoesNotExistException{
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
