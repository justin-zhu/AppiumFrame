package com.meitu.domain;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebElement;

import cn.hutool.core.util.RuntimeUtil;

import com.meitu.utils.Helper;

public class AccoundServiceAutoTestScript {
	private Helper helper;
	Map<String, Boolean> map;
	Logger logger = Logger.getLogger(this.getClass());
	String tencentName;
	String storeName;
	public AccoundServiceAutoTestScript() {
	}

	public AccoundServiceAutoTestScript(Helper helper) {
		this.helper = helper;
		logger.info("Helper:" + helper);
	}

	// 腾讯帐户已登陆
	public void loginStatusYes() {		
		WebElement element = helper.find_by_uiautomator_text("退出登录");
		if (element != null) {
			map.put("loginStatusYes", true);
		} else {
			helper.snapshot("fail-"+"loginStatusYes");
			map.put("loginStatusYes", false);
		}
	}

	// 腾讯帐户未登陆
	public void loginStatusNo() {
		WebElement element = helper.find_by_uiautomator_text("退出登录");
		if (element == null) {
			map.put("loginStatusNo", true);
		} else {
			helper.snapshot("fail-"+"loginStatusNo");
			map.put("loginStatusNo", false);
		}
	}

	// 退出登录
	public void logOut() {
		WebElement element = helper.find_by_uiautomator_text("退出登录");
		if (element != null) {
			helper.click(element, "退出登录");// 成功
			helper.click(helper.find_by_uiautomator_text("确定"), "确定退出");		
			
			tencentAccount();
			element = null;
		} else {
			helper.snapshot("fail-"+"logOut");
			map.put("logOut", false);
		}
	}

	// 从设置界面点击腾讯帐户
	public void tencentAccount() {
		helper.click(helper.find_by_uiautomator_text("应用市场帐号"), "帐号");
		map.put("tencentAccount", true);
		
	}

	// 微信登录
	public void loginWechat() {		
		
		helper.click(helper.find_by_uiautomator_text("微信登录"), "微信登录");	
		// helper.tap(400, 1130);		
		helper.sleep(3000);	
		
	}

	// QQ登录其它帐户
	public void loginQQ2() {
		helper.click(helper.find_by_uiautomator_text("QQ登录"), "QQ2登录");
		helper.tap(945, 1100);
		helper.tap(500, 1430);
		helper.tap(1000, 1300);
		helper.sleep(3000);		
	}

	// QQ登录
	public void loginQQ() {
		helper.click(helper.find_by_uiautomator_text("QQ登录"), "QQ登录");
		helper.tap(500, 1350);
		helper.sleep(3000);
		
	}

	// 清空帐户数据
	public void clearAccountData() {
		String cmd = "adb shell pm clear com.tencent.southpole.usercenter";
		clearCache(cmd);
	}

	// 清空应用商店数据
	public void clearStoreData() {
		String cmd = "adb shell pm clear com.tencent.southpole.appstore";
		clearCache(cmd);
	}

	// 清理数据
	public void clearCache(String cmd) {
		try {
			RuntimeUtil.exec(cmd);
			logger.info(cmd + "数据清除完成");
			Thread.sleep(2000);
			map.put("clearCache", true);
		} catch (Exception e) {
			helper.snapshot("fail-"+"clearCache");
			map.put("clearCache", false);
		}
	}

	// 应用商店--个人中心
	public void storeCenter() {
		WebElement element = helper.find_by_id("com.tencent.southpole.appstore:id/tab_mine");
		if (element != null) {
			helper.click(element, "个人中心");
		}
		
	}
	//跳过应用商店引导界面
	public void storeSkipGuide() {
		helper.sleep(2000);
		storeClickAllowButton();		
	}
	//登录用户名比较
	public void accountNameCompare(){
		try {
			if(tencentName.equals(storeName)){
				logger.info("用户名一致");
				map.put("accountNameCompare", true);
			}else{
				logger.info("用户名不一致");
				map.put("accountNameCompare", false);
			}
		} catch (Exception e) {
			map.put("accountNameCompare", false);
			try {
				throw new Exception("用户名核对失败");
			} catch (Exception e1) {				
				e1.printStackTrace();
			}
		}		
	}
	public void storeClickAllowButton() {
		
		WebElement element2 = helper.find_by_id("com.android.packageinstaller:id/ok_button");
		if (element2!=null) {			
			helper.click(element2, "确定");			
			WebElement element = helper.find_by_id("com.tencent.southpole.appstore:id/dialog_right_btn");
			helper.click(element, "同意");			
			element = null;			
			back();
			
		}
		//WebElement element = helper.find_by_id("com.android.packageinstaller:id/ok_button");
//		WebElement element2 = helper.find_by_id("com.tencent.southpole.appstore:id/close");
//		if(null!=element2){
//			boolean b  =helper.click(element2, "装机必备");
//			map.put("storeClickAllowButton", b);
//		}else{
//			map.put("storeClickAllowButton", false);
//		}
	}

	// 应用商店，点击登录按钮
	public void storeClickLogin() {
		WebElement element = helper.find_by_uiautomator_text("点击登录");
		if (element != null) {
			helper.click(element, "点击登录");
		}
	}
	public void clickDeskAppSetting(){
		clickDeskApp("设置");
	}
	public void clickDeskAppStore(){
		clickDeskApp("应用市场");
	}
	// 默认登录，即快捷登录
	public void storeDefaultLogin() {
		helper.click(helper.find_by_id("com.tencent.southpole.appstore:id/login_system"), "快捷登录");
		
	}

