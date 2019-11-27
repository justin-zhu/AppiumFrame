package com.meitu.test;
import org.apache.log4j.Logger;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Listeners;
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
@Listeners
public class TestWeakNetwork {
	private Logger logger =Logger.getLogger(this.getClass());	
	private Worker worker;   
    private DriverEntity driverEntity;
	@BeforeTest
    public void initParameters() {
		driverEntity = new DriverEntity();			
		driverEntity.setPath(JustinUtil.getRootPathCase()+"\\cases.xls");
		driverEntity.setPort("4723");		
		driverEntity.setUdid(DeviceUtils.getDeviceInfo().trim());		
		driverEntity.setVersion("9.0");
		logger.info(driverEntity);
		worker =new Worker(driverEntity,"WeakNetwork");	
		worker.startAppiumServer();			
    }    
	@BeforeClass
	public void connectDriver()
	{	
		worker.startAndroidDriver();
	}
	@AfterClass
	public void stopServer() {
		worker.stopAndroidDriver();
	}
	@AfterTest
	public void tearDown() {
		worker.stopServer();
		logger.info("测试结束,导出结果");
		if(worker != null) {
			worker.createResult();
		}	
	}	
	
	@Test(priority = 1)
	public void testFirstLogin() {			
		logger.info("首次登陆应用商店");			
		worker.execute("firstLogin");
	}	
	@Test(priority = 2)
	public void testReOpenAppStore() {		
		logger.info("非首次登陆应用商店");			
		worker.execute("reOpenAppStore");
	}
	@Test(priority = 3)
	public void testNewGame() {		
		logger.info("新游");			
		worker.execute("newGame");
	}
	@Test(priority = 4)
	public void testHomePageWelfare() {		
		logger.info("福利");			
		worker.execute("homePageWelfare");
	}
	@Test(priority = 5)
	public void testHomePageList() {		
		logger.info("榜单");			
		worker.execute("homePageList");
	}
	@Test(priority = 6)
	public void testHomeClassify() {		
		logger.info("分类");			
		worker.execute("homeClassify");
	}
	@Test(priority = 7)
	public void testHomeClassifyList() {		
		logger.info("分类");			
		worker.execute("homeClassifyList");
	}
	@Test(priority = 8)
	public void testWelfareGift() {		
		logger.info("福利礼包");			
		worker.execute("welfareGift");
	}
	@Test(priority = 9)
	public void testSearch() {		
		logger.info("搜索");			
		worker.execute("search");
	}
	@Test(priority = 10)
	public void testAppInformation() {		
		logger.info("应用详情界面");			
		worker.execute("appInformation");
	}
	@Test(priority = 11)
	public void testCenter() {		
		logger.info("个人中心");			
		worker.execute("center");
	}
	
}
