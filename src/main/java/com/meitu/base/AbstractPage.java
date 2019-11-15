package com.meitu.base;

import com.meitu.page.PageOfGame;
import com.meitu.page.PageOfHotApps;
import com.meitu.page.PageOfMain;
import com.meitu.page.PageOfNavigation;
import com.meitu.page.PageOfSoft;
import com.meitu.utils.Helper;

abstract public class AbstractPage {
	public PageOfGame game;
	public PageOfHotApps hotApps;
	public PageOfMain main;
	public PageOfNavigation navigation;
	public PageOfSoft soft;
	public Helper helper;
	public AbstractPage(Helper helper) {
		this.helper = helper;
		game = new PageOfGame(helper);
		hotApps = new PageOfHotApps(helper);
		main = new PageOfMain(helper);
		navigation = new PageOfNavigation(helper);
		soft = new PageOfSoft(helper);		
	}
	
}
