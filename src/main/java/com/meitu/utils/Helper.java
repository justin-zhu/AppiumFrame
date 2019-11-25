package com.meitu.utils;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.android.AndroidTouchAction;
import io.appium.java_client.android.connection.ConnectionState;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;
import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.concurrent.TimeUnit;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import cn.hutool.core.util.RuntimeUtil;

/**
 * @author p_xiaogzhu 2019年3月11日 元素查找类
 */
public class Helper {
	private String path;	
	private int width;
	private int height;
	private String methodName;
	private AndroidTouchAction action;
	private AndroidDriver<AndroidElement> androidDriver;		
	private Logger log = Logger.getLogger(this.getClass());
	/**
	 * 构造函数
	 * 
	 * @param driver
	 */
	public Helper(AndroidDriver<AndroidElement> driver, String path) {
		this.path = path;
		this.androidDriver = driver;	
		this.action = new AndroidTouchAction(androidDriver);
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);		
		log.info("Active Stack info:"+action);		
		width = androidDriver.manage().window().getSize().width;
		height = androidDriver.manage().window().getSize().height;		
	}
	
	public AndroidDriver<AndroidElement> getAndroidDriver() {
		return androidDriver;
	}

	public void setAndroidDriver(AndroidDriver<AndroidElement> androidDriver) {
		this.androidDriver = androidDriver;
	}

	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}	

	/**
	 * 使用ID定位
	 * @param id 
	 * @return WebElement
	 */
	public WebElement findById(String id) {
		WebElement element = null;		
		try {
			element = androidDriver.findElement(By.id(id));			
			return element;
		} catch (WebDriverException e) {			
			return null;
		}
	}
	/**
	 * 使用XPATH方式定位
	 * @param xpath
	 * @return
	 */
	public WebElement findByXpath(String xpath) {		
		try {
			WebElement element = androidDriver.findElementByXPath(xpath);
			return element;
		} catch (Exception e) {
			return null;
		}
	}
	

	/**
	 * 通过 classname 定位
	 * 
	 * @param classname
	 * @return WebElement
	 */
	public WebElement findByClassName(String classname) {
		WebElement element = null;		
		try {
			element = androidDriver.findElement(By.className(classname));			
			return element;
		} catch (Exception e) {			
			return null;
		}
	}	
	
	/**
	 * 通过classname和text混合定位
	 * 
	 * @param classname
	 * @param text
	 * @return WebElement
	 */
	public WebElement findByClassNameAndText(String classname, String text) {		
		String exp = "new UiSelector().className(" + "\"" + classname + "\"" + ").text(" + "\"" + text + "\"" + ")";
		try {
			WebElement element = androidDriver.findElementByAndroidUIAutomator(exp);			
			return element;
		} catch (Exception e) {			
			return null;
		}
	}

	/**
	 * 通过id和text混合定位
	 * 
	 * @param id
	 * @param text
	 * @return WebElement
	 */
	public WebElement findByIdAndText(String id, String text) {		
		String exp = "new UiSelector().resourceId(" + "\"" + id + "\"" + ").text(" + "\"" + text + "\"" + ")";
		try {
			WebElement element = androidDriver.findElementByAndroidUIAutomator(exp);			
			return element;
		} catch (Exception e) {			
			return null;
		}
	}	

	/**
	 * @param text 页面元素中存在唯一一个文本值时，使用文本定位（没有id时，推荐使用此种方法）
	 * @return WebElement
	 */
	public WebElement findByUiautomatorText(String text) {		
		String value = "new UiSelector().textContains" + "(" + "\"" + text + "\"" + ")";
		try {
			WebElement element = androidDriver.findElementByAndroidUIAutomator(value);			
			return element;
		} catch (WebDriverException e) {			
			return null;
		}
	}
	

	/**
	 * @param className 与findByClassName_index方法类似（此种方法更快一些）
	 * @param index
	 * @return WebElement
	 */
	public WebElement findByUiautomatorClassnameInstance(String className, int index) {
		WebElement element = null;		
		String exp = "new UiSelector().className(" + "\"" + className + "\"" + ").instance(" + index + ")";
		try {			
			element = androidDriver.findElementByAndroidUIAutomator(exp);			
			return element;
		} catch (Exception e) {			
			return null;
		}
	}

	/**
	 * @param x 根据坐标点击，通常用于无法定位元素时使用
	 * @param y
	 * @return boolean 操作结果
	 */
	public void tap(int x, int y) {		
		new AndroidTouchAction(androidDriver).tap(PointOption.point(x, y)).release().perform();			
		log.info("点击成功:tap");
		snapshot("点击");		
	}

	/**
	 * 返回当前页的activity信息	 * 
	 * @return Activity值
	 */
	public String getActivity() {
		return androidDriver.currentActivity();
	}

	/**
	 * @param webElement  传入元素
	 * @param elementName 元素名称
	 * @return boolean 操作结果
	 */
	public void click(WebElement webElement, String elementName) {			
		if (webElement != null) {
			webElement.click();
			webElement = null;			
			log.info("点击成功:"+elementName);
			snapshot(elementName);			
		} else {
			log.info("点击失败:"+elementName);
			snapshot("fail"+elementName);
			throw new RuntimeException("元素点击失败");			
		}
		System.gc();
	}

	/**
	 * @param element 元素
	 * @param text    输入的内容
	 * @return boolean 操作结果
	 */
	public void send(WebElement element, String text) {
		if (element == null) {
			log.info("输入内容失败,元素未找到");
			snapshot("输入失败");	
			throw new RuntimeException("元素未找到,输入失败");
		} else {
			element.sendKeys(text);
			element = null;
			log.info("输入内容成功:" + text);
			snapshot("输入"+text+"成功");			
		}
	}

	/**
	 * @param webElement 元素
	 * @return boolean 操作结果
	 */
	public void clearText(WebElement webElement) {
		if (webElement == null) {
			log.info("清除失败,元素不存在");
			snapshot("清除失败");			
		} else {
			androidDriver.pressKey(new KeyEvent(AndroidKey.FORWARD_DEL));
			webElement.clear();
			webElement = null;
			log.info("清除完成");
			snapshot("清除完成");			
		}
	}
	
	
	/**
	 * 自动上下滑动寻找元素 text
	 * @param text 传入元素的唯一文本值（此方法多用于元素不在当前窗口时）
	 * @return WebElement
	 */
	public WebElement findBySlideText(String text) {
		WebElement element = null;
		if (isExist(text)) {
			String uiautoExp = "new UiSelector().textContains(\"" + text + "\")";
			element = androidDriver.findElementByAndroidUIAutomator(uiautoExp);			
			return element;
		}		
		return null;

	}
	public WebElement findByaccessibilityid(String accessi) {
		try {
			WebElement element = androidDriver.findElementByAccessibilityId(accessi);
			return element;
		} catch (Exception e) {			
			return null;
		}
		
	}
	/**
	 *
	 * @param text 传入元素的唯一文本值（此方法多用于元素不在当前窗口时，可自动左右滑动寻找元素）
	 * @return WebElement
	 */
	public WebElement findBySlideTextHorizontal(String text) {	
		
		String ui = "new UiScrollable(new UiSelector().scrollable(true)).setAsHorizontalList().scrollIntoView(new UiSelector().text(\""
				+ text + "\"))";
		try {
			WebElement element = androidDriver.findElementByAndroidUIAutomator(ui);			
			return element;
		} catch (Exception e) {			
			return null;
		}
	}

	/**
	 * @param text 传入元素的唯一id值（此方法多用于元素不在当前窗口时，可自动上下滑动寻找元素）
	 * @return WebElement
	 */
	public WebElement findBySlideId(String id) {
		WebElement element = null;
		sleep(3000);
		String uiautoExp = "new UiScrollable(new UiSelector().scrollable(true)).scrollIntoView(new UiSelector().resourceId(\""
				+ id + "\"))";
		try {
			element = androidDriver.findElementByAndroidUIAutomator(uiautoExp);			
			return element;
		} catch (Exception e) {			
			return null;
		}
	}
	
	/**
	 * @return 返回当前页面的XML信息
	 */
	public String getPageSource() {
		return androidDriver.getPageSource();
	}

	/**
	 * 滚动搜索15次
	 * @param str 从源文件中查找是否存在某个名为str的元素信息
	 */
	public boolean isExist(String str) {
		
		
		int count = 0;
		String page1;
		String page2;
		do {
			page1 = getPageSource();
			if (page1.contains(str)) {
				return true;
			}
			new AndroidTouchAction(androidDriver).press(PointOption.point(width / 2, height * 3 / 4))
			.waitAction(WaitOptions.waitOptions(Duration.ofSeconds(2))).
			moveTo(PointOption.point(width / 2, height / 4)).release().perform();				
			sleep(2000);
			page2 = getPageSource();
			count++;
		} while (!page1.equals(page2) && count < 15);
		return false;
	}

	/**
	 * @return 返回上一步
	 */
	public void back() {
		androidDriver.navigate().back();
		log.info("返回操作执行成功");
		snapshot("返回");		
	}

	/**
	 * 向上滑
	 * 
	 * @param x 指定起始的x坐标
	 * @param y 指定起始的y坐标
	 */
	public void swipeUpByCustom(int x, int y) {
		sleep(2000);
		new AndroidTouchAction(androidDriver).press(PointOption.point(x, y))
		.waitAction(WaitOptions.waitOptions(Duration.ofSeconds(1)))
		.moveTo(PointOption.point(x, y/2)).release().perform();		
		log.info("上滑成功");
				
	}

	/**
	 * 向下滑
	 * 
	 * @param x 指定起始的x坐标
	 * @param y 指定起始的y坐标
	 */
	public void swipeDownByCustom(int x, int y) {
		sleep(2000);
		new AndroidTouchAction(androidDriver).press(PointOption.point(x, y))
		.waitAction(WaitOptions.waitOptions(Duration.ofSeconds(1)))
		.moveTo(PointOption.point(x, y + (height - y) / 2)).release().perform();		
		log.info("下滑成功");		
	}

	/**
	 * 向左滑
	 * 
	 * @param x 指定起始的x坐标
	 * @param y 指定起始的y坐标
	 */
	public void swipeLeftByCustom(int x, int y) {
		sleep(2000);
		new AndroidTouchAction(androidDriver).press(PointOption.point(x, y))
		.waitAction(WaitOptions.waitOptions(Duration.ofSeconds(1)))
		.moveTo(PointOption.point(3 % 2, y)).release().perform();		
		log.info("左滑成功");			
	}



	/**
	 * 向右滑
	 * 
	 * @param x 指定起始的x坐标
	 * @param y 指定起始的y坐标
	 */
	public void swipeRightByCustom(int x, int y) {		
		action.press(PointOption.point(x, y))
		.waitAction(WaitOptions.waitOptions(Duration.ofSeconds(1)))
		.moveTo(PointOption.point((width - 20), y)).release().perform();				
	}

	/**
	 * 滑动
	 * 
	 * @param direction 传进来一个up down left right
	 * @return
	 */
	public void swipeDirection(String direction) {		
		if (direction.equals("right")) {
			new AndroidTouchAction(androidDriver).press(PointOption.point(width / 4, height / 2))
			.waitAction(WaitOptions.waitOptions(Duration.ofSeconds(1)))
			.moveTo(PointOption.point(width * 3 / 4, height / 2)).release().perform();			
			snapshot("右滑" + direction);
			log.info("右滑成功");
			
		} else if (direction.equals("left")) {
			new AndroidTouchAction(androidDriver).press(PointOption.point(width * 3 / 4, height / 2))
			.waitAction(WaitOptions.waitOptions(Duration.ofSeconds(1)))
			.moveTo(PointOption.point(width / 4, height / 2)).release().perform();			
			snapshot("左滑" + direction);
			log.info("左滑成功");
			
		} else if (direction.equals("down")) {
			new AndroidTouchAction(androidDriver).press(PointOption.point(width / 2, height / 4))
			.waitAction(WaitOptions.waitOptions(Duration.ofSeconds(1)))
			.moveTo(PointOption.point( width / 2, height * 3 / 4)).release().perform();			
			snapshot("下滑" + direction);
			log.info("下滑成功");
			
		} else if (direction.equals("up")) {
			new AndroidTouchAction(androidDriver).press(PointOption.point(width / 2, height * 3 / 4))
			.waitAction(WaitOptions.waitOptions(Duration.ofSeconds(1)))
			.moveTo(PointOption.point(width / 2, height / 4)).release().perform();			
			snapshot("上滑" + direction);
			log.info("上滑成功");
			
		} else if (direction.equals("end")) {
			String page1;
			String page2;
			int count = 0;
			do {
				page1 = androidDriver.getPageSource();
				new AndroidTouchAction(androidDriver).press(PointOption.point(width / 2, height * 3 / 4))
				.waitAction(WaitOptions.waitOptions(Duration.ofSeconds(1))).moveTo(PointOption.point(width / 2, height / 4))
				.release().perform();
				sleep(3000);
				snapshot("滑动至底部");
				page2 = androidDriver.getPageSource();
				count++;
			} while (!page1.equals(page2)&&count < 15);
			snapshot("滑动" + direction);
			
		} else if (direction.equals("top")) {
			String page1;
			String page2;
			int count = 0;
			do {
				page1 = androidDriver.getPageSource();
				new AndroidTouchAction(androidDriver).press(PointOption.point(width / 2, height / 4))
				.waitAction(WaitOptions.waitOptions(Duration.ofSeconds(1)))
				.moveTo(PointOption.point(width / 2, height * 3 / 4)).release().perform();				
				sleep(3000);
				snapshot("滑动至顶部");
				page2 = androidDriver.getPageSource();
				count++;
			} while (!page1.equals(page2)&&count < 15);
			snapshot("滑动" + direction);			
		} 
	}

	/**
	 * @param actionName 图片名称
	 * @throws InterruptedException 截图失败时，抛出异常
	 */
	public void snapshot(String actionName) {
		sleep(1000);
		//当前时间+动作名称
		String fileName = JustinUtil.getLocalTime() + actionName;
		log.debug("fileName:"+fileName);
		File scrFile = androidDriver.getScreenshotAs(OutputType.FILE);
		log.debug("Screenshot:"+scrFile.getPath());
		File saveLocal = new File(path,getMethodName());
		log.debug("saveLocal:"+saveLocal.getPath());
		if(!saveLocal.exists()) {			
			saveLocal.mkdirs();
			log.debug("文件夹不存在,已创建:"+saveLocal.getPath());
		}else {
			log.debug("Folder already exists");
		}		
		File picture = new File(saveLocal + "\\" + fileName +".png");		
		try {
			FileUtils.copyFile(scrFile, picture);
			log.info("screenshot save path:" + picture.getPath());
			if(actionName.contains("fail")) {
				FileUtils.copyFile(scrFile, new File(saveLocal+"\\"+"failPic"+fileName+".png"));
				log.debug("fail operation :"+saveLocal+"\\"+"failPic"+fileName+".png");
			}			
		} catch (IOException e1) {
			log.info("screenshot failed");
		} finally {
			saveLocal = null;
			actionName = null;
		}
	}

	/**
	 * @param element 用于H5页面视频元素的暂停操作
	 */
	public void H5pause(WebElement element) {
		androidDriver.executeScript("arguments[0].pause()", element);
		String url = (String) androidDriver.executeScript("return arguments[0].currentSrc;", element);
		log.info("已暂停,URL地址为:" + url);		
	}

	/**
	 * @param element 用于H5页面视频元素的播放操作
	 */
	public void H5player(WebElement element) {
		androidDriver.executeScript("arguments[0].play()", element);
		String url = (String) androidDriver.executeScript("return arguments[0].currentSrc;", element);
		log.info("开始播放,URL地址为:" + url);		
	}

	/**
	 * @param element 调用JS对元素进行点击（用于H5页面）
	 */
	public void H5clickByJs(WebElement element) {
		androidDriver.executeScript("arguments[0].click();", element);
		log.info("开始点击");
	}

	/**
	 * @return 切换于原生界面
	 */
	public void contextToNative() {
		androidDriver.context("NATIVE_APP");
		log.info("已切换至:NATIVE_APP");
		sleep(2000);		
	}
	public void getContext() {
		log.info("上下文:"+androidDriver.getContext());
	}

	/**
	 * 切换于webview界面（用于H5操作）
	 * 
	 * @return 操作结果
	 */
	public void contextToWebview() {
		/*
		 * 切换为WebView
		 */
		androidDriver.context("WEBVIEW_chrome");
		log.info("已切换至:WEBVIEW_chrome");
		sleep(2000);		
	}

	/**
	 * 休眠
	 * 
	 * @param ms 毫秒（1秒=1000ms）
	 */
	public void sleep(int ms) {
		try {
			log.info("sleep "+ms+"ms");
			Thread.sleep(ms);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 键盘事件
	 * 
	 * @param code 对应的key值（请百度安卓Keycode大全）
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public void pressKeyCode(int code) {		
		androidDriver.pressKeyCode(code);
		snapshot("KeyEvent" + code);		
	}
	/**
	 * 回到桌面
	 */
	public void pressKeyCodeToHome() {
		log.info("返回桌面");
		pressKeyCode(3);
	}
	// 清理数据
	public void clearCache(String cmd) {
		try {
			RuntimeUtil.exec(cmd);
			log.info(cmd + "data clean done");
			Thread.sleep(2000);
		} catch (Exception e) {
			snapshot("fail-" + "clearCache");

		}
	}
	// 结束应用商店进程
	public void killAppStore() {
		RuntimeUtil.execForStr("adb shell am force-stop com.tencent.southpole.appstore");
		log.debug("stop com.tencent.southpole.appstore successed");
	}

	// 结束QNET进程
	public void killQNET() {
		log.debug("stop com.tencent.qnet");
		RuntimeUtil.execForStr("adb shell am force-stop com.tencent.qnet");
		log.debug("stop com.tencent.qnet successed");
	}

	// 清量应用商店数据
	public void clearAppSroreData() {
		log.debug("clear com.tencent.southpole.appstore");
		String cmd = "adb shell pm clear com.tencent.southpole.appstore";
		clearCache(cmd);
	}
	/**
	 * 切换网络到飞行模式
	 */
	public void changeNetworkingToAIRPLANE() {		
		ConnectionState state = new ConnectionState(ConnectionState.AIRPLANE_MODE_MASK);
		androidDriver.setConnection(state);
	}
	/**
	 * 切换网络到WiFi
	 */
	public void changeNetworkingToWiFi() {		
		ConnectionState state = new ConnectionState(ConnectionState.WIFI_MASK);
		androidDriver.setConnection(state);
	}
	/**
	 * 切换网络到数据流量
	 */
	public void changeNetworkingToData() {		
		ConnectionState state = new ConnectionState(ConnectionState.DATA_MASK);
		androidDriver.setConnection(state);
	}
	/**
	 * 打开通知栏
	 */
	public void openNotifications() {
		androidDriver.openNotifications();
	}
	/**
	 * 关闭通知栏（需要紧随打开通知栏使用，否则是一个返回键操作）
	 */
	public void closeNotifications() {
		androidDriver.navigate().back();
	}
	/**
	 * 元素如果存在则点击，不存在，跳过
	 * @param element
	 * @param elementName
	 */
	public void isExist(WebElement element,String elementName) {
		if (element != null) {
			click(element, elementName);
		}else {
			log.info(elementName+",未找到,已跳过");
		}
	}
	
	public void isExistToast(String key) {
		log.info("准备捕获toast:"+key);
		String result = getToast(key);
		if("no".equals(result)) {
			throw new RuntimeException("未找到Toast控件");
		}else {
			log.info("成功获取到Toast信息:"+result);
		}
	}
	/**
	 * 检查元素是否存在，仅做判断
	 * @param element
	 * @param desc
	 */
	public void checkElement(WebElement element,String desc) {
		if(element==null) {
			throw new RuntimeException("检查的元素不存在:"+desc);
		}else {
			log.info("检查元素成功!"+desc+"元素存在");
			log.info("界面提示文本:"+element.getText());
		}
	}
	
	public String getToast(String key) {
		WebDriverWait driverWait = new WebDriverWait(androidDriver, 20); 		
		return driverWait.until(new ExpectedCondition<String>() {
			@Override
			public String apply(WebDriver arg0) {
				try {
					WebElement element =androidDriver.findElement(By.xpath("//*[contains(@text,'"+key+"')]"));
					snapshot("toast成功");
					return element.getText();
				} catch (Exception e) {	
					snapshot("toast失败");
					return "no";
				}
				
			}
		});		
	}
	/**
	 * 隐藏键盘
	 */
	public void hideKeyBoard() {
		androidDriver.hideKeyboard();
		log.info("隐藏键盘");		
	}
}
