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
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
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
	private WebDriverWait driverWait ;
	private File saveLocal;
	public Helper(AndroidDriver<AndroidElement> driver, String path) {
		this.path = path;
		this.androidDriver = driver;	
		this.action = new AndroidTouchAction(androidDriver);
		driverWait= new WebDriverWait(androidDriver, 15,100); 
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
	public Helper tap(int x, int y) {		
		new AndroidTouchAction(androidDriver).tap(PointOption.point(x, y)).release().perform();			
		log.info("点击成功:tap");
		snapshot("点击");	
		return this;
	}
	public Helper tapByCommand(int x, int y) {
		String deviceName = getDeviceName();	
		RuntimeUtil.execForStr("adb -s  "+deviceName+"  shell input tap "+x+" "+y);	
		return this;
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
	public Helper click(WebElement webElement, String elementName) {			
		if (webElement != null) {
			webElement.click();
			webElement = null;			
			log.info("点击成功:"+elementName);
			snapshot(elementName);			
		} else {
			log.info("点击失败,元素名称:"+elementName);
			snapshot("点击失败的元素名称:"+elementName);
			throw new RuntimeException("元素点击失败:"+elementName);			
		}
		System.gc();
		return this;
	}

	/**
	 * @param element 元素
	 * @param text    输入的内容
	 * @return boolean 操作结果
	 */
	public Helper send(WebElement element, String text) {
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
		return this;
	}

	/**
	 * @param webElement 元素
	 * @return boolean 操作结果
	 */
	public Helper clearText(WebElement webElement) {
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
		return this;
	}
	
	
	/**
	 * 自动上下滑动寻找元素 text
	 * @param text 传入元素的唯一文本值（此方法多用于元素不在当前窗口时）
	 * @return WebElement
	 */
	public WebElement findBySlideText(String text) {
		WebElement element = null;
		String uiautoExp = "new UiScrollable(new UiSelector().scrollable(true)).scrollIntoView(new UiSelector().text(\""
				+ text + "\"))";
		try {
			element = androidDriver.findElementByAndroidUIAutomator(uiautoExp);			
			return element;
		} catch (Exception e) {			
			return null;
		}		

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
			page1 = this.getPageSource();
			sleep(2000);
			if (page1.contains(str)) {
				log.info("元素已找到");
				return true;
			}
			this.swipeDirection("up");
			sleep(1500);
			page2 = getPageSource();
			count++;
		} while (!page1.equals(page2) && count < 15);
		return false;
	}

	/**
	 * @return 返回上一步
	 */
	public Helper back() {
		androidDriver.navigate().back();
		log.info("返回操作执行成功");
		snapshot("返回");	
		return this;
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
	public Helper swipeDirection(String direction) {
		log.info("滑动方向:"+direction);
		if (direction.equals("right")) {
			new AndroidTouchAction(androidDriver).press(PointOption.point(width / 4, height / 2))
			.waitAction(WaitOptions.waitOptions(Duration.ofMillis(1000)))
			.moveTo(PointOption.point(width * 3 / 4, height / 2)).release().perform();			
			//snapshot("右滑" + direction);
			log.info("右滑成功");
			
		} else if (direction.equals("left")) {
			new AndroidTouchAction(androidDriver).press(PointOption.point(width * 3 / 4, height / 2))
			.waitAction(WaitOptions.waitOptions(Duration.ofMillis(1000)))
			.moveTo(PointOption.point(width / 4, height / 2)).release().perform();			
			//snapshot("左滑" + direction);
			log.info("左滑成功");
			
		} else if (direction.equals("down")) {
			new AndroidTouchAction(androidDriver).press(PointOption.point(width / 2, height / 4))
			.waitAction(WaitOptions.waitOptions(Duration.ofMillis(1000)))
			.moveTo(PointOption.point( width / 2, height * 3 / 4)).release().perform();			
			//snapshot("下滑" + direction);
			log.info("下滑成功");
			
		} else if (direction.equals("up")) {
			new AndroidTouchAction(androidDriver).press(PointOption.point(width / 2, height * 3 / 4))
			.waitAction(WaitOptions.waitOptions(Duration.ofMillis(1000)))
			.moveTo(PointOption.point(width / 2, height / 4)).release().perform();			
			//snapshot("上滑" + direction);
			log.info("上滑成功");
			
		} else if (direction.equals("end")) {
			String page1;
			String page2;
			int count = 0;
			do {
				page1 = androidDriver.getPageSource();
				new AndroidTouchAction(androidDriver).press(PointOption.point(width / 2, height * 3 / 4))
				.waitAction(WaitOptions.waitOptions(Duration.ofMillis(1000))).moveTo(PointOption.point(width / 2, height / 4))
				.release().perform();
				sleep(3000);
				//snapshot("滑动至底部");
				page2 = androidDriver.getPageSource();
				count++;
			} while (!page1.equals(page2)|count < 15);
			//snapshot("滑动" + direction);
			
		} else if (direction.equals("top")) {
			String page1;
			String page2;
			int count = 0;
			do {
				page1 = androidDriver.getPageSource();
				new AndroidTouchAction(androidDriver).press(PointOption.point(width / 2, height / 4))
				.waitAction(WaitOptions.waitOptions(Duration.ofMillis(1000)))
				.moveTo(PointOption.point(width / 2, height * 3 / 4)).release().perform();				
				sleep(3000);
				snapshot("滑动至顶部");
				page2 = androidDriver.getPageSource();
				count++;
			} while (!page1.equals(page2)|count < 15);
			//snapshot("滑动" + direction);			
		} 
		snapshot(direction);
		return this;
	}
	/*
	 * 异步滑动
	 */
	public Helper swipeDirectionByCommand(String direction,int count) {
		String deviceName = getDeviceName();
		log.info("滑动方向:"+direction);			
		if (direction.equals("down")) {
			for (int i = 0; i < count; i++) {
				sleep(3000);
				RuntimeUtil.execForStr("adb -s  "+deviceName+"  shell input swipe 600 500 600 1950");
				
			}
			
		} else if (direction.equals("up")) {
			for (int i = 0; i < count; i++) {
				sleep(3000);
				RuntimeUtil.execForStr("adb -s "+deviceName+"  shell input swipe 600 2100 600 700");
				
			}
			RuntimeUtil.execForStr("adb -s "+deviceName+"  shell input swipe 600 2100 600 700");
		} 
		snapshot(direction);
		return this;
	}

	/**
	 * @param actionName 图片名称
	 * @throws InterruptedException 截图失败时，抛出异常
	 */
	public String snapshot(String actionName) {		
		sleep(100);
		//当前时间+动作名称
		String fileName = JustinUtil.getLocalTime() + actionName;		
		File scrFile = androidDriver.getScreenshotAs(OutputType.FILE);		
		saveLocal = new File(path,getMethodName());		
		if(!saveLocal.exists()) {			
			saveLocal.mkdirs();			
		}
		File picture = new File(saveLocal + "\\" + fileName +".png");		
		try {
			
			if(actionName.contains("fail")) {
				FileUtils.copyFile(scrFile, new File(saveLocal+"\\"+"failPic"+"\\"+fileName+".png"));				
				return saveLocal+"\\"+"failPic"+"\\"+fileName+".png";
			}else {
				FileUtils.copyFile(scrFile, picture);
				log.info("screenshot save path:" + picture.getPath());
			}			
		} catch (IOException e1) {
			log.info("screenshot failed");
		} finally {
			saveLocal = null;
			actionName = null;
		}
		return picture.getPath();
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
		log.info("上下文:"+androidDriver.getContextHandles());
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
	public Helper sleep(int ms) {
		try {
			log.info("sleep "+ms+"ms");
			Thread.sleep(ms);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return this;
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
	public Helper isExistClickElseSkip(WebElement element,String elementName) {
		if (element != null) {
			click(element, elementName);
		}else {
			log.info(elementName+",未找到,已跳过");
		}
		return this;
	}
	
	public Helper isExistToast(String key) {
		log.info("Toast提示:"+key);		
		String result = getToast(key);
		if("no".equals(result)) {
			throw new RuntimeException("未找到Toast信息:"+key);
		}else {
			log.info("成功获取到Toast信息:"+result);
		}
		return this;
	}
	public void isNoExistToast(String key) {
		log.info("验证不存在的Toast:"+key);		
		String result = getToast(key);
		if(!"no".equals(result)) {
			throw new RuntimeException("Toast存在:"+key+",请检查");
		}else {
			log.info("验证通过,此Toast不存在");
		}
	}
	/**
	 * 检查元素是否存在
	 * @param element
	 * @param desc
	 */
	public Helper checkElement(WebElement element,String desc) {
		if(element==null) {
			throw new RuntimeException("检查的元素不存在:"+desc);
		}else {
			log.info("检查元素成功!"+desc+"元素存在");
			log.info("界面提示文本:"+element.getText());
		}
		return this;
	}
	
	public String getToast(String key) {				
		try {
			
			WebElement target = driverWait.until(
				     ExpectedConditions.presenceOfElementLocated(By.xpath("//*[contains(@text,'"+key+"')]")));
			return target.getText();
		} catch (Exception e) {
			return "no";
		}
	}
	/**
	 * 隐藏键盘
	 */
	public Helper hideKeyBoard() {
		androidDriver.hideKeyboard();
		log.info("隐藏键盘");	
		sleep(2000);
		return this;
	}
	/**
	 * 移除白名单之外的所有第三方应用(不包含系统应用)
	 */
	public void removeApp() {
		ConcurrentHashMap<String, String> map = new ConcurrentHashMap<String, String>();
		map.put("io.appium.settings", "0");
		map.put("com.tencent.tmgp.sgame", "0");
		map.put("io.appium.uiautomator2.server", "0");
		map.put("io.appium.uiautomator2.server.test", "0");
		map.put("com.tencent.qnet", "0");
		List<String> list = getLocalAppList();
		for (String name : list) {
			log.info("应用名称:"+name);
			if(map.get(name.trim())==null) {
				androidDriver.removeApp(name);
				log.info("已清除应用:"+name);
			}
		}
	}
	/**
	 * 返回第三方应用列表
	 * @return
	 */
	public List<String> getLocalAppList(){
		List<String> list = new ArrayList<String>();
		String cmd = "adb shell pm list packages -3";		
		String str = RuntimeUtil.execForStr(cmd);
		String[] packagesArray = str.split("package:");		
		for (String name : packagesArray) {
			if(name.length()>2) {
				list.add(name.replace("\r\n", ""));					
			}			
		}
		log.info(list.toString());
		return list;
	}
	public void checkSelectStatusIsTrue(WebElement element) {
		String checked = element.getAttribute("checked");
		boolean b = Boolean.parseBoolean(checked);
		if(!b) {
			log.info("元素校验失败");
			throw new RuntimeException("指定的元素未处于选中状态");
		}
		log.info("元素校验成功");
	}
	public void checkSelectStatusIsFalse(WebElement element) {
		String checked = element.getAttribute("checked");
		boolean b = Boolean.parseBoolean(checked);
		if(b) {
			throw new RuntimeException("指定的元素处于选中状态");
		}
		log.info("元素校验成功");
	}
	public Helper closeWiFi() {
		boolean state = androidDriver.getConnection().isWiFiEnabled();
		String deviceName = getDeviceName();
		if(state) {
			RuntimeUtil.execForStr("adb -s "+deviceName+" shell  svc wifi disable");
		}
		return this;
	}

	/**
	 * @return
	 */
	public String getDeviceName() {
		return androidDriver.getCapabilities().getCapability("deviceName").toString();
	}
	public boolean ping() {
		String device = getDeviceName();
		String cmd = "adb -s "+device+" shell ping -c 1 www.qq.com";
		for (int i = 0; i < 10; i++) {
			sleep(2000);
			String str = RuntimeUtil.execForStr(cmd);
			if(str.contains("unknown")) {
				continue;
			}else {
				return true;
			}
		}
		return false;
	}
	public Helper openWiFi() {
		String device = getDeviceName();
		boolean state = androidDriver.getConnection().isWiFiEnabled();
		if(!state) {
			RuntimeUtil.execForStr("adb -s "+device+" shell  svc wifi enable");			
			if(!ping()) {
				throw new RuntimeException("网络无法连接,请检查网络情况");
			}			
		}
		return this;
	}
	public void checkActivity() {
		String sourcePackage=androidDriver.getCapabilities().getCapability("appPackage").toString();
		String package1 = androidDriver.getCurrentPackage();
		if (sourcePackage.equals(package1)) {
			throw new RuntimeException("未能打开程序");
		}else {
			pressKeyCode(3);
			pressKeyCode(3);
			click(findByUiautomatorText("应用市场"), "应用市场");
		}
	}
	/**
	 * 卸载指定的包
	 * @param packageName
	 */
	public void uninstall(String packageName) {
		getAndroidDriver().removeApp(packageName);
	}
	/**
	 * 获取第三方包名
	 */
	public String getAppList_3() {
		String deviceName = getAndroidDriver().getCapabilities().getCapability("deviceName").toString();
		return RuntimeUtil.execForStr("adb -s "+deviceName+" shell pm list packages -3");
	}
	/**
	 * 检查应用是否已经安装在本机,100秒等待时间
	 * @param packageName
	 */
	public void checkAppIsInstall(String packageName) {
		
		for (int i = 0; i < 20; i++) {
			boolean installed = getAndroidDriver().isAppInstalled(packageName);
			if(installed) {
				log.info(packageName+"已安装完成");
				return;
			}else {
				log.info("等待应用安装完成");
				sleep(5000);				
			}
		}
		log.info(packageName+"未能在指定时间内安装完成");
		throw new RuntimeException("应用未能在指定时间内安装完成");
	}
}
