package com.meitu.utils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

/**
 * 线程池
 * @author p_xiaogzhu
 *2019年3月28日
 *
 */
public class ThreadPoolUtil{
	private static ExecutorService executor;
	private static ScheduledExecutorService scheduledThreadPool; 
	private ThreadPoolUtil(){}
	
	public static ExecutorService getCachedThreadPool()
	{
		if(executor==null)
		{
			executor=Executors.newCachedThreadPool();
		}
		return executor;
	}
	public static ScheduledExecutorService getScheduledThreadPool()
	{
		if(scheduledThreadPool==null)
		{
			scheduledThreadPool=Executors.newScheduledThreadPool(1);
		}
		return scheduledThreadPool;
	}
	
}
