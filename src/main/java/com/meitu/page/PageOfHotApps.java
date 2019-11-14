package com.meitu.page;
/**
 * 装机必备界面
 * @author Administrator
 *
 */

import org.openqa.selenium.WebElement;

import com.meitu.utils.Helper;

public class PageOfHotApps {
	
	private Helper helper;	
	
	public PageOfHotApps(Helper helper) {
		
		this.helper = helper;
	}

	/**
	 * 装机必备:全选
	 * @return
	 */
	public WebElement getCheckAll() {
		return helper.findById("com.tencent.southpole.appstore:id/all");
	}
	/**
	 * 无法加载界面提示
	 * @return
	 */
	public WebElement getErrorDesc() {
		return helper.findById("com.tencent.southpole.appstore:id/tv_net_error_desc");
	}
	/**
	 * 装机必备:关闭按钮
	 * @return
	 */
	public WebElement getClose() {
		return helper.findById("com.tencent.southpole.appstore:id/close");
	}
	/**
	 * 装机必备:安装按钮
	 * @return
	 */
	public WebElement getInsatllBtn() {
		return helper.findById("com.tencent.southpole.appstore:id/install");
	}
}
