package model

import org.junit.Test


class StatusCodeAssertionTest {

	@Test
	void "Return true for a valid return code"() {
		def tested = new StatusCodeAssertion(code:200)
		
		assert tested.assertCondition(200)
	}
	
}
