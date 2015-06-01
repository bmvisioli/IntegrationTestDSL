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
		return result &= validateAssertions()
	}
	
	private void beforeRun() {
		log.info("Running TestStep '${name}'")
	}
	
	private boolean validateAssertions() {
		def valid = true
		if(result)
			assertions.each {
				try{
					def assertionResult = it.assertCondition(result)
					log.info("Assertion '${it.name}' ${assertionResult ? 'succeeded' : 'failed'}.")
					valid &= assertionResult
				} catch(Exception ex) {
					log.info("Assertion '${it.name}' threw an exception.")
					ex.printStackTrace()
					valid = false
				}
		}
		log.info("TestStep ${valid ? 'succeeded' : 'failed'}.")
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
