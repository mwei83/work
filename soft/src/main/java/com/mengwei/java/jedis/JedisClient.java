package com.mengwei.java.jedis;

import java.util.List;

import org.junit.Test;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Pipeline;

public class JedisClient {
	public static void main(String[] args) {
		   Jedis jedis = new Jedis("10.189.228.54",6379);  
//		   testPipeline();
//	        map.put("name", "nomouse");  
//	        map.put("password", "123456");  
//	        map.put("sex", "male");  
//	        jedis.hmset("user", map);  
	        List<String> list = jedis.hmget("user", "name","password");  
	        System.out.println("info=" + jedis.info());  
	        System.out.println("password=" + list.get(1));  
	        System.out.println(jedis.get("123"));
	        System.out.println(jedis.expire("123",1231));
	        jedis.zadd("score", 99, "tim1");
	        jedis.zadd("score", 99, "tim2");
	        jedis.zadd("score", 999, "tim3");
	        jedis.zadd("score", 9999, "tim4");
	        System.out.println("ZSET-zscore:"+jedis.zscore("score", "tim3"));
	        System.out.println("ZSET-zincrby:"+jedis.zincrby("score", 1,"tim3"));
	        System.out.println("ZSET-zrank:"+jedis.zrank("score","tim3"));
	        System.out.println("ZSET-zrangeWithScores:"+jedis.zrangeWithScores("score",0,3));
	        System.out.println("ZSET-zrangeByScore:"+jedis.zrangeByScore("score",0,99));
	        System.out.println(jedis.zcard("score"));
	        System.out.println(jedis.zrange("score", 0, 2));
	        System.out.println(jedis.keys("123*"));
	        System.out.println(jedis.expire("1234", 20));
	        System.out.println(jedis.ttl("1234"));
	        System.out.println(jedis.get("1234"));
	        String[] strs = {"123","111","1234","119"};
	        System.out.println(jedis.mset(strs));
	        System.out.println(jedis.get("1234"));
	        System.out.println(jedis.decrBy("1234", 12));
	    } 
	
	public static void testPipeline(){
		
		Jedis jedis = new Jedis("10.189.228.54",63791);  
		long start = System.currentTimeMillis();
//		for (int i = 0; i < 10000; i++) {
		
//			jedis.set(""+i, ""+i);
//		}
		for (int i = 0; i < 10000; i++) {
			jedis.get(""+i);
		}
		long start1 = System.currentTimeMillis();
		System.out.println("total:"+(start1-start)+"avg:"+(start1-start)/1000);
		Pipeline p = jedis.pipelined();
		for (int i = 0; i < 10000; i++) {
			p.set(""+i, ""+i);
		}
		for (int i = 0; i < 10000; i++) {
			p.get(""+i);
		}
		p.sync();
		long start2 = System.currentTimeMillis();
		System.out.println(start2-start1);
		
	}
	@Test
	public void testMaster(){
		long start = System.currentTimeMillis();
		int n = 5;
		for (int i = 0; i < n; i++) {
			Thread  td = new Thread(new ThreadJedis(i+"sname"));
			td.start();
		}
		System.out.println("activeCountMain1 : " + Thread.getAllStackTraces());
		System.out.println("activeCountMain1 : " + Thread.activeCount());
        while (true)
        {
            if ( Thread.activeCount() == 2 ) break;
        }
		System.out.println("total:"+(System.currentTimeMillis()-start));
		System.out.println("qps:"+(n*1000)/((System.currentTimeMillis()-start)/1000));
	}
}
