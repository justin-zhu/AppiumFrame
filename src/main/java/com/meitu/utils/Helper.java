package com.meitu.utils;

import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.ReflectUtil;
import cn.hutool.core.util.RuntimeUtil;

import com.meitu.entity.TestCaseEntity;

/**
 * @author p_xiaogzhu 2019年3月11日 元素查找类
 */
public class Helper {
	private String path;
	public AndroidDriver<WebElement> androidDriver;
	private final int DEFAULT_TIME_OUT = 5;
	private Logger logger = Logger.getLogger(this.getClass());
	private WebDriverWait driverWait;
	private int width;
	private int height;
	private String methodName;
	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	/**
	 * 构造函数
	 * 
	 * @param driver
	 */
	public Helper(AndroidDriver<WebElement> driver, String path) {
		this.path = path;
		this.androidDriver = driver;		
		driverWait = new WebDriverWait(androidDriver, DEFAULT_TIME_OUT);
		width = androidDriver.manage().window().getSize().width;
		height = androidDriver.manage().window().getSize().height;
		logger.debug("Helper构造方法运行");
	}

	/**
	 * 对指定元素进行长按操作
	 * 
	 * @param element
	 */
	public boolean longclick_by_element(WebElement element) {
		TouchAction ta = new TouchAction(androidDriver);
		ta.longPress(element, 4000).release().perform();// 长按
		snapshot("长按");
		logger.info("长按完成");
		return true;
	}

	public boolean longclick_by_index(int x, int y) {
		TouchAction ta = new TouchAction(androidDriver);
		ta.longPress(x, y, 4000).release().perform();// 长按
		snapshot("长按");
		logger.info("长按完成");
		return true;
	}

	public boolean longclick(TestCaseEntity testCaseEntity) {
		if (testCaseEntity.getArg().trim().contains("*")) {
			String[] arg1 = testCaseEntity.getArg().trim().split("\\*");
			int x1 = Integer.valueOf(arg1[0]);
			int y1 = Integer.valueOf(arg1[1]);
			return longclick_by_index(x1, y1);
		} else {
			WebElement element = getElement(testCaseEntity);
			return longclick_by_element(element);
		}
	}

	/**
	 * 智能等待
	 * 
	 * @param by
	 */
	public void waitForElement(By by) {
		driverWait.until(new ExpectedCondition<Boolean>() {
			@Override
			public Boolean apply(WebDriver arg0) {
				try {
					androidDriver.findElement(by);
					return true;
				} catch (Exception e) {
					// 如果返回false，会导致测试中断，这里统一返回true，在click处判断元素是否为空
					return true;
				}
			}
		});
	}

	/**
	 * @param id 当页面中只存在唯一的id时，推荐使用id定位
	 * @return WebElement
	 */
	public WebElement find_by_id(String id) {
		WebElement element = null;
		waitForElement(By.id(id));
		try {
			element = androidDriver.findElement(By.id(id));
			printElement(true, id);
			return element;
		} catch (WebDriverException e) {
			printElement(false, id);
			return null;
		}
	}

	/**
	 * @param id    使用id定位
	 * @param index 如果存在多个相同的id,可指定下标，下标从0开始的
	 * @return WebElement
	 */
	public WebElement find_by_id_index(String id, String index) {
		WebElement element = null;
		int i = Convert.toInt(index, 0);
		waitForElement(By.id(id));
		try {
			element = androidDriver.findElements(By.id(id)).get(i);
			printElement(true, id + ":" + index);
			return element;
		} catch (Exception e) {
			printElement(false, id + ":" + index);
			return null;
		}
	}

	/**
	 * 通过 classname 定位
	 * 
	 * @param classname
	 * @return WebElement
	 */
	public WebElement find_by_classname(String classname) {
		WebElement element = null;
		waitForElement(By.className(classname));
		try {
			element = androidDriver.findElement(By.className(classname));
			printElement(true, classname);
			return element;
		} catch (Exception e) {
			printElement(false, classname);
			return null;
		}
	}

