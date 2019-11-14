package com.meitu.ctrl;

import org.openqa.selenium.WebElement;

import io.appium.java_client.android.Activity;
import io.appium.java_client.android.AndroidDriver;

public class ActivityCtrl {
	
	public static void startActivityOfSetting(AndroidDriver<WebElement> driver) {
		try {
			Activity activity = new Activity("com.android.settings", "com.android.settings.Settings");
		     activity.setAppWaitPackage("com.tencent.southpole.appstore");
		     activity.setAppWaitActivity("com.tencent.southpole.appstore.activity.SplashActivity");
		     driver.startActivity(activity);
		} catch (Exception e) {
			System.out.println("activity start failed");
			throw new RuntimeException("activity启动失败");
		}
	}
	
	public static void startActivityOfQnet(AndroidDriver<WebElement> driver) {
		try {
			Activity activity = new Activity("com.tencent.qnet", "com.tencent.qnet.ui.login.LoginActivity");
		     activity.setAppWaitPackage("com.tencent.southpole.appstore");
		     activity.setAppWaitActivity("com.tencent.southpole.appstore.activity.SplashActivity");
		     driver.startActivity(activity);
		} catch (Exception e) {
			System.out.println("activity start failed");
			throw new RuntimeException("activity启动失败");
		}
	}
	
	
}
