package connector

import javax.jms.DeliveryMode
import javax.jms.Message
import javax.jms.Session

import org.apache.activemq.ActiveMQConnectionFactory

class JmsConnector {

	static void enqueueMessage(String url, String queue, Object message) {
		new ActiveMQConnectionFactory(url).createConnection().with {
			start()
			createSession(false, Session.AUTO_ACKNOWLEDGE).with {
				def textMessage = createTextMessage(message)
				textMessage.with {
					JMSDeliveryMode = DeliveryMode.NON_PERSISTENT
					JMSCorrelationID = UUID.randomUUID().toString()
				}
				createProducer().send(createQueue(queue), textMessage)
			}
			close()
		}
	}
	
	static Message dequeueMessage(String url, String queue) {
		def result
		new ActiveMQConnectionFactory(url).createConnection().with {
			start()
			createSession(false, Session.AUTO_ACKNOWLEDGE).with {
				result = createConsumer(createQueue(queue)).receive()
			}
			close()
		}
		return result
	}
}
