package model

class ContainsAssertion implements Assertion {

	String name
	String text
	@Override
	public boolean assertCondition(TestStep testStep) {
		return testStep.getResult() ==~ ".*${text}.*"
	}
}
	
