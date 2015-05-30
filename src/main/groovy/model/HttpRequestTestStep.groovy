package model

import org.apache.http.client.fluent.Request
import org.apache.http.entity.ContentType

class HttpRequestTestStep extends AbstractTestStep {

	String endpoint
	String request
	HttpVerb verb
	ContentType contentType
	@Override
	public boolean run() {
		result = Request.Post(endpoint)
				.bodyString(request,contentType)
				.execute()
				.returnResponse()
		return true
	}
	
	enum HttpVerb { GET,POST,PUT,PATCH,DELETE,HEAD,OPTIONS,TRACE }
	
}
