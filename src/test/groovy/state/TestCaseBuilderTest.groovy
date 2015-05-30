package state

import model.HttpRequestTestStep;

import org.apache.http.entity.ContentType;
import org.junit.Test

class TestCaseBuilderTest {

	@Test
	void "TestCase method creates a new Test Case in Context"() {
		def tested = new TestCaseBuilder()
		tested.testCase("testCase")
		assert tested.context.testCases[0].name == "testCase"
	}

	@Test
	void "Sql method creates a new sql test step in Context"() {
		def tested = new TestCaseBuilder()

		tested
				.testCase("testCase")
				.sql("jdbc:mock:str", "select * from dual")

		assert tested.context.testCases[0].testSteps[0].connectionURL == "jdbc:mock:str"
	}
	
	@Test
	void "Post method creates a new http request test step in Context"() {
		def tested = new TestCaseBuilder()

		tested
				.testCase("testCase")
				.post("endpoint", "request", ContentType.APPLICATION_JSON)

		assert tested.context.testCases[0].testSteps[0].endpoint == "endpoint"
		assert tested.context.testCases[0].testSteps[0].request == "request"
		assert tested.context.testCases[0].testSteps[0].verb == HttpRequestTestStep.HttpVerb.POST
	}

	@Test
	void "Contains method creates a new assertion on the test step"() {
		def tested = new TestCaseBuilder()

		tested
				.testCase("testCase")
				.sql("jdbc:mock:str", "select * from dual")
				.contains("text")

		assert tested.context.testCases[0].testSteps[0].assertions[0].text == "text"
	}

	@Test
	void "StepName method creates set the name of a test step"() {
		def tested = new TestCaseBuilder()

		tested
		.testCase("testCase")
		.sql("jdbc:mock:str", "select * from dual")
		.stepName("JDBC MOCK")

		assert tested.context.testCases[0].testSteps[0].name == "JDBC MOCK"
	}

	@Test
	void "A second call to Sql method creates a new Test Step"() {
		def tested = new TestCaseBuilder()

		tested
				.testCase("testCase")
				.sql("jdbc:mock:str", "select * from dual")
				.sql("jdbc:mock:str2", "select * from dual")
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
			.sql("mock","sql")
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
	
	@Test
	void "StatusCode adds an assertion for http test steps return codes"() {
		def tested = new TestCaseBuilder()
		
		tested
			.testCase("testCase")
			.post("", "", null)
			.statusCode(200)
		
		assert tested.context.testCases[0].testSteps[0].assertions[0].code == 200
	}
	
	@Test(expected=IllegalStateException)
	void "Attempting to add a status code to a non-HttpRequest test step throws a exception"() {
		def tested = new TestCaseBuilder()
		
		tested
			.sql("","")
			.statusCode(0)
	}
	
	@Test
	void "Delay adds a delay test step"() {
		def tested = new TestCaseBuilder()
		
		tested
			.testCase("testCase")
			.delay(200)
			
		assert  tested.context.testCases[0].testSteps[0].time == 200
	}
}
