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
		//weakNetworkService.firstLogin();
		//weakNetworkService.reOpenAppStore();
		//weakNetworkService.newGame();
		//weakNetworkService.homePageWelfare();
		weakNetworkService.homePageList();
	}	
}
