package state

import model.Executable;
import model.TestCase

class Context implements Executable {

	List<TestCase> testCases = []

	@Override
	public boolean execute() {
		boolean result = true
		testCases.each { result &= it.execute() }
		return result;
	}
}
