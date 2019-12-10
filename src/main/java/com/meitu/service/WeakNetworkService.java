package com.meitu.service;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebElement;
import com.meitu.base.AbstractPage;
import com.meitu.utils.Helper;

import cn.hutool.core.util.RuntimeUtil;
import io.appium.java_client.android.Activity;
public class WeakNetworkService extends AbstractPage{
	private static final String NETWORK_CLOSE = "100%丢包";
	private static final String NETWORK_DELAY = "延迟";
	private static final String NETWORK_NORMAL = "正常网络";
	private static final Logger logger = Logger.getLogger(WeakNetworkService.class);
	public WeakNetworkService(Helper helper) {
		super(helper);		
	}
	//首次启动应用商店
	public void firstLogin() {
		this.clearAppSroreData().setConnectionType(NETWORK_CLOSE);
		helper.click(pubPage.getAuthor(), "同意").isExistClickElseSkip(pubPage.getAuthorOfSystem(),"同意存储权限");
		helper.sleep(15000).checkElement(pubPage.getErrorOfGetDate(), "没有获取到数据");
		this.setConnectionType(NETWORK_NORMAL);
		helper.swipeDirection("up");
		helper.click(hotAppsPage.getCheckAll(), "全选");
		helper.click(hotAppsPage.getQuitCheckAll(), "取消全选");
		helper.click(hotAppsPage.getCheckAll(), "全选");
		this.setConnectionType(NETWORK_CLOSE);
		helper.click(hotAppsPage.getInsatllBtn(), "安装").back();
		helper.isExistToast("下载中");
		helper.clearAppSroreData();
		this.setConnectionType(NETWORK_NORMAL);		
		helper.click(pubPage.getAuthor(), "同意").isExistClickElseSkip(pubPage.getAuthorOfSystem(),"同意存储权限");
		helper.click(hotAppsPage.getClose(), "关闭");
		
	}
	
	//非首次进入应用商店
	public void reOpenAppStore() {
		this.clean().setConnectionType(NETWORK_NORMAL);
		helper.sleep(6000).isExistClickElseSkip(pubPage.getAdFrame(), "广告");
		this.setConnectionType(NETWORK_CLOSE);
		helper.click(mainPage.getIndex(), "首页");
		helper.swipeDirection("down").sleep(3000).isExistToast("没有获取到数据");	
		helper.click(gamePage.getIndex(), "游戏页").sleep(3000);
		helper.isExistToast("没有获取到数据");		
		helper.swipeDirection("up").sleep(3000);
		helper.isExistToast("没有获取到数据");		
		helper.click(softPage.getIndex(), "软件页").sleep(3000);
		helper.isExistToast("没有获取到数据");		
		helper.swipeDirection("up").sleep(3000).isExistToast("没有获取到数据");		
		this.setConnectionType(NETWORK_NORMAL);
		helper.click(mainPage.getIndex(), "首页").swipeDirection("down");	
		mainPage.swipeHideTheBar();		
		helper.checkElement(pubPage.getWeekHotApps(1), "检查本周热门应用是否已在首页显示");		
		helper.checkElement(mainPage.getDownBtn(), "下载按钮");
		helper.click(gamePage.getIndex(), "游戏页").swipeDirection("down");
		helper.checkElement(gamePage.getTopBanner(), "游戏推荐位");
		helper.checkElement(gamePage.getDownBtn(), "下载按钮");
		helper.click(softPage.getIndex(), "软件页").swipeDirection("down");
		helper.checkElement(pubPage.getWeekHotApps(1), "流行正当时第1个应用");
		helper.checkElement(softPage.getDownBtn(), "下载按钮");
		this.setConnectionType(NETWORK_DELAY);
		helper.click(mainPage.getIndex(), "首页").swipeDirection("down").sleep(10000);
		helper.click(gamePage.getIndex(), "游戏页").swipeDirection("down").sleep(10000);
		helper.click(softPage.getIndex(), "软件页").swipeDirection("down").sleep(10000);
		this.setConnectionType(NETWORK_NORMAL);
		helper.click(mainPage.getIndex(), "首页").swipeDirection("up").sleep(3000);
		helper.click(gamePage.getIndex(), "游戏页").swipeDirection("up").sleep(2000);
		helper.click(softPage.getIndex(), "软件页").swipeDirection("up").sleep(2000);
		this.setConnectionType(NETWORK_CLOSE);
		helper.click(mainPage.getIndex(), "首页").swipeDirection("down");
		helper.click(pubPage.getWeekHotApps(1), "本周热门应用：第1个").sleep(15000);
		helper.checkElement(pubPage.getErrorOfGetDate(), "没有获取到数据").back();		
		helper.click(gamePage.getIndex(), "游戏页").swipeDirection("down").swipeDirection("down");
		helper.sleep(3000).isExistToast("没有获取到数据");		
		helper.click(softPage.getIndex(), "软件页").swipeDirection("down").swipeDirection("down");
		helper.sleep(3000).isExistToast("没有获取到数据");		
	}	
	
