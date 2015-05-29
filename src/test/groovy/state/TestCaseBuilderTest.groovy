package state

import org.junit.Test

class TestCaseBuilderTest {

	@Test
	void "TestCase method creates a new Test Case in Context"() {
		def tested = new TestCaseBuilder()
		tested.testCase("testCase")
		assert tested.context.testCases[0].name == "testCase"
	}

	@Test
	void "Jdbc method creates a new jdbc test step in Context"() {
		def tested = new TestCaseBuilder()

		tested
				.testCase("testCase")
				.jdbc("jdbc:mock:str", "select * from dual")

		assert tested.context.testCases[0].testSteps[0].connectionURL == "jdbc:mock:str"
	}

	@Test
	void "Contains method creates a new assertion on the test step"() {
		def tested = new TestCaseBuilder()

		tested
				.testCase("testCase")
				.jdbc("jdbc:mock:str", "select * from dual")
				.contains("text")

		assert tested.context.testCases[0].testSteps[0].assertions[0].text == "text"
	}

	@Test
	void "StepName method creates set the name of a test step"() {
		def tested = new TestCaseBuilder()

		tested
		.testCase("testCase")
		.jdbc("jdbc:mock:str", "select * from dual")
		.stepName("JDBC MOCK")

		assert tested.context.testCases[0].testSteps[0].name == "JDBC MOCK"
	}

	@Test
	void "A second call to Jdbc method creates a new Test Step"() {
		def tested = new TestCaseBuilder()

		tested
				.testCase("testCase")
				.jdbc("jdbc:mock:str", "select * from dual")
				.jdbc("jdbc:mock:str2", "select * from dual")
				.contains("text")

		assert tested.context.testCases[0].testSteps[1].assertions[0].text == "text"
	}

	@Test(expected=IllegalStateException)
	void "An exception is thrown when adding an assertion to a test case"() {
		def tested = new TestCaseBuilder()

		tested
			.testCase("testCase")
			.contains("text")
	}
	
	@Test(expected=IllegalStateException)
	void "An exception is thrown when adding an test step with no test case"() {
		def tested = new TestCaseBuilder()

		tested
			.jdbc("mock","sql")
	}
	
	@Test
	void "SetupScript adds a setup script to the test case"() {
		def tested = new TestCaseBuilder()
		
		tested
			.testCase("testCase")
			.setupScript { it.name = "newTestCase" }
		tested.context.execute()
			
		assert "newTestCase" == tested.context.testCases[0].name
	}
	
	@Test(expected=IllegalStateException)
	void "An exception is thrown when adding an setup script with no test case"() {
		def tested = new TestCaseBuilder()
		
		tested
			.setupScript { it.name = "newTestCase" }
	}
	
	@Test
	void "TearDownScript adds a tear down script to the test case"() {
		def tested = new TestCaseBuilder()
		
		tested
			.testCase("testCase")
			.tearDownScript { it.name = "newTestCase" }
		tested.context.execute()
			
		assert "newTestCase" == tested.context.testCases[0].name
	}
	
	@Test(expected=IllegalStateException)
	void "An exception is thrown when adding an tear down script with no test case"() {
		def tested = new TestCaseBuilder()
		
		tested
			.tearDownScript { it.name = "newTestCase" }
	}
}
