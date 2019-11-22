package com.meitu.domain;

import com.meitu.service.WeakNetworkService;
import com.meitu.utils.Helper;
public class WeakNetwork{	
	
	private WeakNetworkService weakNetworkService;	
	public WeakNetwork() {}
	public WeakNetwork(Helper helper) {
		weakNetworkService = new WeakNetworkService(helper);
	}
	//执行首次使用应用市场
	public void firstLogin() {	
		//首次打开应用商店
		weakNetworkService.firstLogin();
		//非首次打开应用商店
		weakNetworkService.reOpenAppStore();
		//首页新游标签
		weakNetworkService.newGame();
		//福利
		weakNetworkService.homePageWelfare();
		//榜单
		weakNetworkService.homePageList();
		//分类
		weakNetworkService.homeClassify();
		//分类列表下的子类
		weakNetworkService.homeClassifyList();
		//福利礼包
		weakNetworkService.WelfareGift();
		//搜索
		weakNetworkService.search();
		//应用详情
		weakNetworkService.appInformation();
		//个人中心
		weakNetworkService.center();
		
	}	
}
