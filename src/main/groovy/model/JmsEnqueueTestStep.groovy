package model

import connector.JmsConnector;

class JmsEnqueueTestStep extends AbstractTestStep {

	String url
	String queue
	String message	
	
	@Override
	public boolean run() {
		JmsConnector.enqueueMessage(url, queue, message)
		return true
	}
	
	@Override
	public AbstractAssertion addAssertion(AbstractAssertion assertion) {
		throw new IllegalStateException("JmsEnqueue test step does not allow any assertion because it produces no result.")
	}

}
