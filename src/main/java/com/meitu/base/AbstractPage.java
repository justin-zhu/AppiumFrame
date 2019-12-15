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
	public static final String NETWORK_CLOSE = "100%丢包";
	public static final String NETWORK_DELAY = "延迟";
	public static final String NETWORK_NORMAL = "正常网络";
	public static final String NETWORK_START = "START";
	public static final String NO_DATA="没有获取到数据";
	public static final String NETWORK_DISCONNECTED="网络断开了";
	public static final String NETWORK_NO_CONNECT="网络未连接";
	public static final String UP="up";
	public static final String DOWN="down";
	public static final String HOME_PAGE="首页";
	public static final String SOFT_PAGE="软件页";
	public static final String GAME_PAGE="游戏页";
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
