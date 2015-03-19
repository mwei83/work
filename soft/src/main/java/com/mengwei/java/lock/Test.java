package com.mengwei.java.lock;

public class Test {

	/** 
	 * @Description:
	 * @param
	 * @return 
	 * @throws InterruptedException 
	 * @throws 
	 * @author wb-mw.mengwei
	 * @date   2015-1-29
	 */
	public static void main(String[] args) throws InterruptedException {
		Team team = new Team();
		for (int i = 0; i < 15; i++) {
			Thread thread = new MyThread(team);
			thread.start();
		}
		Thread thread2 = new MyThread2(team);
		thread2.start();
		System.out.println(Team.num);
	}

}
