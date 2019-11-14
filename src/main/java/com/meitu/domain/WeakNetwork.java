package com.meitu.domain;

import org.apache.log4j.Logger;

import com.meitu.service.WeakNetworkService;
import com.meitu.utils.Helper;
public class WeakNetwork{		
	private final Logger logger = Logger.getLogger(this.getClass());	
	private WeakNetworkService weakNetworkService;	
	public WeakNetwork() {}
	public WeakNetwork(Helper helper) {
		weakNetworkService = new WeakNetworkService(helper);
	}
	//执行
	public void start() {
		try {
			weakNetworkService.firstLogin();
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}finally {
			weakNetworkService.killQNET();
		}
		
	}	
}