	// 其它账号登录
	public void storeOtherLogin() {
		helper.click(helper.find_by_id("com.tencent.southpole.appstore:id/other_login"), "其他账号登录");		
	}

	// 状态为登录
	public void storeLoginStatusYes() {
		WebElement element2 = helper.find_by_uiautomator_text("点击登录");
		if (element2 == null) {
			map.put("storeLoginStatusYes", true);
			element2 = null;
		}else{
			helper.snapshot("fail-"+"storeLoginStatusYes");
			map.put("storeLoginStatusYes", false);
		}
	}

	// 状态为未登录
	public void storeLoginStatusNo() {
		WebElement element2 = helper.find_by_uiautomator_text("点击登录");
		if (element2 != null) {
			map.put("storeLoginStatusNo", true);
			element2 = null;
		} else {
			helper.snapshot("fail-"+"storeLoginStatusNo");
			map.put("storeLoginStatusNo", false);
		}
	}

	// 退出登录
	public void storeLogOut() {
		helper.click(helper.find_by_slide_text_h("设置"), "设置");
		WebElement webElement = helper.find_by_uiautomator_text("退出登录");
		if (webElement != null ) {
			helper.click(webElement, "退出登录");
			helper.click(helper.find_by_id("com.tencent.southpole.appstore:id/dialog_right_btn"), "确定");
			
		} 
	}

	// 返回桌面
	public void goHome() {
		helper.pressKeyCode(3);
	}

	// 返回上一级界面
	public void back() {
		helper.back();		
	}

	// 点击桌面的应用：点设置或应用商店
	public void clickDeskApp(String appName) {
		goHome();
		helper.click(helper.find_by_slide_text_h(appName), appName) ;
		helper.sleep(3000);			
	}

	public boolean mapResult(Map<String, Boolean> map) {
		for (String str : map.keySet()) {
			if (map.get(str)) {
				logger.info("【Pass】" + str);
				continue;
			} else {
				logger.info("【Fail】" + str);
				try {
					throw new Exception("测试失败");
				} catch (Exception e) {					
				}
				return false;
			}
		}
		return true;
	}
	//腾讯帐户用户名获取
	public void getTencentAccountName(){
		tencentName = null;
		WebElement element = helper.find_by_id("com.tencent.southpole.usercenter:id/user_name");
		if(null!=element){
			tencentName = element.getText();
		}		
	}
	//应用商店用户名获取
	public void getStoreAccountName(){
		storeName = null;
		WebElement element = helper.find_by_id("com.tencent.southpole.appstore:id/nick_name");
		if(null!=element){
			storeName = element.getText();
		}		
	}

	public String getName(WebElement element){
		logger.info("用户名："+element.getText());
		return element.getText();
	}
	/*
	 * ID1-QQ登录系统账号 系统账号未登录-应用商店未登录-清理应用商店的缓存数据 1，使用QQ1登录系统账号；2，查看系统账号和应用商店的登录状态
	 * 1，系统账号已登录，账号QQ1，界面正常显示；2，查看应用商店，显示已登录状态，账号QQ1
	 */
	public boolean id1(String accountType) {
		map = new HashMap<String, Boolean>();
		clearStoreData();// 清除商店数据
		clearAccountData();// 清除账户数据
		clickDeskApp("设置");// 保持始终处于设置界面		
		tencentAccount();// 腾讯帐户
		loginQQ();
		getTencentAccountName();
		clickDeskApp("应用市场");// 返回桌面，打开应用商店
		storeSkipGuide();// 跳过引导界面
		storeCenter();// 进入个人中心
		storeLoginStatusYes(); // 应用商店为登录状态，返回true
		getStoreAccountName();
		accountNameCompare();
		return mapResult(map);
	}

