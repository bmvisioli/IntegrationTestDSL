package state

import org.junit.Test
import state.TestCaseBuilder

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
		.jdbc("jdbcTestStep", "jdbc:mock:str", "select * from dual")
		
		assert tested.context.testCases[0].testSteps[0].name == "jdbcTestStep"
		assert tested.context.testCases[0].testSteps[0].connectionURL == "jdbc:mock:str"
	}
	
	@Test
	void "Contains method creates a new assertion on the test step"() {
		def tested = new TestCaseBuilder()
		
		tested
		.testCase("testCase")
		.jdbc("jdbcTestStep", "jdbc:mock:str", "select * from dual")
		.contains("Contains a text", "text")
		
		assert tested.context.testCases[0].testSteps[0].assertions[0].name == "Contains a text"
		assert tested.context.testCases[0].testSteps[0].assertions[0].text == "text"
	}
	
	@Test
	void "A second call to Jdbc method creates a new Test Step"() {
		def tested = new TestCaseBuilder()
		
		tested
		.testCase("testCase")
		.jdbc("jdbcTestStep", "jdbc:mock:str", "select * from dual")
		.jdbc("jdbcTestStep2", "jdbc:mock:str2", "select * from dual")
		.contains("Contains a text", "text")
		
		assert tested.context.testCases[0].testSteps[1].assertions[0].name == "Contains a text"
		assert tested.context.testCases[0].testSteps[1].assertions[0].text == "text"
	}
}
