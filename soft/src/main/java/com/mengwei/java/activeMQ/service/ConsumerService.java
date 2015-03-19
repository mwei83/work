package com.mengwei.java.activeMQ.service;

import javax.jms.JMSException;

public interface ConsumerService {

	public void receiveMessage(String message) throws JMSException;
	public void receiveTopicMessage(String message) throws JMSException;
}