	/*
	 * ID2-QQ退出系统账号 系统账号已登录，应用商店采用系统账号登录 "1，退出系统账号QQ1；2，查看系统账号和应用商店的登录状态"
	 * "1，系统账号退出登录，页面显示未登录状态；2，应用商店显示未登录状态"
	 */
	public boolean id2(String accountType) {
		map = new HashMap<String, Boolean>();
		clearStoreData();
		clearAccountData();		
		clickDeskApp("设置");// 保持始终处于设置界面		
		tencentAccount();// 腾讯帐户
		loginQQ();
		back();
		tencentAccount();
		logOut();
		clickDeskApp("应用市场");// 切换到应用商店
		storeSkipGuide();// 跳过引导页
		storeCenter();// 进入应用商店的个人中心
		storeLoginStatusNo();// 判断当前的帐户登录状态，正确的结果应为未登陆，返回true
		return mapResult(map);
	}
	/*
	 * id3-QQ再次登录系统账号
	 * 系统账号QQ1已登录
	 * "1，QQ1退出系统账号；2，QQ1再次登录系统账号；3，查看系统账号和应用商店的登录状态"
	 * "1，QQ1登录系统账号成功，界面显示正常；2，应用商店显示已登录，账号QQ1" 
	 */
	public boolean id3(String accountType){
		map = new HashMap<String, Boolean>();
		clearStoreData();
		clearAccountData();		
		clickDeskApp("设置");
		tencentAccount();
		loginQQ();	
		back();
		tencentAccount();
		logOut();			
		loginQQ();
		back();
		tencentAccount();
		getTencentAccountName();
		back();
		tencentAccount();
		loginStatusYes();
		clickDeskApp("应用市场");
		storeSkipGuide();
		storeCenter();
		storeLoginStatusYes();
		getStoreAccountName();
		accountNameCompare();
		return mapResult(map);
	}
	/*
	 * id4-系统账号QQ1已登录
	 * "1，退出系统账号QQ1；2，更换系统账号QQ2登录；3，查看系统账号和应用商店的登录状态"
	 * "1，QQ2登录系统账号成功，界面显示正常；2，应用商店显示已登录，账号QQ2" 
	 */
	public boolean id4(String acc){
		map = new HashMap<String, Boolean>();		
		clearStoreData();// 清商店缓存
		clearAccountData();
		clickDeskApp("设置");// 保持始终处于设置界面
		tencentAccount();
		loginQQ();
		back();
		tencentAccount();
		logOut();		
		loginQQ2();
		getTencentAccountName();
		back();
		tencentAccount();
		loginStatusYes();		
		clickDeskApp("应用市场");
		storeSkipGuide();
		storeCenter();
		storeLoginStatusYes();
		getStoreAccountName();
		accountNameCompare();		
		return mapResult(map);
	}
	/*
	 * ID5-微信登录系统账号 "系统账号未登录-应用商店未登录-清理应用商店的缓存数据"
	 * "1，使用微信1登录系统账号；2，查看系统账号和应用商店的登录状态"
	 * "1，系统账号已登录，账号微信1，界面显示正常；2，查看应用商店，已登录，账号微信1"
	 */
	public boolean id5(String accountType) {
		map = new HashMap<String, Boolean>();		
		clearStoreData();// 清商店缓存
		clearAccountData();
		clickDeskApp("设置");// 保持始终处于设置界面
		tencentAccount();// 腾讯帐号
		loginWechat();// 微信
		getTencentAccountName();
		clickDeskApp("应用市场");// 切换到应用商店
		storeSkipGuide();// 清理了商店数据，所以要跳过引导页
		storeCenter();// 进入应用商店的个人中心		
		getStoreAccountName();
		accountNameCompare();
		storeLoginStatusYes();// 判断当前的帐户登录状态，登录返回true
		return mapResult(map);
	}

