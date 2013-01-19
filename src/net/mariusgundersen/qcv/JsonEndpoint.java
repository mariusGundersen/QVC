package net.mariusgundersen.qcv;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import net.mariusgundersen.qcv.exceptions.*;
import net.mariusgundersen.qcv.executables.*;
import net.mariusgundersen.qcv.loader.TypeRepository;
import net.mariusgundersen.qcv.results.*;

public class JsonEndpoint {

	private TypeRepository loader;
	private Endpoint endpoint;
	private GsonBuilder jsonBuilder;

	public JsonEndpoint(Endpoint endpoint, TypeRepository loader) {
		this.endpoint = endpoint;
		this.loader = loader;
		

		jsonBuilder = new GsonBuilder();
		jsonBuilder.registerTypeAdapter(Throwable.class, new ExceptionSerializer());
	}

	public String command(String name, String json) {
		return jsonStringify(commandResultFromJson(name, json));
	}

	public String query(String name, String json) {
		return jsonStringify(queryResultFromJson(name, json));
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

	private Command commandFromJson(String name, String json)
			throws CommandDoesNotExistException {
		Gson gson = new Gson();
		Class<? extends Command> command = loader.findCommand(name);
		return gson.fromJson(json, command);
	}

	private Query queryFromJson(String name, String json)
			throws QueryDoesNotExistException {
		Gson gson = new Gson();
		Class<? extends Query> query = loader.findQuery(name);
		return gson.fromJson(json, query);
	}

	private String jsonStringify(Object data) {
		Gson gson = jsonBuilder.create();
		return gson.toJson(data);
	}
}
