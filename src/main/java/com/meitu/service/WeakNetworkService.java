package com.meitu.service;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebElement;
import com.meitu.base.AbstractPage;
import com.meitu.utils.Helper;

import cn.hutool.core.util.RuntimeUtil;
import io.appium.java_client.android.Activity;

public class WeakNetworkService extends AbstractPage {

	private static final Logger logger = Logger.getLogger(WeakNetworkService.class);

	public WeakNetworkService(Helper helper) {
		super(helper);
	}

	/**
	 * 首次进入应用商店
	 */
	public void firstLogin() {
		/*
		 * 子模块：手机无网络
		 */
		helper.closeWiFi();
		this.clearAppSroreData();
		helper.click(pubPage.getAuthor(), "同意").isExistClickElseSkip(pubPage.getAuthorOfSystem(), "同意存储权限");
		/*
		 * 打开应用商店 装机必备页面出现无网络提示，点击可重新加载
		 */
		helper.sleep(5000).checkElement(pubPage.getErrorOfGetDate(), NO_DATA);
		helper.click(pubPage.getErrorOfGetDate(), NO_DATA).sleep(15000).checkElement(pubPage.getErrorOfGetDate(),
				NO_DATA);
		/*
		 * 10s后恢复网络，点击重新加载 可正常加载出装机必备列表
		 */
		this.setConnectionType(NETWORK_NORMAL);
		helper.swipeDirection("up");
		/*
		 * 10s后恢复网络，点击重新加载 页面加载出来后可正常操作
		 */
		helper.checkElement(hotAppsPage.getListOfFirst(), "第一位应用");
		helper.click(hotAppsPage.getCheckAll(), "全选");
		helper.checkSelectStatusIsTrue(hotAppsPage.getCheckBox(1));
		helper.click(hotAppsPage.getQuitCheckAll(), "取消全选");
		helper.checkSelectStatusIsFalse(hotAppsPage.getCheckBox(1));
		helper.click(hotAppsPage.getCheckAll(), "全选");
		/*
		 * 子模块：进入装机必备页面后手机断网
		 */
		helper.closeWiFi();
		/*
		 * 手机断网后，在装机必备页面勾选任意应用,在装机必备页面点击“全选”/“取消全选”按钮 可正常操作，安装按钮状态显示正确
		 */
		helper.click(hotAppsPage.getQuitCheckAll(), "取消全选");
		helper.checkSelectStatusIsFalse(hotAppsPage.getCheckBox(1));
		/*
		 * 已勾选应用，点击“安装” 提示网络中断
		 */
		helper.click(hotAppsPage.getListOfFirst(), "第一位应用");
		helper.checkSelectStatusIsTrue(hotAppsPage.getCheckBox(1));
		helper.click(hotAppsPage.getInsatllBtn(), "安装").checkElement(hotAppsPage.getInsatllBtn(), "安装");
		/*
		 * 未勾选应用，点击关闭装机必备页面 可正常进入首页
		 */
		helper.click(hotAppsPage.getCheckAll(), "全选");
		helper.checkSelectStatusIsTrue(hotAppsPage.getCheckBox(1));
		helper.click(hotAppsPage.getQuitCheckAll(), "取消全选");
		helper.checkSelectStatusIsFalse(hotAppsPage.getCheckBox(1));
		helper.click(hotAppsPage.getClose(), "关闭");
		helper.checkElement(mainPage.getIndex(), "首页");
		helper.openWiFi();
		/*
		 * 子模块：手机处于弱网环境,上行/下行延迟分别设置为1000ms
		 */
		this.clearAppSroreData().setConnectionType(NETWORK_DELAY);
		helper.click(pubPage.getAuthor(), "同意").isExistClickElseSkip(pubPage.getAuthorOfSystem(), "同意存储权限");
		/**
		 * 打开应用商店，进入装机必备 加载成功,页面显示正常,可正常选择任意应用下载/继续下载
		 */
		helper.sleep(10000).checkElement(hotAppsPage.getListOfFirst(), "第一位应用");
		helper.click(hotAppsPage.getCheckAll(), "全选");
		helper.checkSelectStatusIsTrue(hotAppsPage.getCheckBox(1));
		helper.click(hotAppsPage.getQuitCheckAll(), "取消全选");
		helper.checkSelectStatusIsFalse(hotAppsPage.getCheckBox(1));
		helper.click(hotAppsPage.getListOfFirst(), "第一位应用");
		helper.click(hotAppsPage.getInsatllBtn(), "安装");
		this.checkDownResult();
	}

	/**
	 * 非首次进入应用商店
	 */
	public void reOpenAppStore() {
		/*
		 * 子模块：手机无网络
		 */
		helper.closeWiFi();
		this.clean();		
		this.setConnectionType(NETWORK_START);
		helper.isExistClickElseSkip(pubPage.getAdFrame(), "广告");
		/*
		 * 打开应用商店 首页出现无网络提示，点击可重新加载
		 */
		helper.checkElement(pubPage.getErrorOfGetDate(), NETWORK_DISCONNECTED);
		helper.click(pubPage.getErrorOfGetDate(), NETWORK_DISCONNECTED).sleep(5000)
				.checkElement(pubPage.getErrorOfGetDate(), NETWORK_DISCONNECTED);
		/*
		 * 切换到游戏tab 页面显示loading图标出现无网络提示，点击可重新加载
		 */
		helper.click(gamePage.getIndex(), "游戏页").checkElement(pubPage.getErrorOfGetDate(), NETWORK_DISCONNECTED)
				.sleep(2000);
		/*
		 * 切换到应用tab 页面显示loading图标出现无网络提示，点击可重新加载
		 */
		helper.click(softPage.getIndex(), "软件页").checkElement(pubPage.getErrorOfGetDate(), NETWORK_DISCONNECTED)
				.sleep(2000);
		/*
		 * 10s后恢复网络，点击重新加载 可正常加载出首页/游戏/软件页面
		 */
		helper.openWiFi();
		this.setConnectionType(NETWORK_NORMAL);
		helper.click(mainPage.getIndex(), "首页");
		helper.swipeDirection("down");
		mainPage.swipeHideTheBar();
		helper.checkElement(pubPage.getWeekHotApps(1), "检查本周热门应用是否已在首页显示");
		helper.click(gamePage.getIndex(), "游戏页").swipeDirection("down");
		helper.checkElement(gamePage.getTopBanner(), "游戏推荐位");
		helper.click(softPage.getIndex(), "软件页").swipeDirection("down");
		helper.checkElement(pubPage.getWeekHotApps(1), "流行正当时第1个应用");
		/*
		 * 10s后恢复网络，点击重新加载 页面加载出来后可正常滑动页面、选择游戏下载
		 */
		helper.swipeDirection("down").sleep(3000);
		helper.checkElement(pubPage.getWeekHotApps(1), "流行正当时第1个应用");
		helper.click(pubPage.getWeekHotApps(2), "流行正当时第2个应用").click(pubPage.getInstalBtn(), "安装").back();
		this.checkDownResult();
		/*
		 * 子模块：手机处于弱网环境,上行/下行延迟分别设置为1000ms
		 */
		this.clean().setConnectionType(NETWORK_DELAY);
		helper.isExistClickElseSkip(pubPage.getAdFrame(), "广告");
		/*
		 * 打开应用商店，进入首页
		 */
		helper.sleep(10000).checkElement(pubPage.getWeekHotApps(1), "检查本周热门应用是否已在首页显示");
		helper.click(gamePage.getIndex(), "游戏页").sleep(10000).checkElement(gamePage.getTopBanner(), "游戏推荐位");
		helper.click(softPage.getIndex(), "软件页").sleep(10000).checkElement(pubPage.getWeekHotApps(1), "流行正当时第1个应用");
		helper.click(pubPage.getWeekHotApps(3), "流行正当时第3个应用").sleep(10000).click(pubPage.getInstalBtn(), "安装").back();
		this.checkDownResult();
		/*
		 * 首页/游戏/软件tab页内容加载出来后手机断网
		 */
		this.clean().setConnectionType(NETWORK_NORMAL);
		helper.isExistClickElseSkip(pubPage.getAdFrame(), "广告");
		helper.click(mainPage.getIndex(), "首页").sleep(3000);
		helper.click(gamePage.getIndex(), "游戏页").sleep(3000);
		helper.click(softPage.getIndex(), "软件页").sleep(3000);
		helper.closeWiFi();
		/*
		 * 点击进入任意应用详情页 详情页出现loading图标，超时后出现网络中断提示
		 */
		helper.click(mainPage.getIndex(), "首页").click(pubPage.getWeekHotApps(1), "本周热门应用：第1个").sleep(3000);
		helper.checkElement(pubPage.getErrorOfGetDate(), NETWORK_DISCONNECTED).back();
		/*
		 * 点击任意应用安装 出现网络未连接提示
		 */
		helper.swipeDirection("up").click(mainPage.getDownBtn(), "安装").isExistToast("网络未连接");
		helper.sleep(2000);
		/*
		 * 上拉加载数据 显示loading图标后提示加载失败
		 */
		helper.swipeDirectionByCommand("up", 1).isExistToast(NO_DATA);
		/*
		 * 下拉刷新 出现loading图标后提示网络网络中断
		 */
		helper.swipeDirection("down").swipeDirection("down").checkElement(pubPage.getErrorOfGetDate(), NO_DATA);
		;
		/*
		 * 提示网络中断后，恢复手机网络，点击页面重新加载 可正常加载出页面
		 */
		helper.openWiFi();
		helper.click(pubPage.getErrorOfGetDate(), NETWORK_DISCONNECTED).checkElement(pubPage.getWeekHotApps(2),
				"本周热门应用第2个");
		/*
		 * 提示网络中断后，恢复手机网络，点击页面重新加载 可正常上拉/下拉刷新页面
		 */
		helper.swipeDirection("down").sleep(3000).checkElement(pubPage.getWeekHotApps(1), "本周热门应用第1个");
		helper.swipeDirection("up").swipeDirection("up").checkElement(pubPage.getCheckAllLabel(), "查看全部");
		/*
		 * 提示网络中断后，恢复手机网络，点击页面重新加载 可正常选择任意应用下载/继续下载
		 */
		helper.swipeDirection("down").click(pubPage.getWeekHotApps(2), "本周热门应用第2个");
		helper.click(pubPage.getInstalBtn(), "安装").back();
		this.checkDownResult();
	}

