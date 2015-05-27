package model

import groovy.lang.Closure;

class TestCaseImpl implements TestCase {

	List<TestStep> testSteps
	Closure setupScript
	Closure tearDownScript
	
	@Override
	/**
	 * Executes the Setup Script, all the Test Steps and the Tear Down script.
	 */
	public boolean execute() {
		return false;
	}

	@Override
	public TestStep addTestStep(TestStep testStep) {
		testSteps << testStep
		return testStep
	}

	@Override
	public Closure setSetupScript(Closure clousure) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Closure setTearDownScript(Closure clousure) {
		// TODO Auto-generated method stub
		return null;
	}

	
	
}
