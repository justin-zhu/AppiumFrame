package com.meitu.ctrl;

import io.appium.java_client.android.AndroidDriver;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;

import com.meitu.entity.DriverEntity;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

public enum AndroidDriverCtrl {
    Instance;
    static HashMap<String,AndroidDriver<WebElement>> hashMap = new HashMap<>();
    Logger logger =Logger.getLogger(this.getClass());
    AndroidDriver<WebElement> driver;
    /**
     * 创建Driver
     * @param driverEntity
     * @throws MalformedURLException
     */
    public AndroidDriverCtrl creatDriver(DriverEntity driverEntity)  {
    	logger.info("Driver端口号："+driverEntity.getPort());
    	logger.info("Driver Activity："+driverEntity.getAppActivity());
    	logger.info("Driver Package："+driverEntity.getAppPackage());
        DesiredCapabilities capabilities = new DesiredCapabilities();//设置各项参数
        capabilities.setCapability("deviceName", driverEntity.getUdid());
        logger.info("deviceName:"+driverEntity.getUdid());
        capabilities.setCapability("udid",  driverEntity.getUdid());
        capabilities.setCapability("platformVersion", driverEntity.getVersion());
        logger.info("platformVersion:"+driverEntity.getVersion());
        capabilities.setCapability("platformName", "Android");
        capabilities.setCapability("automationName", "Appium");
        capabilities.setCapability("noReset", true);
        capabilities.setCapability("appPackage",  driverEntity.getAppPackage());
        capabilities.setCapability("appActivity",  driverEntity.getAppActivity());
        capabilities.setCapability("unicodeKeyboard", false);// 控制系统键盘
        capabilities.setCapability("resetKeyboard", false);
        logger.info("开始生成Driver");      
		try {
			driver = new AndroidDriver<WebElement>(new URL("http://127.0.0.1:"+driverEntity.getPort()+"/wd/hub"), capabilities);
			logger.info("Driver生成成功");
		} catch (Exception e) {
			logger.info("初始化Driver失败，请核对相关参数是否正确");
			e.printStackTrace();
		}
        
        logger.info("Driver："+driver);
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
		logger.info("Driver已退出");
	}
}
