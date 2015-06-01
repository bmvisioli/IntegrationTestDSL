package model

import javax.jms.TextMessage

import connector.JmsConnector

class JmsDequeueTestStep extends AbstractTestStep {

	String url
	String queue
	
	@Override
	public boolean run() {
		def message = JmsConnector.dequeueMessage(url, queue)
		if(message instanceof TextMessage) {
			result = ((TextMessage)message).getText()
			return true
		}
		return false
	}

}
