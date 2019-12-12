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
		return helper.findByUiautomatorText("全选");
	}
	public WebElement getQuitCheckAll() {
		return helper.findByUiautomatorText("取消全选");
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
	/**
	 * 推荐列表中的应用
	 */
	public WebElement getListOfFirst() {
		String xpath = "//*[@resource-id='com.tencent.southpole.appstore:id/recyview']/android.widget.RelativeLayout[1]/android.widget.TextView[1]";
		return helper.findByXpath(xpath);
	}
	/**
	 * 复选框,默认选择第2个元素
	 */
	public WebElement getCheckBox(int index) {
		return helper.getAndroidDriver().findElementsById("com.tencent.southpole.appstore:id/checkBox").get((index-1));
	}
}
