package com.mengwei.java.activeMQ.listener;

import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import javax.jms.TextMessage;

import org.springframework.jms.support.converter.MessageConverter;

import com.mengwei.java.activeMQ.entity.Email;

public class ConsumerMessageListener implements MessageListener {

	private MessageConverter messageConverter;
	
	public void onMessage(Message message) {
		if (message instanceof TextMessage) {
			TextMessage textMsg = (TextMessage) message;
			System.out.println("接收信息监听");
			try {
				System.out.println("接收信息监听：" + textMsg.getText());
			} catch (JMSException e) {
				e.printStackTrace();
			}
		} else if (message instanceof MapMessage) {
			MapMessage mapMessage = (MapMessage) message;
			try {
				System.out.println(mapMessage.getString(""));
			} catch (JMSException e) {
				e.printStackTrace();
			}
		} else if (message instanceof ObjectMessage) {
			ObjectMessage objMessage = (ObjectMessage) message;
			try {
				/*Object obj = objMessage.getObject();
				Email email = (Email) obj;*/
				Email email = (Email) messageConverter.fromMessage(objMessage);
				System.out.println(email);
			} catch (JMSException e) {
				e.printStackTrace();
			}
			
		}
	}

	public MessageConverter getMessageConverter() {
		return messageConverter;
	}

	public void setMessageConverter(MessageConverter messageConverter) {
		this.messageConverter = messageConverter;
	}

}
