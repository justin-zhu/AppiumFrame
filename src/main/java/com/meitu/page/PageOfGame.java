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
	 * 游戏界面下的：新游
	 * @return
	 */
	public WebElement getNewGame() {
		return helper.findById("com.tencent.southpole.appstore:id/item_1");
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
	 * 分类下的游戏分类:休闲类
	 * @return
	 */
	public WebElement getClassifty_Game_Relax() {
		return helper.findByUiautomatorText("休闲益智");
	}
	/**
	 * 游戏界面下的：下载按钮
	 * @return
	 */
	public WebElement getDownBtn() {
		return helper.findById("com.tencent.southpole.appstore:id/download");
	}
}
