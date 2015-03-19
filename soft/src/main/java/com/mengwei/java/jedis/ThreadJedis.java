package com.mengwei.java.jedis;

import redis.clients.jedis.Jedis;

public class ThreadJedis implements Runnable {

	public String name ;
	
	public ThreadJedis(String name) {
		super();
		this.name = name;
	}

	@Override
	public void run() {
		Jedis jedis = new Jedis("10.189.228.54",63791);  
		for (int i = 0; i < 10000; i++) {
			jedis.set(""+i,name+i);
		}

	}

}
