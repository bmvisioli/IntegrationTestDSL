package modelimport org.apache.http.HttpResponse
class ContainsAssertion extends AbstractAssertion {

	String text		public boolean assertCondition(HttpResponse response) {		def valid = response.getEntity().getContent().text ==~ ".*${text}.*"		return valid	}
	@Override
	public boolean assertCondition(Object result) {
		return result ==~ ".*${text}.*"
	}
}
	
