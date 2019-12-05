package com.meitu.page;

import org.openqa.selenium.WebElement;

import com.meitu.utils.Helper;

public class PageOfNavigation {
	Helper helper;

	public PageOfNavigation(Helper helper) {
		super();
		this.helper = helper;
	}
	public void getnetworkState() {		
		System.out.println(helper.getAndroidDriver().getConnection().isWiFiEnabled());
	}
	/**
	 * 通知栏：WiFi按钮
	 * @return
	 */
	public WebElement getWiFi() {
		this.getnetworkState();
		return helper.findByXpath("//*[@resource-id='com.android.systemui:id/quick_qs_panel']/android.widget.LinearLayout/android.widget.Switch[2]");
	}
	public void closeWiFi() {
		boolean state = helper.getAndroidDriver().getConnection().isWiFiEnabled();
		if(state) {
			helper.getAndroidDriver().openNotifications();
			helper.click(this.getWiFi(), "关闭WiFi");
			helper.back();
		}
	}
	public void openWiFi() {
		boolean state = helper.getAndroidDriver().getConnection().isWiFiEnabled();
		if(!state) {
			helper.getAndroidDriver().openNotifications();
			helper.click(this.getWiFi(), "关闭WiFi");
			helper.back();
		}
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