	/**
	 * Tab主页（首页/游戏/软件）
	 */
	public void tabPage() {
		/*
		 * 子模块：无网络
		 */
		helper.closeWiFi();
		this.clean();		
		this.setConnectionType(NETWORK_START);
		helper.isExistClickElseSkip(pubPage.getAdFrame(), "广告");
		/*
		 * 选择任意TAB主页（首页/游戏/软件） 整页提示网络异常
		 */
		helper.checkElement(pubPage.getErrorOfGetDate(), NO_DATA);
		helper.click(gamePage.getIndex(), "游戏页").sleep(3000).checkElement(pubPage.getErrorOfGetDate(), NO_DATA);
		helper.click(softPage.getIndex(), "软件页").sleep(3000).checkElement(pubPage.getErrorOfGetDate(), NO_DATA);
		/*
		 * 首页/游戏/软件页面，上滑/下拉刷新页面 提示:没有获取到数据，请重试 页面显示网络断开了，请检查网络设置
		 */
		helper.click(mainPage.getIndex(), "首页");
		helper.swipeDirectionByCommand(UP, 1).isExistToast(NO_DATA).sleep(3000);
		helper.swipeDirectionByCommand(DOWN, 1).isExistToast(NO_DATA).sleep(3000);
		helper.click(gamePage.getIndex(), "游戏页");
		helper.swipeDirectionByCommand(UP, 1).isExistToast(NO_DATA).sleep(3000);
		helper.swipeDirectionByCommand(DOWN, 1).isExistToast(NO_DATA).sleep(3000);
		helper.click(softPage.getIndex(), "软件页");
		helper.swipeDirectionByCommand(UP, 1).isExistToast(NO_DATA).sleep(3000);
		helper.swipeDirectionByCommand(DOWN, 1).isExistToast(NO_DATA).sleep(3000);
		/*
		 * 子模块：手机处于弱网环境 上行/下行延迟分别设置为1000ms
		 */
		helper.openWiFi();
		this.clean().setConnectionType(NETWORK_DELAY);
		helper.isExistClickElseSkip(pubPage.getAdFrame(), "广告");
		/*
		 * 选择任意TAB主页（首页/游戏/软件） 查看全部页面显示正常，上滑/下拉页面数据加载正常
		 */
		helper.sleep(5000).swipeDirection(UP).click(pubPage.getCheckAllLabel(), "查看全部").sleep(10000);
		helper.checkElement(pubPage.getWeekHotAppsOfSub(1), "列表中的第1个应用").back();
		
		helper.click(gamePage.getIndex(), "游戏页").sleep(10000).swipeDirection(UP).sleep(10000);
		helper.click(pubPage.getCheckAllLabel(), "查看全部").sleep(10000);
		helper.checkElement(pubPage.getWeekHotAppsOfSub(2), "列表中的第2个应用").back();
		
		helper.click(softPage.getIndex(), "软件页").sleep(10000).click(pubPage.getCheckAllLabel(), "查看全部").sleep(10000);
		helper.checkElement(pubPage.getWeekHotAppsOfSub(1), "列表中的第1个应用").back();
		/*
		 * 首页/游戏/软件页面，上滑/下拉刷新页面 页面刷新正常
		 */
		helper.click(mainPage.getIndex(), HOME_PAGE);
		helper.swipeDirection(DOWN).swipeDirection(DOWN).sleep(10000).checkElement(pubPage.getWeekHotApps(1),
				"本周热门应用第1位");
		helper.swipeDirection(UP).sleep(10000).swipeDirection(UP).swipeDirection(UP);
		helper.checkElement(pubPage.getCheckAllLabel(), "查看全部");
		helper.click(gamePage.getIndex(), GAME_PAGE).sleep(10000);
		helper.swipeDirection(DOWN).sleep(10000).checkElement(pubPage.getWeekHotApps(1), "本周新游第1个");
		helper.swipeDirection(UP).sleep(10000).swipeDirection(UP);
		helper.checkElement(pubPage.getCheckAllLabel(), "查看全部");
		helper.click(softPage.getIndex(), SOFT_PAGE).sleep(10000);
		helper.swipeDirection(DOWN).sleep(10000).checkElement(pubPage.getWeekHotApps(1), "流行正当时第1个");
		helper.swipeDirection(UP).sleep(10000).swipeDirection(UP);
		helper.checkElement(pubPage.getCheckAllLabel(), "查看全部");
		/*
		 * 子模块： Tab主页断开手机网络
		 */
		this.clean();
		helper.closeWiFi();
		this.setConnectionType(NETWORK_START);
		helper.isExistClickElseSkip(pubPage.getAdFrame(), "广告");
		/*
		 * TAB主页（首页/游戏/软件），断开网络 整页提示网络异常
		 */
		helper.checkElement(pubPage.getErrorOfGetDate(), NETWORK_DISCONNECTED);
		helper.click(gamePage.getIndex(), GAME_PAGE).checkElement(pubPage.getErrorOfGetDate(), NETWORK_DISCONNECTED);
		helper.click(softPage.getIndex(), SOFT_PAGE).checkElement(pubPage.getErrorOfGetDate(), NETWORK_DISCONNECTED);
		/*
		 * 恢复网络，上滑/下拉刷新页面 页面刷新正常
		 */
		helper.openWiFi();
		helper.click(mainPage.getIndex(), HOME_PAGE).checkElement(pubPage.getWeekHotApps(1), "本周热门应用第1个");
		helper.click(gamePage.getIndex(), GAME_PAGE).checkElement(pubPage.getWeekHotApps(1), "本周新游第1个");
		helper.click(softPage.getIndex(), SOFT_PAGE).checkElement(pubPage.getWeekHotApps(1), "流行正当时第1个");
	}

