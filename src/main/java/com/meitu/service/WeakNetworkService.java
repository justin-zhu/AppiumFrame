package com.meitu.service;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebElement;
import com.meitu.base.AbstractPage;
import com.meitu.utils.Helper;

import cn.hutool.core.util.RuntimeUtil;
import io.appium.java_client.android.Activity;
public class WeakNetworkService extends AbstractPage{
	public WeakNetworkService(Helper helper) {
		super(helper);		
	}	
	private static final String NETWORK_CLOSE = "100%丢包";
	private static final String NETWORK_DELAY = "延迟";
	private static final String NETWORK_NORMAL = "正常网络";
	Logger logger = Logger.getLogger(this.getClass());
	public void firstLogin() {
		clearAppSroreData();		
		//设置网络状态为无网
		setConnectionType(NETWORK_CLOSE);
		//首次启动 权限窗口
		helper.click(pub.getAuthor(), "同意");		
		//加载完成,提示没有获取到数据
		//捕捉Toast
		helper.isExistToast("没有获取到数据");		
		//切换至正常网络
		setConnectionType(NETWORK_NORMAL);
		//上滑 触发再次加载
		helper.swipeDirection("up");
		//点击全选按钮
		helper.click(hotApps.getCheckAll(), "全选");
		//取消全选
		helper.click(hotApps.getQuitCheckAll(), "取消全选");
		//点击全选按钮
		helper.click(hotApps.getCheckAll(), "全选");
		//切换无网状态
		setConnectionType(NETWORK_CLOSE);
		//点击安装
		helper.click(hotApps.getInsatllBtn(), "安装");
		helper.back();
		//清理应用商店,防止因下载导致的不稳定,中断脚本
		helper.clearAppSroreData();
		//正常网络
		setConnectionType(NETWORK_NORMAL);		
		helper.click(pub.getAuthor(), "同意");		
		//关闭必备界面
		helper.click(hotApps.getClose(), "关闭");
		
	}
	
