package com.meitu.test;

import java.net.URL;
import java.time.Duration;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidTouchAction;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;
public class Test01 {
	AndroidDriver<WebElement>	driver ;
	@BeforeMethod
	public void afterMethod() throws InterruptedException {		
		 DesiredCapabilities capabilities = new DesiredCapabilities();//设置各项参数
	        capabilities.setCapability("deviceName", "ZS62C19408A00067");        
	        capabilities.setCapability("udid",  "ZS62C19408A00067");
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
				driver = new AndroidDriver<WebElement>(new URL("http://127.0.0.1:"+"4725"+"/wd/hub"), capabilities);
				System.out.println("Driver已连接");
				driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
			} catch (Exception e) {				
				e.printStackTrace();
			}
			System.out.println("开始休眠");
			Thread.sleep(10000);
			String express ="//*[@resource-id='com.tencent.southpole.appstore:id/recycle_view']/android.view.ViewGroup["+1+"]";
			new AndroidTouchAction(driver).press(PointOption.point(500, 300))
			.waitAction(WaitOptions.waitOptions(Duration.ofSeconds(1)))
			.moveTo(PointOption.point( 500, 1000)).release().perform();	
			Thread.sleep(5000);
			new AndroidTouchAction(driver).press(PointOption.point(500, 1550))
			.waitAction(WaitOptions.waitOptions(Duration.ofSeconds(2)))
			.moveTo(PointOption.point(535, 807)).release().perform();
			WebElement element = driver.findElementByXPath(express);
			element.click();
			System.out.println("成功点击");
			Thread.sleep(10000);
			
	}
	@Test
	public void test1() {		
		
	}
}
