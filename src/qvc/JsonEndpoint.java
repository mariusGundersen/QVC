package qvc;

import qvc.exceptions.*;
import qvc.executables.*;
import qvc.repository.TypeRepository;
import qvc.results.*;
import qvc.validation.GroupSerializer;
import qvc.validation.metadata.ValidationConstraints;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


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
		return command(name, json, "");
	}

	public String command(String name, String json, String sessionId) {
		return jsonStringify(commandResultFromJson(name, json, sessionId));
	}

	public String query(String name, String json) {
		return query(name, json, "");
	}

	public String query(String name, String json, String sessionId) {
		return jsonStringify(queryResultFromJson(name, json, sessionId));
	}
	
	public String constraints(String name){
		return jsonStringify(constraintsFromJson(name));
	}

	private CommandResult commandResultFromJson(String name, String json, String sessionId) {
		try {
			Command command = commandFromJson(name, json);
			return endpoint.command(command, sessionId);
		} catch (Exception exception) {
			return new CommandResult(exception);
		}
	}

	private QueryResult queryResultFromJson(String name, String json, String sessionId) {
		try {
			Query query = queryFromJson(name, json);
			return endpoint.query(query, sessionId);
		} catch (Exception exception) {
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

	private Command commandFromJson(String name, String json) throws CommandDoesNotExistException, DuplicateCommandException {
		Class<? extends Command> command = loader.findCommand(name);
		return gson.fromJson(json, command);
	}

	private Query queryFromJson(String name, String json) throws QueryDoesNotExistException, DuplicateQueryException {
		Class<? extends Query> query = loader.findQuery(name);
		return gson.fromJson(json, query);
	}
	
	private Class<? extends Executable> executableFromName(String name) throws ExecutableDoesNotExistException, DuplicateCommandException, DuplicateQueryException{
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
