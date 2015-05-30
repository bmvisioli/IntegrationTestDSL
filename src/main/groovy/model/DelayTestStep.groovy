package model

class DelayTestStep extends AbstractTestStep {

	long time
	
	@Override
	public boolean run() {
		Thread.sleep(time)
		return true
	}
	
	@Override
	public AbstractAssertion addAssertion(AbstractAssertion assertion) {
		throw new IllegalStateException("Delay test step does not allow any assertion because it produces no result.")
	}
	
}
