package qvc.repository;

import qvc.exceptions.DuplicateExecutableException;
import qvc.exceptions.ExecutableDoesNotExistException;
import qvc.executables.Executable;
import qvc.repository.tree.SuffixTree;


public class ExecutableMap<E extends Executable> {
	
	private SuffixTree<Class<? extends E>> foundExecutables = new SuffixTree<Class<? extends E>>();
	
	public void addClassesInPackage(TypeRepository.Package packageObject, Class<E> classType) throws DuplicateExecutableException {
				
		for (Class<? extends E> executable : packageObject.reflections.getSubTypesOf(classType)) {
			foundExecutables.add(executable.getSimpleName(), executable);
		}
	}
	
	public Class<? extends E> get(String key) throws ExecutableDoesNotExistException, DuplicateExecutableException{
		return foundExecutables.find(key);
	}
}
