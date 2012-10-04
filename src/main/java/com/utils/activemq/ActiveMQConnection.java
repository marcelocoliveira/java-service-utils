package com.utils.activemq;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.jms.Session;
import org.apache.activemq.ActiveMQConnectionFactory;
import com.utils.logging.MyLogger;

public class ActiveMQConnection {

	private MyLogger logger = new MyLogger(ActiveMQConnection.class);
	private Connection connection ;
	private ConnectionFactory activeMQFactory;
	private Session session;
	
	
	private void init (String url) throws JMSException{
		logger.logInfo("Creating ActiveMQ Connection.");
		activeMQFactory = new ActiveMQConnectionFactory(url);
		connection = activeMQFactory.createConnection();
    	logger.logInfo("Starting ActiveMQ Connection.");
       	connection.start();
       	logger.logInfo("Creating ActiveMQ Session.");
       	session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
    }
	
	public void shutdown () {
		try {
		if (session != null)
				session.close();
		
		if (connection != null)
			connection.close();
		
		} catch (JMSException e) {
			e.printStackTrace();
			logger.logError(e.getMessage(), e);
		}
	}
	
	public static ActiveMQConnection get (String url) throws JMSException {
		ActiveMQConnection instance = new ActiveMQConnection();
		instance.init(url);
		return instance;
	}
	
	public void createMessageListener (MessageListener listener, QueueName queue) throws JMSException{
		logger.logInfo("Creating ActiveMQ Message Listener.");
		Destination destination = session.createQueue(queue.name());
		MessageConsumer consumer = session.createConsumer(destination);
        consumer.setMessageListener(listener);
     }
	
	
	public void createMessageListener (MessageListener listener, TopicName topicName) throws JMSException{
		logger.logInfo("Creating ActiveMQ Message Listener.");
		Destination destination = session.createTopic(topicName.name());
		MessageConsumer consumer = session.createConsumer(destination);
        consumer.setMessageListener(listener);
     }
	
	public void createMessageListener (MessageListener listener, String topicName) throws JMSException{
		logger.logInfo("Creating ActiveMQ Message Listener.");
		Destination destination = session.createTopic(topicName);
		MessageConsumer consumer = session.createConsumer(destination);
        consumer.setMessageListener(listener);
     }
	
	/*
	 * public void createMessageListener (MessageListener listener, TopicName topicName) throws JMSException{
		try {
		logger.logInfo("Creating ActiveMQ Message Listener.");
		InitialContext ctx = new InitialContext();
		Topic topic = (Topic) ctx.lookup("MIDLINK_TOPIC");
		TopicSubscriber topicSubscriber = session.createDurableSubscriber(topic, "TRACKING");
		topicSubscriber.setMessageListener(listener);
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
     }
	 */

	public void createMessageProducer (ActiveMQMessageProducer clazz, QueueName queue) throws JMSException{
		logger.logInfo("Creating ActiveMQ Message Producer.");
		Destination destination = session.createQueue(queue.name());
       	MessageProducer producer = session.createProducer(destination);
       	ActiveMQMessageProducer.setProducer(producer);
       	ActiveMQMessageProducer.setSession(session);
    }
	
	public void createMessageProducer (ActiveMQMessageProducer clazz, TopicName topicName) throws JMSException{
		logger.logInfo("Creating ActiveMQ Message Producer.");
		Destination destination = session.createTopic(topicName.name());
       	MessageProducer producer = session.createProducer(destination);
       	ActiveMQMessageProducer.setProducer(producer);
       	ActiveMQMessageProducer.setSession(session);
    }
	
	
}