	/**
	 * 福利
	 */
	public void homePageWelfare() {
			this.clean().setConnectionType(NETWORK_NORMAL);
			helper.isExistClickElseSkip(pubPage.getAdFrame(), "广告");
			/*
			 * 子模块：手机无网络
			 * 福利与帐户相关，这里需要先验证当前是否处于登录状态
			 */
			helper.click(centerPage.getIndex(), "个人中心");
			centerPage.login();		
			helper.click(mainPage.getIndex(), HOME_PAGE);			
			helper.closeWiFi();
			/*
			 * 进入福利
			 * 页面出现无网络提示，点击可重新加载
			 * 网络恢复后，点击重新加载可正常加载出页面，页面可正常操作
			 */
			helper.click(mainPage.getBoon(), "福利").checkElement(pubPage.getErrorOfGetDate(), NETWORK_DISCONNECTED);
			helper.click(pubPage.getErrorOfGetDate(), NETWORK_DISCONNECTED).checkElement(pubPage.getErrorOfGetDate(), NETWORK_DISCONNECTED);
			helper.openWiFi();
			helper.click(pubPage.getErrorOfGetDate(), NETWORK_DISCONNECTED).sleep(5000);
			helper.click(gamePage.getAllGameBoonBtn(), "全部游戏福利").sleep(5000);
			helper.checkElement(gamePage.getAllGameBoonList(2), "列表中的第2个应用").back();
			/*
			 * 查看全部游戏福利
			 * 页面出现无网络提示，点击可重新加载
			 * 网络恢复后，点击重新加载可正常加载出页面，页面可正常操作
			 */
			helper.closeWiFi();				
			helper.click(gamePage.getAllGameBoonBtn(), "全部游戏福利").checkElement(pubPage.getErrorOfGetDate(), NETWORK_DISCONNECTED);
			helper.openWiFi();
			helper.click(pubPage.getErrorOfGetDate(), NETWORK_DISCONNECTED).sleep(5000);
			helper.click(gamePage.getAllGameBoonList(1), "列表第1个应用").sleep(5000);
			helper.checkElement(pubPage.getReceiveOfOne(), "领取").back();
			/*
			 * 进入游戏礼包专区
			 * 页面出现无网络提示，点击可重新加载
			 * 网络恢复后，点击重新加载可正常加载出页面，页面可正常操作
			 */
			helper.closeWiFi();
			helper.click(gamePage.getAllGameBoonList(4), "列表中第4个应用").checkElement(pubPage.getErrorOfGetDate(), NETWORK_DISCONNECTED);
			helper.openWiFi();
			helper.click(pubPage.getErrorOfGetDate(), NETWORK_DISCONNECTED).sleep(5000);
			helper.click(pubPage.getReceiveOfOne(), "领取");
			helper.checkElement(gamePage.getFrame(), "提示框").back().back().back();
			/*
			 * 进入G-PASS集合页(此界面，不受无网状态影响)
			 * 页面出现无网络提示，点击可重新加载
			 * 网络恢复后，点击重新加载可正常加载出页面，页面可正常操作
			 */
			helper.closeWiFi();
			helper.click(gamePage.getGpassGame(), "GPASS特权游戏");
			helper.checkElement(gamePage.getGpassUserInfo(), "Gpass特权界面下的用户信息面板").back();
			helper.openWiFi();
			/*
			 * 进入G-PASS游戏专区
			 * 页面出现无网络提示，点击可重新加载
			 * 网络恢复后，点击重新加载可正常加载出页面，页面可正常操作
			 */
			helper.click(gamePage.getAllGameBoonBtn(), "全部游戏福利").sleep(5000).click(gamePage.getAllGameBoonList(5), "列表第5个应用");
			helper.closeWiFi();
			helper.click(gamePage.getEnterGpassArea(), "进入GPASS专区").checkElement(pubPage.getErrorOfGetDate(), NETWORK_DISCONNECTED);
			helper.openWiFi();
			helper.click(pubPage.getErrorOfGetDate(), NETWORK_DISCONNECTED).sleep(5000).checkElement(gamePage.getEnableGpass(),"启动我的GPASS特权");
			helper.click(gamePage.getEnableGpass(), "启动我的GPASS特权").back().back().back().back();
			this.checkDownResult();
			/*
			 * 子模块：手机处于弱网环境
			 * 上行/下行延迟分别设置为1000ms
			 */
			this.clean().setConnectionType(NETWORK_DELAY);
			helper.isExistClickElseSkip(pubPage.getAdFrame(), "广告");	
			/*
			 * 进入福利
			 * 1、页面可正常显示加载中
			 * 2、若加载失败，页面提示网络异常，网络恢复后，点击可重新加载
			 * 3、若加载成功，页面展示正常，可正常操作
			 */
			helper.click(mainPage.getBoon(), "福利").sleep(10000);
			/*
			 * 查看全部游戏福利
			 */
			helper.click(gamePage.getAllGameBoonBtn(), "全部游戏福利").sleep(10000);
			/*
			 * 进入游戏礼包专区，点击游戏下载
			 */
			helper.click(gamePage.getAllGameBoonList(2), "列表第2个应用").sleep(10000);
			helper.click(gamePage.getList_DownBtn(), "下载安装").back().back().back();
			this.checkDownResult();
			/*
			 * 进入心悦游戏礼包专区，点击礼包领取（领取/一键领取）
			 * 进入联运游戏礼包专区，点击礼包领取（领取/一键领取）
			 * 目前无法区分游戏类型
			 */
			helper.click(mainPage.getIndex(), HOME_PAGE).click(mainPage.getBoon(), "福利").sleep(5000);
			helper.click(gamePage.getAllGameBoonBtn(), "全部游戏福利").sleep(5000);
			helper.click(gamePage.getAllGameBoonList(5), "列表第5个").sleep(10000);
			helper.click(pubPage.getBoonOnekeyGet(),"一键领取").checkElement(gamePage.getFrame(), "提示框").back();
			helper.click(pubPage.getReceiveOfOne(), "单个领取").checkElement(gamePage.getFrame(), "提示框").back().back().back();	
			/*
			 * 进入G-PASS集合页
			 */
			helper.click(gamePage.getGpassGame(), "GPASS特权游戏");
			helper.checkElement(gamePage.getGpassUserInfo(), "Gpass特权界面下的用户信息面板").back();
			/*
			 * 查看G-PASS全部适用游戏列表
			 */
			helper.click(gamePage.getAllGameBoonBtn(), "全部游戏福利").sleep(5000);
			helper.checkElement(gamePage.getAllGameBoonList(5), "列表第5个应用");
			/*
			 * 进入G-PASS游戏专区，点击游戏下载激活
			 */
			helper.click(gamePage.getAllGameBoonList(6), "列表第6个应用").sleep(10000);
			helper.click(gamePage.getEnterGpassArea(), "进入GPASS专区").sleep(10000);
			helper.click(gamePage.getEnableGpass(), "启用我的GPASS特权");
			helper.back().back().back().back();
			this.checkDownResult();
		/*
		 * 进入G-PASS游戏专区，点击礼包领取 (由于GPASS游戏需要下载才能领取，目前不做实现，skip)
		 */
		/*
		 * 子模块：内容加载出来后手机断网
		 */
		this.setConnectionType(NETWORK_NORMAL);
		helper.click(mainPage.getIndex(), HOME_PAGE).click(mainPage.getBoon(), "福利");
		helper.closeWiFi();
		/*
		 * 在福利中心页面，手机网络断开 点击banner，页面出现网络中断提示 10s后恢复网络，可正常重新加载页面
		 */
		helper.click(gamePage.getBanner(), "福利界面Banner");
		gamePage.checkWebViewPage("网页无法打开");
		helper.openWiFi();
		helper.swipeDirection(UP);
		gamePage.checkWebViewPage("nornal");
		helper.back();
		/*
		 * 在福利中心页面，手机网络断开 点击我的游戏列表中点击进入游戏礼包专区/全部游戏福利列表/G-PASS特权专区，出现网络中断提示
		 * 10s后恢复网络，可正常重新加载页面
		 */
		helper.closeWiFi();
		helper.click(helper.findBySlideText("王者荣耀"), "王者荣耀").checkElement(pubPage.getErrorOfGetDate(),
				NETWORK_DISCONNECTED);
		helper.openWiFi();
		helper.click(pubPage.getErrorOfGetDate(), NETWORK_DISCONNECTED);
		helper.checkElement(pubPage.getBoonOnekeyGet(), "一键领取").back();
		/*
		 * 在G-PASS特权专区，手机网络中断 点击进入G-PASS游戏特权专区，出现网络中断提示 10s后恢复网络，可正常重新加载页面 (此界面不受无网状态影响)
		 */
		helper.click(gamePage.getGpassGame(), "GPASS特权专区");
		helper.closeWiFi();
		helper.click(gamePage.getCheckGPASS(), "查看GPASS").isExistToast("失败了");
		helper.checkElement(gamePage.getEnableGpass(), "启用我的GPASS特权");
	}

	/*
	 * 榜单
	 */
	public void homePageList() {
		/*
		 * 子模块：手机无网络
		 */
		this.clean();		
		this.setConnectionType(NETWORK_START);
		helper.closeWiFi();
		helper.isExistClickElseSkip(pubPage.getAdFrame(), "广告");		
		/*
		 * 进入榜单 页面出现无网络提示，点击可重新加载
		 */
		helper.click(mainPage.getList(), "榜单").checkElement(pubPage.getErrorOfGetDate(), NETWORK_DISCONNECTED);
		helper.click(pubPage.getErrorOfGetDate(), NETWORK_DISCONNECTED).checkElement(pubPage.getErrorOfGetDate(),
				NETWORK_DISCONNECTED);
		/*
		 * 网络恢复后，点击重新加载 可正常加载出页面
		 */
		helper.openWiFi();
		helper.click(pubPage.getErrorOfGetDate(), NETWORK_DISCONNECTED).checkElement(mainPage.getListIndex1(),
				"榜单第1名应用");
		/*
		 * 网络恢复后，点击重新加载 页面加载出来后可正常操作
		 */
		helper.click(mainPage.getListIndex1(), "榜单第1名应用").checkElement(pubPage.getAppInfo(), "详情");
		/*
		 * 子模块：手机处于弱网环境 上行/下行延迟分别设置为1000ms
		 */
		this.clean().setConnectionType(NETWORK_DELAY);
		helper.isExistClickElseSkip(pubPage.getAdFrame(), "广告");
		/*
		 * 进入榜单页面(用例上写的是必备页面) 显示加载loading图标
		 */
		helper.click(mainPage.getList(), "榜单").checkElement(pubPage.getLoadingIcon(), "加载中").sleep(10000);
		/*
		 * 加载成功 页面显示正常
		 * 
		 * 可正常选择任意应用下载安装
		 */
		helper.checkElement(mainPage.getListIndex1(), "榜单第1名应用");
		/*
		 * 可正常上拉/下拉刷新页面 提示：榜单界面，无法下拉，仅能上滑
		 */
		helper.swipeDirection(UP).checkElement(mainPage.getListCurrentIndex(), "列表第1个应用");
		/*
		 * 可正常选择任意应用下载安装
		 */
		helper.swipeDirection(DOWN).click(mainPage.getListIndex1(), "榜单第1名应用").sleep(10000);
		helper.click(pubPage.getInstalBtn(), "安装").back().back();
		this.checkDownResult();
		/*
		 * 子模块：榜单页内容加载出来后手机断网
		 */
		this.clean().setConnectionType(NETWORK_START);
		helper.isExistClickElseSkip(pubPage.getAdFrame(), "广告");
		/*
		 * 点击进入任意应用详情页 详情页出现loading图标，超时后出现网络中断提示
		 */
		helper.click(mainPage.getList(), "榜单").closeWiFi();
		helper.click(mainPage.getListIndex1(), "第一名应用").checkElement(pubPage.getLoadingIcon(), "加载");
		helper.checkElement(pubPage.getErrorOfGetDate(), NETWORK_DISCONNECTED).back();
		/*
		 * 点击任意应用安装 出现网络未连接提示
		 */
		helper.click(helper.findById("com.tencent.southpole.appstore:id/app_1_download"), "榜单第1名");
		helper.isExistToast("网络未连接");
		/*
		 * 上拉/下拉列表 无异常 提示：不支持下拉，仅上滑
		 */
		helper.swipeDirection(UP).checkElement(mainPage.getListCurrentIndex(), "列表第1个应用");
		helper.swipeDirection(DOWN);

		helper.openWiFi();
		/*
		 * 恢复手机网络，选择应用下载/继续下载 上拉列表可正常加载数据
		 */
		helper.swipeDirection(UP).swipeDirection(UP).checkElement(mainPage.getListCurrentIndex(), "列表显示的第1名应用");
		/*
		 * 恢复手机网络，选择应用下载/继续下载 可正常下载/继续下载应用
		 */
		helper.swipeDirection(DOWN).swipeDirection(DOWN);
		helper.click(helper.findById("com.tencent.southpole.appstore:id/app_1_download"), "榜单第1名");
		helper.back();
		this.checkDownResult();
	}

