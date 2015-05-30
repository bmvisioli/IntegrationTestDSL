package model

import org.apache.http.HttpResponse


class StatusCodeAssertion extends AbstractAssertion {

	int code
	
	public boolean assertCondition(HttpResponse response) {
		def valid = response.getStatusLine().getStatusCode() == code
		return valid
	}
	
	@Override
	public boolean assertCondition(Object result) {
		return result == code;
	}
	
}
