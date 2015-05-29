package model

import groovy.util.logging.Slf4j;

@Slf4j
abstract class AbstractTestStep implements TestStep {

	String name
	String result
	List<Assertion> assertions = []
	
	public boolean execute() {
		beforeRun()
		def result = run()
		result &= validateAssertions()
		return result
	}
	
	private void beforeRun() {
		log.info("Running TestStep ${name ?: 'unnamed'}")
	}
	
	private boolean validateAssertions() {
		def valid = true
		if(result)
			assertions.each {
				def assertionResult = it.assertCondition(result)
				log.info("Assertion ${it.name ?: 'unnamed'} ${assertionResult ? 'succeed' : 'failed'}")
				valid &= assertionResult
		}
		return valid
	}
		
	public abstract boolean run()
	
	@Override
	public Assertion addAssertion(Assertion assertion) {
		assertions << assertion
		return assertion
	}
}
