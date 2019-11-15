package com.meitu.test;

import java.net.URL;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.meitu.domain.WeakNetwork;
import io.appium.java_client.android.AndroidDriver;

public class Test01 {
	AndroidDriver<WebElement>	driver ;
	
	public void afterMethod() {		
		 DesiredCapabilities capabilities = new DesiredCapabilities();//设置各项参数
	        capabilities.setCapability("deviceName", "ZS60A19404A00132");        
	        capabilities.setCapability("udid",  "ZS60A19404A00132");
	        capabilities.setCapability("platformVersion", "9.0");       
	        capabilities.setCapability("platformName", "Android");
	        capabilities.setCapability("autoGrantPermissions", true);
	        capabilities.setCapability("automationName", "Appium");
	        capabilities.setCapability("noReset", true);
	        capabilities.setCapability("appPackage",  "com.tencent.southpole.appstore");
	        capabilities.setCapability("appActivity",  "com.tencent.southpole.appstore.activity.SplashActivity");
	        capabilities.setCapability("unicodeKeyboard", false);// 控制系统键盘
	        capabilities.setCapability("resetKeyboard", false);	        
			try {
				driver = new AndroidDriver<WebElement>(new URL("http://127.0.0.1:"+"4723"+"/wd/hub"), capabilities);
				System.out.println("Driver已连接");
			} catch (Exception e) {				
				e.printStackTrace();
			}			
			
	}
	@Test
	public void test1() {
		
	}
}
