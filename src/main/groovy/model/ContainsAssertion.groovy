package model
class ContainsAssertion extends AbstractAssertion {

	String text		public boolean assertCondition(HttpResponse result) {		return assertCondition(result.response)	}
	@Override
	public boolean assertCondition(Object result) {
		return result ==~ /(?ms).*${text}.*/
	}
}
	
