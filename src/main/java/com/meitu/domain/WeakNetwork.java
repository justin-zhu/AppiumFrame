package com.meitu.domain;

import org.apache.log4j.Logger;
import com.meitu.service.WeakNetworkService;
import com.meitu.utils.Helper;

public class WeakNetwork {	
	private Logger logger = Logger.getLogger(this.getClass());	
	private WeakNetworkService weakNetworkService;
	private static final String NETWORK_CLOSE = "100%丢包";
	private static final String NETWORK_DELAY = "延迟";
	private static final String NETWORK_NORMAL = "正常网络";
	public WeakNetwork() {
	}

	public WeakNetwork(Helper helper) {
		weakNetworkService = new WeakNetworkService(helper);
		logger.info("Helper:" + helper);
	}
	/**
	 * 清理环 境
	 */
	public void clean() {
		weakNetworkService.killAppStore();
		weakNetworkService.killQNET();
		logger.info("----------已清理应用商店、QNET进程");
	}
	/**
	 * test方法，勿用
	 */
	public void test01() {			
		
	}

	/**
	 *
	 * 类型：首次打开应用商店，装机必备页操作检查 操作：检查界面
	 * 覆盖100%
	 * @param temp
	 */
	public void firstLogin() {
		weakNetworkService.clearAppSroreData();
		weakNetworkService.changeNetwork(NETWORK_CLOSE);
		weakNetworkService.SkipAuthorizationInterface();
		weakNetworkService.sleep(15000);
		weakNetworkService.changeNetwork(NETWORK_NORMAL);
		weakNetworkService.slideUp(1);
		weakNetworkService.selectAllButton();
		weakNetworkService.disableAllButton();
		weakNetworkService.selectAllButton();
		weakNetworkService.changeNetwork(NETWORK_CLOSE);
		weakNetworkService.installButtonByAxis(500, 2200);
		weakNetworkService.back();
		weakNetworkService.clearAppSroreData();
		weakNetworkService.changeNetwork(NETWORK_NORMAL);
		weakNetworkService.SkipAuthorizationInterface();
		weakNetworkService.closeRecommendApp();
		weakNetworkService.closeAD();
	}

	/**
	 * pass 类型：非首次进入应用商店
	 * 100%
	 */
	public void reOpenAppStore() {
		weakNetworkService.changeNetwork(NETWORK_NORMAL);
		weakNetworkService.sleep(6000);		
		weakNetworkService.closeAD();
		weakNetworkService.changeNetwork(NETWORK_CLOSE);
		weakNetworkService.changeTabToHome();
		weakNetworkService.slideDown(1);
		weakNetworkService.sleep(15000);
		weakNetworkService.changeTabToGame();
		weakNetworkService.slideDown(1);
		weakNetworkService.sleep(15000);
		weakNetworkService.changeTabToSoft();
		weakNetworkService.slideDown(1);
		weakNetworkService.sleep(15000);
		weakNetworkService.changeNetwork(NETWORK_NORMAL);
		weakNetworkService.changeTabToHome();
		weakNetworkService.slideDown(1);
		weakNetworkService.sleep(3000);
		weakNetworkService.randomInstall();
		weakNetworkService.changeTabToGame();
		weakNetworkService.slideDown(1);
		weakNetworkService.sleep(3000);
		weakNetworkService.randomInstall();
		weakNetworkService.changeTabToSoft();
		weakNetworkService.slideDown(1);
		weakNetworkService.sleep(3000);
		weakNetworkService.randomInstall();
		weakNetworkService.changeNetwork(NETWORK_DELAY);
		weakNetworkService.changeTabToHome();
		weakNetworkService.slideDown(1);
		weakNetworkService.sleep(10000);
		weakNetworkService.changeTabToGame();
		weakNetworkService.slideDown(1);
		weakNetworkService.sleep(10000);
		weakNetworkService.changeTabToSoft();
		weakNetworkService.slideDown(1);
		weakNetworkService.sleep(10000);
		weakNetworkService.changeNetwork(NETWORK_NORMAL);
		weakNetworkService.changeTabToHome();		
		weakNetworkService.slideUp(2);
		weakNetworkService.sleep(5000);
		weakNetworkService.changeTabToGame();		
		weakNetworkService.slideUp(2);
		weakNetworkService.sleep(5000);
		weakNetworkService.changeTabToSoft();		
		weakNetworkService.slideUp(2);
		weakNetworkService.sleep(5000);
		weakNetworkService.changeNetwork(NETWORK_CLOSE);
		weakNetworkService.changeTabToHome();
		weakNetworkService.randomCheckAppInfo("首页");
		weakNetworkService.sleep(10000);
		weakNetworkService.changeTabToGame();
		weakNetworkService.randomCheckAppInfo("游戏");
		weakNetworkService.sleep(15000);
		weakNetworkService.changeTabToSoft();
		weakNetworkService.randomCheckAppInfo("软件");
		weakNetworkService.sleep(15000);
	}

