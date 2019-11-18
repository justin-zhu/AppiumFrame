package com.meitu.page;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebElement;

import com.meitu.utils.Helper;

public class PageOfCenter {
	Helper helper;
	Logger logger = Logger.getLogger(getClass());
	public PageOfCenter(Helper helper) {
		super();
		this.helper = helper;
	}
	/**
	 * 个人中心界面
	 * @return
	 */
	public WebElement getIndex() {
		return helper.findById("com.tencent.southpole.appstore:id/tab_mine");
	}
	/**
	 * 用户名元素
	 */
	public WebElement getUserName() {
		return helper.findById("com.tencent.southpole.appstore:id/nick_name");
	}
	/**
	 * 其它帐号登录按钮
	 */
	public WebElement getOtherLoginBtn() {
		return helper.findById("com.tencent.southpole.appstore:id/other_login");
	}
	/**
	 * 设置
	 */
	public WebElement getSettingBtn() {
		return helper.findByIdAndText("com.tencent.southpole.appstore:id/personal_title", "设置");
	}
	/**
	 * 退出登陆按钮
	 */
	public WebElement getLogoutBtn() {
		return helper.findByUiautomatorText("退出登录");
	}
	/**
	 * 微信登陆按钮
	 */
	public WebElement getWechatLoginBtn() {
		return helper.findById("com.tencent.southpole.appstore:id/wechat_item");
	}
	/**
	 * QQ登陆按钮
	 */
	public WebElement getQQLoginBtn() {
		return helper.findById("com.tencent.southpole.appstore:id/qq_item");
	}
	/**
	 * 快捷登陆-系统帐户方式登陆
	 */
	public WebElement getSystemLogin() {
		return helper.findById("com.tencent.southpole.appstore:id/login_system");
	}
	/**
	 * 登陆状态检查
	 * 已登陆返回true,未登陆返回false
	 */
	public boolean isLogin() {
		WebElement element = getUserName();
		if (element != null) {
			logger.info("用户已登陆,用户名:"+element.getText());
			return true;
		} else {
			logger.info("未登陆");
			return false;
		}
	}
	/**
	 * 登陆
	 */
	public void login() {
		if (!isLogin()) {
			helper.click(getUserName(), "点击登陆");
			helper.click(getSystemLogin(), "系统登陆");
		}
	}

	/**
	 * 退出登陆
	 */
	public void logout() {
		if (isLogin()) {
			helper.click(getSettingBtn(), "设置");
			helper.click(getLogoutBtn(), "退出登陆");
		} 
	}	
}
