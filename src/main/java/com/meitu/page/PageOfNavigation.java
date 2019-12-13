package com.meitu.page;

import org.openqa.selenium.WebElement;

import com.meitu.utils.Helper;

public class PageOfNavigation {
	Helper helper;

	public PageOfNavigation(Helper helper) {
		super();
		this.helper = helper;
	}

	/**
	 * 通知栏：数据按钮
	 * @return
	 */
	public WebElement getData() {
		return helper.findByXpath("//*[@resource-id='com.android.systemui:id/quick_qs_panel']/android.widget.LinearLayout/android.widget.Switch[1]");
	}
	/**
	 * 通知栏：飞行模式
	 * @return
	 */
	public WebElement getAirplane() {
		return helper.findByXpath("//*[@resource-id='com.android.systemui:id/quick_qs_panel']/android.widget.LinearLayout/android.widget.Switch[4]");
	}
	
	
}
