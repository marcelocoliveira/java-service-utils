package com.utils.activemq;

import static com.utils.SystemConfiguration.*;
import java.util.HashMap;
import java.util.Map;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.ExceptionListener;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.StreamMessage;
import javax.jms.TextMessage;
import javax.jms.Topic;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import com.utils.SystemConfiguration;
import com.utils.logging.MyLogger;

/**
 * NOT a good abstraction of what a JMS Sender should look like, but a 
 * barely functional piece of reusable JMS logic and reconnection.
 *
 */
public class JmsSender implements InitializingBean, DisposableBean, ExceptionListener {

	private MyLogger logger = new MyLogger(JmsSender.class);
	private boolean connected = false;
	private ConnectionFactory activeMQFactory;
	private Connection connection;
	private Session session; 
	private SystemConfiguration systemConfiguration;
	private Map<String, MqProducerInfo> producerLookup = new HashMap<String, MqProducerInfo>();
	
	private Map<String, MessageListener> queueListeners = new HashMap<String, MessageListener>();
	private Map<String, MessageListener> topicListeners = new HashMap<String, MessageListener>();
	
	public void sendStreamMessage(short[] message, String queueConfigKey) throws JmsUtilsException {
		try {
			
			doSendStreamMessage(message, queueConfigKey);
			
		} catch (Exception e) {
			logger.logError("Unexpected exception while processing message ", e);
			throw new JmsUtilsException(e);
		}
	}

	public void sendTextMessage(String message, String queueConfigKey) throws JmsUtilsException {
		try {

			doSendTextMessage(message, queueConfigKey);
			
		} catch (Exception e) {
			logger.logError("Unexpected exception while processing message ", e);
			throw new JmsUtilsException(e);
		}
	}
	
	public void sendStreamMessageToTopic(short[] message, String queueConfigKey) throws JmsUtilsException {
		try {

			doSendStreamMessageToTopic(message, queueConfigKey);
			
		} catch (Exception e) {
			logger.logError("Unexpected exception while processing message ", e);
			throw new JmsUtilsException(e);
		}
	}

	public void sendTextMessageToTopic(String message, String queueConfigKey) throws JmsUtilsException {
		try {

			doSendTextMessageToTopic(message, queueConfigKey);
			
		} catch (Exception e) {
			logger.logError("Unexpected exception while processing message ", e);
			throw new JmsUtilsException(e);
		}
	}
	
	public void registerQueueConsumer(String destination, MessageListener listener) throws Exception {

		Queue queue = session.createQueue(destination);
		MessageConsumer consumer = session.createConsumer(queue);
		consumer.setMessageListener(listener);
		
		// keeping a reference for further use during reconnection
		queueListeners.put(destination, listener);
	}
	
	public void registerTopicConsumer(String destination, MessageListener listener) throws Exception {
		Topic topic = session.createTopic(destination);
		MessageConsumer consumer = session.createConsumer(topic);
		consumer.setMessageListener(listener);
		
		// keeping a reference for further use during reconnection
		topicListeners.put(destination, listener);
	}

	private void doSendStreamMessage(short[] message, String queueConfigKey) throws Exception {
		StreamMessage jmsMessage = session.createStreamMessage();

		for (short s: message) { 
			jmsMessage.writeShort(s);
		}
		
		doSendMessage(jmsMessage, queueConfigKey, queueProducerInfoFactory);
	}
	
	private void doSendTextMessage(String message, String queueConfigKey) throws Exception {
		TextMessage jmsMessage = session.createTextMessage(message);

		doSendMessage(jmsMessage, queueConfigKey, queueProducerInfoFactory);
	}

	private void doSendStreamMessageToTopic(short[] message, String queueConfigKey) throws Exception {
		StreamMessage jmsMessage = session.createStreamMessage();

		for (short s: message) { 
			jmsMessage.writeShort(s);
		}
		
		doSendMessage(jmsMessage, queueConfigKey, topicProducerInfoFactory);
	}
	
	private void doSendTextMessageToTopic(String message, String queueConfigKey) throws Exception {
		TextMessage jmsMessage = session.createTextMessage(message);

		doSendMessage(jmsMessage, queueConfigKey, topicProducerInfoFactory);
	}