	//新游
	public void newGame() {
		this.clean().setConnectionType(NETWORK_NORMAL);
		helper.sleep(6000).isExistClickElseSkip(pubPage.getAdFrame(), "广告");	
		helper.click(gamePage.getIndex(), "游戏页");
		this.setConnectionType(NETWORK_CLOSE);
		helper.click(gamePage.getNewGame(), "新游标签").isExistToast("没有获取到数据");			
		this.setConnectionType(NETWORK_NORMAL);		
		helper.swipeDirection("up");
		helper.checkElement(gamePage.getTopGame(), "顶部推荐游戏").back();		
		this.setConnectionType(NETWORK_DELAY);
		helper.click(gamePage.getNewGame(), "新游标签").sleep(10000);
		helper.checkElement(gamePage.getTopGame(), "顶部推荐游戏").swipeDirection("end").swipeDirection("top");
		helper.click(gamePage.getTopGame(), "顶部推荐游戏").sleep(10000);
		helper.checkElement(gamePage.getTopGameInfo(), "游戏详情").back();	
		navigation.closeWiFi();	
		helper.click(mainPage.getDownBtn(), "下载").isExistToast("网络未连接");
		helper.click(gamePage.getTopGame(), "顶部推荐游戏").sleep(5000);
		helper.checkElement(pubPage.getErrorOfGetDate(), "网络断开了,请检查网络设置");
		navigation.openWiFi();
		helper.swipeDirection("down");		
	}
	
	//福利
	public void homePageWelfare() {
		this.clean().setConnectionType(NETWORK_NORMAL);
		helper.sleep(6000).isExistClickElseSkip(pubPage.getAdFrame(), "广告");		
		helper.click(centerPage.getIndex(), "个人中心");
		centerPage.login();		
		helper.click(mainPage.getIndex(), "首页");
		this.setConnectionType(NETWORK_CLOSE);
		helper.click(mainPage.getBoon(), "福利").sleep(15000);
		helper.checkElement(pubPage.getErrorOfGetDate(), "没有获取到数据").back();		
		this.setConnectionType(NETWORK_NORMAL);
		helper.click(mainPage.getBoon(), "福利").sleep(5000);
		this.setConnectionType(NETWORK_CLOSE);	
		helper.click(gamePage.getAllGameBoonBtn(), "全部游戏福利").sleep(15000);
		helper.checkElement(pubPage.getErrorOfGetDate(), "没有获取到数据").back();
		helper.click(gamePage.getGpassGame(), "GPASS特权游戏");
		helper.checkElement(gamePage.getGpassUserInfo(), "Gpass特权界面下的用户信息面板").back();
		helper.click(gamePage.getBuyPhoneBoon(), "购机福利").sleep(15000).back();		
		this.setConnectionType(NETWORK_NORMAL);
		helper.click(gamePage.getAllGameBoonBtn(), "全部游戏福利");
		helper.checkElement(gamePage.getAllGameBoonList(1), "游戏福利列表第一个游戏").back();
		helper.click(gamePage.getGpassGame(), "GPASS特权游戏");
		helper.checkElement(helper.findById("com.tencent.southpole.appstore:id/desc"), "Gpass界面描述").back();
		helper.click(gamePage.getBuyPhoneBoon(), "购机福利").sleep(3000).back().back();
		this.setConnectionType(NETWORK_DELAY);
		helper.click(mainPage.getBoon(), "福利");
		helper.click(gamePage.getAllGameBoonBtn(), "全部游戏福利").sleep(5000);	
		helper.checkElement(gamePage.getAllGameBoonList(1), "游戏福利列表第一个游戏").back();
		navigation.closeWiFi();
		helper.click(gamePage.getBanner(), "Banner").sleep(5000);
		helper.checkElement(gamePage.getErrorPage(), "网页无法打开");
		navigation.openWiFi();
		helper.swipeDirection("down");
		helper.checkElement(gamePage.getErrorPage(), "游戏标题");
	}

	//榜单
	public void homePageList() {
		this.clean().setConnectionType(NETWORK_NORMAL);
		helper.sleep(6000).isExistClickElseSkip(pubPage.getAdFrame(), "广告");	
		this.setConnectionType(NETWORK_CLOSE);
		helper.click(mainPage.getList(), "榜单").checkElement(pubPage.getErrorOfGetDate(), "没有获取到数据");		
		this.setConnectionType(NETWORK_NORMAL);
		helper.swipeDirection("up");
		helper.click(mainPage.getListIndex1(), "第一名应用").sleep(3000).back();
		this.setConnectionType(NETWORK_CLOSE);
		helper.click(mainPage.getListIndex1(), "第一名应用").sleep(15000);
		helper.checkElement(pubPage.getErrorOfGetDate(), "没有获取到数据").back().back();		
		this.setConnectionType(NETWORK_DELAY);
		helper.click(mainPage.getList(), "榜单").sleep(15000);
		helper.click(mainPage.getListIndex1(), "第一名应用").back();
		helper.click(mainPage.getList_HotAppsList(), "榜单中的热销榜").sleep(15000);
		helper.click(mainPage.getListIndex1(), "第一名应用").back();
		helper.click(mainPage.getList_NewProduct(), "榜单中的新品榜").sleep(15000);
		helper.click(mainPage.getListIndex1(), "第一名应用").sleep(10000).back();
		helper.click(mainPage.getList_FashionList(), "榜单中的流行榜").sleep(10000);
		navigation.closeWiFi();
		helper.click(mainPage.getListIndex1(), "第一名应用").sleep(5000);
		helper.checkElement(pubPage.getErrorOfGetDate(), "网络断开了").swipeDirection("down").sleep(5000);
		helper.checkElement(pubPage.getErrorOfGetDate(), "网络断开了");
		navigation.openWiFi();
		helper.swipeDirection("down").back();
		helper.click(mainPage.getListIndex1(), "第一名应用");		
	}

