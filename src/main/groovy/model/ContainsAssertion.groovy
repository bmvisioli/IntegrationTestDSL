package model
class ContainsAssertion extends AbstractAssertion {

	String text
	@Override
	public boolean assertCondition(Object result) {
		return result ==~ ".*${text}.*"
	}
}
	
