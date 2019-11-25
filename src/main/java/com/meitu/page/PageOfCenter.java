package com.meitu.page;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebElement;
import com.meitu.utils.Helper;

import cn.hutool.core.util.RuntimeUtil;

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
	 * 确定按钮
	 */
	public WebElement getConfirmBtn() {
		return helper.findById("com.tencent.southpole.appstore:id/dialog_right_btn");
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
			helper.click(getConfirmBtn(), "确定");
		} 
	}
	/**
	 * 应用卸载
	 * @return
	 */
	public WebElement getAppRemove() {
		return helper.findByIdAndText("com.tencent.southpole.appstore:id/personal_title", "应用卸载");
	}
	/**
	 * 应用更新
	 * @return
	 */
	public WebElement getAppUpdate() {
		return helper.findByIdAndText("com.tencent.southpole.appstore:id/personal_title", "应用更新");
	}
	/**
	 * 我的预约
	 */
	public WebElement getMyOrder() {
		return helper.findByIdAndText("com.tencent.southpole.appstore:id/personal_title", "我的预约");
	}
	/**
	 * 我的礼包
	 */
	public WebElement getMyGiftBag() {
		return helper.findByIdAndText("com.tencent.southpole.appstore:id/personal_title", "我的礼包");
	}
	/**
	 * 更新历史
	 */
	public WebElement getUpdateHistory() {
		return helper.findById("com.tencent.southpole.appstore:id/update_history_recyclerview");
	}
	/**
	 * 一键卸载按钮
	 * @return
	 */
	public WebElement getOneKeyUninstall() {
		return helper.findById("com.tencent.southpole.appstore:id/uninstall_btn");
	}
	public String getAppList() {
		return RuntimeUtil.execForStr("adb shell pm list packages");
	}
	/**
	 * 固定删除新浪微博
	 */
	public void removeApp() {
		String installResult = getAppList();
		if(installResult.contains("com.sina.weibo")) {
			helper.click(helper.findBySlideText("微博"), "微博");
			helper.click(getOneKeyUninstall(), "一键卸载");
			helper.click(getConfirmBtn(), "确定");
			logger.info("卸载成功");
		}
	}
	/**
	 * 即将上线元素
	 */
	public WebElement getComingsoon() {
		return helper.findById("com.tencent.southpole.appstore:id/title");
	}
	/**
	 * 已领取礼包面板
	 * @return
	 */
	public WebElement getGiftList() {
		return helper.findById("com.tencent.southpole.appstore:id/content");
	}
	/**
	 * 全部应用标签
	 */
	public WebElement getAllAppList() {
		return helper.findById("com.tencent.southpole.appstore:id/common_head_title");
	}
}