	/*
	 * ID6
	 * 微信退出系统账号 系统账号已登录，应用商店采用系统账号登录 
	 * "1，微信1退出系统账号；2，查看系统账号和应用商店的登录状态"
	 * "1，系统账号退出登录，页面显示未登录状态；2，应用商店显示未登录状态"
	 */
	public boolean id6(String accountType) {
		map = new HashMap<String, Boolean>();
		clearStoreData();//清商店缓存
		clearAccountData();//清帐户缓存
		clickDeskApp("设置");// 保持始终处于设置界面
		tencentAccount();// 腾讯帐号
		loginWechat();//登录微信
		back();//返回上一级
		tencentAccount();//腾讯账户
		logOut();//退出登录
		clickDeskApp("应用市场");// 切换到应用商店
		storeSkipGuide();
		storeCenter();// 进入应用商店的个人中心
		storeLoginStatusNo();// 判断当前的帐户登录状态，未登陆返回true
		return mapResult(map);
	}
	/*
	 * id7: 微信再次登录系统账号	 *
	 * 系统账号微信1已登录
	 * "1，微信1退出系统账号；2，微信1再次登录系统账号；3，查看系统账号和应用商店的登录状态"
	 * "1，系统账号已登录，账号微信1，界面显示正常；2，查看应用商店，已登录，账号微信1" 
	 */
	public boolean id7(String str){
		map = new HashMap<String, Boolean>();
		clearStoreData();//清商店缓存
		clearAccountData();//清帐户缓存
		clickDeskApp("设置");// 保持始终处于设置界面
		tencentAccount();// 腾讯帐号
		loginWechat();//登录微信
		getTencentAccountName();
		back();
		tencentAccount();
		loginStatusYes();		
		clickDeskApp("应用市场");
		storeSkipGuide();
		storeCenter();
		storeLoginStatusYes();
		getStoreAccountName();
		accountNameCompare();		
		return mapResult(map);
	}
	/*
	 * id8
	 * 系统账号微信1已登录
	 * "1.微信1退出系统账号；2，微信2登录系统账号；3，查看系统账号和应用商店的登录状态"
	 * "1，系统账号已登录，账号微信2，界面显示正常；2，应用商店已登录，账号微信2，界面显示正常"
	 */
	public boolean id8(String str){
		map = new HashMap<String, Boolean>();
		clearStoreData();//清商店缓存
		clearAccountData();//清帐户缓存
		clickDeskApp("设置");// 保持始终处于设置界面
		tencentAccount();// 腾讯帐号
		loginWechat();//登录微信
		logOut();		
		loginWechat();
		getTencentAccountName();
		back();
		tencentAccount();
		loginStatusYes();		
		clickDeskApp("应用市场");
		storeSkipGuide();
		storeCenter();
		storeLoginStatusYes();
		getStoreAccountName();
		accountNameCompare();		
		return mapResult(map);
	}
	/*
	 * id9
	 * 1，系统账号已登录QQ1；2，应用商店退出登录QQ1"
	 * "1，系统账号QQ1退出登录；2，查看系统账号和应用商店的登录状态"
	 * "1，系统账号显示未登录状态；2，查看应用商店，显示未登录状态"
	 */
	public boolean id9(String str){
		map = new HashMap<String, Boolean>();
		clearStoreData();//清商店缓存
		clearAccountData();//清帐户缓存
		clickDeskApp("设置");// 保持始终处于设置界面
		tencentAccount();
		loginQQ();
		back();
		tencentAccount();
		loginStatusYes();
		clickDeskApp("应用市场");
		storeSkipGuide();
		storeCenter();
		storeLogOut();		
		storeLoginStatusNo();
		clickDeskApp("设置");
		tencentAccount();
		logOut();		
		loginStatusNo();
		return mapResult(map);
	}
	/*
	 * id10
	 * 1，QQ1登录系统账号默认应用商店登录；2，应用商店退出登录;3，系统账号QQ1退出登录；
	 * "1，QQ1再次登录系统账号；2，查看系统账号和应用商店的登录状态"
	 * "1，系统账号已登录，账号QQ1，界面正常显示；2，查看应用商店，显示未登录状态"
	 */
	public boolean id10(String str){
		map = new HashMap<String, Boolean>();
		clearStoreData();
		clearAccountData();
		clickDeskApp("设置");// 保持始终处于设置界面
		tencentAccount();
		loginQQ();
		loginStatusYes();
		clickDeskApp("应用市场");
		storeSkipGuide();
		storeCenter();
		storeLogOut();
		
		clickDeskApp("设置");
		tencentAccount();
		logOut();
		
		loginQQ();
		loginStatusYes();
		clickDeskApp("应用市场");
		storeCenter();
		storeLoginStatusNo();
		return mapResult(map);
	}
	/*
	 * ID11
	 * QQ再次登录系统账号 "QQ1登录系统账号默认登录应用商店；应用商店退出登录；"
	 * "1，系统账号QQ1退出登录；1，更换QQ2登录系统账号；2，查看系统账号和应用商店的登录状态"
	 * "1，系统账号已登录，账号QQ2，界面显示正常；2，查看应用商店，显示未登录状态"
	 */
	public boolean id11(String accountType) {
		map = new HashMap<String, Boolean>();
		clearStoreData();
		clearAccountData();
		clickDeskApp("设置");// 保持始终处于设置界面
		tencentAccount();// 腾讯帐号	
		loginQQ();
		clickDeskApp("应用市场");// 切换到应用商店
		storeSkipGuide();// 跳过引导
		storeCenter();// 个人中心
		storeLogOut();// 应用商店退出登录
		
		clickDeskApp("设置");// 回到设置界面
		tencentAccount();// 腾讯帐号
		logOut();// 退出登录
		loginQQ2();// 登录其它QQ帐户
		clickDeskApp("应用市场");// 切换到应用商店
		storeCenter();
		storeLoginStatusNo();// 判断当前的帐户登录状态，未登陆返回true
		return mapResult(map);
	}
	/*
	 * id12
	 * "1，QQ1登录系统账号默认应用商店登录；2，应用商店退出登录;3，系统账号QQ1退出登录；
	 * "1，QQ1退出系统账号，更换微信1登录系统账号；2，查看系统账号和应用商店的登录状态"
	 * "1，系统账号已登录，账号微信1，界面显示正常；2，查看应用商店，显示未登录"
	 */
	public boolean id12(String str){
		map = new HashMap<String, Boolean>();
		clearStoreData();
		clearAccountData();
		clickDeskApp("设置");
		tencentAccount();
		loginQQ();		
		back();
		tencentAccount();
		loginStatusYes();
		clickDeskApp("应用市场");
		storeSkipGuide();
		storeCenter();
		storeLogOut();
		
		storeLoginStatusNo();
		clickDeskApp("设置");
		tencentAccount();
		logOut();
		
		loginStatusNo();
		loginWechat();
		back();
		tencentAccount();
		loginStatusYes();
		clickDeskApp("应用市场");
		storeCenter();
		storeLoginStatusNo();
		return mapResult(map);
	}
	/*
	 * id13
	 * "1，系统账号已登录微信1；2，应用商店退出登录微信1"
	 * "1，系统账号微信1退出登录；2.查看系统账号和应用商店的登录状态"
	 * "1，系统账号退出登录，页面显示未登录状态；2，应用商店显示未登录状态"
	 */
	public boolean id13(String str) {
		map = new HashMap<String, Boolean>();
		clearStoreData();
		clearAccountData();
		clickDeskApp("设置");
		tencentAccount();
		loginWechat();
		back();
		tencentAccount();
		loginStatusYes();
		clickDeskApp("应用市场");
		storeSkipGuide();
		storeCenter();
		storeLogOut();
		
		storeLoginStatusNo();
		clickDeskApp("设置");
		tencentAccount();
		logOut();
	
		loginStatusNo();
		clickDeskApp("应用市场");
		storeCenter();
		storeLoginStatusNo();
		return mapResult(map);
	}
	/*
	 * ID14
	 * 微信再次登录系统账号 "
	 * 微信1登录系统账号，默认应用商店同步登录；应用商店退出登录；"
	 * "1，微信1退出系统账号；2，微信1再次登录系统账号；3，查看系统账号和应用商店的登录状态"
	 * "1，系统账号已登录，账号微信1，界面显示正常；2，应用商店显示未登录状态"
	 */
	public boolean id14(String accountType) {
		map = new HashMap<String, Boolean>();
		clearStoreData();
		clearAccountData();
		clickDeskApp("设置");// 保持始终处于设置界面
		tencentAccount();// 腾讯帐号		
		loginWechat();
		clickDeskApp("应用市场");// 切换到应用商店
		storeSkipGuide();// 跳过引导
		storeCenter();// 个人中心
		storeLogOut();// 应用商店退出登录
	
		clickDeskApp("设置");// 回到设置界面
		tencentAccount();// 腾讯帐号
		logOut();// 退出登录
		loginWechat();
		clickDeskApp("应用市场");// 切换到应用商店
		storeCenter();// 个人中心
		storeLoginStatusNo();// 执行结果，未登录，返回true
		return mapResult(map);
	}
	/*
	 * id15
	 * "1，微信1登录系统账号，默认应用商店同步登录；2，应用商店退出登录；"
	 * "1，微信1退出登录系统账号，2，更换微信2登录系统账号；3，查看系统账号和应用商店的登录状态"
	 * "1，系统账号已登录，账号微信2，界面显示正常；2，应用商店未登录"
	 */
	public boolean id15(String str){
		map = new HashMap<String, Boolean>();
		clearStoreData();
		clearAccountData();
		clickDeskApp("设置");
		tencentAccount();
		loginWechat();
		clickDeskAppStore();
		storeSkipGuide();
		storeCenter();
		storeLogOut();
	
		storeLoginStatusNo();
		clickDeskAppSetting();
		tencentAccount();
		logOut();
		
		loginStatusNo();
		loginWechat();
		back();
		tencentAccount();
		loginStatusYes();
		clickDeskAppStore();
		storeCenter();
		storeLoginStatusNo();
		return mapResult(map);
	}
	
