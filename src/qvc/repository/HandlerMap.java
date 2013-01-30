package qvc.repository;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import qvc.executables.Executable;
import qvc.handlers.Handler;


public class HandlerMap<E extends Executable, H extends Handler> {	

	private static final String HANDLE = "handle";
	private Map<Class<? extends E>, Class<? extends H>> foundClasses = new HashMap<Class<? extends E>, Class<? extends H>>();

	public void addClassesInPackage(TypeRepository.Package packageObject, Class<? extends H> handlerType, Class<? extends E> executableType) {
		for (Class<? extends H> handler : packageObject.reflections.getSubTypesOf(handlerType)) {
			for(Method method : handler.getMethods()){
				if(isHandleMethod(method, executableType)){
					System.out.println("method "+method.getName() + ": " + getKey(method) + " - " + handler);
					foundClasses.put(getKey(method), handler);
				}
			}
		}
	}
	
	public boolean has(Class<? extends E> key){
		return foundClasses.containsKey(key);
	}
	
	public Class<? extends H> get(Class<? extends E> key){
		return foundClasses.get(key);
	}
	
	private boolean isHandleMethod(Method method, Class<?> classType){
		String name = method.getName();
		Class<?>[] params = method.getParameterTypes();
		int length = params.length;
		if(name == HANDLE && length == 1){
			Class<?> first = params[0];
			return classType.isAssignableFrom(first);
		}
		return false;
	}
		
	@SuppressWarnings("unchecked")
	public Class<? extends E> getKey(Method method) {
		Class<?>[] params = method.getParameterTypes();
		return (Class<? extends E>) params[0];
	}
}
