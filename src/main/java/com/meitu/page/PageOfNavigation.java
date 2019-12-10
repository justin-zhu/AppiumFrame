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

	public void closeWiFi() {
		boolean state = helper.getAndroidDriver().getConnection().isWiFiEnabled();
		String deviceName = helper.getAndroidDriver().getCapabilities().getCapability("deviceName").toString();
		if(state) {
			RuntimeUtil.execForStr("adb -s "+deviceName+" shell  svc wifi disable");
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
		String device = helper.getAndroidDriver().getCapabilities().getCapability("deviceName").toString();
		boolean state = helper.getAndroidDriver().getConnection().isWiFiEnabled();
		if(!state) {
			RuntimeUtil.execForStr("adb -s "+device+" shell  svc wifi enable");			
			if(!ping()) {
				throw new RuntimeException("网络无法连接,请检查网络情况");
			}			
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
