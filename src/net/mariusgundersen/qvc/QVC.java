package net.mariusgundersen.qvc;

import net.mariusgundersen.qvc.handlers.CommandHandler;
import net.mariusgundersen.qvc.handlers.QueryHandler;
import net.mariusgundersen.qvc.handlers.factory.HandlerFactory;
import net.mariusgundersen.qvc.repository.TypeRepository;

public class QVC {

	private TypeRepository loader;
	private JsonEndpoint jsonEndpoint;
	private Endpoint endpoint;
	
	
	public QVC(){
		loader = new TypeRepository();
		endpoint = new Endpoint(loader);
		jsonEndpoint = new JsonEndpoint(endpoint, loader);
	}
	
	public JsonEndpoint getJsonEndpoint() {
		return jsonEndpoint;
	}

	public QVC addPackage(String ...packagesName){
		for(String packageName: packagesName){
			loader.addPackage(packageName);
		}
		return this;
	}
		
	public void loadCommandsAndQueries(){
		loader.loadCommandsAndQueries();
	}
	
	public void setCommandHandleCreater(HandlerFactory<CommandHandler> creater){
		endpoint.SetCommandHandlerCreater(creater);
	}
	
	public void setQueryHandleCreater(HandlerFactory<QueryHandler> creater){
		endpoint.SetQueryHandlerCreater(creater);
	}
}
