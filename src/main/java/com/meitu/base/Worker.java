package com.meitu.base;
import java.io.File;
import org.apache.log4j.Logger;
import com.meitu.base.AbstractWorker;
import com.meitu.ctrl.AppiumServerCtrl;
import com.meitu.ctrl.AndroidDriverCtrl;
import com.meitu.entity.DriverEntity;
import com.meitu.entity.TestCaseEntity;
import com.meitu.utils.Helper;
import com.meitu.utils.JustinUtil;
import com.meitu.utils.HelperManager;
import com.meitu.utils.LogcatUtil;
import cn.hutool.core.util.ReflectUtil;
import cn.hutool.json.JSONUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelWriter;

public class Worker extends AbstractWorker {	
	Logger logger =Logger.getLogger(this.getClass());
	public Worker(DriverEntity driverEntity, String sheetName) {
		this.driverEntity = driverEntity;
		this.sheetName = sheetName;
		logger.debug("传入Worker构造方法中的driverEntity参数：" + JSONUtil.toJsonStr(driverEntity));
	}
	/**
	 * 实例化一个AppiumServer
	 */
	@Override
	public void getAppiumServer() {
		logger.info("当前设备：" + driverEntity.getUdid());		
		new Thread(()->{
			try {
				AppiumServerCtrl.Instance.startServer(driverEntity.getPort(), driverEntity.getUdid());
			} catch (Exception e1) {				
				e1.printStackTrace();
			}
		}
		).start();
		logger.info("等待AppiumServer启动");
		try {
			Thread.sleep(15000);
			logger.info("休眠结束");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 实例化一个androidDriver
	 *
	 */
	@Override
	public void getDriver() {
		androidDriver=AndroidDriverCtrl.Instance.creatDriver(driverEntity).getDriver(driverEntity.getPort());			
		logger.info("androidDriver：" + androidDriver);
		helperManager = new HelperManager(new Helper(androidDriver, path), sheetName);
		logger.info("helperManager：" + androidDriver);
	}
	/**
	 * 执行测试
	 */
	@Override
	public void execute() {			
		logcat = new LogcatUtil(driverEntity.getUdid(), new File(path));
		logcat.start();		
		for (TestCaseEntity caseEntity : getCaseList()) {
			logger.info("---------------------"+JSONUtil.toJsonStr(caseEntity)+"---------------------");
			String operation = caseEntity.getOperation().trim();
			try {
				ReflectUtil.invoke(helperManager, operation, caseEntity);
			} catch (Exception e) {
				throw new RuntimeException("方法未指定参数："+caseEntity.getType());
			}			
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
		logger.info("初始化sheetName：" + sheetName);
		driverEntity.setAppPackage(reader.getCell(1, 0).toString());		
		driverEntity.setAppActivity(reader.getCell(1, 1).toString());	
		setCaseList(reader.read(2, 3, TestCaseEntity.class));
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
		logger.info("用例执行完成，正在生成EXCEL结果文件");
		String path1 = path + File.separator + sheetName + "测试结果" + ".xls";
		ExcelWriter excelWriter = new ExcelWriter(path1, sheetName);
		excelWriter.merge(6, "测试结果");
		excelWriter.write(helperManager.getResultList());
		excelWriter.close();
		logger.info("文件已生成，路径：" + path1);
	}
}
