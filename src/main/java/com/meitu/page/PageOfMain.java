package com.meitu.page;

import org.openqa.selenium.WebElement;

import com.meitu.utils.Helper;
/**
 * 首页
 * @author Administrator
 *
 */
public class PageOfMain {
	Helper helper;

	public PageOfMain(Helper helper) {		
		this.helper = helper;
	}
	
	public PageOfMain() {
		super();
	}
	/**
	 * 启动时，弹出的权限窗口，同意元素
	 * @return
	 */
	public WebElement getAuthor() {
		return helper.findById("com.tencent.southpole.appstore:id/dialog_right_btn");
	}
	public WebElement getErrorOfGetDate() {
		return helper.findById("com.tencent.southpole.appstore:id/tv_net_error_desc");
	}
	
	
	/**
	 * 首页广告弹窗口，关闭按钮
	 * @return
	 */
	public WebElement getOpenAd() {
		return helper.findById("com.tencent.southpole.appstore:id/ic_close");
	}
	/**
	 * 底部导航区的首页元素
	 * @return
	 */
	public WebElement getIndex() {
		return helper.findById("com.tencent.southpole.appstore:id/tab_home");
	}
	/**
	 * 首页顶部:新游 
	 * @return
	 */
	public WebElement getNewGame() {
		return helper.findById("com.tencent.southpole.appstore:id/tag_layout_1");
	}
	/**
	 * 首页顶部:福利
	 * @return
	 */
	public WebElement getBoon() {
		return helper.findById("com.tencent.southpole.appstore:id/tag_layout_2");
	}
	/**
	 * 首页顶部:榜单
	 * @return
	 */
	public WebElement getList() {
		return helper.findById("com.tencent.southpole.appstore:id/tag_layout_3");
	}
	/**
	 * 榜单界面下的：流行榜
	 * @return
	 */
	public WebElement getList_FashionList(){
		return helper.findByUiautomatorText("流行榜");
	}
	/**
	 * 榜单界面下的：热销榜
	 * @return
	 */
	public WebElement getList_HotAppsList() {
		return helper.findByUiautomatorText("热销榜");
	}
	/**
	 * 榜单界面下的：新品榜
	 * @return
	 */
	public WebElement getList_NewProduct() {
		return helper.findByUiautomatorText("新品榜");
	}
	/**
	 * 榜单界面中的下载按钮
	 * @return
	 */
	public WebElement getList_DownBtn() {
		return helper.findById("com.tencent.southpole.appstore:id/download_button");
	}
	/**
	 * 首页顶部:分类
	 * @return
	 */
	public WebElement getClassify() {
		return helper.findById("com.tencent.southpole.appstore:id/tag_layout_4");
	}
	/**
	 * 分类界面下的软件分类
	 * @return
	 */
	public WebElement getClassify_Soft() {
		return helper.findByXpath("//androidx.appcompat.app.ActionBar.Tab[@content-desc=\"软件\"]");
	}
	/**
	 * 分类下的软件分类:视频类
	 * @return
	 */
	public WebElement getClassify_Soft_Video() {
		return helper.findByUiautomatorText("视频");
	}
	/**
	 * 分类界面下的游戏分类
	 * @return
	 */
	public WebElement getClassify_Game() {
		return helper.findByXpath("//androidx.appcompat.app.ActionBar.Tab[@content-desc=\"游戏\"]");
	}
	/**
	 * 分类下的游戏分类:休闲类
	 * @return
	 */
	public WebElement getClassifty_Game_Relax() {
		return helper.findByUiautomatorText("休闲益智");
	}	
	
	/**
	 * 首页下载按钮
	 * @return
	 */
	public WebElement getDownBtn() {
		return helper.findById("com.tencent.southpole.appstore:id/download");
	}
	/**
	 * 首页:搜索
	 * @return
	 */
	public WebElement getSearch() {
		return helper.findById("com.tencent.southpole.appstore:id/search_action_bar_content");
	}
}
