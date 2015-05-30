package model

class DelayTestStep extends AbstractTestStep {

	long time
	
	@Override
	public boolean run() {
		Thread.sleep(time)
		return true
	}
	
}
