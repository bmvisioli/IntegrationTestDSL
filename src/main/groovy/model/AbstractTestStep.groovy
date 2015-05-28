package model

abstract class AbstractTestStep implements TestStep {

	String name
	List<Assertion> assertions = []
	
	@Override
	public Assertion addAssertion(Assertion assertion) {
		assertions << assertion
		return assertion
	}
}