	private void connect() throws Exception {
		
		releaseResources();
		
		String brokerURL = systemConfiguration.getEndpointURL(ACTIVE_MQ);
		
		activeMQFactory = new ActiveMQConnectionFactory(brokerURL);
		
    	logger.logInfo("Creating ActiveMQ Connection.");

    	connection = activeMQFactory.createConnection();
    	
    	logger.logInfo("Starting ActiveMQ Connection.");
       	
    	// TODO handle reconnection 
    	connection.start();
       	
    	logger.logInfo("Creating ActiveMQ Session.");
       	
    	session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
    	
    	connection.setExceptionListener(this);
    	
    	// re-registering previously registered consumers
    	registerListeners();
    	
    	connected = true;
    	
	}
	
	private void registerListeners() throws Exception {
		
		if (queueListeners != null && queueListeners.size() > 0) {
			for (String k : queueListeners.keySet()) {
				registerQueueConsumer(k, queueListeners.get(k));
			}
		}
		if (topicListeners != null && topicListeners.size() > 0) {
			for (String k : topicListeners.keySet()) {
				registerTopicConsumer(k, topicListeners.get(k));
			}
		}
	}

	private void releaseResources() {

		if (session != null) {
			try {
				session.close();
			} catch (Exception e) {
			}
		}
		if (connection != null) {
			try {
				connection.close();
			} catch (Exception e) {
				
			}
		}
		if (producerLookup != null && producerLookup.size() > 0) {
			producerLookup.clear();
		}
	}
	
	@SuppressWarnings("unused")
	private static class MqProducerInfo {
		
		public MqProducerInfo(Destination destination, MessageProducer producer) {
			this.destination = destination;
			this.producer = producer;
		}
		
		public Destination destination;
		public MessageProducer producer;
	}
	
	@SuppressWarnings("unused")
	private static class MqConsumerInfo {
		
		public MqConsumerInfo(Destination destination, MessageConsumer consumer) {
			this.destination = destination;
			this.consumer = consumer;
		}
		
		public Destination destination;
		public MessageConsumer consumer;
	}

	private void doSendMessage(Message amqMessage, String destinationKey, MqProducerInfoFactory amqInfoFactory)
		throws Exception {
		
		MqProducerInfo mqInfo = producerLookup.get(destinationKey);
		
		if (mqInfo == null) {

			String destinationName = systemConfiguration.getConfigElement(ACTIVE_MQ, destinationKey);
			
			if (destinationName == null) {
				throw new JmsUtilsException("NULL Queue name for config parameter " + destinationKey);
			}
			
			logger.logInfo("Creating destination " + destinationName + " for config key " + destinationKey);
	       	
			mqInfo = amqInfoFactory.createActiveMqInfo(destinationName);
			
	       	producerLookup.put(destinationKey, mqInfo);
		}
		
		mqInfo.producer.send(amqMessage);
	}
	
	private static interface MqProducerInfoFactory {
		MqProducerInfo createActiveMqInfo(String destination) throws Exception;
	}
	
	private MqProducerInfoFactory queueProducerInfoFactory = new MqProducerInfoFactory() {
		@Override
		public MqProducerInfo createActiveMqInfo(String destination) throws Exception {
			Queue queue = session.createQueue(destination);
	       	MessageProducer queueProducer = session.createProducer(queue);
	       	return new MqProducerInfo(queue, queueProducer);
		}
	};
	
	private MqProducerInfoFactory topicProducerInfoFactory = new MqProducerInfoFactory() {
		@Override
		public MqProducerInfo createActiveMqInfo(String destination) throws Exception {
			Topic topic = session.createTopic(destination);
			MessageProducer topicProducer = session.createProducer(topic);
	       	return new MqProducerInfo(topic, topicProducer);
		}
	};

	@Override
	public void afterPropertiesSet() throws Exception {
		logger.logInfo("Starting JMS");
		connect();
	}

	public void setSystemConfiguration(SystemConfiguration systemConfiguration) {
		this.systemConfiguration = systemConfiguration;
	}

	@Override
	public void destroy() throws Exception {
		releaseResources();
	}

	@Override
	public void onException(JMSException arg0) {
		
		try {
			connected = false;
			while (!connected) {
				
				reconnect();
				
				if (!connected) {
					try { 
						Thread.sleep(30000);
					} catch (InterruptedException e) {
						// intentionally blank
					}
				}
			}
			logger.logInfo("Successfully reconnected to Active MQ");
		} catch (Exception e) {
			logger.logError("Unexpected exception while reconnecting to Active MQ", e);
		}
	}

	private void reconnect() {
		try {
			connect();
		} catch (Exception e) {
		}
	}
}
