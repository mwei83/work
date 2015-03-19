package com.mengwei.java.lock;

public class MyThread extends Thread {

	private Team team;
	MyThread(Team team) {
		super();
		this.team = team;
	}
	@Override
	public void run() {
		team.add(Thread.currentThread().getName());
//		team.add2(Thread.currentThread().getName());
	}

}
