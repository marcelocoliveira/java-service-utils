package com.utils.activemq;

import javax.jms.MessageProducer;
import javax.jms.Session;


public class ActiveMQMessageProducer {

	private static MessageProducer producer;
	private static Session session;
	
	
	public static MessageProducer getProducer() {
		return producer;
	}
	public static void setProducer(MessageProducer producer) {
		ActiveMQMessageProducer.producer = producer;
	}
	public static Session getSession() {
		return session;
	}
	public static void setSession(Session session) {
		ActiveMQMessageProducer.session = session;
	}
	
	
}
