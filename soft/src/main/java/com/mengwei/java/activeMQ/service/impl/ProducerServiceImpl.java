package com.mengwei.java.activeMQ.service.impl;

import java.io.Serializable;

import javax.annotation.Resource;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.Session;

import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.jms.core.ProducerCallback;
import org.springframework.jms.core.SessionCallback;
import org.springframework.stereotype.Component;

import com.mengwei.java.activeMQ.service.ProducerService;

@Component
public class ProducerServiceImpl implements ProducerService {

	@Resource
	private JmsTemplate jmsTemplate;
	@Resource(name="jmsTopicTemplate")
	private JmsTemplate jmsTopicTemplate;
	
	public void sendMessage(Destination destination, final String message) {
		System.out.println("---------------ProducerServiceImpl-----------------");
		System.out.println("---------------ProducerServiceImpl" + message);
		jmsTemplate.send(destination, new MessageCreator() {
			public Message createMessage(Session session) throws JMSException {
				/*TextMessage textMessage = session.createTextMessage(message);
				textMessage.setJMSReplyTo(responseDestination);
				return textMessage;*/
				return session.createTextMessage(message);
			}
		});
	}
	
	public void sendMessage(final Destination destination, final Serializable obj) {
		jmsTemplate.convertAndSend(destination, obj);
		jmsTemplate.execute(new SessionCallback<Object>() {
			public Object doInJms(Session session) throws JMSException {
				MessageProducer producer = session.createProducer(destination);
				Message message = session.createObjectMessage(obj);
				producer.send(message);
				return null;
			}
			
		});
		jmsTemplate.execute(new ProducerCallback<Object>() {

			public Object doInJms(Session session, MessageProducer producer)
					throws JMSException {
				Message message = session.createObjectMessage(obj);
				producer.send(destination, message);
				return null;
			}
			
		});
	}

	@Override
	public void sendTopicMessage(Destination destination, final String message) {
		System.out.println("---------------ProducerServiceTopicImpl" + message);
		jmsTopicTemplate.send(destination, new MessageCreator() {
			public Message createMessage(Session session) throws JMSException {
				return session.createTextMessage(message);
			}
		});
	}
	
}