	/*
	 * id16
	 * "1，微信2登录系统账号；2，应用商店未登录"
	 * "1，微信2退出系统账号，更换QQ1登录系统账号；2，查看系统账号和应用商店"
	 * "1，系统账号已登录，账号QQ1，界面显示正常；2，应用商店未登录"
	 */
	public boolean id16(String str){
		map = new HashMap<String, Boolean>();
		clearStoreData();
		clearAccountData();
		clickDeskApp("设置");
		tencentAccount();
		loginWechat();
		back();
		tencentAccount();
		loginStatusYes();
		clickDeskAppStore();
		storeSkipGuide();
		storeCenter();
		storeLogOut();
	
		storeLoginStatusNo();
		clickDeskAppSetting();
		tencentAccount();
		logOut();
		
		loginQQ();
		back();
		tencentAccount();
		loginStatusYes();
		clickDeskAppStore();
		storeCenter();
		storeLoginStatusNo();
		return mapResult(map);
	}
	
	/*
	 * id17
	 * "1，系统账号未登录；2，应用商店未登录；3，清除应用商店数据缓存"
	 * "1，QQ1登录应用商店，查看系统账号已登录；2，系统账号QQ1退出登录；3，查看系统账号和应用商店的登录状态"
	 * "1，系统账号显示未登录状态；2，查看应用商店，显示未登录状态"
	 */
	public boolean id17(String str){
		map = new HashMap<String, Boolean>();
		clearStoreData();
		clearAccountData();
		clickDeskAppStore();
		storeSkipGuide();
		storeCenter();
		storeClickLogin();
		loginQQ();
		clickDeskAppSetting();
		tencentAccount();
		logOut();		
		loginStatusNo();
		clickDeskAppStore();
		storeCenter();
		storeLoginStatusNo();		
		return mapResult(map);
	}
	
	/*
	 * id18
	 * "1，系统账号已登录（QQ）；2，应用商店未登录；
	 * "1，应用商店采用系统账号快捷登录方式登录；2，系统账号退出登录；3，查看系统账号和应用商店登录"
	 * "1，系统账号显示未登录状态；2，查看应用商店，显示未登录状态"
	 */
	public boolean id18(String str){
		map = new HashMap<String, Boolean>();
		clearStoreData();
		clearAccountData();
		clickDeskApp("设置");
		tencentAccount();
		loginQQ();
		clickDeskAppStore();
		storeSkipGuide();
		storeCenter();
		storeLogOut();
		
		storeClickLogin();
		storeDefaultLogin();
		clickDeskAppSetting();
		tencentAccount();
		logOut();
	
		loginStatusNo();
		clickDeskAppStore();
		storeCenter();
		storeLoginStatusNo();
		return mapResult(map);
	}
	/*
	 * id19
	 * "1，系统账号未登录；2，应用商店未登录；3，清除应用商店数据缓存"
	 * "1，微信1登录应用商店，查看系统账号显示已登录；2，微信1退出系统账号；3，查看系统账号和应用商店的登录状态"
	 * "1，系统账号显示未登录状态；2，查看应用商店，显示未登录状态"
	 */
	public boolean id19(String str){
		map = new HashMap<String, Boolean>();
		clearStoreData();
		clearAccountData();
		clickDeskAppStore();
		storeSkipGuide();
		storeCenter();
		storeClickLogin();		
		loginWechat();
		clickDeskAppSetting();
		tencentAccount();
		logOut();		
		loginStatusNo();
		clickDeskAppStore();
		storeCenter();
		storeLoginStatusNo();
		return mapResult(map);
	}
	
