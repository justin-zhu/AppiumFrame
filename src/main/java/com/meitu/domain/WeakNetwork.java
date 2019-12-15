package com.meitu.domain;

import com.meitu.service.WeakNetworkService;
import com.meitu.utils.Helper;
public class WeakNetwork{	
	
	private WeakNetworkService weakNetworkService;		
	public WeakNetwork(Helper helper) {
		weakNetworkService = new WeakNetworkService(helper);
	}
	//执行首次使用应用市场
	public void firstLogin() {	
		//首次打开应用商店
		weakNetworkService.firstLogin();	
	}
	public void center() {
		//个人中心
		weakNetworkService.center();
	}
	public void appInformation() {
		//应用详情
		weakNetworkService.appInformation();
	}
	public void search() {
		//搜索
		weakNetworkService.search();
	}
	public void welfareGift() {
		//福利礼包
		weakNetworkService.welfareGift();
	}
	
	public void homeClassify() {
		//分类
		weakNetworkService.homeClassify();
	}
	public void homePageList() {
		//榜单
		weakNetworkService.homePageList();
	}
	public void homePageWelfare() {
		//福利
		weakNetworkService.homePageWelfare();
	}
	public void newGame() {
		//新游标签
		weakNetworkService.newGame();
	}
	public void reOpenAppStore() {
		//非首次打开应用商店
		weakNetworkService.reOpenAppStore();
	}
	
	public void installApp() {
		//安装应用
		weakNetworkService.installApp();
	}	
}
