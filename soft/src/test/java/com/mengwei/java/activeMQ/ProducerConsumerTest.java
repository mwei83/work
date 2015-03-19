package com.mengwei.java.activeMQ;

import javax.annotation.Resource;
import javax.jms.Destination;
import javax.jms.JMSException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.mengwei.java.activeMQ.service.ConsumerService;
import com.mengwei.java.activeMQ.service.ProducerService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/spring-jms.xml")
public class ProducerConsumerTest {

	@Resource
	private ProducerService producerService;
	@Resource
	private ConsumerService consumerService;
	@Resource(name="destinationQueue")
	private Destination destination;
	@Resource(name="destinationTopic")
	private Destination destinationTopic;
	/**
	 * 
	* @Description:发送信息点对点
	* @param
	* @return 
	* @throws 
	* @author 孟伟
	* @date   2015-3-17
	 */
//	@Test
	public void testSend() {
		for (int i=0; i<100; i++) {
			producerService.sendMessage(destination, "发送mq信息" + (i+1));
		}
	} 
	
	/**
	 * 
	* @Description:发送信息点对点
	* @param
	* @return 
	* @throws 
	* @author 孟伟
	* @date   2015-3-17
	 */
	@Test
	public void testTopicSend() {
		for (int i=0; i<10; i++) {
			producerService.sendTopicMessage(destinationTopic, "发送mq信息" + (i+1));
		}
	} 

//	@Test
	public void testActive() {
		for (int i=0; i<200; i++) {
			try {
				consumerService.receiveMessage("");
			} catch (JMSException e) {
				e.printStackTrace();
			}
		}
	}
	
	@Test
	public void testTopicActive() {
		for (int i=0; i<10; i++) {
			try {
				consumerService.receiveTopicMessage("");
			} catch (JMSException e) {
				e.printStackTrace();
			}
		}
	}

}