	//分类
	public void homeClassify() {
		this.clean().setConnectionType(NETWORK_NORMAL);
		helper.sleep(6000).isExistClickElseSkip(pubPage.getAdFrame(), "广告");
		this.setConnectionType(NETWORK_CLOSE);
		helper.click(mainPage.getClassify(), "首页分类").sleep(15000);
		helper.checkElement(pubPage.getErrorOfGetDate(), "没有获取到数据");
		this.setConnectionType(NETWORK_NORMAL);
		helper.swipeDirection("up");
		helper.checkElement(pubPage.getClassify_Soft_Video(), "视频分类").back();
		this.setConnectionType(NETWORK_DELAY);
		helper.click(mainPage.getClassify(), "首页分类");
		helper.click(pubPage.getClassify_Soft_Video(), "视频分类").sleep(10000);
		helper.checkElement(pubPage.getClassify_SubClassApp(), "视频分类第一个应用").swipeDirection("end").swipeDirection("top");
		helper.click(pubPage.getClassify_SubClassApp(), "视频分类第一个应用");
		helper.checkElement(pubPage.getAppInfo(), "详情标签").back();	
		navigation.closeWiFi();
		helper.click(mainPage.getDownBtn(), "下载").isExistToast("网络未连接");
		navigation.openWiFi();
		helper.back();
		helper.click(pubPage.getClassify_Soft_Video(), "视频分类");		
	}

	//首页分类-任意分类下的列表
	public void homeClassifyList() {
		this.clean().setConnectionType(NETWORK_NORMAL);
		helper.sleep(6000).isExistClickElseSkip(pubPage.getAdFrame(), "广告");
		helper.click(mainPage.getClassify(), "首页分类");
		this.setConnectionType(NETWORK_CLOSE);
		helper.click(pubPage.getClassify_Soft_Video(), "视频分类").sleep(15000);
		helper.checkElement(pubPage.getErrorOfGetDate(), "没有获取到数据").back();
		this.setConnectionType(NETWORK_DELAY);
		helper.click(pubPage.getClassify_Soft_Video(), "视频分类").sleep(15000);
		helper.checkElement(pubPage.getClassify_SubClassApp(), "检查视频分类应用").swipeDirection("up");
		helper.checkElement(pubPage.getClassify_SubClassApp(), "检查视频分类应用").back();
		this.setConnectionType(NETWORK_CLOSE);
		helper.click(pubPage.getClassify_Soft_Life(), "生活分类").sleep(15000);
		helper.checkElement(pubPage.getErrorOfGetDate(), "没有获取到数据");
		this.setConnectionType(NETWORK_NORMAL);
		helper.swipeDirection("up");
		helper.checkElement(pubPage.getClassify_SubClassApp(), "检查生活分类应用").back();
		helper.click(pubPage.getClassify_Game(), "游戏分类");		
		this.setConnectionType(NETWORK_CLOSE);
		helper.click(pubPage.getClassifty_Game_Relax(), "休闲益智").sleep(15000);
		helper.checkElement(pubPage.getErrorOfGetDate(), "没有获取到数据").back();
		this.setConnectionType(NETWORK_DELAY);
		helper.click(pubPage.getClassifty_Game_Relax(), "休闲益智").sleep(15000);
		helper.checkElement(pubPage.getClassify_SubClassApp(), "检查休闲益智分类应用").swipeDirection("up");
		helper.checkElement(pubPage.getClassify_SubClassApp(), "检查休闲益智分类应用").back();
		this.setConnectionType(NETWORK_CLOSE);
		helper.click(pubPage.getClassify_Game_OnLineGame(), "网络游戏分类").sleep(15000);
		helper.checkElement(pubPage.getErrorOfGetDate(), "没有获取到数据");
		this.setConnectionType(NETWORK_NORMAL);
		helper.swipeDirection("up");
		helper.checkElement(pubPage.getClassify_SubClassApp(), "检查网络游戏分类应用");		
	}

