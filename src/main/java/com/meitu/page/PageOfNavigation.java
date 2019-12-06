package com.meitu.page;

import org.openqa.selenium.WebElement;

import com.meitu.utils.Helper;

import cn.hutool.core.util.RuntimeUtil;

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
	public boolean ping() {
		String device = helper.getAndroidDriver().getCapabilities().getCapability("deviceName").toString();
		String cmd = "adb -s "+device+" shell ping -c 1 www.qq.com";
		for (int i = 0; i < 10; i++) {
			helper.sleep(2000);
			String str = RuntimeUtil.execForStr(cmd);
			if(str.contains("unknown")) {
				continue;
			}else {
				return true;
			}
		}
		return false;
	}
	public void openWiFi() {
		boolean state = helper.getAndroidDriver().getConnection().isWiFiEnabled();
		if(!state) {
			helper.getAndroidDriver().openNotifications();
			helper.click(this.getWiFi(), "打开WiFi");
			if(ping()) {
				helper.back();
			}else {
				throw new RuntimeException("网络无法连接,请检查网络情况");
			}
			
		}
		helper.sleep(2000);
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
