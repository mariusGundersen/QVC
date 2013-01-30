package qvc.repository.tree;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import qvc.exceptions.DuplicateExecutableException;
import qvc.exceptions.ExecutableDoesNotExistException;


class Branch<T>{
	private Map<String, Branch<T>> branches = new HashMap<String, Branch<T>>();
	public final String name;
	private Fruit fruit = new Fruit();
	
	public Branch(String name){
		this.name = name;
	}
	
	public Branch(String name, List<String> path, Seed<T> seed) throws DuplicateExecutableException{
		this.name = name;
		add(path, seed);
	}
	
	public void add(List<String> path, Seed<T> seed) throws DuplicateExecutableException{
		boolean isLeaf = path.size() == 0;
				
		if(isLeaf){
			this.fruit.addFruit(seed);
		}else{
			addBranch(path.remove(0), path, seed);
		}
	}

	private void addBranch(String name, List<String> path, Seed<T> seed) throws DuplicateExecutableException {
		if(branches.containsKey(name)){
			branches.get(name).add(path, seed);
		}else{		
			branches.put(name, new Branch<T>(name, path, seed));
		}
	}

	public T findFruit(List<String> path) throws ExecutableDoesNotExistException, DuplicateExecutableException{
		boolean isLeaf = path.size() == 0;
		
		if(isLeaf){
			return fruit.getFruit();
		}else{
			String key = path.remove(0);
			
			if(branches.containsKey(key)){
				return branches.get(key).findFruit(path);
			}else{
				throw new ExecutableDoesNotExistException(key);
			}
		}
	}
	
	public void drawTree(int depth){
		for(int i=0; i<depth; i++) System.out.print(" ");
		System.out.println(name + (fruit.seed != null ? " Fruit: " + fruit.fullName : ""));
		for(Map.Entry<String, Branch<T>> branch: branches.entrySet()){
			branch.getValue().drawTree(depth + 1);
		}
	}
	
	public static class Seed<T>{
		public final T value;
		public final String name;
		
		public Seed(String name, T value){
			this.name = name;
			this.value = value;
		}
	}
	
	private class Fruit{
		private String fullName = "";
		private boolean isDuplicate = false;
		private T seed;
		
		private void addFruit(Seed<T> seed) throws DuplicateExecutableException {
			if(this.seed != null){
				this.isDuplicate = true;
				setDuplicateMessage(seed.name);
			}else{
				this.seed = seed.value;
				this.fullName = seed.name;
			}
		}
		
		private T getFruit() throws DuplicateExecutableException{
			if(isDuplicate){
				throw new DuplicateExecutableException("Multiple Executables with that name, use "+fullName);
			}else{
				return seed;
			}
		}
		
		private void setDuplicateMessage(String otherName) throws DuplicateExecutableException{
			if(fullName.equals(otherName)){
				throw new DuplicateExecutableException("Cannot have two Executables with the same name: " + fullName);
			}else{
				this.fullName += " or " + otherName;
			}
		}
		
	}
}
