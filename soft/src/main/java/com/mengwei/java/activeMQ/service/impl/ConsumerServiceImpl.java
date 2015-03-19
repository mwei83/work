package com.mengwei.java.activeMQ.service.impl;

import javax.annotation.Resource;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.TextMessage;

import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import com.mengwei.java.activeMQ.service.ConsumerService;
@Service
public class ConsumerServiceImpl implements ConsumerService {
	@Resource
	private JmsTemplate jmsTemplate;
	@Resource(name="destinationQueue")
	private Destination destination;
	@Resource(name="jmsTopicTemplate")
	private JmsTemplate jmsTopicTemplate;
	@Resource(name="destinationTopic")
	private Destination destinationTopic;
	
	public void receiveMessage(String message) throws JMSException {
		System.out.println("-----------------ConsumerServiceImpl" + message);
		Message msg = jmsTemplate.receive(destination);
		if (msg instanceof TextMessage) {
			TextMessage textMsg = (TextMessage) msg;
			System.out.println("接收信息监听");
			try {
				System.out.println("接收信息监听：" + textMsg.getText());
			} catch (JMSException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void receiveTopicMessage(String message) throws JMSException {
		System.out.println("-----------------ConsumerServiceTopicImpl" + message);
		Message msg = jmsTopicTemplate.receive(destinationTopic);
		if (msg instanceof TextMessage) {
			TextMessage textMsg = (TextMessage) msg;
			System.out.println("接收信息监听");
			try {
				System.out.println("接收信息监听：" + textMsg.getText());
			} catch (JMSException e) {
				e.printStackTrace();
			}
		}
	}

}
