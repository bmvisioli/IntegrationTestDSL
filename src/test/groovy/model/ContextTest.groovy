package model

import static org.mockito.Mockito.*

import org.junit.Test

import state.Context

class ContextTest {

	@Test
	void "Execute fails if a test case fails"() {
		def tested = new Context()
		def testCase1 = mock(TestCase)
		def testCase2 = mock(TestCase)
		when(testCase1.execute()).thenReturn(true)
		when(testCase2.execute()).thenReturn(false)
		tested.testCases << testCase1
		tested.testCases << testCase2
		assert !tested.execute()
	}
	
	@Test
	void "Execute succeeds if all test case succeed"() {
		def tested = new Context()
		def testCase1 = mock(TestCase)
		def testCase2 = mock(TestCase)
		when(testCase1.execute()).thenReturn(true)
		when(testCase2.execute()).thenReturn(true)
		tested.testCases << testCase1
		tested.testCases << testCase2
		assert tested.execute()
	}
}
