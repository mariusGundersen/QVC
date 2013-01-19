package net.mariusgundersen.qcv.loader;

import java.lang.reflect.ParameterizedType;
import java.util.HashSet;
import java.util.Set;
import net.mariusgundersen.qcv.exceptions.*;
import net.mariusgundersen.qcv.executables.*;
import net.mariusgundersen.qcv.handlers.*;


@SuppressWarnings("rawtypes")
public class TypeRepository {
	
	private Set<String> packages = new HashSet<String>();

	private TypeMap<String, Command> commands = new TypeMap<String, Command>();
	private TypeMap<String, Query> queries = new TypeMap<String, Query>();
	private TypeMap<Class<? extends Command>, CommandHandler> commandHandlers = new TypeMap<Class<? extends Command>, CommandHandler>();
	private TypeMap<Class<? extends Query>, QueryHandler> queryHandlers = new TypeMap<Class<? extends Query>, QueryHandler>();
	
	public void addPackage(String packageName){
		packages.add(packageName);
	}
	
	public void loadCommandsAndQueries(){
		for (String packageName : packages) {
			addCommands(packageName);
			addQueries(packageName);			
			addCommandHandlers(packageName);		
			addQueryHandlers(packageName);
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
	

	private void addQueries(String packageName){
		queries.addClassesInPackage(packageName, Query.class, new TypeMap.KeyFromValue<String, Query>() {
			public String getKey(Class<? extends Query> value) {
				return value.getSimpleName();
			}
		});
	}
	
	private void addCommands(String packageName){
		commands.addClassesInPackage(packageName, Command.class, new TypeMap.KeyFromValue<String, Command>() {
			public String getKey(Class<? extends Command> value) {
				return value.getSimpleName();
			}
		});
	}

	private void addCommandHandlers(String packageName){
		commandHandlers.addClassesInPackage(packageName, CommandHandler.class, new TypeMap.KeyFromValue<Class<? extends Command>, CommandHandler>() {
			@SuppressWarnings("unchecked")
			public Class<? extends Command> getKey(Class<? extends CommandHandler> value) {
				ParameterizedType superClass = (ParameterizedType) value.getGenericSuperclass();
				return (Class<? extends Command>) superClass.getActualTypeArguments()[0];
			}
		});
	}	
	
	private void addQueryHandlers(String packageName){
		queryHandlers.addClassesInPackage(packageName, QueryHandler.class, new TypeMap.KeyFromValue<Class<? extends Query>, QueryHandler>() {
			@SuppressWarnings("unchecked")
			public Class<? extends Query> getKey(Class<? extends QueryHandler> value) {
				ParameterizedType superClass = (ParameterizedType) value.getGenericSuperclass();
				return (Class<? extends Query>) superClass.getActualTypeArguments()[0];
			}
		});
	}	
}
