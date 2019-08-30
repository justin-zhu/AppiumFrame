package com.meitu.test;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import com.meitu.base.Worker;
import com.meitu.entity.DriverEntity;
import com.meitu.utils.JustinUtil;
/**
 * Test类
 * @author p_xiaogzhu
 *2019年4月1日
 *无法测试 shell pm list packages
 *
 */
public class TestCl {
	private Logger logger =Logger.getLogger(this.getClass());
	private Worker worker;
    private List<String> sheetNameList;
	private DriverEntity driverEntity ;	
	
	@BeforeSuite
	public void BeforeSuite(){
		logger.info("Suite前初始化");
		sheetNameList=new ArrayList<String>();
		driverEntity = new DriverEntity();
	}
	@BeforeTest
	public void configuration()
	{	
		//加入List的名称，需要在domain中有相应的实体类		
		sheetNameList.add("WeakNetwork");		
		driverEntity.setPath(JustinUtil.getRootPathCase()+"\\cases.xls");
		driverEntity.setPort("4723");
		driverEntity.setUdid("ZS60A19404A00132");		
		driverEntity.setVersion("9.0");
		driverEntity.setSheetNameList(sheetNameList);		
	}
	
	@Test(dataProvider="moduleNameArray", invocationCount =1)
	public void test1(String sheetName) {			
		logger.info("模块:"+sheetName+"开始执行");
		worker =new Worker(driverEntity,sheetName);
		worker.start();
	}
	
	@DataProvider(name = "moduleNameArray")
	public  Object[][] primeNumbers() {		
		String[][] arr = new String[sheetNameList.size()][1];
	    for (int i = 0; i < arr.length; i++) {
	    	for (int j = 0; j < arr[i].length; j++) {
	    		arr[i][j]=sheetNameList.get(i);
			}			
		}
	    return arr;
	}
	@AfterSuite
	public void tearDown() {
		logger.info("测试结束，导出结果");
		if(worker != null) {
			worker.createResult();
		}else {
			logger.info("测试未能启动，无法输出结果");
		}		
	}	
}
