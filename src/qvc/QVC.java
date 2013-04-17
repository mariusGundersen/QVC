package qvc;

import qvc.exceptions.DuplicateExecutableException;
import qvc.handlers.CommandHandler;
import qvc.handlers.QueryHandler;
import qvc.handlers.factory.HandlerFactory;
import qvc.repository.TypeRepository;

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
		
	public void loadCommandsAndQueries() throws DuplicateExecutableException{
		loader.loadCommandsAndQueries();
	}
	
	public void setCommandHandleFactory(HandlerFactory<CommandHandler> creater){
		endpoint.SetCommandHandlerCreater(creater);
	}
	
	public void setQueryHandleFactory(HandlerFactory<QueryHandler> creater){
		endpoint.SetQueryHandlerCreater(creater);
	}
}
