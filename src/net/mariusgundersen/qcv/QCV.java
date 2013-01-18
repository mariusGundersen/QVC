package net.mariusgundersen.qcv;

import net.mariusgundersen.qcv.handlers.CommandHandler;
import net.mariusgundersen.qcv.loader.DefaultCommandHandlerInstancer;
import net.mariusgundersen.qcv.loader.Endpoint;
import net.mariusgundersen.qcv.loader.Instancer;
import net.mariusgundersen.qcv.loader.Loader;
import net.mariusgundersen.qcv.loader.WebEndpoint;

public class QCV {

	private Loader loader;
	private WebEndpoint webEndpoint;
	private Endpoint endpoint;
	private Instancer<CommandHandler> instancer = new DefaultCommandHandlerInstancer();
	
	
	public QCV(){
		loader = new Loader();
		endpoint = new Endpoint(loader, instancer );
		webEndpoint = new WebEndpoint(endpoint, loader);
	}
	
	
	public Loader getLoader() {
		return loader;
	}
	public WebEndpoint getWebEndpoint() {
		return webEndpoint;
	}
}