	/*
	 * id20
	 * "1，系统账号已登录（微信）；2，应用商店未登录；
	 * "1，应用商店采用系统账号快捷登录方式登录；2，系统账号退出登录；3，查看系统账号和应用商店登录"
	 * "1，系统账号显示未登录状态；2，查看应用商店，显示未登录状态"
	 */
	public boolean id20(String str){
		map = new HashMap<String, Boolean>();
		clearStoreData();
		clearAccountData();
		clickDeskApp("设置");
		tencentAccount();
		loginWechat();
		clickDeskAppStore();
		storeSkipGuide();
		storeCenter();
		storeLogOut();
		
		storeClickLogin();
		storeDefaultLogin();
		clickDeskAppSetting();
		tencentAccount();
		logOut();
		
		loginStatusNo();
		clickDeskAppStore();
		storeCenter();
		storeLoginStatusNo();
		return mapResult(map);
	}
	/*
	 * ID21-QQ登录应用商店 "1，系统账号未登录；2，应用商店未登录；3，清除应用商店数据缓存"
	 * "1，QQ1登录应用商店；2，查看系统账号和应用商店的登录状态"
	 * "1，应用商店已登录，账号QQ1，界面显示正常；2，系统账号已登录，账号QQ1，界面显示正常"
	 */
	public boolean id21(String accountType) {
		map = new HashMap<String, Boolean>();
		clearStoreData();
		clearAccountData();
		clickDeskApp("设置");// 保持始终处于设置界面
		tencentAccount();// 腾讯帐号		
		loginStatusNo();//未登录状态
		clickDeskApp("应用市场");// 切换到应用商店
		storeSkipGuide();// 跳过引导
		storeCenter();// 个人中心
		storeClickLogin();
		loginQQ();
		clickDeskApp("设置");// 回到设置界面
		tencentAccount();// 腾讯帐号
		loginStatusYes();// 预期结果：系统帐户为登录状态
		return mapResult(map);
	}

	/*
	 * id22-QQ退出登录应用商店 应用商店采用系统账号登录 
	 * "1，QQ1退出应用商店；2，查看应用商店和系统账号的登录状态"
	 * "1，应用商店退出登录，显示未登录状态；2，系统账号不受影响，账号QQ1"
	 */
	public boolean id22(String accountType) {
		map = new HashMap<String, Boolean>();
		clearStoreData();
		clearAccountData();
		clickDeskApp("设置");// 保持始终处于设置界面
		tencentAccount();// 腾讯帐号		
		loginQQ();
		clickDeskApp("应用市场");// 切换到应用商店
		storeSkipGuide();// 跳过引导
		storeCenter();// 个人中心
		storeLogOut();		
		clickDeskApp("设置");// 回到设置界面
		tencentAccount();// 腾讯帐号
		loginStatusYes();// 预期结果：系统帐户为登录状态
		return mapResult(map);
	}

	/*
	 * ID23-微信登录应用商店 "1，系统账号未登录；2，应用商店未登录；3，清除应用商店数据缓存"
	 * "1，微信1登录应用商店；2，查看系统账号和应用商店的登录状态"
	 * "1，应用商店已登录，账号微信1，界面显示正常；2，系统账号已登录，账号微信1，界面显示正常"
	 */
	public boolean id23(String accountType) {
		map = new HashMap<String, Boolean>();
		clearAccountData();
		clearStoreData();// 清空缓存		
		clickDeskApp("设置");// 保持始终处于设置界面
		tencentAccount();// 腾讯帐号		
		loginStatusNo();
		clickDeskApp("应用市场");// 切换到应用商店
		storeSkipGuide();// 跳过引导
		storeCenter();// 个人中心		
		storeClickLogin();
		loginWechat();
		back();//只有此用例需要返回一下
		clickDeskApp("设置");// 回到设置界面
		tencentAccount();// 腾讯帐号
		loginStatusYes();// 预期结果：系统帐户为登录状态
		return mapResult(map);
	}

	/*
	 * ID24-微信退出应用商店 应用商店采用系统账号登录 "
	 * 1，微信1退出应用商店；2，查看应用商店和系统账号的登录状态"
	 * "1，应用商店退出登录，显示未登录状态；2，系统账号不受影响，账号微信1"
	 */
	public boolean id24(String accountType) {
		map = new HashMap<String, Boolean>();
		clickDeskApp("设置");// 保持始终处于设置界面
		clearStoreData();	
		clearAccountData();
		tencentAccount();// 腾讯帐号		
		loginWechat();
		clickDeskApp("应用市场");// 切换到应用商店
		storeSkipGuide();// 跳过引导
		storeCenter();// 个人中心
		storeLogOut();		
		clickDeskApp("设置");// 回到设置界面
		tencentAccount();// 腾讯帐号
		loginStatusYes();// 预期结果：系统帐户为登录状态
		return mapResult(map);
	}