	/**
	 * pass 类型：非首次进入应用商店
	 * 100%
	 */
	public void reOpenAppStore() {
		
		this.clean();
		//设置网络为正常网络
		setConnectionType(NETWORK_NORMAL);
		//跳过广告
		helper.sleep(6000);			
		//关闭广告弹窗 如果有的话
		helper.isExist(pub.getAdFrame(), "广告");
		//切换无网状态
		setConnectionType(NETWORK_CLOSE);
		helper.click(main.getIndex(), "首页");
		//向上滑动一次
		helper.swipeDirection("down");
		//捕捉Toast
		helper.isExistToast("没有获取到数据");	
		//切到游戏界面
		helper.click(game.getIndex(), "游戏页");
		//捕捉Toast
		helper.isExistToast("没有获取到数据");		
		//向上滑
		helper.swipeDirection("up");		
		//捕捉Toast
		helper.isExistToast("没有获取到数据");		
		//切到软件页
		helper.click(soft.getIndex(), "软件页");
		//捕捉Toast
		helper.isExistToast("没有获取到数据");		
		helper.swipeDirection("up");
		//捕捉Toast
		helper.isExistToast("没有获取到数据");		
		//正常网络
		setConnectionType(NETWORK_NORMAL);
		//点首页
		helper.click(main.getIndex(), "首页");
		//下滑触发加载
		helper.swipeDirection("down");	
		//遮住动态的导航条区
		main.swipeHideTheBar();		
		//判断首页上的元素是否存在 存在即表示加载成功
		helper.checkElement(main.getWeekHotApps(1), "检查本周热门应用是否已在首页显示");		
		//点击安装按钮
		helper.checkElement(main.getDownBtn(), "下载按钮");
		//切到游戏页
		helper.click(game.getIndex(), "游戏页");
		//下滑触发加载
		helper.swipeDirection("down");
		//检查元素是否存在
		helper.checkElement(game.getTopBanner(), "游戏推荐位");
		//点安装
		helper.checkElement(game.getDownBtn(), "下载按钮");
		//切到软件页
		helper.click(soft.getIndex(), "软件页");
		//下滑触发加载
		helper.swipeDirection("down");
		//检查元素
		helper.checkElement(soft.getFashionApp(1), "流行正当时第1个应用");
		//点击软件界面中的下载按钮
		helper.checkElement(soft.getDownBtn(), "下载按钮");
		//延迟网络
		setConnectionType(NETWORK_DELAY);
		//首页
		helper.click(main.getIndex(), "首页");
		helper.swipeDirection("down");
		//延迟网络下 等待界面加载
		helper.sleep(10000);
		helper.click(game.getIndex(), "游戏页");
		helper.swipeDirection("down");
		helper.sleep(10000);
		helper.click(soft.getIndex(), "软件页");
		helper.swipeDirection("down");
		helper.sleep(10000);
		//切换正常网络
		setConnectionType(NETWORK_NORMAL);
		//切到首页
		helper.click(main.getIndex(), "首页");
		helper.swipeDirection("up");		
		helper.sleep(3000);
		helper.click(game.getIndex(), "游戏页");
		helper.swipeDirection("up");		
		helper.sleep(2000);
		helper.click(soft.getIndex(), "软件页");
		helper.swipeDirection("up");
		helper.sleep(2000);
		//切换无网
		setConnectionType(NETWORK_CLOSE);
		//切到首页
		helper.click(main.getIndex(), "首页");
		//点击首页上的任意应用
		helper.click(main.getWeekHotApps(1), "本周热门应用：第1个");
		//无网状态 点击应用详情界面 需要等待
		helper.sleep(15000);
		//界面加载完成 显示没有获取到数据 判断元素是否存在
		helper.checkElement(pub.getErrorOfGetDate(), "没有获取到数据");
		//返回
		helper.back();
		//切到游戏页
		helper.click(game.getIndex(), "游戏页");
		//会有Toast
		helper.isExistToast("没有获取到数据");			
		//点击软件页
		helper.click(soft.getIndex(), "软件页");	
		//会有Toast
		helper.isExistToast("没有获取到数据");		
	}	
	
	/**
	 * 新游
	 * 11-15 pass
	 */
	public void newGame() {
		this.clean();
		setConnectionType(NETWORK_NORMAL);
		helper.sleep(6000);
		//关闭广告弹窗 如果有的话
		helper.isExist(pub.getAdFrame(), "广告");	
		//切到游戏页
		helper.click(game.getIndex(), "游戏页");
		setConnectionType(NETWORK_CLOSE);
		helper.click(game.getNewGame(), "新游标签");
		//捕捉Toast
		helper.isExistToast("没有获取到数据");		
		//正常网络
		setConnectionType(NETWORK_NORMAL);
		//向上滑
		helper.swipeDirection("up");
		helper.checkElement(game.getTopGame(), "顶部推荐游戏");
		helper.back();
		//延迟网络
		setConnectionType(NETWORK_DELAY);
		helper.click(game.getNewGame(), "新游标签");
		helper.sleep(10000);
		helper.checkElement(game.getTopGame(), "顶部推荐游戏");
		helper.swipeDirection("end");
		helper.swipeDirection("top");
		helper.click(game.getTopGame(), "顶部推荐游戏");
		helper.sleep(10000);
		helper.checkElement(game.getTopGameInfo(), "游戏详情");
		helper.back();		
	}
	