	/**
	 * @param className 使用className定位（如果不存在id，不存在text，可考虑使用className方式）
	 * @param index     存在多个相同className时，可指定下标，下标从0开始
	 * @return WebElement
	 */
	public WebElement find_by_classname_index(String className, String index) {
		WebElement element = null;
		int i = Convert.toInt(index);
		waitForElement(By.className(className));
		try {
			element = androidDriver.findElements(By.className(className)).get(i);
			printElement(true, className + ":" + index);
			return element;
		} catch (Exception e) {
			printElement(false, className + ":" + index);
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
	public WebElement find_by_classname_text(String classname, String text) {
		WebElement element = null;
		sleep(2000);
		String exp = "new UiSelector().className(" + "\"" + classname + "\"" + ").text(" + "\"" + text + "\"" + ")";
		try {
			element = androidDriver.findElementByAndroidUIAutomator(exp);
			printElement(true, classname + ":" + text);
			return element;
		} catch (Exception e) {
			printElement(false, classname + ":" + text);
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
	public WebElement find_by_id_text(String id, String text) {
		WebElement element = null;
		sleep(2000);
		String exp = "new UiSelector().resourceId(" + "\"" + id + "\"" + ").text(" + "\"" + text + "\"" + ")";
		try {
			element = androidDriver.findElementByAndroidUIAutomator(exp);
			printElement(true, id + ":" + text);
			return element;
		} catch (Exception e) {
			printElement(false, id + ":" + text);
			return null;
		}
	}

	/**
	 * @param text  使用文本信息定位
	 * @param index 存在多个相同文件时，可指定下标，下标从0开始
	 * @return WebElement
	 */
	public WebElement find_by_text_index(String text, String index) {
		WebElement element = null;
		int i = Convert.toInt(index);
		waitForElement(By.xpath("//*[@text='" + text + "']"));
		try {
			element = androidDriver.findElements(By.xpath("//*[@text='" + text + "']")).get(i);
			printElement(true, text + ":" + index);
			return element;
		} catch (Exception e) {
			printElement(false, text + ":" + index);
			return null;
		}
	}

	/**
	 * @param text 页面元素中存在唯一一个文本值时，使用文本定位（没有id时，推荐使用此种方法）
	 * @return WebElement
	 */
	public WebElement find_by_uiautomator_text(String text) {
		WebElement element = null;
		String value = "new UiSelector().textContains" + "(" + "\"" + text + "\"" + ")";
		try {
			element = androidDriver.findElementByAndroidUIAutomator(value);
			printElement(true, text);
			return element;
		} catch (WebDriverException e) {
			printElement(false, text);
			return null;
		}
	}

	/**
	 *
	 * @param exp 传入一个uiautomator表达式
	 * @return WebElement
	 */
	public WebElement find_by_uiautomator_exp(String exp) {
		WebElement element = null;
		try {
			sleep(2000);
			element = androidDriver.findElementByAndroidUIAutomator(exp.replace("\\", ""));// 工具生成的字段有\字符，需要去除
			printElement(true, exp);
			return element;
		} catch (WebDriverException e) {
			printElement(false, exp);
			return null;
		}
	}

	/**
	 * @param className 与find_by_classname_index方法类似（此种方法更快一些）
	 * @param index
	 * @return WebElement
	 */
	public WebElement find_by_uiautomator_classname_instance(String className, String index) {
		WebElement element = null;
		int i = Convert.toInt(index);
		String exp = "new UiSelector().className(" + "\"" + className + "\"" + ").instance(" + i + ")";
		try {
			sleep(3000);
			element = androidDriver.findElementByAndroidUIAutomator(exp);
			printElement(true, className + ":" + index);
			return element;
		} catch (Exception e) {
			printElement(false, className + ":" + index);
			return null;
		}
	}

	/**
	 * @param x 根据坐标点击，通常用于无法定位元素时使用
	 * @param y
	 * @return boolean 操作结果
	 */
	public void tap(int x, int y) {
		sleep(2500);
		androidDriver.tap(1, x, y, 300);
		sleep(1000);
		logger.info("Tap已执行");
		snapshot("点击");		
	}

	/**
	 * 返回当前页的activity信息
	 * 
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
		sleep(1000);
		//&& isClick(webElement)
		if (webElement != null) {
			webElement.click();
			webElement = null;			
			logger.info("点击成功:"+elementName);
			snapshot(elementName);			
		} else {
			logger.info("点击成功:"+elementName);
			snapshot("fail"+elementName);
			throw new RuntimeException("元素点击失败");			
		}
		System.gc();
		logger.debug("click method over");
	}

	/**
	 * @param element 元素
	 * @param text    输入的内容
	 * @return boolean 操作结果
	 */
	public void send(WebElement element, String text) {
		if (element == null) {
			logger.info("输入内容失败");
			snapshot("输入失败");			
		} else {
			element.sendKeys(text);
			element = null;
			logger.info("输入内容成功:" + text);
			snapshot("输入"+text+"成功");
			throw new RuntimeException("元素为空,输入失败");
		}
	}

	/**
	 * @param webElement 元素
	 * @return boolean 操作结果
	 */
	public void clear(WebElement webElement) {
		if (webElement == null) {
			logger.info("清除失败");
			snapshot("清除失败");			
		} else {
			androidDriver.pressKeyCode(112);
			webElement.clear();
			webElement = null;
			logger.info("清除完成");
			snapshot("清除完成");			
		}
	}

	/**
	 * @param element 元素
	 * @return boolean 操作结果
	 */
	public boolean isClick(WebElement element) {
		if (element.isEnabled()) {
			logger.info("元素可点击");
			return true;
		}
		logger.info("元素不可点击");
		return false;
	}

	/**
	 * @param text 传入元素的唯一文本值（此方法多用于元素不在当前窗口时，可自动上下滑动寻找元素）
	 * @return WebElement
	 */
	public WebElement find_by_slide_text(String text) {
		WebElement element = null;
		if (isExist(text)) {
			String uiautoExp = "new UiSelector().textContains(\"" + text + "\")";
			element = androidDriver.findElementByAndroidUIAutomator(uiautoExp);
			printElement(true, text);
			return element;
		}
		printElement(false, text);
		return null;

	}

	/**
	 *
	 * @param text 传入元素的唯一文本值（此方法多用于元素不在当前窗口时，可自动左右滑动寻找元素）
	 * @return WebElement
	 */
	public WebElement find_by_slide_text_h(String text) {
		WebElement element = null;
		sleep(3000);
		String ui = "new UiScrollable(new UiSelector().scrollable(true)).setAsHorizontalList().scrollIntoView(new UiSelector().text(\""
				+ text + "\"))";
		try {
			element = androidDriver.findElementByAndroidUIAutomator(ui);
			printElement(true, text);
			return element;
		} catch (Exception e) {
			printElement(false, text);
			return null;
		}
	}

	/**
	 * @param text 传入元素的唯一id值（此方法多用于元素不在当前窗口时，可自动上下滑动寻找元素）
	 * @return WebElement
	 */
	public WebElement find_by_slide_id(String id) {
		WebElement element = null;
		sleep(3000);
		String uiautoExp = "new UiScrollable(new UiSelector().scrollable(true)).scrollIntoView(new UiSelector().resourceId(\""
				+ id + "\"))";
		try {
			element = androidDriver.findElementByAndroidUIAutomator(uiautoExp);
			printElement(true, id);
			return element;
		} catch (Exception e) {
			printElement(false, id);
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
			androidDriver.swipe(width / 2, height * 3 / 4, width / 2, height / 4, 800);
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
		logger.info("返回操作执行成功");
		snapshot("返回");		
	}

	/**
	 * 向上滑
	 * 
	 * @param x 指定起始的x坐标
	 * @param y 指定起始的y坐标
	 */
	public void swipe_up(int x, int y) {
		sleep(2000);
		androidDriver.swipe(x, y, x, y / 2, 800);
		logger.info("向上滑动------------>执行成功");
		sleep(2000);		
	}

	/**
	 * 向下滑
	 * 
	 * @param x 指定起始的x坐标
	 * @param y 指定起始的y坐标
	 */
	public void swipe_down(int x, int y) {
		sleep(2000);
		androidDriver.swipe(x, y, x, y + (height - y) / 2, 800);
		logger.info("向下滑动------------>执行成功");
		sleep(2000);
	}

	/**
	 * 向左滑
	 * 
	 * @param x 指定起始的x坐标
	 * @param y 指定起始的y坐标
	 */
	public void swipe_left(int x, int y) {
		sleep(2000);
		androidDriver.swipe(x, y, 3 % 2, y, 800);
		logger.info("向左滑动------------>执行成功");
		sleep(2000);		
	}



	/**
	 * 向右滑
	 * 
	 * @param x 指定起始的x坐标
	 * @param y 指定起始的y坐标
	 */
	public void swipe_right(int x, int y) {
		sleep(2000);
		androidDriver.swipe(x, y, (width - 20), y, 800);
		sleep(2000);		
	}

	/**
	 * 滑动
	 * 
	 * @param direction 传进来一个up down left right
	 * @return
	 */
	public void swipeDirection(String direction) {
		int speed = 800;
		if (direction.equals("right")) {
			androidDriver.swipe(width / 4, height / 2, width * 3 / 4, height / 2, speed);
			snapshot("滑动" + direction);
			
		} else if (direction.equals("left")) {
			androidDriver.swipe(width * 3 / 4, height / 2, width / 4, height / 2, speed);
			snapshot("滑动" + direction);
			
		} else if (direction.equals("down")) {
			androidDriver.swipe(width / 2, height / 4, width / 2, height * 3 / 4, speed);
			snapshot("滑动" + direction);
			
		} else if (direction.equals("up")) {
			androidDriver.swipe(width / 2, height * 3 / 4, width / 2, height / 4, speed);
			snapshot("滑动" + direction);
			
		} else if (direction.equals("end")) {
			String page1;
			String page2;
			do {
				page1 = androidDriver.getPageSource();
				androidDriver.swipe(width / 2, height * 3 / 4, width / 2, height / 4, speed);
				sleep(2000);
				snapshot("滑动"+direction);
				page2 = androidDriver.getPageSource();
			} while (!page1.equals(page2));
			snapshot("滑动" + direction);
			
		} else if (direction.equals("top")) {
			String page1;
			String page2;
			do {
				page1 = androidDriver.getPageSource();
				androidDriver.swipe(width / 2, height / 4, width / 2, height * 3 / 4, speed);
				sleep(2000);
				snapshot("滑动"+direction);
				page2 = androidDriver.getPageSource();
			} while (!page1.equals(page2));
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
		logger.debug("----------生成文件名:"+fileName);
		File scrFile = androidDriver.getScreenshotAs(OutputType.FILE);
		logger.debug("----------截图文件:"+scrFile.getPath());
		File saveLocal = new File(path,getMethodName());
		logger.info("saveLocal:"+saveLocal.getPath());
		if(!saveLocal.exists()) {			
			saveLocal.mkdirs();
			logger.debug("----------文件夹不存在，已创建:"+saveLocal.getPath());
		}else {
			logger.debug("----------文件夹已存在");
		}		
		File picture = new File(saveLocal + "\\" + fileName +".png");		
		try {
			FileUtils.copyFile(scrFile, picture);
			logger.info("----------截图保存路径:" + picture.getPath());
			if(actionName.contains("fail")) {
				FileUtils.copyFile(scrFile, new File(saveLocal+"\\"+"failPic"+fileName+".png"));
				logger.debug("失败截图保存路径:"+saveLocal+"\\"+"failPic"+fileName+".png");
			}			
		} catch (IOException e1) {
			logger.info("截图失败");
		} finally {
			saveLocal = null;
			actionName = null;
		}
	}

	/**
	 * @param element 用于H5页面视频元素的暂停操作
	 */
	public void pause(WebElement element) {
		androidDriver.executeScript("arguments[0].pause()", element);
		String url = (String) androidDriver.executeScript("return arguments[0].currentSrc;", element);
		logger.info("已暂停，URL地址为:" + url);		
	}

	/**
	 * @param element 用于H5页面视频元素的播放操作
	 */
	public void player(WebElement element) {
		androidDriver.executeScript("arguments[0].play()", element);
		String url = (String) androidDriver.executeScript("return arguments[0].currentSrc;", element);
		logger.info("开始播放，URL地址为:" + url);		
	}

	/**
	 * @param element 调用JS对元素进行点击（用于H5页面）
	 */
	public void click_by_js(WebElement element) {
		androidDriver.executeScript("arguments[0].click();", element);
		logger.info("开始点击");
	}

	/**
	 * @return 切换于原生界面
	 */
	public void contextToNative() {
		androidDriver.context("NATIVE_APP");
		logger.info("已切换至:NATIVE_APP");
		sleep(2000);		
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
		logger.info("已切换至:WEBVIEW_chrome");
		sleep(2000);		
	}

	/**
	 * 休眠
	 * 
	 * @param ms 毫秒（1秒=1000ms）
	 */
	public void sleep(int ms) {
		try {
			logger.debug("----------暂停"+(ms/1000)+"秒");
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
	public void pressKeyCode(int code) {
		logger.info("键盘事件:[" + code+"]");
		androidDriver.pressKeyCode(code);
		snapshot("键盘事件" + code);		
	}

	public void printElement(boolean b, String str) {
		if (b) {
			logger.debug("元素:" + str + "[存在]");
		} else {
			logger.debug("元素:" + str + "[不存在]");			
		}
	}

	public WebElement getElement(TestCaseEntity testCaseEntity) {
		WebElement element = null;
		if (testCaseEntity.getArg().trim().contains("*")) {
			String[] str = testCaseEntity.getArg().trim().split("\\*");
			String argm = str[0];
			String index = str[1];
			try {
				element = ReflectUtil.invoke(this, testCaseEntity.getType().trim(), argm, index);
			} catch (Exception e) {
				return null;
			}
		} else {
			try {
				element = ReflectUtil.invoke(this, testCaseEntity.getType().trim(), testCaseEntity.getArg());
			} catch (Exception e) {
				return null;
			}
		}
		return element;
	}

	// 清理数据
	public void clearCache(String cmd) {
		try {
			RuntimeUtil.exec(cmd);
			logger.info(cmd + "数据清除完成");
			Thread.sleep(2000);
		} catch (Exception e) {
			snapshot("fail-" + "clearCache");

		}
	}
	// 结束应用商店进程
	public void killAppStore() {
		RuntimeUtil.execForStr("adb shell am force-stop com.tencent.southpole.appstore");
	}

	// 结束QNET进程
	public void killQNET() {
		logger.debug("kill QNET..");
		RuntimeUtil.execForStr("adb shell am force-stop com.tencent.qnet");
		logger.debug("QNET killed..");
	}

	// 清量应用商店数据
	public void clearAppSroreData() {
		logger.debug("kill appstore..");
		String cmd = "adb shell pm clear com.tencent.southpole.appstore";
		clearCache(cmd);
	}
	
}