	/*
	 * id25-QQ登录应用商店 "1，系统账号已登录QQ1；2，应用商店未登录；"
	 * "1，使用系统账号快捷方式登录应用商店；2，查看应用商店和系统账号的登录状态"
	 * "1，应用商店已登录，账号QQ1，界面显示正常；2，系统账号不受影响，账号QQ1"
	 */
	public boolean id25(String accountType) {
		map = new HashMap<String, Boolean>();
		clearStoreData();
		clearAccountData();
		clickDeskApp("设置");// 保持始终处于设置界面
		tencentAccount();// 腾讯帐号			
		loginQQ();
		clickDeskApp("应用市场");// 切换到应用商店
		storeSkipGuide();// 跳过引导
		storeCenter();// 个人中心
		storeLogOut();// 退出应用商店
		
		storeClickLogin();// 点击登陆按钮
		storeDefaultLogin();// 使用快捷登陆
		storeLoginStatusYes();// 登陆状态判断
		clickDeskApp("设置");// 回到设置界面
		tencentAccount();
		loginStatusYes();// 预期结果：系统帐户为登录状态
		return mapResult(map);
	}

	/*
	 * ID26-微信登录应用商店 "1，系统账号已登录微信1；2，应用商店未登录；"
	 * "1，使用系统账号快捷方式登录应用商店；2，查看应用商店和系统账号的登录状态"
	 * "1，应用商店已登录，账号微信1，界面显示正常；2，系统账号不受影响，账号微信1"
	 */
	public boolean id26(String accountType) {
		map = new HashMap<String, Boolean>();
		clearStoreData();
		clearAccountData();
		clickDeskApp("设置");// 保持始终处于设置界面
		tencentAccount();// 腾讯帐号			
		loginWechat();
		clickDeskApp("应用市场");// 切换到应用商店
		storeSkipGuide();// 跳过引导
		storeCenter();// 个人中心
		storeLogOut();// 退出应用商店
		
		storeClickLogin();// 点击登陆按钮
		storeDefaultLogin();// 使用快捷登陆
		storeLoginStatusYes();// 登陆状态判断
		clickDeskApp("设置");// 回到设置界面
		tencentAccount();
		loginStatusYes();// 预期结果：系统帐户为登录状态
		return mapResult(map);
	}
	/*
	 * id27
	 * "1，系统账号已登录QQ1；2，应用商店未登录；"
	 * "1，使用其他方式QQ1（与系统账号一致）登录应用商店；2，查看应用商店和系统账号的登录状态"
	 * "1，应用商店已登录，账号QQ1，界面显示正常；2，系统账号不受影响，账号QQ1"
	 */
	public boolean id27(String str) {
		map = new HashMap<String, Boolean>();
		clearStoreData();
		clearAccountData();
		clickDeskApp("设置");// 保持始终处于设置界面
		tencentAccount();
		loginQQ();
		back();
		tencentAccount();
		loginStatusYes();
		clickDeskAppStore();
		storeSkipGuide();
		storeCenter();
		storeLogOut();
		
		storeClickLogin();
		storeOtherLogin();
		loginQQ();
		storeLoginStatusYes();
		return mapResult(map);
	}
	/*
	 * id28-应用商店退出再登录 "1，系统账号已登录；2，应用商店未登录；"
	 * "1，使用其他方式QQ2（与系统账号不一致）登录应用商店；2，查看应用商店和系统账号的登录状态"
	 * "1，应用商店已登录，账号QQ2，界面显示正常；2，系统账号不受影响，账号QQ1"
	 */
	public boolean id28(String accountType) {
		map = new HashMap<String, Boolean>();
		clearStoreData();
		clearAccountData();
		clickDeskApp("设置");// 保持始终处于设置界面
		tencentAccount();// 腾讯帐号		
		loginQQ();
		clickDeskApp("应用市场");// 切换到应用商店
		storeSkipGuide();// 跳过引导
		storeCenter();// 个人中心
		storeLogOut();// 退出应用商店
		
		storeClickLogin();// 点击登陆按钮
		storeOtherLogin();
		loginQQ2();
		storeLoginStatusYes();
		clickDeskApp("设置");// 保持始终处于设置界面
		tencentAccount();// 腾讯帐号
		loginStatusYes();
		return mapResult(map);
	}
	/*
	 * id29
	 * "1，系统账号已登录QQ1；2，应用商店使用其他方式QQ1（与系统账号一致）登录"
	 * "1，应用商店退出登录；2，查看应用商店和系统账号的登录状态"
	 * "1，应用商店退出登录，显示未登录状态；2，系统账号不受影响，账号显示QQ1"
	 */
	public boolean id29(String accountType) {
		map = new HashMap<String, Boolean>();
		clearAccountData();//
		clearStoreData();
		clickDeskApp("设置");// 保持始终处于设置界面		
		tencentAccount();
		loginQQ();
		back();
		tencentAccount();
		loginStatusYes();
		clickDeskAppStore();
		storeSkipGuide();
		storeCenter();
		storeLogOut();
		
		storeClickLogin();
		storeOtherLogin();
		loginQQ();
		storeLoginStatusYes();
		storeLogOut();
	
		storeLoginStatusNo();
		clickDeskAppSetting();
		tencentAccount();
		loginStatusYes();
		return mapResult(map);
	}
	
