package net.mariusgundersen.qcv;

import net.mariusgundersen.qcv.handlers.CommandHandler;
import net.mariusgundersen.qcv.handlers.QueryHandler;
import net.mariusgundersen.qcv.loader.HandlerFactory;
import net.mariusgundersen.qcv.loader.TypeRepository;

public class QCV {

	private TypeRepository loader;
	private JsonEndpoint jsonEndpoint;
	private Endpoint endpoint;
	
	
	public QCV(){
		loader = new TypeRepository();
		endpoint = new Endpoint(loader);
		jsonEndpoint = new JsonEndpoint(endpoint, loader);
	}
	
	public JsonEndpoint getJsonEndpoint() {
		return jsonEndpoint;
	}

	public QCV addPackage(String ...packagesName){
		for(String packageName: packagesName){
			loader.addPackage(packageName);
		}
		return this;
	}
		
	public void loadCommandsAndQueries(){
		loader.loadCommandsAndQueries();
	}
	
	@SuppressWarnings("rawtypes")
	public void setCommandHandleCreater(HandlerFactory<CommandHandler> creater){
		endpoint.SetCommandHandlerCreater(creater);
	}
	@SuppressWarnings("rawtypes")
	public void setQueryHandleCreater(HandlerFactory<QueryHandler> creater){
		endpoint.SetQueryHandlerCreater(creater);
	}
}
