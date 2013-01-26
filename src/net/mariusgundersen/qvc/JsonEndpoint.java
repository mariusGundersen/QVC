package net.mariusgundersen.qvc;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import net.mariusgundersen.qvc.exceptions.*;
import net.mariusgundersen.qvc.executables.*;
import net.mariusgundersen.qvc.repository.TypeRepository;
import net.mariusgundersen.qvc.results.*;
import net.mariusgundersen.qvc.validation.GroupSerializer;
import net.mariusgundersen.qvc.validation.metadata.ValidationConstraints;

public class JsonEndpoint {

	private TypeRepository loader;
	private Endpoint endpoint;
	private GsonBuilder jsonBuilder;
	private Gson gson;

	public JsonEndpoint(Endpoint endpoint, TypeRepository loader) {
		this.endpoint = endpoint;
		this.loader = loader;
		

		jsonBuilder = new GsonBuilder();
		jsonBuilder.registerTypeAdapter(Throwable.class, new ExceptionSerializer());
		jsonBuilder.registerTypeAdapter(Class.class, new GroupSerializer());
		gson = jsonBuilder.create();
	}

	public String command(String name, String json) {
		return jsonStringify(commandResultFromJson(name, json));
	}

	public String query(String name, String json) {
		return jsonStringify(queryResultFromJson(name, json));
	}
	
	public String constraints(String name){
		return jsonStringify(constraintsFromJson(name));
	}

	private CommandResult commandResultFromJson(String name, String json) {
		try {
			Command command = commandFromJson(name, json);
			return endpoint.command(command);
		} catch (CommandDoesNotExistException exception) {
			return new CommandResult(exception);
		}
	}

	private QueryResult queryResultFromJson(String name, String json) {
		try {
			Query query = queryFromJson(name, json);
			return endpoint.query(query);
		} catch (QueryDoesNotExistException exception) {
			return new QueryResult(exception);
		}
	}
	
	private ValidationConstraints constraintsFromJson(String name){
		try{
			Class<? extends Executable> executable = executableFromName(name);
			return endpoint.constraints(executable);
		} catch (Exception exception){
			return new ValidationConstraints(exception);
		}
	}

	private Command commandFromJson(String name, String json) throws CommandDoesNotExistException {
		Class<? extends Command> command = loader.findCommand(name);
		return gson.fromJson(json, command);
	}

	private Query queryFromJson(String name, String json) throws QueryDoesNotExistException {
		Class<? extends Query> query = loader.findQuery(name);
		return gson.fromJson(json, query);
	}
	
	private Class<? extends Executable> executableFromName(String name) throws ExecutableDoesNotExistException{
		try {
			return loader.findCommand(name);
		} catch (CommandDoesNotExistException e) {
			try {
				return loader.findQuery(name);
			} catch (QueryDoesNotExistException e1) {
				throw new ExecutableDoesNotExistException(name);
			}
		}
		
	}

	private String jsonStringify(Object data) {
		return gson.toJson(data);
	}
}