	/**
	 *11-15 pass
	 *
	 * 福利
	 */
	public void homePageWelfare() {
		this.clean();
		setConnectionType(NETWORK_NORMAL);
		helper.sleep(6000);
		//关闭广告弹窗 如果有的话
		helper.isExist(pub.getAdFrame(), "广告");
		setConnectionType(NETWORK_CLOSE);
		helper.click(main.getBoon(), "福利");		
		helper.sleep(15000);
		helper.checkElement(pub.getErrorOfGetDate(), "没有获取到数据");
		helper.back();
		//内容加载出来后 断网
		setConnectionType(NETWORK_NORMAL);
		helper.click(main.getBoon(), "福利");
		setConnectionType(NETWORK_CLOSE);	
		helper.click(game.getAllGameBoon(), "全部游戏福利");		
		helper.sleep(15000);
		helper.checkElement(pub.getErrorOfGetDate(), "没有获取到数据");
		helper.back();
		helper.click(game.getGpassGame(), "GPASS特权游戏");
		helper.checkElement(game.getGpassUserInfo(), "Gpass特权界面下的用户信息面板");		
		helper.back();
		helper.click(game.getBuyPhoneBoon(), "购机福利");		
		helper.sleep(15000);
		//获取上下文信息
		helper.getContext();
		helper.back();		
		setConnectionType(NETWORK_NORMAL);
		helper.click(game.getAllGameBoon(), "全部游戏福利");
		helper.checkElement(game.getAllGameBoonList(), "游戏福利列表第一个游戏");
		helper.back();
		helper.click(game.getGpassGame(), "GPASS特权游戏");
		helper.checkElement(helper.findById("com.tencent.southpole.appstore:id/desc"), "Gpass界面描述");
		helper.back();
		helper.click(game.getBuyPhoneBoon(), "购机福利");
		helper.sleep(3000);
		//打印上下文
		helper.getContext();
		helper.back();
		helper.back();
		setConnectionType(NETWORK_DELAY);
		helper.click(main.getBoon(), "福利");
		helper.click(game.getAllGameBoon(), "全部游戏福利");	
		helper.sleep(5000);	
		helper.checkElement(game.getAllGameBoonList(), "游戏福利列表第一个游戏");
	}

	/**
	 * pass 11-15 榜单
	 * 100%
	 */
	public void homePageList() {
		this.clean();
		setConnectionType(NETWORK_NORMAL);
		helper.sleep(6000);
		//关闭广告弹窗 如果有的话
		helper.isExist(pub.getAdFrame(), "广告");	
		setConnectionType(NETWORK_CLOSE);
		helper.click(main.getList(), "榜单");
		helper.checkElement(pub.getErrorOfGetDate(), "没有获取到数据");		
		setConnectionType(NETWORK_NORMAL);
		helper.swipeDirection("up");
		helper.click(main.getListIndex1(), "第一名应用");
		helper.sleep(3000);
		helper.back();
		setConnectionType(NETWORK_CLOSE);
		helper.click(main.getListIndex1(), "第一名应用");
		helper.sleep(15000);
		helper.checkElement(pub.getErrorOfGetDate(), "没有获取到数据");
		helper.back();
		helper.back();
		setConnectionType(NETWORK_DELAY);
		helper.click(main.getList(), "榜单");
		helper.sleep(15000);
		helper.click(main.getListIndex1(), "第一名应用");
		helper.back();
		helper.click(main.getList_HotAppsList(), "榜单中的热销榜");
		helper.sleep(15000);
		helper.click(main.getListIndex1(), "第一名应用");
		helper.back();
		helper.click(main.getList_NewProduct(), "榜单中的新品榜");
		helper.sleep(15000);
		helper.click(main.getListIndex1(), "第一名应用");
		helper.sleep(10000);
		helper.back();
		helper.click(main.getList_FashionList(), "榜单中的流行榜");	
		helper.sleep(10000);
	}