	//福利礼包
	public void welfareGift() {
		this.clean().setConnectionType(NETWORK_NORMAL);
		helper.sleep(6000).isExistClickElseSkip(pubPage.getAdFrame(), "广告");
		helper.click(centerPage.getIndex(), "个人中心");		
		centerPage.logout();
		helper.click(mainPage.getIndex(), "首页").click(mainPage.getBoon(), "福利");		
		this.setConnectionType(NETWORK_CLOSE);
		//测试前需要安装游戏 否则将不会出现此元素
		helper.sleep(3000);
		helper.click(mainPage.getBoonOnekeyGet(), "一键领取");
		helper.click(centerPage.getSystemLogin(), "快捷登陆按钮").sleep(10000);
		helper.checkElement(pubPage.getErrorOfGetDate(), "没有获取到数据").back();
		this.setConnectionType(NETWORK_DELAY);		
		helper.click(centerPage.getIndex(), "个人中心");
		centerPage.logout();
		helper.click(mainPage.getIndex(), "首页").click(mainPage.getBoon(), "福利").sleep(15000);
		helper.click(mainPage.getBoonOnekeyGet(), "一键领取").sleep(5000);		
		helper.click(centerPage.getSystemLogin(), "快捷登录").sleep(5000);			
		helper.click(helper.findBySlideText("王者荣耀"), "王者荣耀").sleep(15000);
		helper.click(mainPage.getReceiveOfOne(), "单个领取").sleep(5000);
		helper.click(pubPage.getAuthor(), "确定领取").sleep(5000);
		helper.checkElement(helper.findByUiautomatorText("领取失败"), "领取失败");				
	}
	//搜索
	public void search() {		
		this.clean().setConnectionType(NETWORK_NORMAL);
		helper.sleep(6000).isExistClickElseSkip(pubPage.getAdFrame(), "广告");
		this.setConnectionType(NETWORK_CLOSE);
		helper.click(mainPage.getSearchContext(), "搜索框").sleep(15000);
		helper.hideKeyBoard();	
		helper.checkElement(pubPage.getErrorOfGetDateOfSearch(), "没有获取到数据");		
		this.setConnectionType(NETWORK_NORMAL);
		helper.swipeDirection("up").sleep(10000);
		helper.checkElement(mainPage.getHotSearchLabel(), "热搜标签").back();
		this.setConnectionType(NETWORK_DELAY);
		helper.click(mainPage.getSearchContext(), "搜索框").send(mainPage.getSearchContext(), "微信");
		helper.hideKeyBoard();		
		helper.click(mainPage.getSearchBtn(), "开始搜索").sleep(10000);
		helper.checkElement(mainPage.getSerachResultFirstApp(), "搜索结果第一个应用");
		helper.click(mainPage.getSerachResultFirstApp(), "搜索结果第一个应用").sleep(10000);
		helper.checkElement(pubPage.getAppInfo(), "检查详情标签").back().back();		
		//应用宝在弱网下安装 所以提前卸载应用宝
		pubPage.removeApp("com.tencent.android.qqdownloader");
		helper.click(mainPage.getSearchContext(), "搜索框").send(mainPage.getSearchContext(), "应用宝");
		helper.hideKeyBoard();		
		helper.click(mainPage.getSearchBtn(), "开始搜索").sleep(20000);
		helper.checkElement(mainPage.getSerachResultFirstApp(), "搜索结果第一个应用");
		helper.click(mainPage.getSerachResultFirstApp(), "搜索结果第一个应用").sleep(10000);
		helper.click(pubPage.getInstalBtn(), "安装").back().back();
		helper.click(centerPage.getIndex(), "个人中心");
		//等待一百秒 检查应用是否安装成功
		pubPage.checkAppIsInstall("com.tencent.android.qqdownloader");
		helper.click(mainPage.getIndex(), "首页").click(mainPage.getSearchContext(), "搜索框");
		this.setConnectionType(NETWORK_CLOSE);
		helper.send(mainPage.getSearchContext(), "微信");
		helper.click(mainPage.getSearchBtn(), "开始搜索").sleep(15000);
		helper.checkElement(pubPage.getErrorOfGetDateOfSearch(), "没有获取到数据");				
	}
	//应用详情
	public void appInformation() {
		this.clean().setConnectionType(NETWORK_NORMAL);
		helper.sleep(6000).isExistClickElseSkip(pubPage.getAdFrame(), "广告");
		this.setConnectionType(NETWORK_CLOSE);
		helper.click(pubPage.getWeekHotApps(1), "本周热门应用第一个").sleep(15000);	
		helper.checkElement(pubPage.getErrorOfGetDate(), "没有获取到数据");
		helper.click(pubPage.getErrorOfGetDate(), "没有获取到数据").sleep(15000);
		this.setConnectionType(NETWORK_NORMAL);
		helper.click(pubPage.getErrorOfGetDate(), "没有获取到数据");
		helper.checkElement(pubPage.getAppInfo(), "详情标签").swipeDirection("up").back();
		this.setConnectionType(NETWORK_DELAY);
		helper.click(pubPage.getWeekHotApps(2), "本击热门应用第二个").sleep(10000);
		helper.checkElement(pubPage.getAppInfo(), "详情标签");
		helper.click(pubPage.getAppComment(), "切至评论").checkElement(pubPage.getAppScope(), "评分");
		this.setConnectionType(NETWORK_CLOSE);
		helper.click(pubPage.getAppInfo(), "切至详情").checkElement(pubPage.getAppState(), "检查应用介绍");			
	}
	/**
	 * 安装应用
	 */
	public void installApp() {
		this.clean().setConnectionType(NETWORK_NORMAL);
		helper.sleep(6000).isExistClickElseSkip(pubPage.getAdFrame(), "广告");
		helper.click(pubPage.getWeekHotApps(1), "本周热门应用第1个");
		navigation.closeWiFi();
		helper.click(pubPage.getInstalBtn(), "安装").isExistToast("网络未连接");		
		navigation.openWiFi();
		helper.back();
		//弱网下选择任意应用安装
		this.setConnectionType(NETWORK_DELAY);
		helper.click(pubPage.getWeekHotApps(2), "本周热门应用第2个").sleep(10000);
		helper.click(pubPage.getInstalBtn(), "安装").back();
		this.checkDownResult();
		//点击暂停 显示继续
		helper.click(mainPage.getIndex(), "首页");
		helper.click(mainPage.getSearchContext(), "搜索框").send(mainPage.getSearchContext(), "乱世王者");
		helper.hideKeyBoard();	
		helper.click(mainPage.getSearchBtn(), "开始搜索").sleep(20000);
		helper.checkElement(mainPage.getSerachResultFirstApp(), "搜索结果第一个应用");
		helper.click(mainPage.getSerachResultFirstApp(), "搜索结果第一个应用").sleep(10000);
		helper.click(pubPage.getInstalBtn(), "安装").back().back();
		//执行暂停 并检查是否处于下载状态
		this.checkPauseToContinue();
		//暂停后 恢复下载
		this.checkContinueToDownload();			
		helper.click(mainPage.getIndex(), "首页");
		//模拟应用宝在弱网下安装 需要提前卸载应用宝
		pubPage.removeApp("com.tencent.android.qqdownloader");
		helper.click(mainPage.getSearchContext(), "搜索框").send(mainPage.getSearchContext(), "应用宝");
		helper.hideKeyBoard();		
		helper.click(mainPage.getSearchBtn(), "开始搜索").sleep(20000);
		helper.checkElement(mainPage.getSerachResultFirstApp(), "搜索结果第一个应用");
		helper.click(mainPage.getSerachResultFirstApp(), "搜索结果第一个应用").sleep(10000);
		helper.click(pubPage.getInstalBtn(), "安装").back().back();
		helper.click(centerPage.getIndex(), "个人中心");
		pubPage.checkAppIsInstall("com.tencent.android.qqdownloader");
		//卸载应用后 重新下载 		
		helper.click(centerPage.getAppRemove(), "应用卸载");
		centerPage.uninstall("com.tencent.android.qqdownloader", "应用宝");
		helper.click(mainPage.getIndex(), "首页");
		helper.click(mainPage.getSearchContext(), "搜索框").send(mainPage.getSearchContext(), "应用宝");
		helper.hideKeyBoard();		
		helper.click(mainPage.getSearchBtn(), "开始搜索").sleep(20000);
		helper.checkElement(mainPage.getSerachResultFirstApp(), "搜索结果第一个应用");
		helper.click(mainPage.getSerachResultFirstApp(), "搜索结果第一个应用").sleep(10000);
		helper.click(pubPage.getInstalBtn(), "安装").back().back();
		helper.click(centerPage.getIndex(), "个人中心");
		//等待一百秒 检查应用是否安装成功
		pubPage.checkAppIsInstall("com.tencent.android.qqdownloader");		
		//更新界面点击应用更新 先安装低版本应用 抖音为例 先卸载	
		pubPage.removeApp("com.ss.android.ugc.aweme");
		//安装低版本
		centerPage.installDouYin(helper.getAndroidDriver().getCapabilities().getCapability("deviceName").toString());
		//校验是否已安装
		pubPage.checkAppIsInstall("com.ss.android.ugc.aweme");	
		//进入更新列表
		helper.click(centerPage.getAppUpdate(), "应用更新").sleep(10000);
		helper.click(centerPage.getAppUpdateListDouYin(), "抖音").sleep(10000);
		helper.click(pubPage.getInstalBtn(), "更新").back().back();
		this.setConnectionType(NETWORK_NORMAL);
		helper.click(mainPage.getIndex(), "首页");		
		pubPage.removeApp("com.tencent.tmgp.pubgmhd");
		helper.click(mainPage.getSearchContext(), "搜索框").send(mainPage.getSearchContext(), "和平精英");
		helper.hideKeyBoard();		
		helper.click(mainPage.getSearchBtn(), "开始搜索").sleep(5000);
		helper.checkElement(mainPage.getSerachResultFirstApp(), "搜索结果第一个应用");
		helper.click(mainPage.getSerachResultFirstApp(), "搜索结果第一个应用");
		//下载过程中断网
		helper.click(pubPage.getInstalBtn(), "安装").back().back();		
		navigation.closeWiFi();		
		helper.isExistToast("网络");
		navigation.openWiFi();
		this.checkDownResult();		
	}
	
