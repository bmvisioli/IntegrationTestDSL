package model

import groovy.util.logging.Slf4j;

@Slf4j
abstract class AbstractTestStep implements TestStep {

	String name = "unnamed"
	Object result
	List<AbstractAssertion> assertions = []
	Map headers = [:]
	
	public boolean execute() {
		beforeRun()
		def result = run()
		result &= validateAssertions()
		return result
	}
	
	private void beforeRun() {
		log.info("Running TestStep '${name}'")
	}
	
	private boolean validateAssertions() {
		def valid = true
		if(result)
			assertions.each {
				def assertionResult = it.assertCondition(result)
				log.info("Assertion '${it.name}' ${assertionResult ? 'succeeded' : 'failed'}")
				valid &= assertionResult
		}
		log.info("TestStep ${valid ? 'succeeded' : 'failed'}")
		return valid
	}
	
	/**
	 * Run the process defined by this test step.
	 * 
	 * @return if the execution succeeded. 
	 */
	public abstract boolean run()
	
	@Override
	public AbstractAssertion addAssertion(AbstractAssertion assertion) {
		assertions << assertion
		return assertion
	}
}
