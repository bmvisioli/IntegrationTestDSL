package state

import model.ContainsAssertion
import model.JdbcTestStep
import model.TestCase
import model.TestCaseImpl
import model.TestStep

class TestCaseBuilder {
	
	Context context = new Context()
	TestStep activeTestStep
	TestCase activeTestCase
	
	/**
	 * Starting point to create the test case.
	 * 
	 * @param name the test case name.
	 * @return this test case builder.
	 */
	TestCaseBuilder testCase(String name) {
		activeTestCase = new TestCaseImpl(name:name)
		context.testCases << activeTestCase
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
	 * @param name Test Step name
	 * @param connectionString A JDBC connection string like 'jdbc:oracle:user/pass@localhost:1521'.
	 * @return this test case builder.
	 */
	TestCaseBuilder jdbc(String connectionString, String sql) {
		activeTestStep = new JdbcTestStep(connectionURL:connectionString, sql:sql)
		activeTestCase.testSteps << activeTestStep
		return this
	}
	
	/**
	 * Create a new contains assertion.
	 *
	 * @param name The assertion name
	 * @param text The text to validate if contains on the result.
	 * @return this test case builder.
	 */
	TestCaseBuilder contains(String text) {
		activeTestStep.assertions << new ContainsAssertion(text:text)
		return this
	}
}