	/**
	 * 我的
	 * 11-20 pass
	 */
	public void center() {
		//此用例需要卸载验证，确保手机中有此应用，这里以新浪微博为例子验证
		centerPage.installWeiBo(helper.getAndroidDriver().getCapabilities().getCapability("deviceName").toString());
		this.clean().setConnectionType(NETWORK_NORMAL);
		helper.sleep(6000).isExistClickElseSkip(pubPage.getAdFrame(), "广告");
		helper.click(centerPage.getIndex(), "个人中心");
		centerPage.login();
		this.setConnectionType(NETWORK_CLOSE);
		helper.click(centerPage.getAppUpdate(), "应用更新").isExistClickElseSkip(centerPage.getUpdateHistory(), "更新历史");
		helper.back().click(centerPage.getAppRemove(), "应用卸载");
		centerPage.uninstall("com.sina.weibo","微博");
		helper.click(centerPage.getMyOrder(), "我的预约").sleep(15000);
		helper.checkElement(pubPage.getErrorOfGetDate(), "没有获取到数据");
		this.setConnectionType(NETWORK_NORMAL);
		helper.click(pubPage.getErrorOfGetDate(), "没有获取到元素");
		helper.checkElement(centerPage.getComingsoon(), "即将上线").back();
		this.setConnectionType(NETWORK_CLOSE);
		helper.click(centerPage.getMyGiftBag(), "我的礼包").sleep(15000);
		helper.checkElement(pubPage.getErrorOfGetDate(), "没有获取到数据");
		this.setConnectionType(NETWORK_NORMAL);
		helper.click(pubPage.getErrorOfGetDate(), "没有获取到元素").checkElement(centerPage.getGiftList(), "已领取礼包界面");
		helper.swipeDirection("up").back();
		this.setConnectionType(NETWORK_DELAY);
		helper.click(centerPage.getAppUpdate(), "应用更新");
		helper.isExistClickElseSkip(centerPage.getUpdateHistory(), "更新历史").back();
		helper.click(centerPage.getAppRemove(), "应用卸载");
		helper.checkElement(centerPage.getAllAppList(), "全部应用标签").back();
		helper.click(centerPage.getMyOrder(), "我的预约").sleep(10000);
		helper.checkElement(centerPage.getComingsoon(), "即将上线").back();
		helper.click(centerPage.getMyGiftBag(), "我的礼包").sleep(10000);
		helper.checkElement(centerPage.getGiftList(), "已领取礼包界面").back();
	}
	/**
	 * 查看全部模块
	 */
	public void checkAllPage() {
		this.clean().setConnectionType(NETWORK_NORMAL);
		helper.sleep(6000).isExistClickElseSkip(pubPage.getAdFrame(), "广告");
		this.setConnectionType(NETWORK_CLOSE);
		helper.click(pubPage.getCheckAllLabel(), "查看全部").sleep(15000);
		helper.checkElement(pubPage.getErrorOfGetDate(), "没有获取到数据");
		this.setConnectionType(NETWORK_NORMAL);
		helper.swipeDirection("down").sleep(2000);
		helper.checkElement(pubPage.getWeekHotAppsOfSub(1), "界面显示的第1个应用").back();
		this.setConnectionType(NETWORK_DELAY);
		helper.click(pubPage.getCheckAllLabel(), "查看全部").sleep(10000);
		helper.checkElement(pubPage.getWeekHotAppsOfSub(2), "界面显示的第2个应用");
		helper.swipeDirection("up").sleep(3000);
		helper.checkElement(pubPage.getWeekHotAppsOfSub(3), "界面显示的第3个应用");
		this.clean().setConnectionType(NETWORK_NORMAL);
		helper.click(pubPage.getCheckAllLabel(), "查看全部");
		navigation.closeWiFi();
		helper.swipeDirection("up").isExistToast("网络未连接");
		helper.click(pubPage.getWeekHotAppsOfSub(1), "界面显示的第1个应用").sleep(5000);
		helper.checkElement(pubPage.getErrorOfGetDate(), "网络断开了").back();
		helper.click(mainPage.getDownBtn(), "安装").isExistToast("网络未连接");
		navigation.openWiFi();
		helper.swipeDirection("up").sleep(3000);
		helper.checkElement(pubPage.getWeekHotAppsOfSub(3), "界面显示的第3个应用").back();
		//游戏界面的查看全部标签
		helper.click(gamePage.getIndex(), "游戏界面");
		this.setConnectionType(NETWORK_CLOSE);
		helper.click(pubPage.getCheckAllLabel(), "查看全部").sleep(15000);
		helper.checkElement(pubPage.getErrorOfGetDate(), "没有获取到数据");
		this.setConnectionType(NETWORK_NORMAL);
		helper.swipeDirection("down").sleep(2000);
		helper.checkElement(pubPage.getWeekHotAppsOfSub(1), "界面显示的第1个应用").back();
		this.setConnectionType(NETWORK_DELAY);
		helper.click(pubPage.getCheckAllLabel(), "查看全部").sleep(10000);
		helper.checkElement(pubPage.getWeekHotAppsOfSub(2), "界面显示的第2个应用");
		helper.swipeDirection("up").sleep(3000);
		helper.checkElement(pubPage.getWeekHotAppsOfSub(3), "界面显示的第3个应用");
		this.clean().setConnectionType(NETWORK_NORMAL);
		helper.click(pubPage.getCheckAllLabel(), "查看全部");
		navigation.closeWiFi();
		helper.swipeDirection("up").isExistToast("网络未连接");
		helper.click(pubPage.getWeekHotAppsOfSub(1), "界面显示的第1个应用").sleep(5000);
		helper.checkElement(pubPage.getErrorOfGetDate(), "网络断开了").back();
		helper.click(mainPage.getDownBtn(), "安装").isExistToast("网络未连接");
		navigation.openWiFi();
		helper.swipeDirection("up").sleep(3000);
		helper.checkElement(pubPage.getWeekHotAppsOfSub(3), "界面显示的第3个应用").back();
		//软件界面的查看全部标签
		helper.click(softPage.getIndex(), "游戏界面");
		this.setConnectionType(NETWORK_CLOSE);
		helper.click(pubPage.getCheckAllLabel(), "查看全部").sleep(15000);
		helper.checkElement(pubPage.getErrorOfGetDate(), "没有获取到数据");
		this.setConnectionType(NETWORK_NORMAL);
		helper.swipeDirection("down").sleep(2000);
		helper.checkElement(pubPage.getWeekHotAppsOfSub(1), "界面显示的第1个应用").back();
		this.setConnectionType(NETWORK_DELAY);
		helper.click(pubPage.getCheckAllLabel(), "查看全部").sleep(10000);
		helper.checkElement(pubPage.getWeekHotAppsOfSub(2), "界面显示的第2个应用");
		helper.swipeDirection("up").sleep(3000);
		helper.checkElement(pubPage.getWeekHotAppsOfSub(3), "界面显示的第3个应用");
		this.clean().setConnectionType(NETWORK_NORMAL);
		helper.click(pubPage.getCheckAllLabel(), "查看全部");
		navigation.closeWiFi();
		helper.swipeDirection("up").isExistToast("网络未连接");
		helper.click(pubPage.getWeekHotAppsOfSub(1), "界面显示的第1个应用").sleep(5000);
		helper.checkElement(pubPage.getErrorOfGetDate(), "网络断开了").back();
		helper.click(mainPage.getDownBtn(), "安装").isExistToast("网络未连接");
		navigation.openWiFi();
		helper.swipeDirection("up").sleep(3000);
		helper.checkElement(pubPage.getWeekHotAppsOfSub(3), "界面显示的第3个应用").back();		
	}
	/**
	 * 网络恢复后的下载检查(游戏界面、首页、软件页、福利、榜单、分类)
	 */
	public void downApp() {
		//删除白名单之外的所有第三方应用
		helper.removeApp();
		this.clean().setConnectionType(NETWORK_NORMAL);
		helper.sleep(6000).isExistClickElseSkip(pubPage.getAdFrame(), "广告");
		helper.click(centerPage.getIndex(), "个人中心");
		centerPage.login();
		helper.click(gamePage.getIndex(), "游戏页");
		//游戏界面 无网恢复到正常网络
		this.setConnectionType(NETWORK_CLOSE);
		helper.click(pubPage.getWeekHotApps(1), "本周新游").sleep(15000);
		helper.checkElement(pubPage.getErrorOfGetDate(), "没有获取到数据");		
		this.setConnectionType(NETWORK_NORMAL);
		helper.swipeDirection("up").sleep(3000);
		helper.click(pubPage.getInstalBtn(), "安装").back();
		this.checkDownResult();
		this.setConnectionType(NETWORK_DELAY);
		helper.click(gamePage.getIndex(), "游戏").sleep(10000);		
		helper.click(pubPage.getWeekHotApps(1), "本周新游").sleep(10000);
		helper.checkElement(pubPage.getAppInfo(), "详情标签");
		helper.click(pubPage.getInstalBtn(), "安装").back();
		this.checkDownResult();
		//首页无网恢复至正常网络下载
		helper.click(mainPage.getIndex(), "首页");
		this.setConnectionType(NETWORK_CLOSE);
		helper.click(pubPage.getWeekHotApps(3), "本周热门应用第3个").sleep(15000);
		helper.checkElement(pubPage.getErrorOfGetDate(), "没有获取到数据");
		this.setConnectionType(NETWORK_NORMAL);
		helper.swipeDirection("up").sleep(2000);
		helper.click(pubPage.getInstalBtn(), "安装").back();
		this.checkDownResult();
		//首页延迟网络下载
		helper.click(mainPage.getIndex(), "首页");
		this.setConnectionType(NETWORK_DELAY);
		helper.click(pubPage.getWeekHotApps(1), "本周热门应用第1个").sleep(10000);	
		helper.click(pubPage.getInstalBtn(), "安装").back();
		this.checkDownResult();
		this.setConnectionType(NETWORK_NORMAL);
		//软件页无网恢复正常网络下载
		helper.click(softPage.getIndex(), "软件页");
		this.setConnectionType(NETWORK_CLOSE);
		helper.click(pubPage.getWeekHotApps(1), "流行正当时第1个应用").sleep(15000);
		helper.checkElement(pubPage.getErrorOfGetDate(), "没有获取到数据");
		this.setConnectionType(NETWORK_NORMAL);
		helper.swipeDirection("up").sleep(2000);
		helper.click(pubPage.getInstalBtn(), "安装").back();
		//软件页 延迟网络下载
		this.checkDownResult();
		this.setConnectionType(NETWORK_DELAY);		
		helper.click(pubPage.getWeekHotApps(1), "流行正当时第1个应用").sleep(10000);
		helper.click(pubPage.getInstalBtn(), "安装").back();
		this.checkDownResult();
		this.setConnectionType(NETWORK_NORMAL);
		//首页福利网络恢复下载
		helper.click(mainPage.getIndex(), "首页").click(mainPage.getBoon(), "福利");
		this.setConnectionType(NETWORK_DELAY);
		helper.click(gamePage.getAllGameBoonBtn(), "全部游戏福利").sleep(10000);
		helper.click(helper.findBySlideText("多多自走棋"), "多多自走棋").sleep(10000);		
		helper.click(gamePage.getList_DownBtn(), "安装");			
		helper.back().back().back();
		this.checkDownResult();
		helper.click(mainPage.getIndex(), "首页").sleep(10000);
		helper.click(mainPage.getBoon(), "福利").sleep(10000);
		helper.click(gamePage.getGpassGame(), "GPASS特权界面").sleep(10000);
		helper.click(gamePage.getCheckGPASS(), "查看GPASS").sleep(10000);
		helper.click(gamePage.getEnableGpass(), "启用我的GPASS特权").back().back().back();
		this.checkDownResult();
		this.setConnectionType(NETWORK_NORMAL);
		//榜单弱网下载
		helper.click(mainPage.getIndex(), "首页");
		this.setConnectionType(NETWORK_DELAY);
		helper.click(mainPage.getList(), "榜单").sleep(10000);
		helper.click(mainPage.getListIndex1(), "第一名应用").sleep(10000);
		helper.click(pubPage.getInstalBtn(), "安装").back().back();
		this.checkDownResult();
		//榜单网络恢复后下载
		this.setConnectionType(NETWORK_NORMAL);
		helper.click(mainPage.getIndex(), "首页").click(mainPage.getList(), "榜单");
		this.setConnectionType(NETWORK_CLOSE);
		helper.click(mainPage.getListIndex1(), "第一名应用").sleep(15000);
		helper.checkElement(pubPage.getErrorOfGetDate(), "没有获取到数据");
		this.setConnectionType(NETWORK_NORMAL);
		helper.swipeDirection("up").sleep(2000);
		helper.click(pubPage.getInstalBtn(), "安装").back().back();
		this.checkDownResult();
		// 分类无网络进入后 恢复网络
		helper.click(mainPage.getIndex(), "首页");
		this.setConnectionType(NETWORK_CLOSE);
		helper.click(mainPage.getClassify(), "分类").sleep(15000);
		helper.checkElement(pubPage.getErrorOfGetDate(), "没有获取到数据");
		this.setConnectionType(NETWORK_NORMAL);
		helper.swipeDirection("up").sleep(2000);
		helper.click(pubPage.getClassify_Game(), "游戏分类").click(pubPage.getClassifty_Game_Relax(), "休闲益智");
		helper.click(pubPage.getClassify_SubClassApp(), "第一名应用");
		helper.click(pubPage.getInstalBtn(), "安装").back().back().back();
		this.checkDownResult();
		helper.click(mainPage.getIndex(), "首页").click(mainPage.getClassify(), "分类");
		//分类网络恢复后下载
		this.setConnectionType(NETWORK_CLOSE);
		helper.click(pubPage.getClassify_Soft_Life(), "软件-生活").sleep(15000);
		helper.checkElement(pubPage.getErrorOfGetDate(), "没有获取到数据");
		this.setConnectionType(NETWORK_NORMAL);
		helper.swipeDirection("up").sleep(3000);
		helper.checkElement(pubPage.getClassify_SubClassApp(), "第一个应用");
		helper.swipeDirection("up");
		helper.click(pubPage.getClassify_SubClassApp(), "第一个应用");
		helper.click(pubPage.getInstalBtn(), "安装").back().back().back();
		this.checkDownResult();
		helper.click(mainPage.getIndex(), "首页");
		//分类弱网下载
		this.setConnectionType(NETWORK_DELAY);
		helper.click(mainPage.getClassify(), "分类").sleep(10000);
		helper.click(pubPage.getClassify_Soft_Video(), "视频类").sleep(10000);
		helper.click(pubPage.getClassify_SubClassApp(), "第一位应用").sleep(10000);
		helper.click(pubPage.getInstalBtn(), "安装").back().back().back();
		this.checkDownResult();
		helper.click(mainPage.getIndex(), "首页");		
	}	
	
	
	
	
	/**
	 * 清理环境
	 */
	private WeakNetworkService clean() {
		helper.killAppStore();
		helper.killQNET();
		logger.info("结束QNET进程");	
		return this;
	}	
	
