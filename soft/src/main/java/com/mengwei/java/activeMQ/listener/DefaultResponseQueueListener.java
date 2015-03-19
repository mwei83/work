package com.mengwei.java.activeMQ.listener;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

public class DefaultResponseQueueListener implements MessageListener {

	public void onMessage(Message message) {
		if (message instanceof TextMessage) {
			TextMessage textMessage = (TextMessage) message;
			try {
				System.out.println("DefaultResponseQueueListener" + textMessage.getText());
			} catch (JMSException e) {
				e.printStackTrace();
			}
		}
	}

}
