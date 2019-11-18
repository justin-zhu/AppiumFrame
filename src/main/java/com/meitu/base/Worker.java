package com.meitu.base;
import java.io.File;
import java.util.List;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebElement;

import com.meitu.base.AbstractWorker;
import com.meitu.ctrl.AppiumServerCtrl;
import com.meitu.ctrl.AndroidDriverCtrl;
import com.meitu.entity.DriverEntity;
import com.meitu.entity.TestCaseEntity;
import com.meitu.utils.Helper;
import com.meitu.utils.JustinUtil;
import com.meitu.utils.HelperManager;
import com.meitu.utils.LogcatUtil;
import cn.hutool.json.JSONUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelWriter;
import io.appium.java_client.android.AndroidDriver;

public class Worker extends AbstractWorker {
	protected LogcatUtil logcat;
	protected AndroidDriver<WebElement> androidDriver;
	protected HelperManager helperManager;
	protected DriverEntity driverEntity;
	protected List<TestCaseEntity> caseList;
	protected String sheetName;
	protected String path;	
	Logger log =Logger.getLogger(this.getClass());
	public Worker(DriverEntity driverEntity, String sheetName) {
		this.driverEntity = driverEntity;
		this.sheetName = sheetName;
		log.debug("DriverEntity:" + JSONUtil.toJsonStr(driverEntity));
	}
	/**
	 * 实例化一个AppiumServer
	 */
	@Override
	public void getAppiumServer() {
		log.info("Current deviceUDID:" + driverEntity.getUdid());		
		new Thread(()->{
			try {
				AppiumServerCtrl.Instance.startServer(driverEntity.getPort(), driverEntity.getUdid());
			} catch (Exception e1) {				
				e1.printStackTrace();
			}
		}
		).start();
		log.info("Waiting AppiumServer startup");
		try {
			Thread.sleep(15000);
			log.info("Sleep end");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 实例化androidDriver	 
	 */
	@Override
	public void getDriver() {
		androidDriver=AndroidDriverCtrl.Instance.creatDriver(driverEntity).getDriver(driverEntity.getPort());			
		log.info("GetAndroidDriver:" + androidDriver);
		helperManager = new HelperManager(new Helper(androidDriver, path), sheetName);		
	}
	/**
	 * 执行测试
	 */
	@Override
	public void execute() {			
		logcat = new LogcatUtil(driverEntity.getUdid(), new File(path));
		logcat.start();		
		for (TestCaseEntity caseEntity : caseList) {
			log.info(JSONUtil.toJsonStr(caseEntity));			
			helperManager.method(caseEntity);			
		}
		logcat.interrupt();				
	}
	/**
	 * 被构造函数调用，初始化测试前的必要参数
	 */
	@Override
	public void init() {
		path = JustinUtil.getRootPath(driverEntity.getUdid() +"_"+sheetName+ JustinUtil.getLocalTime());		
		ExcelReader reader = JustinUtil.readExcel(driverEntity.getPath(), sheetName);
		log.info("Init sheetName：" + sheetName);
		driverEntity.setAppPackage(reader.getCell(1, 0).toString());
		log.info("App package name:"+driverEntity.getAppPackage());
		driverEntity.setAppActivity(reader.getCell(1, 1).toString());
		log.info("App activity name:"+driverEntity.getAppActivity());
		caseList=reader.read(2, 3, TestCaseEntity.class);
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
	 * @param list
	 * @param path1
	 */	
	public void createResult() {
		log.info("用例执行完成,正在生成EXCEL结果文件");
		String path1 = path + File.separator + sheetName + "测试结果" + ".xls";
		ExcelWriter excelWriter = new ExcelWriter(path1, sheetName);
		excelWriter.merge(6, "测试结果");
		excelWriter.write(helperManager.getResultList());
		excelWriter.close();
		log.info("文件已生成，路径：" + path1);
	}
	
}
