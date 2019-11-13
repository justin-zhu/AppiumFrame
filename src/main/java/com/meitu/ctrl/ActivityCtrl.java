package com.meitu.ctrl;

import org.openqa.selenium.WebElement;

import io.appium.java_client.android.Activity;
import io.appium.java_client.android.AndroidDriver;

public class ActivityCtrl {
	
	public void startActivity(AndroidDriver<WebElement> driver) {
		Activity activity = new Activity("com.tencent.qnet", "com.tencent.qnet.ui.MainActivity");
	     activity.setAppWaitPackage("");
	     activity.setAppWaitActivity("");
	     driver.startActivity(activity);
	}
}
