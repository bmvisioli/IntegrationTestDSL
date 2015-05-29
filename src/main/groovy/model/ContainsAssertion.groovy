package model
class ContainsAssertion implements Assertion {

	String name
	String text
	@Override
	public boolean assertCondition(Object result) {
		return result ==~ ".*${text}.*"
	}
}
	
