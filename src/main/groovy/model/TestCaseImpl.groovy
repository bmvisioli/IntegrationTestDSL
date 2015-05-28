package model

import groovy.lang.Closure;

class TestCaseImpl implements TestCase {

	String name
	List<TestStep> testSteps
	Closure setupScript
	Closure tearDownScript
	
	@Override
	/**
	 * Executes the Setup Script, all the Test Steps and the Tear Down script.
	 */
	public boolean execute() {
		setupScript.call(this)
		tearDownScript.call(this)
		return false;
	}

	@Override
	public TestStep addTestStep(TestStep testStep) {
		testSteps << testStep
		return testStep
	}

	
	
}