	/**
	 * pass 首页--必备
	 * 100%
	 */
	public void homePageNecessary() {
		weakNetworkService.changeNetwork(NETWORK_NORMAL);
		weakNetworkService.sleep(6000);
		weakNetworkService.closeAD();		
		weakNetworkService.changeNetwork(NETWORK_CLOSE);		
		weakNetworkService.clickNecessary();
		weakNetworkService.sleep(15000);
		weakNetworkService.back();
		weakNetworkService.changeNetwork(NETWORK_NORMAL);
		weakNetworkService.clickNecessary();
		weakNetworkService.slideEnd();
		weakNetworkService.slideTop();
		weakNetworkService.randomCheckAppInfo("首页必备");
		weakNetworkService.back();	
		weakNetworkService.back();
		weakNetworkService.changeNetwork(NETWORK_DELAY);
		weakNetworkService.clickNecessary();
		weakNetworkService.sleep(10000);
		weakNetworkService.slideEnd();
		weakNetworkService.slideTop();
		weakNetworkService.back();
		weakNetworkService.changeNetwork(NETWORK_NORMAL);
		weakNetworkService.clickNecessary();
		weakNetworkService.changeNetwork(NETWORK_CLOSE);
		weakNetworkService.randomCheckAppInfo("首页必备");
		weakNetworkService.sleep(15000);
		weakNetworkService.randomInstall();
		weakNetworkService.slideUp(1);
		weakNetworkService.slideDown(1);		
	}

	/**
	 * pass
	 *60%
	 * 福利
	 */
	public void homePageWelfare() {
		weakNetworkService.changeNetwork(NETWORK_NORMAL);
		weakNetworkService.sleep(6000);
		weakNetworkService.closeAD();		
		weakNetworkService.changeNetwork(NETWORK_CLOSE);
		weakNetworkService.clickWelfare();
		weakNetworkService.sleep(15000);
		weakNetworkService.back();
		weakNetworkService.changeNetwork(NETWORK_NORMAL);
		weakNetworkService.clickWelfare();
		weakNetworkService.changeNetwork(NETWORK_CLOSE);		
		weakNetworkService.clickAllGameWelfare();
		weakNetworkService.sleep(15000);
		weakNetworkService.back();		
		weakNetworkService.clickGpassGameList();
		weakNetworkService.sleep(15000);
		weakNetworkService.back();
		weakNetworkService.clickBuyPhoneWelfare();
		weakNetworkService.sleep(15000);
		weakNetworkService.back();		
		weakNetworkService.changeNetwork(NETWORK_NORMAL);
		weakNetworkService.clickAllGameWelfare();
		weakNetworkService.back();
		weakNetworkService.clickGpassGameList();
		weakNetworkService.back();
		weakNetworkService.clickBuyPhoneWelfare();
		weakNetworkService.sleep(3000);
		weakNetworkService.back();
		weakNetworkService.back();
		weakNetworkService.changeNetwork(NETWORK_DELAY);
		weakNetworkService.clickWelfare();
		weakNetworkService.sleep(10000);
		weakNetworkService.clickAllGameWelfare();
		weakNetworkService.sleep(10000);
		weakNetworkService.randomCheckAppInfo("全部游戏福利");
		weakNetworkService.sleep(10000);
		weakNetworkService.back();
		weakNetworkService.back();
		weakNetworkService.clickWelfareBanner();
		weakNetworkService.sleep(10000);
		weakNetworkService.back();		
	}

	/**
	 * pass 榜单
	 * 100%
	 */
	public void homePageList() {
		weakNetworkService.changeNetwork(NETWORK_NORMAL);
		weakNetworkService.sleep(6000);
		weakNetworkService.closeAD();		
		weakNetworkService.changeNetwork(NETWORK_CLOSE);
		weakNetworkService.clickList();
		weakNetworkService.sleep(15000);		
		weakNetworkService.changeNetwork(NETWORK_NORMAL);
		weakNetworkService.slideUp(1);
		weakNetworkService.changeNetwork(NETWORK_CLOSE);
		weakNetworkService.randomCheckAppInfo("榜单");
		weakNetworkService.sleep(15000);
		weakNetworkService.back();
		weakNetworkService.back();
		weakNetworkService.changeNetwork(NETWORK_DELAY);
		weakNetworkService.clickList();
		weakNetworkService.sleep(10000);
	}