	/*
	 * 分类
	 */
	public void homeClassify() {
		this.clean().setConnectionType(NETWORK_START);
		helper.isExistClickElseSkip(pubPage.getAdFrame(), "广告");
		/*
		 * 子模块：无网络
		 */
		helper.closeWiFi();
		/*
		 * 进入分类 页面出现无网络提示，点击可重新加载
		 */
		helper.click(mainPage.getClassify(), "首页分类").checkElement(pubPage.getErrorOfGetDate(), NETWORK_DISCONNECTED);
		helper.click(pubPage.getErrorOfGetDate(), NETWORK_DISCONNECTED).checkElement(pubPage.getErrorOfGetDate(),
				NETWORK_DISCONNECTED);
		/*
		 * 网络恢复后，点击重新加载 可正常加载出页面
		 */
		helper.openWiFi();
		helper.click(pubPage.getErrorOfGetDate(), NETWORK_DISCONNECTED).checkElement(mainPage.getClassify_Soft(),
				"软件标签");
		/*
		 * 网络恢复后，点击重新加载 页面加载出来后可正常滑动页面、选择游戏下载
		 */
		helper.swipeDirection(UP).swipeDirection(UP).checkElement(pubPage.getClassify_Soft_System(), "系统");
		helper.swipeDirection(DOWN).swipeDirection(DOWN).click(pubPage.getClassify_Game(), "游戏分类");
		helper.click(pubPage.getClassifty_Game_Relax(), "休闲益智");
		helper.click(pubPage.getClassify_SubClassApp(), "列表第1个应用").click(pubPage.getInstalBtn(), "安装");
		helper.back().back().back();
		this.checkDownResult();
		/*
		 * 子模块：手机处于弱网环境 上行/下行延迟分别设置为1000ms
		 */
		this.clean().setConnectionType(NETWORK_DELAY);
		/*
		 * 进入分类页面 1、页面可正常显示加载中 2、若加载成功，页面展示正常，可正常操作
		 */
		helper.isExistClickElseSkip(pubPage.getAdFrame(), "广告");
		helper.click(mainPage.getClassify(), "首页分类").checkElement(pubPage.getLoadingIcon(), "加载");
		helper.sleep(10000);
		helper.swipeDirection(UP).swipeDirection(UP).swipeDirection(UP).checkElement(pubPage.getClassify_Soft_System(), "系统");
		helper.swipeDirection(DOWN).swipeDirection(DOWN);
		/*
		 * 点击进入任意分类列表 1、页面可正常显示加载中 2、若加载失败，页面提示网络异常，网络恢复后，点击可重新加载 3、若加载成功，页面展示正常，可正常操作
		 */
		helper.click(pubPage.getClassify_Soft_Video(), "视频").checkElement(pubPage.getLoadingIcon(), "加载");
		helper.sleep(10000);
		helper.swipeDirection(UP).swipeDirection(UP);
		helper.checkElement(pubPage.getClassify_SubClassApp(), "列表显示的第1个应用").back();
		/*
		 * 切换不同分类
		 */
		helper.click(pubPage.getClassify_Game(), "游戏分类").checkElement(pubPage.getClassifty_Game_Relax(), "休闲益智");
		/*
		 * 点击任意应用下载安装
		 */
		helper.click(pubPage.getClassifty_Game_Relax(), "休闲益智").sleep(10000);
		helper.click(pubPage.getClassify_SubClassApp(), "列表第1名应用").click(pubPage.getInstalBtn(), "安装");
		helper.back().back().back();
		this.checkDownResult();
		/*
		 * 子模块：进入分类列表后断开手机网络
		 */
		this.clean().setConnectionType(NETWORK_START);
		helper.isExistClickElseSkip(pubPage.getAdFrame(), "广告");
		helper.click(mainPage.getClassify(), "分类").click(pubPage.getClassify_Soft_Video(), "视频分类");
		helper.closeWiFi();
		/*
		 * 上拉列表继续加载 加载失败，出现网络异常提示
		 */
		helper.swipeDirection(UP).swipeDirection(UP).swipeDirection(UP);
		helper.checkElement(pubPage.getErrorOfGetDate(), NETWORK_DISCONNECTED);
		/*
		 * 点击进入任意应用详情页 应用详情加载失败，出现网络异常提示
		 */
		helper.openWiFi().click(pubPage.getErrorOfGetDate(), NETWORK_DISCONNECTED);
		helper.closeWiFi().click(pubPage.getInstall(), "安装").isExistToast("网络未连接");
		/*
		 * 恢复网络后，继续加载当前列表或切换不同列表 列表可正常展示
		 */
		helper.openWiFi().swipeDirection(UP).swipeDirection(UP);
		helper.back().click(pubPage.getClassify_Soft_Life(), "生活分类");
		helper.checkElement(pubPage.getClassify_SubClassApp(), "列表第1个应用");
		/*
		 * 恢复网络后，点击任意应用安装 可正常进入下载安装
		 */
		helper.click(pubPage.getInstall(), "安装").back().back();
		this.checkDownResult();
	}

