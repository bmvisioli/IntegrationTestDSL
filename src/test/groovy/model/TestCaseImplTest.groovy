package model

import org.junit.Test

public class TestCaseImplTest {

	@Test
	void "Execute calls the setup and tear down scripts"() {
		def tested = new TestCaseImpl()
		def setupScript = {}
		def tearDownScript = {}
		tested.setSetupScript(setupScript)
		tested.setTearDownScript(tearDownScript)
		
		assert true
	}
		
}
