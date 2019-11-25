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
import com.meitu.utils.DeviceUtils;
import com.meitu.utils.JustinUtil;
/**
 * Test类
 * @author p_xiaogzhu
 *
 */
public class TestWeakNetwork {
	private Logger logger =Logger.getLogger(this.getClass());
	private Worker worker;
    private List<String> sheetNameList;
	private DriverEntity driverEntity ;	
	
	@BeforeSuite
	public void BeforeSuite(){		
		sheetNameList=new ArrayList<String>();
		driverEntity = new DriverEntity();
	}
	@BeforeTest
	public void configuration()
	{	
		//添加测试项目	
		sheetNameList.add("WeakNetwork");		
		driverEntity.setPath(JustinUtil.getRootPathCase()+"\\cases.xls");
		driverEntity.setPort("4723");
		//自动获取连接的设置UDID		
		driverEntity.setUdid(DeviceUtils.getDeviceInfo().trim());		
		driverEntity.setVersion("9.0");			
	}
	
	@Test(dataProvider="moduleNameArray", invocationCount =1)
	public void test1(String sheetName) throws InterruptedException {
		logger.info("*****************************************");
		logger.info("Test start");	
		logger.info("*****************************************");
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