	/**
	 * 首页分类
	 * 11-15
	 * @return pass
	 */
	public void homeClassify() {
		this.clean();
		setConnectionType(NETWORK_NORMAL);
		helper.sleep(6000);
		//关闭广告弹窗 如果有的话
		helper.isExist(pub.getAdFrame(), "广告");
		setConnectionType(NETWORK_CLOSE);
		helper.click(main.getClassify(), "首页分类");
		helper.sleep(15000);
		helper.checkElement(pub.getErrorOfGetDate(), "没有获取到数据");
		setConnectionType(NETWORK_NORMAL);
		helper.swipeDirection("up");
		helper.checkElement(pub.getClassify_Soft_Video(), "视频分类");
		helper.back();
		setConnectionType(NETWORK_DELAY);
		helper.click(main.getClassify(), "首页分类");
		helper.click(pub.getClassify_Soft_Video(), "视频分类");		
		helper.sleep(10000);
		helper.checkElement(pub.getClassify_SubClassApp(), "视频分类第一个应用");
		helper.swipeDirection("end");
		helper.swipeDirection("top");
		helper.click(pub.getClassify_SubClassApp(), "视频分类第一个应用");
		helper.checkElement(pub.getAppInfo(), "详情标签");
		helper.back();		
	}

	/**
	 * 首页分类-任意分类下的列表 
	 * 
	 */
	public void homeClassifyList() {
		this.clean();
		setConnectionType(NETWORK_NORMAL);
		helper.sleep(6000);
		helper.click(pub.getAdFrame(), "广告");
		helper.click(main.getClassify(), "首页分类");
		setConnectionType(NETWORK_CLOSE);
		helper.click(pub.getClassify_Soft_Video(), "视频分类");	
		helper.sleep(15000);
		helper.checkElement(pub.getErrorOfGetDate(), "没有获取到数据");
		helper.back();
		setConnectionType(NETWORK_DELAY);
		helper.click(pub.getClassify_Soft_Video(), "视频分类");
		helper.sleep(15000);
		helper.checkElement(pub.getClassify_SubClassApp(), "检查视频分类应用");
		helper.swipeDirection("up");
		helper.checkElement(pub.getClassify_SubClassApp(), "检查视频分类应用");
		helper.back();
		setConnectionType(NETWORK_CLOSE);
		helper.click(pub.getClassify_Soft_Life(), "生活分类");
		helper.sleep(15000);
		helper.checkElement(pub.getErrorOfGetDate(), "没有获取到数据");
		setConnectionType(NETWORK_NORMAL);
		helper.swipeDirection("up");
		helper.checkElement(pub.getClassify_SubClassApp(), "检查生活分类应用");	
		helper.back();
		helper.click(pub.getClassify_Game(), "游戏分类");		
		setConnectionType(NETWORK_CLOSE);
		helper.click(pub.getClassifty_Game_Relax(), "休闲益智");	
		helper.sleep(15000);
		helper.checkElement(pub.getErrorOfGetDate(), "没有获取到数据");
		helper.back();
		setConnectionType(NETWORK_DELAY);
		helper.click(pub.getClassifty_Game_Relax(), "休闲益智");
		helper.sleep(15000);
		helper.checkElement(pub.getClassify_SubClassApp(), "检查休闲益智分类应用");
		helper.swipeDirection("up");
		helper.checkElement(pub.getClassify_SubClassApp(), "检查休闲益智分类应用");
		helper.back();
		setConnectionType(NETWORK_CLOSE);
		helper.click(pub.getClassify_Game_OnLineGame(), "网络游戏分类");
		helper.sleep(15000);
		helper.checkElement(pub.getErrorOfGetDate(), "没有获取到数据");
		setConnectionType(NETWORK_NORMAL);
		helper.swipeDirection("up");
		helper.checkElement(pub.getClassify_SubClassApp(), "检查网络游戏分类应用");		
	}

