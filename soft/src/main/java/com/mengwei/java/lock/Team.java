package com.mengwei.java.lock;

public  class Team {
	public static int num=0;
	public synchronized void add(String name){
		num=num+1;
		System.out.println(name+":1:"+num);
	}
	public synchronized void add2(String name){
		num=num+1;
		System.out.println(name+":2:"+num);
	}
	
	public int getNum() {
		return num;
	}
}
