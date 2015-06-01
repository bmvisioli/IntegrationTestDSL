package model
class ContainsAssertion extends AbstractAssertion {

	String text		public boolean assertCondition(HttpResponse result) {		return result.response ==~ ".*${text}.*"	}
	@Override
	public boolean assertCondition(Object result) {
		return result ==~ ".*${text}.*"
	}
}
	
