package model

import org.junit.Test
import static org.mockito.Mockito.*

public class TestCaseImplTest {

	@Test
	void "Execute calls the setup and tear down scripts"() {
		def tested = new TestCaseImpl()
		tested.setupScript = { it.name = "test" }
		tested.tearDownScript = { it.name = "${it.name}test" }
		tested.execute()
		assert tested.name == "testtest"
	}
	
	@Test
	void "Execute calls the test steps execute"() {
		def tested = new TestCaseImpl()
		def step1 = mock(TestStep)
		tested.addTestStep(step1)
		tested.execute()
		verify(step1, times(1)).execute()
	}
	
	@Test
	void "Test case succeed if one of test step succeed"() {
		def tested = new TestCaseImpl()
		def step1 = mock(TestStep)
		def step2 = mock(TestStep)
		when(step1.execute()).thenReturn(true)
		when(step2.execute()).thenReturn(true)
		tested.addTestStep(step1)
		tested.addTestStep(step2)
		assert tested.execute()
	}
	
	@Test
	void "Test case fails if one of test step fails"() {
		def tested = new TestCaseImpl()
		def step1 = mock(TestStep)
		def step2 = mock(TestStep)
		when(step1.execute()).thenReturn(true)
		when(step2.execute()).thenReturn(false)
		tested.addTestStep(step1)
		tested.addTestStep(step2)
		assert !tested.execute()
	}
}
