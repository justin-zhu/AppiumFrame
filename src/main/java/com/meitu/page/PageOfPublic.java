package com.meitu.page;

import org.openqa.selenium.WebElement;

import com.meitu.utils.Helper;

public class PageOfPublic {
	Helper helper;

	public PageOfPublic(Helper helper) {		
		this.helper = helper;
	}

	public PageOfPublic() {		
	}
	/**
	 * 启动时，弹出的权限窗口，同意元素
	 * @return
	 */
	public WebElement getAuthor() {
		return helper.findById("com.tencent.southpole.appstore:id/dialog_right_btn");
	}
	/**
	 *  没有获取到数据，轻触界面尝试
	 * @return
	 */
	public WebElement getErrorOfGetDate() {
		return helper.findById("com.tencent.southpole.appstore:id/tv_net_error_desc");
	}
	/**
	 *  没有获取到数据，轻触界面尝试-搜索界面
	 * @return
	 */
	public WebElement getErrorOfGetDateOfSearch() {
		return helper.findById("com.tencent.southpole.appstore:id/tv_nothing_tip");
	}
	
	/**
	 * 首页广告弹窗口，关闭按钮
	 * @return
	 */
	public WebElement getAdFrame() {
		return helper.findById("com.tencent.southpole.appstore:id/ic_close");
	}
	/**
	 * 分类-子分类-全部列表中的应用
	 * 默认取检查到元素的第一个应用
	 */
	public WebElement getClassify_SubClassApp() {
		return helper.findById("com.tencent.southpole.appstore:id/app_name");
	}
	/**
	 * 分类下的游戏分类:休闲类
	 * @return
	 */
	public WebElement getClassifty_Game_Relax() {
		return helper.findByUiautomatorText("休闲益智");
	}
	/**
	 * 分类界面下的游戏分类
	 * @return
	 */
	public WebElement getClassify_Game() {
		return helper.findByXpath("//androidx.appcompat.app.ActionBar.Tab[@content-desc=\"游戏\"]");
	}
	/**
	 * 详情标签 
	 * @return
	 */
	public WebElement getAppInfo() {
		return helper.getAndroidDriver().findElementByAccessibilityId("详情");
	}
	/**
	 * 评论
	 */
	public WebElement getAppComment() {
		return helper.findByXpath("//*[contains(@text,\"评论\")]");
	}
	/**
	 * 评分
	 */
	public WebElement getAppScope() {
		return helper.findById("com.tencent.southpole.appstore:id/score");
	}
	/**
	 * 应用介绍
	 */
	public WebElement getAppState() {
		return helper.findById("com.tencent.southpole.appstore:id/short_text");
	}
	/**
	 * 分类下的软件分类:生活类
	 * @return
	 */
	public WebElement getClassify_Soft_Life() {
		return helper.findByUiautomatorText("生活");
	}
	/**
	 * 分类下的软件分类:生活类
	 * @return
	 */
	public WebElement getClassify_Game_OnLineGame() {
		return helper.findByUiautomatorText("网络游戏");
	}
	/**
	 * 分类下的软件分类:视频类
	 * @return
	 */
	public WebElement getClassify_Soft_Video() {
		return helper.findByUiautomatorText("视频");
	}
	
	
}
