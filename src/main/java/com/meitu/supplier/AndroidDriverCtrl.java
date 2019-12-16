package com.meitu.supplier;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;

import org.apache.log4j.Logger;
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
    private static HashMap<String,AndroidDriver<AndroidElement>> hashMap = new HashMap<>();
    private Logger logger =Logger.getLogger(this.getClass());
    private AndroidDriver<AndroidElement> driver;
    /**
     * 创建Driver
     * @param driverEntity
     * @throws MalformedURLException
     */
    public synchronized AndroidDriverCtrl  creatDriver(DriverEntity driverEntity,URL url)  {    	
        DesiredCapabilities capabilities = new DesiredCapabilities();//设置各项参数
        capabilities.setCapability("deviceName", driverEntity.getUdid());        
        capabilities.setCapability("udid",  driverEntity.getUdid());
        capabilities.setCapability("platformVersion", driverEntity.getVersion());       
        capabilities.setCapability("platformName", "Android");
        capabilities.setCapability("autoGrantPermissions", true);
        capabilities.setCapability("automationName", "uiautomator2");
        capabilities.setCapability("newCommandTimeout",150);
        capabilities.setCapability("noReset", true);
        capabilities.setCapability("appPackage",  "com.tencent.southpole.appstore");
        capabilities.setCapability("appActivity",  "com.tencent.southpole.appstore.activity.SplashActivity");
        capabilities.setCapability("unicodeKeyboard", false);// 控制系统键盘
        capabilities.setCapability("resetKeyboard", false);       
		try {			
			driver = new AndroidDriver<AndroidElement>(url, capabilities);
			logger.info("driver init successed");					
		} catch (Exception e) {
			logger.info("driver init failure");
			e.printStackTrace();
		}       
        hashMap.put(String.valueOf(url.getPort()),driver);
        return this;
    }
    /**
     * 获取Driver
     * @param port
     * @return
     */
    public synchronized AndroidDriver<AndroidElement> getDriver(int port){
        AndroidDriver<AndroidElement> androidDriver = hashMap.get(String.valueOf(port));
        return androidDriver;
    }
    /**
           * 停止Driver
     * @param port
     */
    public  synchronized void stopDriver(int port) {
		AndroidDriver<AndroidElement> androidDriver = hashMap.get(String.valueOf(port));
		androidDriver.quit();	
		logger.info("driver quit successed");
	}
}
