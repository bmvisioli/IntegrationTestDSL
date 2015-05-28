package model

import org.junit.Test
import static org.mockito.Mockito.*

class ConstainsAssertionTest {

	@Test
	void "AssertCondition returns true when result contains passed text"() {
		def tested = new ContainsAssertion(name:"test", text:"content")
		assert tested.assertCondition("<test>content</test>")
	}
	
	@Test
	void "AssertCondition returns false when result don't contains passed text"() {
		def tested = new ContainsAssertion(name:"test", text:"invalid")
		assert !tested.assertCondition("<test>content</test>")
	}
}
