package qvc.repository.tree;

import java.util.*;

import qvc.exceptions.DuplicateExecutableException;
import qvc.exceptions.ExecutableDoesNotExistException;


public class SuffixTree<T>{

	private Branch<T> trunk = new Branch<T>("ROOT");
	
	
	public void add(String name, T fruit) throws DuplicateExecutableException{
		String[] pathArray = split(name);
		for(int start=0; start<pathArray.length; start++){
			trunk.add(toList(pathArray, start), new Branch.Seed<T>(name, fruit));
		}
	}
	
	public T find(String name) throws ExecutableDoesNotExistException, DuplicateExecutableException{
		try{
			return trunk.findFruit(toList(split(name), 0));
		}catch(ExecutableDoesNotExistException e){
			throw new ExecutableDoesNotExistException(name);
		}
	}
	
	private String[] split(String name){
		if(name.contains(".")){
			return name.split("\\.");
		}else{
			return new String[]{name};
		}
	}
	
	
	private List<String> toList(String[] pathArray, int start){
		List<String> path = new ArrayList<String>();
		for(int i=start; i<pathArray.length; i++){
			path.add(pathArray[i]);
		}
		return path;
	}

	public void draw() {
		trunk.drawTree(0);
	}
	
}
