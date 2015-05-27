package state

class TestCaseBuilder {
	
	Context context
	
	/**
	 * Starting point to create the test case.
	 * 
	 * @param name the test case name.
	 * @return this test case builder.
	 */
	TestCaseBuilder testCase(String name) {
		return this
	}
	
	/**
	 * Create a new JDBC test step.
	 * 
	 * @param connectionString A JDBC connection string like 'jdbc:oracle:user/pass@localhost:1521'.
	 * @return this test step builder.
	 */
	TestStepBuilder jdbc(String connectionString) {
		TestStepBuilder builder = new TestStepBuilder()
		return builder
	}
}
