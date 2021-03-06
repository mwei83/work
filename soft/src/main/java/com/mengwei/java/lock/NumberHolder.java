package com.mengwei.java.lock;



public class NumberHolder
{
	private int number;

	public synchronized void increase()
	{
		while (0 != number)
		{
			try
			{
				wait();
			}
			catch (InterruptedException e)
			{
				e.printStackTrace();
			}
		}

		// 能执行到这里说明已经被唤醒
		// 并且number为0
		number++;
		System.out.println(number);

		// 通知在等待的线程
		notifyAll();
	}

	public synchronized void decrease()
	{
		while (0 == number)
		{
			try
			{
				wait();
			}
			catch (InterruptedException e)
			{
				e.printStackTrace();
			}

		}

		// 能执行到这里说明已经被唤醒
		// 并且number不为0
		number--;
		System.out.println(number);
		notifyAll();
	}

}






