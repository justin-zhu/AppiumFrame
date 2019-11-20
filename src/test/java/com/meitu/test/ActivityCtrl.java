package com.meitu.test;

import io.appium.java_client.android.Activity;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;

public class ActivityCtrl extends Thread{
	
	String packageName ;
	String activityName;
	AndroidDriver<AndroidElement> driver;
	
	public ActivityCtrl(AndroidDriver<AndroidElement> driver,String packageName, String activityName) {		
		this.packageName = packageName;
		this.activityName = activityName;
		this.driver = driver;
	}


	@Override
	public void run() {		
		Activity activity = new Activity("com.tencent.qnet", "com.tencent.qnet.ui.login.LoginActivity");		    
		driver.startActivity(activity);		
	}	
	
		
}
