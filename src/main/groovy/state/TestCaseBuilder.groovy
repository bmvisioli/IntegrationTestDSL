package state

import model.AbstractAssertion
import model.ContainsAssertion
import model.DelayTestStep
import model.HttpRequestTestStep
import model.SqlTestStep
import model.StatusCodeAssertion
import model.TestCase
import model.TestCaseImpl
import model.TestStep
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
	 * Create a new HttpRequest test step.
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
		activeTestStep.assertions << assertion
	}
	
	/**
	 * 
	 */
	TestCaseBuilder statusCode(int code) {
		addAssertion(new StatusCodeAssertion(code:code))
		return this
	}
}
