package com.meitu.page;

import org.openqa.selenium.WebElement;

import com.meitu.utils.Helper;

public class PageOfSoft {
	Helper helper;

	public PageOfSoft(Helper helper) {
		super();
		this.helper = helper;
	}
	/**
	 * 软件界面：榜单排行
	 * @return
	 */
	public WebElement getListSort() {
		return helper.findById("com.tencent.southpole.appstore:id/tag_1");
	}
	/**
	 * 软件界面榜单排行下的流行榜
	 * @return
	 */
	public WebElement getListSort_FashionList() {
		return helper.findByUiautomatorText("流行榜");
	}
	/**
	 * 软件界面榜单排行下的飙升榜
	 * @return
	 */
	public WebElement getListSort_RiseList() {
		return helper.findByUiautomatorText("飙升榜");
	}
	/**
	 * 软件界面榜单排行下的新品榜
	 * @return
	 */
	public WebElement getListSort_NewProductList() {
		return helper.findByUiautomatorText("新品榜");
	}
	/**
	 * 榜单排行中的下载按钮
	 * @return
	 */
	public WebElement getListSort_DownBtn() {
		return helper.findById("com.tencent.southpole.appstore:id/download_button");
	}
	/**
	 * 软件界面：腾讯出品
	 * @return
	 */
	public WebElement getTencentApp() {
		return helper.findById("com.tencent.southpole.appstore:id/tag_2");
	}
	/**
	 * 软件界面，腾讯出品下的：下载按钮
	 * @return
	 */
	public WebElement getTencentApp_DownBtn() {
		return helper.findById("com.tencent.southpole.appstore:id/download");
	}
	/**
	 * 点击任意APP详情界面
	 * @return
	 */
	public WebElement getTencentApp_AppInfo() {
		return helper.findById("com.tencent.southpole.appstore:id/tvCity");
	}
	
	/**
	 * 软件界面：软件分类
	 * @return
	 */
	public WebElement getSoftClassify() {
		return helper.findById("com.tencent.southpole.appstore:id/tag_3");
	}
}