	/*
	 * 列表（首页、游戏、软件、新游）
	 */
	public void listOfCheckAll() {
//		/*
//		 * 子模块：无网络
//		 */
//		this.clean().setConnectionType(NETWORK_NORMAL);
//		helper.isExistClickElseSkip(pubPage.getAdFrame(), "广告");
//		helper.swipeDirection(UP).closeWiFi();
//		/*
//		 * 点击查看全部进入列表页----首页 页面出现无网络提示，点击可重新加载
//		 */
//		helper.click(pubPage.getCheckAllLabel(), "查看全部").checkElement(pubPage.getErrorOfGetDate(),
//				NETWORK_DISCONNECTED);
//		helper.click(pubPage.getErrorOfGetDate(), NETWORK_DISCONNECTED).checkElement(pubPage.getErrorOfGetDate(),
//				NETWORK_DISCONNECTED);
//		/*
//		 * 网络恢复后，点击重新加载 页面加载出来后可正常滑动页面，选择游戏下载
//		 */
//		helper.openWiFi().click(pubPage.getErrorOfGetDate(), NETWORK_DISCONNECTED);
//		helper.click(pubPage.getWeekHotAppsOfSub(1), "列表第1个应用").click(pubPage.getInstalBtn(), "安装");
//		helper.back().back();
//		this.checkDownResult();
//		/*
//		 * 点击查看全部进入列表页----游戏页 页面出现无网络提示，点击可重新加载
//		 */
//		helper.click(gamePage.getIndex(), GAME_PAGE).swipeDirection(UP).closeWiFi();
//		helper.click(pubPage.getCheckAllLabel(), "查看全部").checkElement(pubPage.getErrorOfGetDate(),
//				NETWORK_DISCONNECTED);
//		helper.click(pubPage.getErrorOfGetDate(), NETWORK_DISCONNECTED).checkElement(pubPage.getErrorOfGetDate(),
//				NETWORK_DISCONNECTED);
//		/*
//		 * 网络恢复后，点击重新加载 页面加载出来后可正常滑动页面，选择游戏下载
//		 */
//		helper.openWiFi().click(pubPage.getErrorOfGetDate(), NETWORK_DISCONNECTED);
//		helper.click(pubPage.getWeekHotAppsOfSub(1), "列表第1个应用").click(pubPage.getInstalBtn(), "安装");
//		helper.back().back();
//		this.checkDownResult();
//		/*
//		 * 点击查看全部进入列表页----软件页 页面出现无网络提示，点击可重新加载
//		 */
//		helper.click(softPage.getIndex(), SOFT_PAGE).closeWiFi();
//		helper.click(pubPage.getCheckAllLabel(), "查看全部").checkElement(pubPage.getErrorOfGetDate(),
//				NETWORK_DISCONNECTED);
//		helper.click(pubPage.getErrorOfGetDate(), NETWORK_DISCONNECTED).checkElement(pubPage.getErrorOfGetDate(),
//				NETWORK_DISCONNECTED);
//		/*
//		 * 网络恢复后，点击重新加载 页面加载出来后可正常滑动页面，选择游戏下载
//		 */
//		helper.openWiFi().click(pubPage.getErrorOfGetDate(), NETWORK_DISCONNECTED);
//		helper.click(pubPage.getWeekHotAppsOfSub(2), "列表第2个应用").click(pubPage.getInstalBtn(), "安装");
//		helper.back().back();
//		this.checkDownResult();
		/*
		 * 点击查看全部进入列表页----新游页 页面出现无网络提示，点击可重新加载 提示：新游列表，没有"查看全部",待确认
		 */

		/*
		 * 子模块：手机处于弱网环境 上行/下行延迟分别设置为1000ms
		 */
		this.clean().setConnectionType(NETWORK_DELAY);
		helper.isExistClickElseSkip(pubPage.getAdFrame(), "广告");
//		logger.info("点击查看全部进入列表页----首页");
//		/*
//		 * 点击查看全部进入列表页----首页 1、页面可正常显示加载中 2、若加载失败，页面提示网络异常，网络恢复后，点击可重新加载
//		 * 3、若加载成功，页面展示正常，可正常操作
//		 */
//		helper.swipeDirection(UP).click(pubPage.getCheckAllLabel(), "查看全部").sleep(10000);
//		helper.click(pubPage.getWeekHotAppsOfSub(1), "列表第1个应用").sleep(10000).back();
//		/*
//		 * 列表内容超过1页时，上下滑动列表 1、页面可正常显示加载中 2、若加载失败，页面提示网络异常，网络恢复后，点击可重新加载
//		 * 3、若加载成功，页面展示正常，可正常操作
//		 */
//		helper.swipeDirection(UP).sleep(15000).click(pubPage.getWeekHotAppsOfSub(3), "列表第3个应用");
//		/*
//		 * 点击任意应用下载安装
//		 */
//		helper.click(pubPage.getInstalBtn(), "安装").back().back();
//		this.checkDownResult();
//		logger.info("点击查看全部进入列表页----游戏页");
//		/*
//		 * 点击查看全部进入列表页----游戏页 1、页面可正常显示加载中 2、若加载失败，页面提示网络异常，网络恢复后，点击可重新加载
//		 * 3、若加载成功，页面展示正常，可正常操作
//		 */
//		helper.click(gamePage.getIndex(), GAME_PAGE).swipeDirection(UP).sleep(10000);
//		helper.click(pubPage.getCheckAllLabel(), "查看全部").sleep(10000);
//		helper.click(pubPage.getWeekHotAppsOfSub(1), "列表第1个应用").sleep(10000).back();
//		/*
//		 * 列表内容超过1页时，上下滑动列表 1、页面可正常显示加载中 2、若加载失败，页面提示网络异常，网络恢复后，点击可重新加载
//		 * 3、若加载成功，页面展示正常，可正常操作
//		 */
//		helper.swipeDirection(UP).sleep(10000).swipeDirection(UP).click(pubPage.getWeekHotAppsOfSub(1), "列表第1个应用");
//		/*
//		 * 点击任意应用下载安装
//		 */
//		helper.click(pubPage.getInstalBtn(), "安装").back().back();
//		this.checkDownResult();
		logger.info("点击查看全部进入列表页----软件页");
		/*
		 * 点击查看全部进入列表页----软件页 1、页面可正常显示加载中 2、若加载失败，页面提示网络异常，网络恢复后，点击可重新加载
		 * 3、若加载成功，页面展示正常，可正常操作
		 */
		helper.click(softPage.getIndex(), SOFT_PAGE).sleep(10000);
		helper.click(pubPage.getCheckAllLabel(), "查看全部").sleep(10000);
		helper.click(pubPage.getWeekHotAppsOfSub(1), "列表第1个应用").sleep(10000).back();
		/*
		 * 列表内容超过1页时，上下滑动列表 1、页面可正常显示加载中 2、若加载失败，页面提示网络异常，网络恢复后，点击可重新加载
		 * 3、若加载成功，页面展示正常，可正常操作
		 */
		helper.swipeDirection(UP).sleep(10000).swipeDirection(UP).sleep(15000)
		.click(pubPage.getWeekHotAppsOfSub(2), "列表第2个应用");
		/*
		 * 点击任意应用下载安装
		 */
		helper.click(pubPage.getInstalBtn(), "安装").back().back();
		this.checkDownResult();
		/*
		 * 子模块：进入查看全部页面后断开手机网络
		 */
		logger.info("进入查看全部页面后断开手机网络----首页");
		this.clean().setConnectionType(NETWORK_START);
		helper.sleep(6000).isExistClickElseSkip(pubPage.getAdFrame(), "广告");
		helper.swipeDirection(UP).click(pubPage.getCheckAllLabel(), "查看全部").sleep(5000);
		helper.closeWiFi();
		/*
		 * 列表内容不超过1页 上下滑动页面无异常
		 */
		helper.swipeDirection(UP).checkElement(pubPage.getWeekHotAppsOfSub(1), "列表第1个应用");
		/*
		 * 列表内容超过1页，下拉列表继续加载 加载失败，出现网络异常提示
		 */
		helper.swipeDirectionByCommand(UP, 1).isExistToast(NO_DATA);
		/*
		 * 点击进入任意应用详情页 应用详情加载失败，出现网络异常提示
		 */
		helper.click(pubPage.getWeekHotAppsOfSub(1), "列表第1个应用");
		helper.checkElement(pubPage.getErrorOfGetDate(), NETWORK_DISCONNECTED).back();
		/*
		 * 点击任意应用安装 安装失败，出现网络异常提示
		 */
		helper.click(pubPage.getInstall(), "安装").isExistToast(NETWORK_NO_CONNECT);
		/*
		 * 恢复网络后，下拉加载超过1页的 列表可正常展示
		 */
		helper.openWiFi().swipeDirectionByCommand(UP, 1).isNoExistToast(NETWORK_NO_CONNECT);
		helper.checkElement(pubPage.getWeekHotAppsOfSub(1), "列表第1个应用");
		/*
		 * 恢复网络后，点击任意应用安装 可正常进入下载安装
		 */
		helper.click(pubPage.getInstall(), "安装");
		this.checkDownResult();
		logger.info("进入查看全部页面后断开手机网络----游戏页");
		helper.click(gamePage.getIndex(), GAME_PAGE).swipeDirection(UP);
		helper.click(pubPage.getCheckAllLabel(), "查看全部").closeWiFi();
		/*
		 * 列表内容不超过1页 上下滑动页面无异常
		 */
		helper.swipeDirection(UP).checkElement(pubPage.getWeekHotAppsOfSub(1), "列表第1个应用");
		/*
		 * 列表内容超过1页，下拉列表继续加载 加载失败，出现网络异常提示
		 */
		helper.swipeDirectionByCommand(UP, 1).isExistToast(NO_DATA);
		/*
		 * 点击进入任意应用详情页 应用详情加载失败，出现网络异常提示
		 */
		helper.click(pubPage.getWeekHotAppsOfSub(1), "列表第1个应用");
		helper.checkElement(pubPage.getErrorOfGetDate(), NETWORK_DISCONNECTED).back();
		/*
		 * 点击任意应用安装 安装失败，出现网络异常提示
		 */
		helper.click(pubPage.getInstall(), "安装").isExistToast(NETWORK_NO_CONNECT);
		/*
		 * 恢复网络后，下拉加载超过1页的 列表可正常展示
		 */
		helper.openWiFi().swipeDirectionByCommand(UP, 1).isNoExistToast(NETWORK_NO_CONNECT);
		helper.checkElement(pubPage.getWeekHotAppsOfSub(1), "列表第1个应用");
		/*
		 * 恢复网络后，点击任意应用安装 可正常进入下载安装
		 */
		helper.click(pubPage.getInstall(), "安装");
		this.checkDownResult();
		logger.info("进入查看全部页面后断开手机网络----软件页");
		helper.click(softPage.getIndex(), SOFT_PAGE);
		helper.click(pubPage.getCheckAllLabel(), "查看全部").closeWiFi();
		/*
		 * 列表内容不超过1页 上下滑动页面无异常
		 */
		helper.swipeDirection(UP).checkElement(pubPage.getWeekHotAppsOfSub(1), "列表第1个应用");
		/*
		 * 列表内容超过1页，下拉列表继续加载 加载失败，出现网络异常提示
		 */
		helper.swipeDirectionByCommand(UP, 1).isExistToast(NO_DATA);
		/*
		 * 点击进入任意应用详情页 应用详情加载失败，出现网络异常提示
		 */
		helper.click(pubPage.getWeekHotAppsOfSub(1), "列表第1个应用");
		helper.checkElement(pubPage.getErrorOfGetDate(), NETWORK_DISCONNECTED).back();
		/*
		 * 点击任意应用安装 安装失败，出现网络异常提示
		 */
		helper.click(pubPage.getInstall(), "安装").isExistToast(NETWORK_NO_CONNECT);
		/*
		 * 恢复网络后，下拉加载超过1页的 列表可正常展示
		 */
		helper.openWiFi().swipeDirectionByCommand(UP, 1).isNoExistToast(NETWORK_NO_CONNECT);
		helper.checkElement(pubPage.getWeekHotAppsOfSub(1), "列表第1个应用");
		/*
		 * 恢复网络后，点击任意应用安装 可正常进入下载安装
		 */
		helper.click(pubPage.getInstall(), "安装");
		this.checkDownResult();
	}