	/**
	 * 福利礼包
	 * 11-19
	 */
	public void WelfareGift() {
		this.clean();
		setConnectionType(NETWORK_NORMAL);
		helper.sleep(6000);
		helper.click(pub.getAdFrame(), "广告");
		helper.click(center.getIndex(), "个人中心");		
		center.logout();
		helper.click(main.getIndex(), "首页");
		helper.click(main.getBoon(), "福利");		
		setConnectionType(NETWORK_CLOSE);
		//测试前需要安装游戏 否则将不会出现此元素
		helper.click(main.getBoonOnekeyGet(), "一键领取");
		helper.click(center.getSystemLogin(), "快捷登陆按钮");		
		helper.sleep(10000);
		helper.checkElement(pub.getErrorOfGetDate(), "没有获取到数据");
		helper.back();
		setConnectionType(NETWORK_DELAY);
		helper.click(center.getIndex(), "个人中心");
		center.logout();
		helper.click(main.getIndex(), "首页");
		helper.click(main.getBoon(), "福利");
		helper.sleep(10000);
		helper.click(center.getSystemLogin(), "快捷登录");
		//领取礼包暂不实现		
	}
	/**
	 * 搜索
	 * pass 11-20
	 */
	public void search() {		
		this.clean();		
		setConnectionType(NETWORK_NORMAL);
		helper.sleep(6000);		
		helper.isExist(pub.getAdFrame(), "广告");
		setConnectionType(NETWORK_CLOSE);
		helper.click(main.getSearchContext(), "搜索框");		
		helper.sleep(15000);
		helper.hideKeyBoard();	
		helper.checkElement(pub.getErrorOfGetDateOfSearch(), "没有获取到数据");		
		setConnectionType(NETWORK_NORMAL);
		helper.swipeDirection("up");
		helper.sleep(10000);
		helper.checkElement(main.getHotSearchLabel(), "热搜标签");
		helper.back();
		setConnectionType(NETWORK_DELAY);
		helper.click(main.getSearchContext(), "搜索框");
		helper.send(main.getSearchContext(), "微信");
		helper.hideKeyBoard();		
		helper.click(main.getSearchBtn(), "开始搜索");		
		helper.sleep(10000);
		helper.checkElement(main.getSerachResultFirstApp(), "搜索结果第一个应用");
		helper.click(main.getSerachResultFirstApp(), "搜索结果第一个应用");
		helper.sleep(10000);
		helper.checkElement(pub.getAppInfo(), "检查详情标签");
		//下载操作暂不实现
		helper.back();
		helper.back();		
		helper.click(main.getSearchContext(), "搜索框");
		setConnectionType(NETWORK_CLOSE);
		helper.send(main.getSearchContext(), "微信");
		helper.click(main.getSearchBtn(), "开始搜索");
		helper.sleep(15000);
		helper.checkElement(pub.getErrorOfGetDateOfSearch(), "没有获取到数据");
				
	}
	/**
	 * 应用详情
	 * 90%
	 */
	public void appInformation() {
		this.clean();
		setConnectionType(NETWORK_NORMAL);
		helper.sleep(6000);
		helper.isExist(pub.getAdFrame(), "广告");
		setConnectionType(NETWORK_CLOSE);
		helper.click(main.getWeekHotApps(1), "本周热门应用第一个");
		helper.sleep(15000);	
		helper.checkElement(pub.getErrorOfGetDate(), "没有获取到数据");
		helper.click(pub.getErrorOfGetDate(), "没有获取到数据");
		helper.sleep(15000);
		setConnectionType(NETWORK_NORMAL);
		helper.click(pub.getErrorOfGetDate(), "没有获取到数据");
		helper.checkElement(pub.getAppInfo(), "详情标签");
		helper.swipeDirection("up");
		helper.back();
		setConnectionType(NETWORK_DELAY);
		helper.click(main.getWeekHotApps(2), "本击热门应用第二个");
		helper.sleep(10000);
		helper.checkElement(pub.getAppInfo(), "详情标签");
		helper.click(pub.getAppComment(), "切至评论");
		helper.checkElement(pub.getAppScope(), "评分");
		setConnectionType(NETWORK_CLOSE);
		helper.click(pub.getAppInfo(), "切至详情");
		helper.checkElement(pub.getAppState(), "检查应用介绍");
		
			
	}
	