	/*
	 * id30
	 * "1，系统账号已登录QQ1；2，应用商店使用其他方式QQ2（与系统账号不一致）登录"
	 * "1，应用商店退出登录；2，查看应用商店和系统账号的登录状态"
	 * "1，应用商店退出登录，显示未登录状态；2，系统账号不受影响，账号显示QQ1"
	 */
	public boolean id30(String accountType) {
		map = new HashMap<String, Boolean>();
		clearAccountData();//
		clearStoreData();
		clickDeskApp("设置");// 保持始终处于设置界面		
		tencentAccount();
		loginQQ();
		back();
		tencentAccount();
		loginStatusYes();
		clickDeskAppStore();
		storeSkipGuide();
		storeCenter();
		storeLogOut();
	
		storeClickLogin();
		storeOtherLogin();
		loginQQ2();
		storeLoginStatusYes();
		storeLogOut();
	
		storeLoginStatusNo();
		clickDeskAppSetting();
		tencentAccount();
		loginStatusYes();
		return mapResult(map);
	}
	/*
	 * ID31-应用商店退出再登录 "1，系统账号已登录微信1；2，应用商店未登录；"
	 * "1，使用其他方式微信1（与系统账号一致）登录应用商店；2，查看应用商店和系统账号的登录状态"
	 * "1，应用商店已登录，账号微信1，界面显示正常；2，系统账号不受影响，账号微信1"
	 */
	public boolean id31(String accountType) {
		map = new HashMap<String, Boolean>();
		clearAccountData();//
		clearStoreData();
		clickDeskApp("设置");// 保持始终处于设置界面		
		tencentAccount();// 腾讯帐号		
		loginWechat();
		clickDeskApp("应用市场");// 切换到应用商店
		storeSkipGuide();// 跳过引导
		storeCenter();// 个人中心
		storeLogOut();// 退出应用商店
		
		storeClickLogin();// 点击登陆按钮
		storeOtherLogin();
		loginWechat();
		storeLoginStatusYes();
		clickDeskApp("设置");// 保持始终处于设置界面
		tencentAccount();// 腾讯帐号
		loginStatusYes();
		return mapResult(map);
	}
	/*
	 * id32
	 * "1，系统账号已登录微信1；2，应用商店未登录；"
	 * "1，使用其他方式微信2（与系统账号不一致）登录应用商店；2，查看应用商店和系统账号的登录状态"
	 * "1，应用商店已登录，账号微信2，界面显示正常；2，系统账号不受影响，账号微信1" 
	 */
	public boolean id32(String accountType) {
		map = new HashMap<String, Boolean>();
		clearAccountData();//
		clearStoreData();
		clickDeskApp("设置");// 保持始终处于设置界面		
		tencentAccount();
		loginWechat();
		back();
		tencentAccount();
		loginStatusYes();
		clickDeskAppStore();
		storeSkipGuide();
		storeCenter();
		storeLogOut();
		
		storeLoginStatusNo();
		storeClickLogin();
		storeOtherLogin();
		loginWechat();
		storeLoginStatusYes();
		clickDeskAppSetting();
		tencentAccount();
		loginStatusYes();
		return mapResult(map);
	}
	/*
	 * id33
	 * "1，系统账号已登录微信1；2，应用商店使用其他方式微信1（与系统账号一致）登录"
	 * "1，应用商店退出登录；2，查看应用商店和系统账号的登录状态"
	 * "1，应用商店退出登录，显示未登录状态；2，系统账号不受影响，账号显示微信1"
	 */
	public boolean id33(String accountType) {
		map = new HashMap<String, Boolean>();
		clearAccountData();//
		clearStoreData();
		clickDeskApp("设置");// 保持始终处于设置界面		
		tencentAccount();
		loginWechat();
		back();
		tencentAccount();
		loginStatusYes();
		clickDeskAppStore();
		storeSkipGuide();
		storeCenter();
		storeLogOut();
		
		storeClickLogin();
		storeOtherLogin();
		loginWechat();
		storeLoginStatusYes();
		storeLogOut();
		
		storeLoginStatusNo();
		clickDeskAppSetting();
		tencentAccount();
		loginStatusYes();
		return mapResult(map);
	}
	/*
	 * id34
	 * "1，系统账号已登录微信1；2，应用商店使用其他方式微信2（与系统账号不一致）登录"
	 * "1，应用商店退出登录；2，查看应用商店和系统账号的登录状态"
	 * "1，应用商店退出登录，显示未登录状态；2，系统账号不受影响，账号显示微信1"
	 */
	public boolean id34(String accountType) {
		map = new HashMap<String, Boolean>();
		clearAccountData();//
		clearStoreData();
		clickDeskApp("设置");// 保持始终处于设置界面		
		tencentAccount();
		loginWechat();
		back();
		tencentAccount();
		loginStatusYes();
		clickDeskAppStore();
		storeSkipGuide();
		storeCenter();
		storeLogOut();
		
		storeClickLogin();
		storeOtherLogin();
		loginWechat();
		storeLoginStatusYes();
		storeLogOut();
		
		storeLoginStatusNo();
		clickDeskAppSetting();
		tencentAccount();
		loginStatusYes();
		return mapResult(map);
	}
}
