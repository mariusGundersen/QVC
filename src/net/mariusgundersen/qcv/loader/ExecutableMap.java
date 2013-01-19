package net.mariusgundersen.qcv.loader;

import java.util.HashMap;
import java.util.Map;
import org.reflections.Reflections;
import net.mariusgundersen.qcv.executables.Executable;

public class ExecutableMap<E extends Executable> {
	
	private Map<String, Class<? extends E>> foundClasses = new HashMap<String, Class<? extends E>>();
	
	
	public void addClassesInPackage(Reflections reflections, Class<E> classType) {
		for (Class<? extends E> executable : reflections.getSubTypesOf(classType)) {
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
