package com.mengwei.java.lock;

public class MyThread2 extends Thread {

	private Team team;
	MyThread2(Team team) {
		super();
		this.team = team;
	}
	@Override
	public void run() {
//		team.add(Thread.currentThread().getName());
		team.add2(Thread.currentThread().getName());
	}

}