	/**
	 * 首页分类
	 * 70%
	 * @return pass
	 */
	public void homeClassify() {
		weakNetworkService.changeNetwork(NETWORK_NORMAL);
		weakNetworkService.sleep(6000);
		weakNetworkService.closeAD();		
		weakNetworkService.changeNetwork(NETWORK_CLOSE);
		weakNetworkService.clickClassify();
		weakNetworkService.sleep(15000);
		weakNetworkService.changeNetwork(NETWORK_NORMAL);
		weakNetworkService.slideUp(1);
		weakNetworkService.back();
		weakNetworkService.changeNetwork(NETWORK_DELAY);
		weakNetworkService.clickClassify();
		weakNetworkService.sleep(10000);
		weakNetworkService.randomCheckAppInfo("分类");
		weakNetworkService.sleep(10000);
		weakNetworkService.back();
		weakNetworkService.slideEnd();
		weakNetworkService.randomCheckAppInfo("分类");
		weakNetworkService.back();
		weakNetworkService.changeNetwork(NETWORK_CLOSE);
		weakNetworkService.slideEnd();
		weakNetworkService.randomCheckAppInfo("分类");
		weakNetworkService.sleep(15000);
		
	}

	/**
	 * 首页分类-任意分类下的列表 
	 * pass
	 * 100%
	 */
	public void homeClassifyList() {
		weakNetworkService.changeNetwork(NETWORK_NORMAL);
		weakNetworkService.sleep(6000);
		weakNetworkService.closeAD();		
		weakNetworkService.clickClassify();	
		weakNetworkService.changeNetwork(NETWORK_CLOSE);
		weakNetworkService.randomCheckAppInfo("分类");	
		weakNetworkService.back();
		weakNetworkService.back();
		weakNetworkService.changeTabToGame();
		weakNetworkService.slideDown(1);
		weakNetworkService.sleep(15000);
		weakNetworkService.changeTabToSoft();
		weakNetworkService.slideDown(1);
		weakNetworkService.sleep(15000);
		weakNetworkService.changeTabToHome();
		weakNetworkService.slideDown(1);
		weakNetworkService.sleep(15000);
		weakNetworkService.changeNetwork(NETWORK_DELAY);
		weakNetworkService.sleep(10000);
		weakNetworkService.clickClassify();
		weakNetworkService.sleep(10000);
		weakNetworkService.randomCheckAppInfo("分类");
		weakNetworkService.sleep(10000);
		weakNetworkService.back();
		weakNetworkService.clickClassify();
		weakNetworkService.changeNetwork(NETWORK_CLOSE);
		weakNetworkService.checkList("软件", 15000);		
	}

	/**
	 * 新游
	 */
	public void newGame() {
		weakNetworkService.changeNetwork(NETWORK_NORMAL);
		weakNetworkService.sleep(6000);
		weakNetworkService.closeAD();		
		weakNetworkService.changeTabToGame();
		weakNetworkService.changeNetwork(NETWORK_CLOSE);
		weakNetworkService.clickGameTabNewGame();
		weakNetworkService.sleep(15000);
		weakNetworkService.changeNetwork(NETWORK_NORMAL);
		weakNetworkService.slideUp(1);
		weakNetworkService.back();
		weakNetworkService.changeNetwork(NETWORK_DELAY);
		weakNetworkService.clickGameTabNewGame();
		weakNetworkService.sleep(10000);
		weakNetworkService.slideEnd();
		weakNetworkService.slideTop();
		weakNetworkService.randomCheckAppInfo("新游");
		weakNetworkService.sleep(10000);
		// 后续实现安装功能
		weakNetworkService.randomInstall();
		weakNetworkService.back();
		weakNetworkService.changeNetwork(NETWORK_CLOSE);
		weakNetworkService.slideDown(1);
		weakNetworkService.sleep(15000);
		weakNetworkService.slideUp(1);
		weakNetworkService.sleep(15000);
		weakNetworkService.randomCheckAppInfo("新游");
		weakNetworkService.changeNetwork(NETWORK_NORMAL);
		weakNetworkService.slideDown(1);
		weakNetworkService.back();
		weakNetworkService.slideDown(1);
		weakNetworkService.randomInstall();		
	}

