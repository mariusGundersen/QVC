package net.mariusgundersen.qcv.loader;

import java.lang.reflect.ParameterizedType;
import java.util.HashSet;
import java.util.Set;

import net.mariusgundersen.qcv.executables.*;
import net.mariusgundersen.qcv.handlers.*;


@SuppressWarnings("rawtypes")
public class Loader {
	
	private Set<String> packages = new HashSet<String>();

	private Finder<String, Command> commands = new Finder<String, Command>();
	private Finder<String, Query> queries = new Finder<String, Query>();
	private Finder<Class<? extends Command>, CommandHandler> commandHandlers = new Finder<Class<? extends Command>, CommandHandler>();
	private Finder<Class<? extends Query>, QueryHandler> queryHandlers = new Finder<Class<? extends Query>, QueryHandler>();
	
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

	public Class<? extends Command> findCommand(String name) {
		return commands.get(name);
	}
	
	public Class<? extends Query> findQuery(String name) {
		return queries.get(name);
	}
	
	public Class<? extends CommandHandler> findCommandHandler(Class<? extends Command> command){
		return commandHandlers.get(command);
	}
	
	public Class<? extends QueryHandler> findQueryHandler(Class<? extends Query> query){
		return queryHandlers.get(query);
	}
	

	private void addQueries(String packageName){
		queries.addClassesInPackage(packageName, Query.class, new Finder.KeyFromValue<String, Query>() {
			public String getKey(Class<? extends Query> value) {
				return value.getSimpleName();
			}
		});
	}
	
	private void addCommands(String packageName){
		commands.addClassesInPackage(packageName, Command.class, new Finder.KeyFromValue<String, Command>() {
			public String getKey(Class<? extends Command> value) {
				return value.getSimpleName();
			}
		});
	}

	private void addCommandHandlers(String packageName){
		commandHandlers.addClassesInPackage(packageName, CommandHandler.class, new Finder.KeyFromValue<Class<? extends Command>, CommandHandler>() {
			@SuppressWarnings("unchecked")
			public Class<? extends Command> getKey(Class<? extends CommandHandler> value) {
				ParameterizedType superClass = (ParameterizedType) value.getGenericSuperclass();
				return (Class<? extends Command>) superClass.getActualTypeArguments()[0];
			}
		});
	}	
	
	private void addQueryHandlers(String packageName){
		queryHandlers.addClassesInPackage(packageName, QueryHandler.class, new Finder.KeyFromValue<Class<? extends Query>, QueryHandler>() {
			@SuppressWarnings("unchecked")
			public Class<? extends Query> getKey(Class<? extends QueryHandler> value) {
				ParameterizedType superClass = (ParameterizedType) value.getGenericSuperclass();
				return (Class<? extends Query>) superClass.getActualTypeArguments()[0];
			}
		});
	}	
}
