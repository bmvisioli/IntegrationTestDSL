package state

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
	 * Create a new JDBC test step.
	 * 
	 * @param name Test Step name
	 * @param connectionString A JDBC connection string like 'jdbc:oracle:user/pass@localhost:1521'.
	 * @return this test case builder.
	 */
	TestCaseBuilder jdbc(String name, String connectionString) {
		return this
	}
}
