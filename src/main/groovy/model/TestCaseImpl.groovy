package model

class TestCaseImpl implements TestCase {

	String name
	List<TestStep> testSteps = []
	Closure setupScript
	Closure tearDownScript
	
	@Override
	/**
	 * Executes the Setup Script, all the Test Steps and the Tear Down script.
	 * 
	 * @return if the test case succeed or failed
	 */
	public boolean execute() {
		boolean succeed = true
		setupScript?.call(this)
		testSteps.each { succeed &= it.execute() }
		tearDownScript?.call(this)
		return succeed;
	}

	@Override
	public TestStep addTestStep(TestStep testStep) {
		testSteps << testStep
		return testStep
	}

	
	
}
