package com.meitu.page;

import org.openqa.selenium.WebElement;

import com.meitu.utils.Helper;

public class PageOfGame {
	Helper helper;

	public PageOfGame(Helper helper) {
		super();
		this.helper = helper;
	}

	public PageOfGame() {
		super();		
	}
	
	/**
	 * 游戏页
	 * @return
	 */
	public WebElement getIndex() {
		return helper.findById("com.tencent.southpole.appstore:id/tab_game");
	}
	/**
	 * 游戏界面下的：新游
	 * @return
	 */
	public WebElement getNewGame() {
		return helper.findById("com.tencent.southpole.appstore:id/item_1");
	}
	/**
	 * 游戏顶部推荐部
	 * @return
	 */
	public WebElement getTopBanner() {
		return helper.findByXpath("//*[@resource-id='com.tencent.southpole.appstore:id/app_banner']/android.widget.TextView[1]");
	}
	/**
	 * 游戏界面下的:福利
	 * @return
	 */
	public WebElement getBoon() {
		return helper.findById("com.tencent.southpole.appstore:id/item_2");
	}
	/**
	 * 游戏界面下的:榜单
	 * @return
	 */
	public WebElement getList() {
		return helper.findById("com.tencent.southpole.appstore:id/item_3");
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
	 * 游戏界面下的:分类
	 * @return
	 */
	public WebElement getClassify() {
		return helper.findById("com.tencent.southpole.appstore:id/item_4");
	}
	
	/**
	 * 游戏界面下的：下载按钮
	 * @return
	 */
	public WebElement getDownBtn() {
		return helper.findById("com.tencent.southpole.appstore:id/download");
	}
	/**
	 * 新游顶部
	 * @return
	 */
	public WebElement getTopGame() {
		return helper.findByXpath("//*[@resource-id='com.tencent.southpole.appstore:id/item']/android.widget.TextView[1]");
	}
	/**
	 * 顶部新游推荐位详情界面 判断是否存在元素
	 * @return
	 */
	public WebElement getTopGameInfo() {
		return helper.findById("com.tencent.southpole.appstore:id/app_name_atmosphere");
	}
	/**
	 * 全部游戏福利
	 */
	public WebElement getAllGameBoonBtn() {
		return helper.findById("com.tencent.southpole.appstore:id/button_all_game");
	}
	/**
	 * 全部游戏福利下的游戏列表
	 * index 1 默认取第一个
	 */
	public WebElement getAllGameBoonList(int index) {
		return helper.findByXpath("//*[@resource-id='com.tencent.southpole.appstore:id/recycler_view']/android.view.ViewGroup[+"+(index+1)+"]/android.view.ViewGroup/android.widget.TextView[1]");
	}
	/**
	 * Gpass特权
	 */
	public WebElement getGpassGame() {
		return helper.findById("com.tencent.southpole.appstore:id/button2");
	}
	/**
	 * Gpass特权界面-用户信息
	 */
	public WebElement getGpassUserInfo() {
		return helper.findById("com.tencent.southpole.appstore:id/bg_userinfo");
	}
	/**
	 * 购机福利
	 */
	public WebElement getBuyPhoneBoon() {
		return helper.findById("com.tencent.southpole.appstore:id/button1");
	}
	/**
	 * 福利界面banner
	 * @return
	 */
	public WebElement getBanner() {
		return helper.findById("com.tencent.southpole.appstore:id/image_view");
	}
	/**
	 * 无网状态，点击福利banner界面 提示网页找不到
	 * @return
	 */
	public WebElement getErrorPage() {
		return helper.findById("com.tencent.southpole.appstore:id/back_btn");
	}
	/**
	 * 查看GPASS
	 */
	public WebElement getCheckGPASS() {
		return helper.findByUiautomatorText("查看G-PASS");
	}
	/**
	 * 启用我的GPASS特权
	 */
	public WebElement getEnableGpass() {
		return helper.findById("com.tencent.southpole.appstore:id/textView19");
	}
	
}
