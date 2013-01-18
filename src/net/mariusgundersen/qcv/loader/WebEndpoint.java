package net.mariusgundersen.qcv.loader;

import com.google.gson.Gson;

import net.mariusgundersen.qcv.executables.*;
import net.mariusgundersen.qcv.results.*;

public class WebEndpoint {

	private Loader loader;
	private Endpoint endpoint;

	public WebEndpoint(Endpoint endpoint, Loader loader) {
		this.endpoint = endpoint;
		this.loader = loader;
	}

	public String command(String name, String json){
		Command command = commandFromJson(name, json);
		CommandResult result = endpoint.command(command);
		Gson gson = new Gson();
		return gson.toJson(result);
	}
	
	public String query(String name, String json){
		Query query = queryFromJson(name, json);
		QueryResult result = endpoint.query(query);
		Gson gson = new Gson();
		return gson.toJson(result);
	}

	private Command commandFromJson(String name, String json) {
		Gson gson = new Gson();
		Class<? extends Command> command = loader.findCommand(name);
		return gson.fromJson(json, command);
	}

	private Query queryFromJson(String name, String json) {
		Gson gson = new Gson();
		Class<? extends Query> query = loader.findQuery(name);
		return gson.fromJson(json, query);
	}
}
