package com.meitu.test;

import java.net.URL;
import java.time.Duration;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import cn.hutool.core.util.RuntimeUtil;
import io.appium.java_client.android.Activity;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
public class Test01 {
	AndroidDriver<AndroidElement>	driver ;
	
	public void afterMethod() throws InterruptedException {		
		 DesiredCapabilities capabilities = new DesiredCapabilities();//设置各项参数
	        capabilities.setCapability("deviceName", "K6AIKNN51219P99");        
	        capabilities.setCapability("udid",  "K6AIKNN51219P99");
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
				driver = new AndroidDriver<AndroidElement>(new URL("http://127.0.0.1:"+"4725"+"/wd/hub"), capabilities);
				System.out.println("Driver已连接");
				driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
			} catch (Exception e) {				
				e.printStackTrace();
			}	
			System.out.println("休息10秒");
			Thread.sleep(5000);
			System.out.println("打开另外一个应用");	
			//com.tencent.qnet.ui.login.LoginActivity
			//com.tencent.qnet.ui.MainActivity
			Activity activity = new Activity("com.tencent.qnet", "com.tencent.qnet.ui.MainActivity");
			activity.setAppWaitActivity("com.tencent.qnet.ui.MainActivity");
			activity.setAppWaitPackage("com.tencent.qnet");			
			driver.startActivity(activity);				
			Thread.sleep(5000);			
			driver.findElementById("com.tencent.qnet:id/btnAddProfile").click();
			Thread.sleep(5000);	
			driver.pressKey(new KeyEvent(AndroidKey.HOME));
			
			Thread.sleep(10000);
			
	}
	@Test
	public void test1() {		
		String str = RuntimeUtil.execForStr("adb shell pm list packages");
		System.out.println(str);
		if(str.contains("weibo")) {
			System.out.println("已安装新浪微博");
		}
	}
}
