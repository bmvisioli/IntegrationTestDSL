package state

import model.AbstractAssertion
import model.ContainsAssertion
import model.DelayTestStep
import model.HttpRequestTestStep
import model.JmsDequeueTestStep
import model.JmsEnqueueTestStep
import model.MockResponseTestStep
import model.SqlTestStep
import model.StatusCodeAssertion
import model.TestCase
import model.TestCaseImpl
import model.TestStep
import model.XPathAssertion
import model.HttpRequestTestStep.HttpVerb

import org.apache.http.entity.ContentType

class TestCaseBuilder {
	
	Context context = new Context()
	TestStep activeTestStep
	TestCase activeTestCase
	AbstractAssertion activeAssertion
	
	/**
	 * Starting point to create the test case.
	 * 
	 * @param name the test case name.
	 * @return this test case builder.
	 */
	TestCaseBuilder testCase(String name) {
		activeTestCase = new TestCaseImpl(name:name)
		context.testCases << activeTestCase
		activeTestStep = null
		activeAssertion = null
		return this
	}
	
	/**
	 * Set the test step name.
	 *
	 * @param name of test step.
	 * @return this test case builder.
	 */
	TestCaseBuilder stepName(String name) {
		activeTestStep.name = name
		return this
	}
	
	/**
	 * Add a setup script to the test case.
	 * 
	 * @param closure the script to run before the test case execution.
	 * @return this test case builder.
	 */
	TestCaseBuilder setupScript(Closure closure) {
		if(!activeTestCase) throw new IllegalStateException("A setup script must be added to a Test Case.")
		activeTestCase.setupScript = closure
		return this
	}
	
	/**
	 * Add a tear down script to the test case.
	 *
	 * @param closure the script to run after the test case execution.
	 * @return this test case builder.
	 */
	TestCaseBuilder tearDownScript(Closure closure) {
		if(!activeTestCase) throw new IllegalStateException("A tear down script must be added to a Test Case.")
		activeTestCase.tearDownScript = closure
		return this
	}
	
	/**
	 * Create a new SQL test step.
	 * 
	 * @param connectionString A JDBC connection string i.e.: 'jdbc:oracle:user/pass@localhost:1521'.
	 * @param sql the sql query/update to execute.
	 * @return this test case builder.
	 */
	TestCaseBuilder sql(String connectionString, String sql) {
		addTestStep(new SqlTestStep(connectionURL:connectionString, sql:sql))
		return this
	}
	
	/**
	 * Create a new POST HttpRequest test step.
	 * 
	 * @param endpoint the endpoint to call i.e.: http://host:8080/soap.
	 * @param request the request to send.
	 * @return this test case builder.
	 */
	TestCaseBuilder post(String endpoint, String request, ContentType contentType) {
		addTestStep(new HttpRequestTestStep(endpoint:endpoint, request:request, verb:HttpVerb.POST, contentType:contentType))
		return this
	}
	
	/**
	 * Create a new HttpRequest GET test step.
	 *
	 * @param endpoint the endpoint to call i.e.: http://host:8080/get?param=value.
	 * @return this test case builder.
	 */
	TestCaseBuilder get(String endpoint) {
		addTestStep(new HttpRequestTestStep(endpoint:endpoint, verb:HttpVerb.GET))
		return this
	}
	
	/**
	 * Create a new dealay test step.
	 *
	 * @param milliseconds
	 * @return this test case builder.
	 */
	TestCaseBuilder delay(long milliseconds) {
		addTestStep(new DelayTestStep(time:milliseconds))
		return this
	}
	
	/**
	 * Add a new test step to the active test case.
	 * 
	 * @param testStep
	 */
	private void addTestStep(TestStep testStep) {
		if(!activeTestCase) throw new IllegalStateException("A test step must be added to a Test Case.")
		activeTestStep = testStep
		activeTestCase.testSteps << testStep
		activeAssertion = null
	}
	
	/**
	 * Create a new contains assertion.
	 *
	 * @param text The text to validate if contains on the result.
	 * @return this test case builder.
	 */
	TestCaseBuilder contains(String text) {
		addAssertion(new ContainsAssertion(text:text))
		return this
	}
	
	/**
	 * Add a new assertion to the active test step.
	 * 
	 * @param assertion
	 */
	private void addAssertion(AbstractAssertion assertion) {
		if(!activeTestStep) throw new IllegalStateException("An assertion must be added to a Test Step.")
		activeAssertion = assertion
		activeTestStep.addAssertion(assertion)
	}
	
	/**
	 * Adds the header to the active test testp.
	 * 
	 * @param name header name.
	 * @param value header value.
	 */
	private void addHeader(String name, Object value) {
		if(!activeTestStep) throw new IllegalStateException("A header must be added to a Test Step.")
		activeTestStep.headers.put(name, value)
	}
	
	/**
	 * Add a status code assertion on a HttpRequest test step.
	 * 
	 * @param code the expected HTTP status code.
	 */
	TestCaseBuilder statusCode(int code) {
		if(activeTestStep instanceof HttpRequestTestStep) 
			addAssertion(new StatusCodeAssertion(code:code))
		else throw new IllegalStateException("Status Code assertion only allowed on http request test steps.")
		return this
	}
	
	/**
	 * Add a new xpath assertion.
	 * 
	 * @param xpath the xpath expression i.e.: /parent/child/text().
	 * @param expectedValue the expected resulting value from the xpath.
	 * @return this test case builder.
	 */
	TestCaseBuilder xpath(String xpath, String expectedValue) {
		addAssertion(new XPathAssertion(xpath:xpath, expectedValue:expectedValue))
		return this
	}
	
	/**
	 * Add a header to a test step.
	 * 
	 * @param key the header name.
	 * @param value the header value.
	 * @return this test case builder.
	 */
	TestCaseBuilder header(String name, Object value) {
		addHeader(name, value)
		return this
	}
	
	/**
	 * Read and dequeue a message from a queue.
	 * 
	 * @param url the JMS Provider URL i.e: tcp://localhost:61616.
	 * @param queue the name of the queue to consume from.
	 * @return this test case builder.
	 */
	TestCaseBuilder dequeue(String url, String queue) {
		addTestStep(new JmsDequeueTestStep(url:url, queue:queue))
		return this
	}
	
	/**
	 * Create a mock service that listen on the given port and path and returns the response once.
	 * 
	 * @param path the path the service will be listening to i.e.: /resources/people.
	 * @param port the port the service will be listening to.
	 * @param response the response the service will return.
	 * @return this test case builder.
	 */
	TestCaseBuilder mock(String path, int port, String response) {
		addTestStep(new MockResponseTestStep(path:path, port:port, response:response))
		return this
	}
	
	/**
	 * Enqueue a message on the given queue.
	 * 
	 * @param url the JMS Provider URL i.e: tcp://localhost:61616.
	 * @param queue the name of the queue.
	 * @param message the message to enqueue.
	 * @return this test case builder.
	 */
	TestCaseBuilder enqueue(String url, String queue, String message) {
		addTestStep(new JmsEnqueueTestStep(url:url, queue:queue, message:message))
		return this
	}
	
	/**
	 * Add credentials information for the test step.
	 * 
	 * @param username
	 * @param password
	 * @return this test case builder.
	 */
	TestCaseBuilder user(String username, String password) {
		addHeader("username", username)
		addHeader("password", password)
		return this
	}
}
