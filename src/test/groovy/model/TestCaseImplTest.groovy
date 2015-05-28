package model

import org.junit.Test

public class TestCaseImplTest {

	@Test
	void "Execute calls the setup and tear down scripts"() {
		def tested = new TestCaseImpl()
		tested.setupScript = { it.name = "test" }
		tested.tearDownScript = { it.name = "${it.name}test" }
		tested.execute()
		assert tested.name == "testtest"
	}
}