	/**
	 * 新游 目前点击过的预约无法取消,为保证脚本后续运行,此处不再预约游戏,其它操作与用例一致
	 */
	public void newGame() {
		/*
		 * 子模块：无网络
		 */
		this.clean().setConnectionType(NETWORK_START);
		helper.isExistClickElseSkip(pubPage.getAdFrame(), "广告");
		/*
		 * 进入新游 页面出现无网络提示，点击可重新加载
		 */
		helper.click(gamePage.getIndex(), "游戏页");
		helper.closeWiFi().click(gamePage.getNewGame(), "新游标签").isExistToast(NO_DATA);
		helper.click(pubPage.getErrorOfGetDate(), NETWORK_DISCONNECTED).isExistToast(NO_DATA);
		/*
		 * 网络恢复后，点击重新加载 可正常加载出页面 页面加载出来后可正常滑动页面、选择游戏下载 提示：新游界面只有预约，无下载按钮
		 */
		helper.openWiFi().click(pubPage.getErrorOfGetDate(), NETWORK_DISCONNECTED);
		helper.checkElement(gamePage.getNewGameListOfOne(), "列表第1个游戏").back();
		/*
		 * 子模块：手机处于弱网环境 上行/下行延迟分别设置为1000ms
		 */
		this.clean().setConnectionType(NETWORK_DELAY);
		helper.isExistClickElseSkip(pubPage.getAdFrame(), "广告");
		helper.click(gamePage.getIndex(), GAME_PAGE).sleep(10000);
		helper.click(gamePage.getNewGame(), "新游标签").checkElement(pubPage.getLoadingIcon(), "加载").sleep(10000);
		helper.checkElement(gamePage.getNewGameListOfOne(), "列表第1个游戏");
		/*
		 * 上下滑动列表加载
		 */
		helper.swipeDirection(UP).sleep(5000).checkElement(gamePage.getNewGameListOfOne(), "列表第1个游戏");
		/*
		 * 点击任意新游进入详情页面
		 */
		helper.click(gamePage.getNewGameListOfOne(), "列表第1个游戏");
		helper.checkElement(gamePage.getTopGameInfo(), "游戏详情").back();
		/**
		 * 子模块：进入新游页面后断开手机网络
		 */
		this.clean().setConnectionType(NETWORK_START);
		helper.isExistClickElseSkip(pubPage.getAdFrame(), "广告");
		helper.click(gamePage.getIndex(), GAME_PAGE);
		helper.click(gamePage.getNewGame(), "新游").sleep(2000).closeWiFi();
		/*
		 * 新游页面上拉/下载刷新页面 出现加载失败提示
		 */
		helper.swipeDirectionByCommand(UP, 13).isExistToast(NO_DATA);
		/*
		 * 点击任意应用下载安装 出现网络中断提示
		 */
		helper.click(pubPage.getInstall(), "预约/安装").isExistToast("未连接");
		/*
		 * 点击任意新游进入详情页面 应用详情加载失败，出现网络异常提示
		 */
		helper.click(gamePage.getNewGameListOfOne(), "列表第1个").checkElement(pubPage.getErrorOfGetDate(),
				NETWORK_DISCONNECTED);
		helper.back();
		/*
		 * 恢复网络后，上拉/下拉加载新游页面 可正常加载出新游列表
		 */
		helper.openWiFi().swipeDirectionByCommand(UP, 5).isNoExistToast("未连接");
		helper.checkElement(gamePage.getNewGameListOfOne(), "列表第1个游戏");
		/*
		 * 恢复网络后，选择应用下载/继续下载 可正常进入下载状态
		 * 提示：新游界面的预约与下载一致，由于无法取消预约，且保存后续脚本可正常运行，此处不再做下载和预约的操作，skip
		 */
	}

	/*
	 * 搜索
	 */
	public void search() {
		/*
		 * 子模块：无网络
		 */
		this.clean().setConnectionType(NETWORK_START);
		helper.isExistClickElseSkip(pubPage.getAdFrame(), "广告");
		/*
		 * 进入搜索页面 页面出现无网络提示，点击可重新加载
		 */
		helper.closeWiFi().click(mainPage.getSearchContext(), "搜索框");
		helper.hideKeyBoard();
		helper.checkElement(pubPage.getErrorOfGetDate(), NETWORK_DISCONNECTED);
		helper.click(pubPage.getErrorOfGetDate(), NETWORK_DISCONNECTED).checkElement(pubPage.getErrorOfGetDate(),
				NETWORK_DISCONNECTED);
		/*
		 * 网络恢复后，点击重新加载 可正常加载出页面 页面加载出来后可正常操作
		 */
		helper.openWiFi().click(pubPage.getErrorOfGetDate(), NETWORK_DISCONNECTED);
		helper.checkElement(mainPage.getHotSearchLabel(), "热搜标签");
		helper.click(mainPage.getSearchContext(), "搜索框").send(mainPage.getSearchContext(), "腾讯视频");
		helper.hideKeyBoard();
		helper.click(mainPage.getSearchBtn(), "开始搜索").checkElement(mainPage.getSerachResultFirstApp(), "搜索结果第1个应用");
		/*
		 * 子模块：手机处于弱网环境 上行/下行延迟分别设置为1000ms
		 */
		this.clean().setConnectionType(NETWORK_DELAY);
		helper.isExistClickElseSkip(pubPage.getAdFrame(), "广告");
		/*
		 * 进入搜索页面 页面显示正在加载及loading标识
		 */
		helper.click(mainPage.getSearchContext(), "搜索框");
		helper.hideKeyBoard();
		helper.checkElement(pubPage.getLoadingIcon(), "加载");
		helper.send(mainPage.getSearchContext(), "腾讯新闻");
		/*
		 * 输入搜索内容，点击搜索
		 */
		helper.click(mainPage.getSearchBtn(), "开始搜索").sleep(15000);
		helper.checkElement(mainPage.getSerachResultFirstApp(), "搜索结果第一个应用");
		/*
		 * 输入搜索内容 点击搜索结果
		 */
		helper.click(mainPage.getSerachResultFirstApp(), "搜索结果第一个应用").sleep(10000);
		helper.checkElement(pubPage.getAppInfo(), "检查详情标签").back();
		/*
		 * 点击搜索结果下载/继续下载按钮
		 */
		helper.click(pubPage.getSearchResulInstallBtn(), "安装").back();
		this.checkDownResult();
		/*
		 * 子模块：进入搜索页面后网络中断
		 */
		this.clean().setConnectionType(NETWORK_START);
		helper.isExistClickElseSkip(pubPage.getAdFrame(), "广告");
		helper.click(mainPage.getSearchContext(), "搜索框").sleep(2000);
		helper.closeWiFi();
		/*
		 * 输入搜索内容，点击搜索 出现网络未连接提示 网络恢复后点击重新加载，可正常搜索出结果
		 */
		helper.send(mainPage.getSearchContext(), "腾讯新闻").hideKeyBoard();
		helper.click(mainPage.getSearchBtn(), "开始搜索").sleep(10000);
		helper.checkElement(pubPage.getErrorOfGetDate(), NETWORK_DISCONNECTED);
		helper.openWiFi().click(pubPage.getErrorOfGetDate(), NETWORK_DISCONNECTED);
		helper.sleep(10000).checkElement(mainPage.getSerachResultFirstApp(), "搜索结果第1个应用");
		/*
		 * 点击搜索结果 出现网络未连接提示 网络恢复后点击重新加载，可正常加载出页面
		 */
		helper.closeWiFi().click(mainPage.getSerachResultFirstApp(), "搜索结果第1个应用");
		helper.checkElement(pubPage.getErrorOfGetDate(), NETWORK_DISCONNECTED);
		helper.openWiFi().click(pubPage.getErrorOfGetDate(), NETWORK_DISCONNECTED);
		helper.checkElement(pubPage.getAppInfo(), "详情标签").back();
		/*
		 * 点击搜索结果下载/继续下载按钮 出现网络未连接提示 网络恢复后，点击下载/继续下载可正常进入下载
		 */
		helper.closeWiFi().click(pubPage.getSearchResulInstallBtn(), "安装").isExistToast(NETWORK_NO_CONNECT);
		helper.openWiFi().click(pubPage.getSearchResulInstallBtn(), "安装").back();
		this.checkDownResult();

	}

