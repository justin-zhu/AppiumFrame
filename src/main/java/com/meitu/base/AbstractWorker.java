package com.meitu.base;

/**
 * 抽象类模版
 * @author p_xiaogzhu
 *2019年3月18日
 *
 */
public abstract class AbstractWorker {	
	
	abstract public void getAppiumServer();
	abstract public void getDriver();
	abstract public void init();
	abstract public void execute();
	abstract public void stopServerAndDriver();
	final public void start(){
		init();
		getAppiumServer();
		getDriver();		
		execute();		
		//createResult();
		stopServerAndDriver();
	}
	
}
