package model

class StatusCodeAssertion extends AbstractAssertion {

	int code
	
	public boolean assertCondition(HttpResponse response) {
		return response.statusCode == code
	}
	
	@Override
	public boolean assertCondition(Object result) {
		return result == code;
	}
	
}
