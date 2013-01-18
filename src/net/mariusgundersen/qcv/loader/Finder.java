package net.mariusgundersen.qcv.loader;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.reflections.Reflections;

public class Finder<K, S> {
	
	

	private Map<K, Class<? extends S>> foundClasses = new HashMap<K, Class<? extends S>>();

	public void addClassesInPackage(String packageName, Class<S> classType, KeyFromValue<K, S> keyFromValue) {
		for (Class<? extends S> commandHandler : findClassesInPackage(packageName, classType)) {
			foundClasses.put(keyFromValue.getKey(commandHandler), commandHandler);
		}
	}
	
	public Class<? extends S> get(K key){
		return foundClasses.get(key);
	}

	private Set<Class<? extends S>> findClassesInPackage(String packageName, Class<S> classType) {
		Reflections reflections = new Reflections(packageName);
		Set<Class<? extends S>> classes = reflections.getSubTypesOf(classType);
		return classes;
	}
	

	
	public interface KeyFromValue<K, S>{
		K getKey(Class<? extends S> value);
	}
}
