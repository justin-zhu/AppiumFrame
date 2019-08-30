package com.meitu.base;
import java.util.List;
import org.openqa.selenium.WebElement;
import com.meitu.entity.DriverEntity;
import com.meitu.entity.TestCaseEntity;
import com.meitu.utils.HelperManager;
import com.meitu.utils.LogcatUtil;
import io.appium.java_client.android.AndroidDriver;

/**
 * 抽象类模版
 * @author p_xiaogzhu
 *2019年3月18日
 *
 */
public abstract class AbstractWorker {
	protected LogcatUtil logcat;
	protected AndroidDriver<WebElement> androidDriver;
	protected HelperManager helperManager;
	protected DriverEntity driverEntity;
	protected List<TestCaseEntity> caseList;
	protected String sheetName;
	protected String path;
	final public void start(){
		init();
		getAppiumServer();
		getDriver();		
		execute();
		//此方法交由testNG调用，子类不需要再强制执行
		//createResult();
		stopServerAndDriver();
	}
	abstract public void getAppiumServer();
	abstract public void getDriver();
	abstract public void init();
	abstract public void execute();
	abstract public void stopServerAndDriver();
	//abstract public void createResult();
	public void setCaseList(List<TestCaseEntity> caseList) {
		this.caseList = caseList;
	}
	public List<TestCaseEntity> getCaseList() {
		return caseList;
	}
}
