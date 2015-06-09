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
		result = new HttpResponse()
		def httpRequest = null
		switch(verb) {
			case HttpVerb.POST:
				httpRequest = Request.Post(endpoint).bodyString(request,contentType)
				break
			case HttpVerb.GET:
				httpRequest = Request.Get(endpoint)
				break
		}
		headers.each { httpRequest.addHeader(it.key, it.value) }
		httpRequest.execute()
		.returnResponse().with {
			result.statusCode = getStatusLine().getStatusCode()
			result.response = getEntity().getContent().text
		}
		return true
	}
	
	enum HttpVerb { GET,POST,PUT,PATCH,DELETE,HEAD,OPTIONS,TRACE }
	
}
