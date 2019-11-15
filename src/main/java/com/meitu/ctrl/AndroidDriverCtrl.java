package com.meitu.ctrl;

import io.appium.java_client.android.AndroidDriver;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;

import com.meitu.entity.DriverEntity;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
/**
 *  保证全局唯一，所有driver由getDriver提供
 * @author Administrator
 *
 */
public enum AndroidDriverCtrl {
    Instance;
    private static HashMap<String,AndroidDriver<WebElement>> hashMap = new HashMap<>();
    private Logger logger =Logger.getLogger(this.getClass());
    private AndroidDriver<WebElement> driver;
    /**
     * 创建Driver
     * @param driverEntity
     * @throws MalformedURLException
     */
    public AndroidDriverCtrl creatDriver(DriverEntity driverEntity)  {    	
        DesiredCapabilities capabilities = new DesiredCapabilities();//设置各项参数
        capabilities.setCapability("deviceName", driverEntity.getUdid());        
        capabilities.setCapability("udid",  driverEntity.getUdid());
        capabilities.setCapability("platformVersion", driverEntity.getVersion());       
        capabilities.setCapability("platformName", "Android");
        capabilities.setCapability("autoGrantPermissions", true);
        capabilities.setCapability("automationName", "uiautomator2");
        capabilities.setCapability("noReset", true);
        capabilities.setCapability("appPackage",  "com.tencent.southpole.appstore");
        capabilities.setCapability("appActivity",  "com.tencent.southpole.appstore.activity.SplashActivity");
        capabilities.setCapability("unicodeKeyboard", false);// 控制系统键盘
        capabilities.setCapability("resetKeyboard", false);       
		try {
			driver = new AndroidDriver<WebElement>(new URL("http://127.0.0.1:"+driverEntity.getPort()+"/wd/hub"), capabilities);
			logger.info("Driver init successed!");
		} catch (Exception e) {
			logger.info("Driver init failed!");
			e.printStackTrace();
		}       
        hashMap.put(driverEntity.getPort(),driver);
        return this;
    }
    /**
     * 获取Driver
     * @param port
     * @return
     */
    public AndroidDriver<WebElement> getDriver(String port){
        AndroidDriver<WebElement> androidDriver = hashMap.get(port);
        return androidDriver;
    }
    /**
           * 停止Driver
     * @param port
     */
    public  void stopDriver(String port) {
		AndroidDriver<WebElement> androidDriver = hashMap.get(port);
		androidDriver.quit();	
		logger.info("Driver quit successed!");
	}
}
