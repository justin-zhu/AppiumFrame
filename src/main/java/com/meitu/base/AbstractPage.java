package com.meitu.base;

import com.meitu.page.PageOfCenter;
import com.meitu.page.PageOfGame;
import com.meitu.page.PageOfHotApps;
import com.meitu.page.PageOfMain;
import com.meitu.page.PageOfNavigation;
import com.meitu.page.PageOfPublic;
import com.meitu.page.PageOfSoft;
import com.meitu.utils.Helper;

abstract public class AbstractPage {
	public PageOfPublic pubPage;
	public PageOfGame gamePage;
	public PageOfHotApps hotAppsPage;
	public PageOfMain mainPage;
	public PageOfNavigation navigation;
	public PageOfSoft softPage;
	public Helper helper;
	public PageOfCenter centerPage;
	public AbstractPage(Helper helper) {
		this.helper = helper;
		gamePage = new PageOfGame(helper);
		hotAppsPage = new PageOfHotApps(helper);
		mainPage = new PageOfMain(helper);
		navigation = new PageOfNavigation(helper);
		softPage = new PageOfSoft(helper);		
		pubPage = new PageOfPublic(helper);
		centerPage = new PageOfCenter(helper);
	}
	
}
