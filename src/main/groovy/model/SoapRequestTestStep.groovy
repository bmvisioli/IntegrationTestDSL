package model

class SoapRequestTestStep implements TestStep {

	String name
	List<Assertion> assertions
	
	@Override
	public boolean execute() {
		return false
	}

	@Override
	public Assertion addAssertion(Assertion assertion) {
		assertions << assertion
	}

}
