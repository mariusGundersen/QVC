package net.mariusgundersen.qvc.repository;

import java.util.HashMap;
import java.util.Map;

import net.mariusgundersen.qvc.executables.Executable;

public class ExecutableMap<E extends Executable> {
	
	private Map<String, Class<? extends E>> foundClasses = new HashMap<String, Class<? extends E>>();
	
	
	public void addClassesInPackage(TypeRepository.Package packageObject, Class<E> classType) {
		for (Class<? extends E> executable : packageObject.reflections.getSubTypesOf(classType)) {
			foundClasses.put(executable.getSimpleName(), executable);
		}
	}
	
	public boolean has(String key){
		return foundClasses.containsKey(key);
	}
	
	public Class<? extends E> get(String key){
		return foundClasses.get(key);
	}
}
