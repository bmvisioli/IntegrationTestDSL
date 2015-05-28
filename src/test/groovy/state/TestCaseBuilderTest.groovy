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
	void "jdbc method creates a new jdbc test step in Context"() {
		def tested = new TestCaseBuilder()
		tested.testCase("testCase")
		assert tested.context.testCases[0].name == "testCase"
	}
}
