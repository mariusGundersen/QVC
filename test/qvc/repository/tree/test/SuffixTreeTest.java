package qvc.repository.tree.test;

import static org.junit.Assert.*;

import java.util.Date;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import qvc.exceptions.DuplicateExecutableException;
import qvc.exceptions.ExecutableDoesNotExistException;
import qvc.repository.tree.SuffixTree;

public class SuffixTreeTest {

	final Date value = new Date();

	@Test
	public void OneLevelTree_OneLevelMatch() throws ExecutableDoesNotExistException, DuplicateExecutableException {
		SuffixTree<Date> tree = new SuffixTree<Date>();
		tree.add("first", value);
		tree.draw();
		
		assertEquals(value, tree.find("first"));
	}

	@Test
	public void TwoLevelTree_TwoLevelMatch() throws ExecutableDoesNotExistException, DuplicateExecutableException {
		SuffixTree<Date> tree = new SuffixTree<Date>();
		tree.add("first.second", value);
		tree.draw();

		assertEquals(value, tree.find("first.second"));
	}

	@Test
	public void TwoLevelTree_OneLevelMatch() throws ExecutableDoesNotExistException, DuplicateExecutableException {
		SuffixTree<Date> tree = new SuffixTree<Date>();
		tree.add("first.second", value);
		tree.draw();

		assertEquals(value, tree.find("second"));
	}

	@Test
	public void DuplicateExecutable() throws ExecutableDoesNotExistException, DuplicateExecutableException {
		
		exception.expect(DuplicateExecutableException.class);
		exception.expectMessage("Cannot have two Executables with the same name: first.second");
		
		SuffixTree<Date> tree = new SuffixTree<Date>();
		tree.add("first.second", value);
		tree.add("first.second", value);
		tree.draw();
	}

	@Test
	public void TwoExecutablesInDifferentPackagesFullPath() throws ExecutableDoesNotExistException, DuplicateExecutableException {
		SuffixTree<Date> tree = new SuffixTree<Date>();
		tree.add("first.second.Executable", value);
		tree.add("first.third.Executable", value);
		tree.draw();

		assertEquals(value, tree.find("first.second.Executable"));
	}
	
	@Test
	public void TwoExecutablesInDifferentPackagesUniquePath() throws ExecutableDoesNotExistException, DuplicateExecutableException {
		SuffixTree<Date> tree = new SuffixTree<Date>();
		tree.add("first.second.Executable", value);
		tree.add("first.third.Executable", value);
		tree.draw();

		assertEquals(value, tree.find("second.Executable"));
	}
	
	@Test
	public void TwoExecutablesInDifferentPackagesDuplicatePath() throws ExecutableDoesNotExistException, DuplicateExecutableException {
		
		exception.expect(DuplicateExecutableException.class);
		exception.expectMessage("Multiple Executables with that name, use first.second.Executable or first.third.Executable");
		
		SuffixTree<Date> tree = new SuffixTree<Date>();
		tree.add("first.second.Executable", value);
		tree.add("first.third.Executable", value);
		tree.draw();

		tree.find("Executable");
	}

	@Test
	public void DuplicatePackage_UniqueExecutable() throws ExecutableDoesNotExistException, DuplicateExecutableException {
		SuffixTree<Date> tree = new SuffixTree<Date>();
		tree.add("first.second.second", value);
		tree.add("first.second.third", value);
		tree.draw();

		assertEquals(value, tree.find("second.second"));
	}

	
	@Rule
	public ExpectedException exception = ExpectedException.none();
}
