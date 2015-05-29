package state

import model.Assertion
import model.ContainsAssertion
import model.JdbcTestStep
import model.TestCase
import model.TestCaseImpl
import model.TestStep

class TestCaseBuilder {
	
	Context context = new Context()
	TestStep activeTestStep
	TestCase activeTestCase
	Assertion activeAssertion
	
	/**
	 * Starting point to create the test case.
	 * 
	 * @param name the test case name.
	 * @return this test case builder.
	 */
	TestCaseBuilder testCase(String name) {
		activeTestCase = new TestCaseImpl(name:name)
		context.testCases << activeTestCase
		activeTestStep = null
		activeAssertion = null
		return this
	}
	
	/**
	 * Set the test step name.
	 *
	 * @param name of test step.
	 * @return this test case builder.
	 */
	TestCaseBuilder stepName(String name) {
		activeTestStep.name = name
		return this
	}
	
	/**
	 * Create a new JDBC test step.
	 * 
	 * @param connectionString A JDBC connection string like 'jdbc:oracle:user/pass@localhost:1521'.
	 * @return this test case builder.
	 */
	TestCaseBuilder jdbc(String connectionString, String sql) {
		addTestStep(new JdbcTestStep(connectionURL:connectionString, sql:sql))
		return this
	}
	
	/**
	 * Add a new test step to the active test case.
	 * 
	 * @param testStep
	 */
	private void addTestStep(TestStep testStep) {
		activeTestStep = testStep
		activeTestCase.testSteps << testStep
		activeAssertion = null
	}
	
	/**
	 * Create a new contains assertion.
	 *
	 * @param text The text to validate if contains on the result.
	 * @return this test case builder.
	 */
	TestCaseBuilder contains(String text) {
		addAssertion(new ContainsAssertion(text:text))
		return this
	}
	
	/**
	 * Add a new assertion to the active test step.
	 * 
	 * @param assertion
	 */
	private void addAssertion(Assertion assertion) {
		if(!activeTestStep) throw new IllegalStateException("An assertion must be added to a Test Step")
		activeAssertion = assertion
		activeTestStep.assertions << assertion
	}
}