	/**
	 * 福利礼包
	 * 50%
	 */
	public void WelfareGift() {
		weakNetworkService.changeNetwork(NETWORK_NORMAL);
		weakNetworkService.sleep(6000);
		weakNetworkService.closeAD();
		weakNetworkService.changeTabToMy();
		weakNetworkService.logout();
		weakNetworkService.changeTabToGame();
		weakNetworkService.clickWelfare();
		weakNetworkService.changeNetwork(NETWORK_CLOSE);
		weakNetworkService.clickOneKeyGet();
		weakNetworkService.otherLogin();
		weakNetworkService.loginQQ();
		weakNetworkService.sleep(7000);
		weakNetworkService.checkLoginView();
		weakNetworkService.back();
		weakNetworkService.clickWelfareBanner();
		weakNetworkService.sleep(15000);
		weakNetworkService.back();
		weakNetworkService.changeNetwork(NETWORK_DELAY);
		weakNetworkService.clickOneKeyGet();
		weakNetworkService.checkLoginView();
		weakNetworkService.back();
		weakNetworkService.clickDefaultGet();
		weakNetworkService.checkLoginView();
		weakNetworkService.back();
	}
	/**
	 * 搜索
	 * 70%
	 */
	public void search() {
		weakNetworkService.changeNetwork(NETWORK_NORMAL);
		weakNetworkService.sleep(6000);		
		weakNetworkService.closeAD();
		weakNetworkService.changeNetwork(NETWORK_CLOSE);
		weakNetworkService.changeTabToHome();
		weakNetworkService.clickSearch();
		weakNetworkService.sleep(15000);
		weakNetworkService.back();
		weakNetworkService.changeNetwork(NETWORK_NORMAL);
		weakNetworkService.slideUp(1);
		weakNetworkService.back();
		weakNetworkService.changeNetwork(NETWORK_DELAY);
		weakNetworkService.sendText("微视");
		weakNetworkService.sleep(10000);
		weakNetworkService.clickSearchButton();
		weakNetworkService.sleep(10000);
		weakNetworkService.randomInstall();
		weakNetworkService.back();
		weakNetworkService.changeNetwork(NETWORK_NORMAL);
		weakNetworkService.clickSearch();
		weakNetworkService.changeNetwork(NETWORK_CLOSE);
		weakNetworkService.sendText("快手");
		weakNetworkService.clickSearchButton();
		weakNetworkService.sleep(15000);
		weakNetworkService.changeNetwork(NETWORK_NORMAL);
		weakNetworkService.clickSearchButton();
		weakNetworkService.randomInstall();		
	}
	/**
	 * 应用详情
	 * 90%
	 */
	public void appInformation() {
		weakNetworkService.changeNetwork(NETWORK_NORMAL);
		weakNetworkService.sleep(6000);
		weakNetworkService.closeAD();
		weakNetworkService.changeTabToSoft();
		weakNetworkService.changeNetwork(NETWORK_CLOSE);
		weakNetworkService.randomCheckAppInfo("软件");
		weakNetworkService.sleep(15000);		
		weakNetworkService.changeNetwork(NETWORK_NORMAL);
		weakNetworkService.slideUp(1);
		weakNetworkService.back();
		weakNetworkService.changeNetwork(NETWORK_DELAY);
		weakNetworkService.randomCheckAppInfo("软件");
		weakNetworkService.sleep(10000);
		weakNetworkService.clickCommit();
		weakNetworkService.back();
		weakNetworkService.changeNetwork(NETWORK_NORMAL);
		weakNetworkService.randomCheckAppInfo("软件");
		weakNetworkService.changeNetwork(NETWORK_CLOSE);
		weakNetworkService.clickCommit();
		weakNetworkService.changeNetwork(NETWORK_NORMAL);
		weakNetworkService.randomInstall();		
	}
	/**
	 * 查看游戏图片
	 */
	public void checkGamePicture() {
		weakNetworkService.changeNetwork(NETWORK_NORMAL);
		weakNetworkService.sleep(6000);
		weakNetworkService.closeAD();
		weakNetworkService.changeTabToGame();
		weakNetworkService.clickClassifyOfGame();
		weakNetworkService.randomCheckAppInfo("游戏分类");
		weakNetworkService.changeNetwork(NETWORK_CLOSE);
		weakNetworkService.clickInformationPicture();
		weakNetworkService.slideLeft(5);
		weakNetworkService.back();
		weakNetworkService.changeNetwork(NETWORK_NORMAL);
		weakNetworkService.clickInformationPicture();
		weakNetworkService.slideLeft(5);
		weakNetworkService.back();
		weakNetworkService.changeNetwork(NETWORK_DELAY);
		weakNetworkService.clickInformationPicture();
		weakNetworkService.slideLeft(5);
		weakNetworkService.back();		
	}
	/**
	 * 我的
	 */
	public void my() {
		weakNetworkService.changeNetwork(NETWORK_NORMAL);
		weakNetworkService.sleep(6000);
		weakNetworkService.closeAD();		
		weakNetworkService.changeTabToMy();
		weakNetworkService.clickLogin();
		weakNetworkService.otherLogin();
		weakNetworkService.loginQQ();
		weakNetworkService.sleep(6000);
		weakNetworkService.changeNetwork(NETWORK_CLOSE);
		weakNetworkService.clickAppUpdate();
		weakNetworkService.back();
		weakNetworkService.clickAppUninstall();
		weakNetworkService.back();
		weakNetworkService.clickMyReserve();
		weakNetworkService.sleep(15000);
		weakNetworkService.back();
		weakNetworkService.clickMyGift();
		weakNetworkService.sleep(15000);
		weakNetworkService.back();		
	}
}
