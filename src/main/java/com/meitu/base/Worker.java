package com.meitu.base;

import java.io.File;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;
import org.testng.Assert;
import com.meitu.base.AbstractWorker;
import com.meitu.ctrl.AppiumServerCtrl;
import com.meitu.ctrl.AndroidDriverCtrl;
import com.meitu.entity.DriverEntity;
import com.meitu.entity.TestCaseEntity;
import com.meitu.utils.Helper;
import com.meitu.utils.JustinUtil;
import com.meitu.utils.LogcatUtil;

import cn.hutool.core.util.ReflectUtil;
import cn.hutool.json.JSONUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelWriter;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;

public class Worker extends AbstractWorker {
	boolean operationResult = false;
	private  LogcatUtil logcat;
	private Helper helper;
	public AndroidDriver<AndroidElement> androidDriver;
	public DriverEntity driverEntity;
	public List<TestCaseEntity> caseList;
	public String sheetName;
	public String path;
	private List<Map<String, Object>> resultList;
	private Object domainObject;
	Logger log = Logger.getLogger(this.getClass());

	public Worker(DriverEntity driverEntity, String sheetName) {
		this.driverEntity = driverEntity;
		this.sheetName = sheetName;
		log.debug("driverEntity:" + JSONUtil.toJsonStr(driverEntity));		
	}

	/**
	 * 实例化一个AppiumServer
	 */
	@Override
	public void getAppiumServer() {
		log.info("current deviceUDID:" + driverEntity.getUdid());
		new Thread(() -> {
			try {
				AppiumServerCtrl.Instance.startServer(driverEntity.getPort(), driverEntity.getUdid());
			} catch (Exception e1) {
				throw new RuntimeException("appium server failure");
			}
		}).start();
		log.info("waiting appiumServer startup");
		try {
			Thread.sleep(18000);
			log.info("appium thread sleep end");
		} catch (InterruptedException e) {			
		}
	}

	/**
	 * 实例化androidDriver
	 */
	@Override
	public void getDriver() {
		androidDriver = AndroidDriverCtrl.Instance.creatDriver(driverEntity).getDriver(driverEntity.getPort());
		log.info("getAndroidDriver:" + androidDriver);
		helper = new Helper(androidDriver, path);
	}

	/**
	 * 执行测试
	 */
	@Override
	public void execute() {
		initDaomainObject();
		logcat = new LogcatUtil(driverEntity.getUdid(), new File(path));
		logcat.start();
		for (TestCaseEntity caseEntity : caseList) {
			log.info(JSONUtil.toJsonStr(caseEntity));
			this.method(caseEntity);
		}
		logcat.interrupt();
	}

	/**
	 * 被构造函数调用，初始化测试前的必要参数
	 */
	@Override
	public void init() {
		path = JustinUtil.getRootPath(driverEntity.getUdid() + "_" + sheetName + JustinUtil.getLocalTime());
		ExcelReader reader = JustinUtil.readExcel(driverEntity.getPath(), sheetName);
		log.info("init sheetName：" + sheetName);
		driverEntity.setAppPackage(reader.getCell(1, 0).toString());
		log.info("app package name:" + driverEntity.getAppPackage());
		driverEntity.setAppActivity(reader.getCell(1, 1).toString());
		log.info("app activity name:" + driverEntity.getAppActivity());
		caseList = reader.read(2, 3, TestCaseEntity.class);
		reader.close();
	}

	/**
	 * 停止Appium服务
	 */
	@Override
	public void stopServerAndDriver() {
		AppiumServerCtrl.Instance.stopServer(driverEntity.getPort());
		AndroidDriverCtrl.Instance.stopDriver(driverEntity.getPort());
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
		excelWriter.merge(5, "测试结果");
		excelWriter.write(getResultList());
		excelWriter.close();
		log.info("文件已生成,路径：" + path1);
	}

	/**
	 * 获取要测试模块名称domain
	 */
	public void initDaomainObject() {
		resultList = new ArrayList<Map<String, Object>>();
		try {
			// 获取表格中对应的域对象
			Class<?> class1 = Class.forName("com.meitu.domain." + sheetName);
			Constructor<?> con = class1.getConstructor(Helper.class);
			domainObject = con.newInstance(helper);
		} catch (Exception e) {
			log.info("Get domain failure!! arg:" + sheetName);
		}
	}

	public void method(TestCaseEntity userCase) {
		helper.setMethodName(userCase.getType());
		log.info("userCase:" + userCase.getType().trim());
		try {
			ReflectUtil.invoke(domainObject, userCase.getType().trim());
			operationResult = true;
		} catch (Exception e) {
			// 接收异常,不处理,
			operationResult = false;
			log.info(e.getMessage());
			e.printStackTrace();
		} finally {
			// 写入单个模块的执行结果
			addResultToList(userCase);
			log.info(userCase+" Test end");
		}
	}

	public void addResultToList(TestCaseEntity userCase) {
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		boolean expectboolean = new Boolean(userCase.getExpect());
		map.put("步骤名称", userCase.getStep());		
		map.put("操作类型", userCase.getType());
		map.put("传递的参数", userCase.getArg());
		map.put("实际结果", "" + operationResult);
		map.put("期望结果", "" + expectboolean);
		resultList.add(map);
		try {
			Assert.assertEquals(operationResult, expectboolean, userCase.getStep() + "--------->>执行失败");
		} catch (AssertionError e) {
			helper.snapshot(userCase.getStep());
			log.info("实际执行结果:" + operationResult + ",期望结果:" + expectboolean);
		}
	}

	public List<Map<String, Object>> getResultList() {
		return resultList;
	}
}
