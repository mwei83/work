package com.mengwei.java.activeMQ.service;

import java.io.Serializable;

import javax.jms.Destination;

public interface ProducerService {

	public void sendMessage(Destination destination, String message);
	
	public void sendTopicMessage(Destination destination, String message);
	
	public void sendMessage(Destination destination, Serializable obj);
	
}
