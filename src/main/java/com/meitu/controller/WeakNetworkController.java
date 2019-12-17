package com.meitu.controller;

import com.meitu.service.WeakNetworkService;
import com.meitu.utils.Helper;
public class WeakNetworkController{	
	
	private WeakNetworkService weakNetworkService;		
	public WeakNetworkController(Helper helper) {
		weakNetworkService = new WeakNetworkService(helper);
	}
	
	public void firstLogin() {	
		//首次打开应用商店
		weakNetworkService.firstLogin();	
	}
	public void reOpenAppStore() {
		//非首次打开应用商店
		weakNetworkService.reOpenAppStore();
	}
	public void tabPage() {
		//Tab主页（首页/游戏/软件）
		weakNetworkService.tabPage();
	}
	public void homePageWelfare() {
		//福利
		weakNetworkService.homePageWelfare();
	}
	public void homePageList() {
		//榜单
		weakNetworkService.homePageList();
	}
	public void homeClassify() {
		//分类
		weakNetworkService.homeClassify();
	}
	public void  listOfCheckAll() {
		//列表（首页、游戏、软件、新游）
		weakNetworkService.listOfCheckAll();
	}
	public void newGame() {
		//新游标签
		weakNetworkService.newGame();
	}
	public void search() {
		//搜索
		weakNetworkService.search();
	}
	public void appInformation() {
		//应用详情
		weakNetworkService.appInformation();
	}
	public void installApp() {
		//安装应用
		weakNetworkService.installApp();
	}
	
	public void center() {
		//个人中心
		weakNetworkService.center();
	}		
}
