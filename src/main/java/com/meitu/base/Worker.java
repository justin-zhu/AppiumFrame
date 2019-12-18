package com.meitu.base;

import java.io.File;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;
import org.testng.Assert;

import com.meitu.entity.DriverEntity;
import com.meitu.supplier.AndroidDriverSupplier;
import com.meitu.supplier.AppiumServerSupplier;
import com.meitu.utils.Helper;
import com.meitu.utils.JustinUtil;
import com.meitu.utils.LogcatUtil;
import cn.hutool.core.util.ReflectUtil;
import cn.hutool.poi.excel.ExcelWriter;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.service.local.AppiumDriverLocalService;

public class Worker {
	private boolean operationResult = false;
	private  LogcatUtil logcat;
	private Helper helper;
	private AndroidDriver<AndroidElement> androidDriver;
	public DriverEntity driverEntity;	
	private String sheetName;
	private String path;
	private List<Map<String, Object>> resultList;
	private Object domainObject;
	private AppiumDriverLocalService appiumDriver;
	private final Logger log = Logger.getLogger(this.getClass());

	public Worker(DriverEntity driverEntity, String sheetName) {
		this.driverEntity = driverEntity;
		this.sheetName = sheetName;			
	}

	/**
	 * 实例化一个AppiumServer
	 */	
	public void startAppiumServer() {
		log.info("current device:" + driverEntity.getUdid());
		appiumDriver = AppiumServerSupplier.getAppiumDriver();
		appiumDriver.start();
		log.info("appium server running");
	}

	/**
	 * 实例化androidDriver&Logcat
	 */	
	public void startAndroidDriver() {	
		
		AndroidDriverSupplier.Instance.creatDriver(driverEntity,appiumDriver.getUrl());
		int port = appiumDriver.getUrl().getPort();		
		androidDriver = AndroidDriverSupplier.Instance.getDriver(port);
		path = JustinUtil.getRootPath(driverEntity.getUdid() + "_" + sheetName + JustinUtil.getLocalTime());
		helper = new Helper(androidDriver, path);
		initDaomainObject();		
		logcat = new LogcatUtil(driverEntity.getUdid(), new File(path));
		logcat.start();
	}

	/**
	 * 执行测试
	 */	
	public void execute(String methodName) {
		helper.openWiFi();
		this.method(methodName);	
	}

	/**
	 * 停止Appium服务
	 */	
	public void stopAppiumServer() {
		appiumDriver.stop();
		log.info("appium server is destoryed");
	}
	/**
	 * 停止Driver logcat
	 */
	public void stopAndroidDriver() {
		AndroidDriverSupplier.Instance.stopDriver(appiumDriver.getUrl().getPort());
		logcat.interrupt();
	}

	/**
	 * 写入测试结果
	 * 
	 * @param list
	 * @param path1
	 */
	public void createResult() {
		log.info("用例执行完成,正在生成Excel文件");
		String path1 = path + File.separator + sheetName + "测试结果" + ".xls";
		ExcelWriter excelWriter = new ExcelWriter(path1, sheetName);
		excelWriter.merge(4, "测试结果");
		excelWriter.write(getResultList());
		excelWriter.close();
		log.info("文件已生成,路径:" + path1);
	}

	/**
	 * 获取要测试模块名称domain
	 */
	private void initDaomainObject() {
		resultList = new ArrayList<Map<String, Object>>();
		try {
			// 获取表格中对应的域对象
			Class<?> class1 = Class.forName("com.meitu.controller." + sheetName);
			Constructor<?> con = class1.getConstructor(Helper.class);
			domainObject = con.newInstance(helper);
		} catch (Exception e) {
			log.info("get domain failure arg:" + sheetName);
			e.printStackTrace();
		}
	}

	public void method(String methodName) {
		helper.setMethodName(methodName);
		log.info("userCase:"+methodName);
		try {
			ReflectUtil.invoke(domainObject,methodName);
			operationResult = true;
		} catch (Exception e) {			
			operationResult = false;
			//log.info(e.getMessage());
			e.printStackTrace();
		} finally {
			// 写入单个模块的执行结果
			addResultToList(methodName);
			log.info(methodName+" Test end");
		}
	}

	public void addResultToList(String userCase) {
		Map<String, Object> map = new LinkedHashMap<String, Object>();		
		map.put("步骤名称", sheetName);		
		map.put("操作类型", "method");
		map.put("传递的参数", userCase);
		map.put("实际结果", "" + operationResult);
		map.put("期望结果", "" + true);
		resultList.add(map);
		try {
			Assert.assertEquals(operationResult, true, userCase + "--------->>Failure");
		} catch (AssertionError e) {
			String path = helper.snapshot("fail"+userCase);
			log.info("操作失败,截图保存路径:\r\n"+path);
			throw e;
		}
	}

	public List<Map<String, Object>> getResultList() {
		return resultList;
	}
	public Helper getHelper() {
		return helper;
	}

	public void setHelper(Helper helper) {
		this.helper = helper;
	}
}