	/*
	 * 应用详情
	 */
	public void appInformation() {
		this.clean().setConnectionType(NETWORK_START);
		helper.isExistClickElseSkip(pubPage.getAdFrame(), "广告");
		helper.closeWiFi();
		/*
		 * 进入应用详情
		 * 页面出现无网络提示，点击可重新加载
		 */
		helper.click(pubPage.getWeekHotApps(1), "本周热门应用第一个");
		helper.checkElement(pubPage.getErrorOfGetDate(), NETWORK_DISCONNECTED);
		helper.click(pubPage.getErrorOfGetDate(), NO_DATA).checkElement(pubPage.getErrorOfGetDate(), NETWORK_DISCONNECTED);
		/*
		 * 网络恢复后，点击重新加载
		 * 可正常加载出页面
		 * 页面加载出来后可正常操作
		 */
		helper.openWiFi().click(pubPage.getErrorOfGetDate(), NETWORK_DISCONNECTED);
		helper.checkElement(pubPage.getAppInfo(), "详情标签");
		helper.click(pubPage.getAppComment(), "评论");
		/*
		 * 子模块：手机处于弱网环境
		 * 上行/下行延迟分别设置为1000ms
		 */
		this.clean().setConnectionType(NETWORK_DELAY);
		helper.isExistClickElseSkip(pubPage.getAdFrame(), "广告");
		/*
		 * 点击进入应用
		 * 显示加载过程
		 */
		helper.sleep(10000).click(pubPage.getWeekHotApps(2), "本周热门应用第二个").checkElement(pubPage.getLoadingIcon(), "加载");
		helper.sleep(10000).checkElement(pubPage.getAppInfo(), "详情标签");
		/*
		 * 进入详情页面时，图片未加载出
		 * 提示：弱网下，可加载出图片，skip
		 * 进入详情页面时，加载出来部分内容
		 * 提示：弱网下，可加载出图片，skip
		 * “详情”加载出来部分内容后，退出详情页面，重新进入
		 * 提示：弱网下，可加载出详情信息，skip
		 */
		/*
		 * 1、“详情”加载出来部分内容后，退出详情页面
		 * 2、切换到正常网络后重新进入
		 * 提示：可正常加载，skip
		 */
		/*
		 * 进入详情页面时，页面加载失败
		 * 提示：可正常加载，skip
		 * 页面加载失败后，点击重新加载
		 * 提示：可正常加载，skip
		 * 页面加载失败后，切换正常网络，选择重新加载
		 * 提示：可正常加载，skip
		 * 1、“详情”加载出来部分内容后，退出详情页面
		 * 2、切换到正常网络后重新进入
		 * 提示：可正常加载，skip
		 */
		/*
		 * 切换到评论tab
		 * 显示加载过程
		 * 提示：不显示加载过程
		 */
		helper.click(pubPage.getAppComment(), "评论").checkElement(pubPage.getAppScope(), "评分");
		/*
		 * “评论”加载出来部分内容后，退出详情页面，重新进入
		 */
		helper.back().click(pubPage.getWeekHotAppsOfSub(2), "本周热门应用第2个").sleep(10000);
		/*
		 * 进入评论页面时
		 * 页面加载失败，正常提示
		 * 提示：评论可正常加载，skip
		 * 评论加载失败后，切换正常网络，选择重新加载
		 * 提示：评论可正常加载，skip
		 */
		/*
		 * 子模块：进入详情页后手机网络中断
		 */
		this.clean().setConnectionType(NETWORK_START);
		helper.isExistClickElseSkip(pubPage.getAdFrame(), "广告");
		helper.click(pubPage.getWeekHotApps(1), "本周热门应用第1个").closeWiFi();
		/*
		 * 可正常查看已加载出的详情内容
		 */
		helper.checkElement(pubPage.getAppState(), "应用介绍");
		/*
		 * 切换到评论tab
		 * 可正常显示已加载出的评论列表
		 */
		helper.click(pubPage.getAppComment(), "评论").checkElement(pubPage.getAppScope(), "评分");
		/*
		 * 点击应用安装
		 * 出现网络中断提示
		 */
		helper.click(pubPage.getInstalBtn(), "安装").isExistToast(NETWORK_NO_CONNECT);
		/*
		 * 网络恢复后，点击应用安装
		 * 可正常进入下载状态
		 */
		helper.openWiFi().click(pubPage.getInstalBtn(), "安装").back();
		this.checkDownResult();
	}


	/**
	 * 安装
	 */
	public void installApp() {
		/*
		 * 子模块：无网络
		 */
		this.clean().setConnectionType(NETWORK_START);
		helper.isExistClickElseSkip(pubPage.getAdFrame(), "广告");
		helper.click(pubPage.getWeekHotApps(1), "本周热门应用第1个");
		helper.closeWiFi();
		/*
		 * 点击安装按钮，提示网络未连接，请重试
		 */
		helper.click(pubPage.getInstalBtn(), "安装").isExistToast(NETWORK_NO_CONNECT);
		helper.openWiFi();		
		/*
		 * 子模块：手机处于弱网环境
		 * 上行/下行延迟分别设置为1000ms
		 */
		this.setConnectionType(NETWORK_DELAY);
		/*
		 * 选择任意应用安装
		 * 
		 */
		helper.click(pubPage.getInstalBtn(), "安装").back();
		this.checkDownResultExist();
		
		/*
		 * 选择任意应用安装
		 * 点击暂停，状态显示继续
		 * 提示：此处使用应用宝
		 */	
		helper.click(mainPage.getIndex(), HOME_PAGE);		
		helper.uninstall("com.tencent.android.qqdownloader");
		helper.click(mainPage.getSearchContext(), "搜索框").send(mainPage.getSearchContext(), "应用宝");
		helper.hideKeyBoard();
		helper.click(mainPage.getSearchBtn(), "开始搜索").sleep(20000);
		helper.checkElement(mainPage.getSerachResultFirstApp(), "搜索结果第一个应用");
		helper.click(mainPage.getSerachResultFirstApp(), "搜索结果第一个应用").sleep(10000);
		helper.click(pubPage.getInstalBtn(), "安装");
		this.checkPauseToContinue();
		/*
		 * 点击继续，恢复下载，显示下载进度
		 */
		this.checkContinueToDownload();
		/*
		 * 下载进度100%，查看安装可正常安装应用
		 * 自动安装应用后，点击开启可正常开启应用
		 */
		helper.checkAppIsInstall("com.tencent.android.qqdownloader");
		helper.click(pubPage.getInstalBtn(), "打开").sleep(2000).checkActivity();
		helper.back().back();
		/*
		 * 应用下载过程中删除应用
		 * 正常删除无异常
		 */		
		helper.uninstall("com.tencent.android.qqdownloader");
		helper.click(mainPage.getSearchContext(), "搜索框").send(mainPage.getSearchContext(), "应用宝");
		helper.hideKeyBoard();
		helper.click(mainPage.getSearchBtn(), "开始搜索").sleep(20000);
		helper.checkElement(mainPage.getSerachResultFirstApp(), "搜索结果第一个应用");
		helper.click(mainPage.getSerachResultFirstApp(), "搜索结果第一个应用").sleep(10000);
		helper.click(pubPage.getInstalBtn(), "安装").back().back();
		this.downloadingDelApp();
		helper.checkElement(centerPage.getUserName(), "用户名");
		/*
		 * 应用暂停下载后删除应用
		 * 正常删除无异常
		 */
		helper.click(mainPage.getIndex(), "首页");
		helper.uninstall("com.tencent.android.qqdownloader");
		helper.click(mainPage.getSearchContext(), "搜索框").send(mainPage.getSearchContext(), "应用宝");
		helper.hideKeyBoard();
		helper.click(mainPage.getSearchBtn(), "开始搜索").sleep(20000);
		helper.checkElement(mainPage.getSerachResultFirstApp(), "搜索结果第一个应用");
		helper.click(mainPage.getSerachResultFirstApp(), "搜索结果第一个应用").sleep(10000);
		helper.click(pubPage.getInstalBtn(), "安装");
		this.checkPauseToContinue();
		helper.back().back();
		this.downloadingDelApp();
		helper.checkElement(centerPage.getUserName(), "用户名");
		/*
		 * 下载单个应用/同时下载多个应用
		 * 单个应用进度显示正常，多个应用，正在安装列表两个应用显示下载中，其他应用显示等待中
		 * 提示：自动化较难实现此场景(拿不到按钮状态)，先skip
		 */
		/*
		 * 更新列表选择可更新应用下载安装
		 * 安装列表显示正常
		 * 说明:此处使用低版本抖音
		 */		
		helper.uninstall("com.ss.android.ugc.aweme");		
		centerPage.installDouYin();		
		helper.checkAppIsInstall("com.ss.android.ugc.aweme");		
		helper.click(centerPage.getAppUpdate(), "应用更新").sleep(10000);
		helper.click(centerPage.getAppUpdateListDouYin(), "抖音").sleep(10000);
		helper.click(pubPage.getInstalBtn(), "更新").back().back();
		this.checkDownResult();		
		/*
		 * 应用卸载列表，卸载应用后，可重新下载安装无异常
		 */
		helper.click(centerPage.getAppRemove(), "卸载").click(helper.findBySlideText("应用宝"), "应用宝");
		helper.click(centerPage.getOneKeyUninstall(), "一键卸载").click(centerPage.getConfirmBtn(), "确定").back();
		helper.click(mainPage.getIndex(), "首页");
		helper.click(mainPage.getSearchContext(), "搜索框").send(mainPage.getSearchContext(), "应用宝");
		helper.hideKeyBoard();
		helper.click(mainPage.getSearchBtn(), "开始搜索").sleep(20000);
		helper.checkElement(mainPage.getSerachResultFirstApp(), "搜索结果第一个应用");
		helper.click(mainPage.getSerachResultFirstApp(), "搜索结果第一个应用").sleep(10000);
		helper.click(pubPage.getInstalBtn(), "安装");
		helper.checkAppIsInstall("com.tencent.android.qqdownloader");
		helper.back().back();
		/*
		 * 桌面卸载应用，重新安装应用可正常安装
		 * 说明：此处使用adb uninstall代替桌面卸载（不操作桌面应用），减少自动化失败风险
		 */
		helper.uninstall("com.tencent.android.qqdownloader");
		helper.click(mainPage.getSearchContext(), "搜索框").send(mainPage.getSearchContext(), "应用宝");
		helper.hideKeyBoard();
		helper.click(mainPage.getSearchBtn(), "开始搜索").sleep(20000);
		helper.checkElement(mainPage.getSerachResultFirstApp(), "搜索结果第一个应用");
		helper.click(mainPage.getSerachResultFirstApp(), "搜索结果第一个应用").sleep(10000);
		helper.click(pubPage.getInstalBtn(), "安装");
		helper.checkAppIsInstall("com.tencent.android.qqdownloader");
		/**
		 * 子模块：下载过程中手机断网
		 */
		this.clean().setConnectionType(NETWORK_NORMAL);
		helper.isExistClickElseSkip(pubPage.getAdFrame(), "广告");
		helper.uninstall("com.tencent.android.qqdownloader");
		helper.click(mainPage.getSearchContext(), "搜索框").send(mainPage.getSearchContext(), "应用宝");
		helper.hideKeyBoard();
		helper.click(mainPage.getSearchBtn(), "开始搜索").sleep(20000);
		helper.checkElement(mainPage.getSerachResultFirstApp(), "搜索结果第一个应用");
		helper.click(mainPage.getSerachResultFirstApp(), "搜索结果第一个应用").sleep(10000);
		helper.click(pubPage.getInstalBtn(), "安装").back().back();
		helper.click(centerPage.getIndex(), "个人中心");
		helper.closeWiFi().isExistToast(NETWORK_NO_CONNECT);
		/*
		 * 手机断网不影响正在安装的应用，应用安装无异常
		 * 说明：此场景很难使用自动化实现，skip
		 */
		/*
		 * 恢复网络后，正在安装列表中正在下载应用恢复下载，等待下载的应用仍然显示等待
		 * 说明：多个应用下载时，等待状态用例，后续实现，先skip
		 */
		helper.openWiFi();
		this.checkDownResult();
	}
	