	/**
	 * 我的
	 * 11-20 pass
	 */
	public void center() {
		this.clean();
		setConnectionType(NETWORK_NORMAL);
		helper.sleep(6000);
		helper.isExist(pub.getAdFrame(), "广告");
		helper.click(center.getIndex(), "个人中心");
		center.login();
		setConnectionType(NETWORK_CLOSE);
		helper.click(center.getAppUpdate(), "应用更新");
		helper.isExist(center.getUpdateHistory(), "更新历史");
		helper.back();
		helper.click(center.getAppRemove(), "应用卸载");
		center.removeApp();
		helper.back();		
		helper.click(center.getMyOrder(), "我的预约");
		helper.sleep(15000);
		helper.checkElement(pub.getErrorOfGetDate(), "没有获取到数据");
		setConnectionType(NETWORK_NORMAL);
		helper.click(pub.getErrorOfGetDate(), "没有获取到元素");
		helper.checkElement(center.getComingsoon(), "即将上线");
		helper.back();
		setConnectionType(NETWORK_CLOSE);
		helper.click(center.getMyGiftBag(), "我的礼包");
		helper.sleep(15000);
		helper.checkElement(pub.getErrorOfGetDate(), "没有获取到数据");
		setConnectionType(NETWORK_NORMAL);
		helper.click(pub.getErrorOfGetDate(), "没有获取到元素");
		helper.checkElement(center.getGiftList(), "已领取礼包界面");
		helper.swipeDirection("up");
		helper.back();
		setConnectionType(NETWORK_DELAY);
		helper.click(center.getAppUpdate(), "应用更新");
		helper.isExist(center.getUpdateHistory(), "更新历史");
		helper.back();
		helper.click(center.getAppRemove(), "应用卸载");
		helper.checkElement(center.getAllAppList(), "全部应用标签");
		helper.back();
		helper.click(center.getMyOrder(), "我的预约");
		helper.sleep(10000);
		helper.checkElement(center.getComingsoon(), "即将上线");
		helper.back();
		helper.click(center.getMyGiftBag(), "我的礼包");
		helper.sleep(10000);
		helper.checkElement(center.getGiftList(), "已领取礼包界面");
		helper.back();
	}
	/**
	 * 清理环 境
	 */
	public void clean() {
		helper.killAppStore();
		helper.killQNET();
		logger.info("结束QNET进程");
		
	}	
	
	/**
	 * 点击桌面的应用
	 * @param appName App名称
	 */
	public void clickDeskApp(String appName) {		
		helper.click(helper.findByaccessibilityid(appName), appName);			
	}	
	/**
	 * 仅支持输入：正常网络、延迟（延迟是自定义的，上下行延迟1000）、100%丢包
	 * 
	 * @param netWorkValue
	 */
	public void setConnectionType(String netWorkValue) {		
		helper.pressKeyCode(3);
		clickDeskApp("QNET");
		//helper.getAndroidDriver().startActivity(getQnetActivity());
		WebElement testButton = helper.findById("com.tencent.qnet:id/buttonTest");
		if("正常网络".equals(netWorkValue)) {
			if(testButton.getText().contains("结束")) {
				helper.click(testButton, "结束QNET");
				logger.info("网络状态已切换至:"+netWorkValue);				
			}
		}else {
			helper.click(helper.findByUiautomatorText(netWorkValue), netWorkValue);		
			if (!testButton.getText().contains("结束")) {
				helper.click(testButton, "开启"+netWorkValue);
				return;
			} else {
				logger.info("网络状态已切换至:"+netWorkValue);				
			}
		}
		helper.pressKeyCode(3);
		clickDeskApp("应用市场");
	}
	public  Activity getQnetActivity() {
		Activity activity = new Activity("com.tencent.qnet", "com.tencent.qnet.ui.login.LoginActivity");
		activity.setAppWaitActivity("com.tencent.qnet.ui.MainActivity");
		activity.setAppWaitPackage("com.tencent.qnet");	
		return activity;
	}
	// 清量应用商店数据
	public void clearAppSroreData() {			
		String cmd = "adb shell pm clear com.tencent.southpole.appstore";
		RuntimeUtil.execForStr(cmd);
	}
}