	/**
	 * 点击应用
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
		helper.getAndroidDriver().startActivity(getQnetActivity());
		WebElement testButton = helper.findById("com.tencent.qnet:id/buttonTest");
		String st = "00正常网络";
		if(st.equals(netWorkValue)) {
			if(testButton.getText().contains("结束")) {
				helper.click(testButton, "结束QNET");						
			}
			logger.info("网络状态:"+netWorkValue);		
		}else {
			helper.click(helper.findByUiautomatorText(netWorkValue), netWorkValue);		
			if (!testButton.getText().contains("结束")) {
				helper.click(testButton, "开启"+netWorkValue);
				return;
			} else {
				logger.info("网络状态:"+netWorkValue);				
			}			
		}
		helper.pressKeyCode(3);
		helper.pressKeyCode(3);
		clickDeskApp("应用市场");
	}
	private  Activity getQnetActivity() {
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
		if(helper.getPageSource().contains(str)) {
			helper.swipeDirection("up");
			logger.info("应用正在下载");	
			navigation.closeWiFi();
			helper.click(centerPage.getIndex(), "个人中心");
			helper.isExistClickElseSkip(centerPage.getDelBtn(), "删除").isExistClickElseSkip(centerPage.getConfirmBtn(), "确定删除");
			navigation.openWiFi();			
		}else {
			throw new RuntimeException("未能触发下载") ;
		}
	}
	/**
	 * 检查完成 停留在个人中心界面
	 */
	private void checkPauseToContinue() {
		helper.openNotifications();	
		String str = "应用正在下载";
		if(helper.getPageSource().contains(str)) {
			helper.swipeDirection("up");
			logger.info("应用正在下载");
			helper.click(centerPage.getIndex(), "个人中心");
			helper.tap(924, 852);			
		}
		helper.openNotifications();
		if(helper.getPageSource().contains(str)) {
			helper.swipeDirection("up");
			throw new RuntimeException("应用没有处于继续状态");
		}else {
			helper.swipeDirection("up");
			logger.info("应用处于继续下载状态");
		}
	}
	/**
	 * 检查完成 停留在个人中心界面
	 */
	private void checkContinueToDownload() {
		helper.click(centerPage.getIndex(), "个人中心").tap(924, 852);
		helper.click(mainPage.getIndex(), "首页");
		checkDownResult();
	}
}