	/**
	 * 福利礼包的领取
	 */
	public void welfareGift() {
		this.clean().setConnectionType(NETWORK_NORMAL);
		helper.sleep(6000).isExistClickElseSkip(pubPage.getAdFrame(), "广告");
		helper.click(centerPage.getIndex(), "个人中心");
		centerPage.logout();
		helper.click(mainPage.getIndex(), "首页").click(mainPage.getBoon(), "福利");
		this.setConnectionType(NETWORK_CLOSE);
		// 测试前需要安装游戏 否则将不会出现此元素
		helper.sleep(3000);
		helper.click(pubPage.getBoonOnekeyGet(), "一键领取");
		helper.click(centerPage.getSystemLogin(), "快捷登陆按钮").sleep(10000);
		helper.checkElement(pubPage.getErrorOfGetDate(), NO_DATA).back();
		this.setConnectionType(NETWORK_DELAY);
		helper.click(centerPage.getIndex(), "个人中心");
		centerPage.logout();
		helper.click(mainPage.getIndex(), "首页").click(mainPage.getBoon(), "福利").sleep(15000);
		helper.click(pubPage.getBoonOnekeyGet(), "一键领取").sleep(10000);
		helper.click(centerPage.getSystemLogin(), "快捷登录").sleep(10000);
		helper.click(helper.findBySlideText("王者荣耀"), "王者荣耀").sleep(15000);
		helper.click(pubPage.getReceiveOfOne(), "单个领取").sleep(10000);
		helper.click(pubPage.getAuthor(), "确定领取").sleep(5000);
		helper.checkElement(helper.findByUiautomatorText("领取失败"), "领取失败");
	}

	/**
	 * 我的
	 */
	public void center() {
		this.clean().setConnectionType(NETWORK_NORMAL);
		helper.uninstall("com.sina.weibo");
		centerPage.installWeiBo();
		helper.sleep(6000).isExistClickElseSkip(pubPage.getAdFrame(), "广告");
		helper.click(centerPage.getIndex(), "个人中心");
		centerPage.login();
		helper.click(centerPage.getAppUpdate(), "应用更新").back();
		this.setConnectionType(NETWORK_CLOSE);
		helper.click(centerPage.getAppUpdate(), "应用更新").checkElement(centerPage.getWaitUpdate(), "等待更新");
		helper.back().click(centerPage.getAppRemove(), "应用卸载");
		centerPage.uninstall("com.sina.weibo", "微博");
		helper.click(centerPage.getMyOrder(), "我的预约").sleep(15000);
		helper.checkElement(pubPage.getErrorOfGetDate(), NO_DATA);
		this.setConnectionType(NETWORK_NORMAL);
		helper.click(pubPage.getErrorOfGetDate(), "没有获取到元素");
		helper.checkElement(centerPage.getComingsoon(), "即将上线").back();
		this.setConnectionType(NETWORK_CLOSE);
		helper.click(centerPage.getMyGiftBag(), "我的礼包").sleep(15000);
		helper.checkElement(pubPage.getErrorOfGetDate(), NO_DATA);
		this.setConnectionType(NETWORK_NORMAL);
		helper.click(pubPage.getErrorOfGetDate(), "没有获取到元素").checkElement(centerPage.getGiftList(), "已领取礼包界面");
		helper.swipeDirection("up").back();
		centerPage.installWeiBo();
		this.setConnectionType(NETWORK_DELAY);
		helper.click(centerPage.getAppUpdate(), "应用更新").checkElement(centerPage.getWaitUpdate(), "等待更新").back();
		helper.click(centerPage.getAppRemove(), "应用卸载");
		helper.checkElement(centerPage.getAllAppList(), "全部应用标签").back();
		helper.click(centerPage.getMyOrder(), "我的预约").sleep(10000);
		helper.checkElement(centerPage.getComingsoon(), "即将上线").back();
		helper.click(centerPage.getMyGiftBag(), "我的礼包").sleep(10000);
		helper.checkElement(centerPage.getGiftList(), "已领取礼包界面").back();
	}
		
	

	/**
	 * 清理环境
	 */
	private WeakNetworkService clean() {
		helper.killAppStore();
		helper.killQNET();
		logger.info("结束应用市场&QNET进程");
		return this;
	}

	/**
	 * 点击应用
	 * 
	 * @param appName App名称
	 */
	private WeakNetworkService clickDeskApp(String appName) {
		helper.click(helper.findBySlideTextHorizontal(appName), appName);
		return this;
	}

	/**
	 * 仅支持输入：正常网络、延迟（延迟是自定义的，上下行延迟1000）、100%丢包
	 * 
	 * @param netWorkValue
	 */
	private void setConnectionType(String netWorkValue) {
		helper.pressKeyCode(3);
		String st = "START";
		if (st.equals(netWorkValue)) {
			logger.info("打开应用市场");
		} else {
			helper.getAndroidDriver().startActivity(getQnetActivity());
			WebElement testButton = helper.findById("com.tencent.qnet:id/buttonTest");
			helper.click(helper.findByUiautomatorText(netWorkValue), netWorkValue);
			if (!testButton.getText().contains("结束")) {
				helper.click(testButton, "开启" + netWorkValue);
				return;
			} else {
				logger.info("网络状态:" + netWorkValue);
			}
		}
		helper.pressKeyCode(3);
		helper.pressKeyCode(3);
		clickDeskApp("应用市场");
	}

	private Activity getQnetActivity() {
		Activity activity = new Activity("com.tencent.qnet", "com.tencent.qnet.ui.login.LoginActivity");
		activity.setAppWaitActivity("com.tencent.qnet.ui.MainActivity");
		activity.setAppWaitPackage("com.tencent.qnet");
		return activity;
	}

	// 清量应用商店数据
	private WeakNetworkService clearAppSroreData() {
		String cmd = "adb shell pm clear com.tencent.southpole.appstore";
		RuntimeUtil.execForStr(cmd);
		return this;
	}

	/**
	 * 检查下载结果
	 */
	private void checkDownResult() {
		helper.openNotifications();
		String str = "应用正在下载";
		if (helper.getPageSource().contains(str)) {
			helper.swipeDirection("up");
			logger.info("应用正在下载");
			helper.closeWiFi();
			helper.click(centerPage.getIndex(), "个人中心");
			helper.isExistClickElseSkip(centerPage.getDelBtn(), "删除").isExistClickElseSkip(centerPage.getConfirmBtn(),
					"确定删除");
			helper.openWiFi();
		} else {
			throw new RuntimeException("未能触发下载");
		}
	}
	/**
	 * 检查下载结果,但不删除应用
	 */
	private void checkDownResultExist() {
		helper.openNotifications();
		String str = "应用正在下载";
		if (helper.getPageSource().contains(str)) {
			helper.swipeDirection("up");
			logger.info("应用正在下载");
			helper.closeWiFi().click(centerPage.getIndex(), "个人中心").click(centerPage.getDelBtn(), "删除");
			helper.openWiFi();
		} else {
			throw new RuntimeException("未能触发下载");
		}
	}

	/**
	 * 检查完成 停留在个人中心界面
	 */
	private void checkPauseToContinue() {
		helper.openNotifications();
		String str = "应用正在下载";
		if (helper.getPageSource().contains(str)) {
			helper.swipeDirection("up");						
			helper.tap(400, 2255);
		}
		helper.openNotifications();
		if (helper.getPageSource().contains(str)) {
			helper.swipeDirection("up");
			throw new RuntimeException("应用没有处于继续状态");
		} else {
			helper.swipeDirection("up");
			logger.info("应用处于继续状态");
		}
	}

	/**
	 * 继续状态，点击后，恢复下载
	 */
	private void checkContinueToDownload() {
		helper.tap(400, 2255);		
		helper.sleep(1000).openNotifications();
		String str = "应用正在下载";
		if (helper.getPageSource().contains(str)) {
			helper.swipeDirection("up");
			logger.info("应用正在下载");			
		}
	}
	private void downloadingDelApp() {
		helper.click(centerPage.getIndex(), "个人中心");
		//点击删除
		helper.tapByCommand(913, 944);	
		helper.snapshot("点击");
		helper.sleep(2000);
		//确认删除
		helper.tapByCommand(845, 2210);
		helper.snapshot("点击");			
	}
}
