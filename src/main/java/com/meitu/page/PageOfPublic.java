package com.meitu.page;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebElement;

import com.meitu.utils.Helper;

import cn.hutool.core.util.RuntimeUtil;

public class PageOfPublic {
	Helper helper;
	private Logger log = Logger.getLogger(this.getClass());
	public PageOfPublic(Helper helper) {		
		this.helper = helper;
	}

	public PageOfPublic() {		
	}
	/**
	 * 确定和同意按钮
	 * @return
	 */
	public WebElement getAuthor() {
		return helper.findById("com.tencent.southpole.appstore:id/dialog_right_btn");
	}
	/**
	 * 启动时，弹出的权限窗口，同意元素
	 * @return
	 */
	public WebElement getAuthorOfSystem() {
		return helper.findById("com.android.packageinstaller:id/ok_button");
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
	 * Loading icon
	 */
	public WebElement getLoadingIcon() {
		return helper.findById("com.tencent.southpole.appstore:id/loading_anim");
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
	 * 搜索结果中的下载按钮
	 */
	public WebElement getSearchResulInstallBtn() {
		return helper.findById("com.tencent.southpole.appstore:id/search_result_downloadbtn");
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
	/**
	 * 社交类
	 */
	public WebElement getClassify_Soft_Social() {
		return helper.findByUiautomatorText("社交");
	}
	/**
	 * 详情界面的安装按钮
	 * @return
	 */
	public WebElement getInstalBtn() {
		return helper.findById("com.tencent.southpole.appstore:id/app_detail_download");
	}
	/**
	 * 游戏界面下的：下载按钮
	 * 分类列表下的：下载按钮
	 * @return
	 */
	public WebElement getInstall() {
		return helper.findById("com.tencent.southpole.appstore:id/download");
	}
	
	/**
	 * 卸载指定的包
	 * @param packageName
	 */
	public void removeApp(String packageName) {
		helper.getAndroidDriver().removeApp(packageName);
	}
	/**
	 * 获取第三方包名
	 */
	public String getAppList_3() {
		String deviceName = helper.getAndroidDriver().getCapabilities().getCapability("deviceName").toString();
		return RuntimeUtil.execForStr("adb -s "+deviceName+" shell pm list packages -3");
	}
	/**
	 * 检查应用是否已经安装在本机100秒等待时间
	 * @param packageName
	 */
	public void checkAppIsInstall(String packageName) {
		
		for (int i = 0; i < 20; i++) {
			boolean installed = helper.getAndroidDriver().isAppInstalled(packageName);
			if(installed) {
				log.info(packageName+"已安装完成");
				return;
			}else {
				log.info("等待应用安装完成");
				helper.sleep(5000);				
			}
		}
		log.info(packageName+"未能在指定时间内安装完成");
		throw new RuntimeException("应用未能在指定时间内安装完成");
	}
	/**
	 * 查看全部标签
	 */
	public WebElement getCheckAllLabel() {
		return helper.findBySlideId("com.tencent.southpole.appstore:id/more_info");
	}
	/**
	 * 首页 点击全部应用显示:本周热门应用列表
	 * 游戏页 点击全部应用显示：本周新游 
	 * 软件页 点击全部应用显示：流行正当时
	 */
	public WebElement getWeekHotAppsOfSub(int index) {
		if(index<0||index>9) {
			throw new RuntimeException("指定的下标不存在");
		}
		String expression = "//*[@resource-id='com.tencent.southpole.appstore:id/recyview']/android.view.ViewGroup["+index+"]/android.widget.TextView[1]";
		return helper.findByXpath(expression);
	}
	/**
	 * 首页的本周热门应用
	 * 游戏界面的本周新游 
	 * 软件界面的流行正当时
	 * @param index scope(1-5)
	 * @return
	 */
	public WebElement getWeekHotApps(int index) {
		if(index<0||index>5) {
			throw new RuntimeException("指定的下标不存在");
		}
		String expression ="//*[@resource-id='com.tencent.southpole.appstore:id/recycle_view']/android.view.ViewGroup["+index+"]/android.widget.TextView[1]";
		return helper.findByXpath(expression);
	}
	/**
	 * 福利界面-一键领取按钮
	 */
	public WebElement getBoonOnekeyGet() {		
		return helper.findById("com.tencent.southpole.appstore:id/button_receive_all");
	}
	/**
	 * 领取按钮
	 * @return
	 */
	public WebElement getReceiveOfOne() {
		return helper.findById("com.tencent.southpole.appstore:id/receive_button");
	}
	
}
