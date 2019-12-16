package com.meitu.supplier;

import java.io.File;

import com.meitu.utils.JustinUtil;

import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;

public class AppiumServerSupplier {
	
	public static  synchronized AppiumDriverLocalService getAppiumDriver() {
		String rootpath = System.getProperty("user.dir")+File.separator+"log4j";
		File logFile = new File(rootpath, JustinUtil.getLocalTime()+".log");
		AppiumServiceBuilder serviceBuilder = new AppiumServiceBuilder();
		serviceBuilder.usingAnyFreePort()
		.withIPAddress("127.0.0.1")
		.withLogFile(logFile);		
		AppiumDriverLocalService service = AppiumDriverLocalService.buildService(serviceBuilder);		
		return service;
	}
}
