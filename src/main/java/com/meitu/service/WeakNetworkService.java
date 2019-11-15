package com.meitu.service;
import java.util.Arrays;
import java.util.List;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebElement;
import com.meitu.base.AbstractPage;
import com.meitu.utils.Helper;
public class WeakNetworkService extends AbstractPage{
	public WeakNetworkService(Helper helper) {
		super(helper);		
	}	
	private static final String NETWORK_CLOSE = "100%丢包";
	private static final String NETWORK_DELAY = "延迟";
	private static final String NETWORK_NORMAL = "普通网络";
	Logger logger = Logger.getLogger(this.getClass());
	public void firstLogin() {
		//构建首次启动场景,清除应用缓存数据	
		helper.clearAppSroreData();
		//设置网络状态为无网
		setConnectionType(NETWORK_CLOSE);
		//首次启动 权限窗口
		helper.click(main.getAuthor(), "同意");		
		//进入无网状,装机必备界面处于加载中,需要等待
		helper.sleep(15000);
		//加载完成,提示没有获取到数据,再次点击,触发二次加载
		helper.click(main.getErrorOfGetDate(), "没有获取到数据提示");	
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
		helper.click(main.getAuthor(), "同意");		
		//关闭必备界面
		helper.click(hotApps.getClose(), "关闭");
		//关闭广告弹窗 如果有的话
		helper.isExist(main.getAdFrame(), "广告");
	}
	
	/**
	 * pass 类型：非首次进入应用商店
	 * 100%
	 */
	public void reOpenAppStore() {
		helper.killAppStore();
		helper.killQNET();
		//设置网络为正常网络
		setConnectionType(NETWORK_NORMAL);
		//跳过广告
		helper.sleep(6000);			
		//关闭广告弹窗 如果有的话
		helper.isExist(main.getAdFrame(), "广告");
		//切换无网状态
		setConnectionType(NETWORK_CLOSE);
		helper.click(main.getIndex(), "首页");
		//向上滑动一次
		helper.swipeDirection("up");
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
		helper.checkElement(main.getErrorOfGetDate(), "没有获取到数据");
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
		helper.killAppStore();
		helper.killQNET();
		setConnectionType(NETWORK_NORMAL);
		helper.sleep(6000);
		//关闭广告弹窗 如果有的话
		helper.isExist(main.getAdFrame(), "广告");	
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
		clean();
		setConnectionType(NETWORK_NORMAL);
		helper.sleep(6000);
		//关闭广告弹窗 如果有的话
		helper.isExist(main.getAdFrame(), "广告");
		setConnectionType(NETWORK_CLOSE);
		helper.click(main.getBoon(), "福利");		
		helper.sleep(15000);
		helper.checkElement(main.getErrorOfGetDate(), "没有获取到数据");
		helper.back();
		//内容加载出来后 断网
		setConnectionType(NETWORK_NORMAL);
		helper.click(main.getBoon(), "福利");
		setConnectionType(NETWORK_CLOSE);	
		helper.click(game.getAllGameBoon(), "全部游戏福利");		
		helper.sleep(15000);
		helper.checkElement(main.getErrorOfGetDate(), "没有获取到数据");
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
		clean();
		setConnectionType(NETWORK_NORMAL);
		helper.sleep(6000);
		//关闭广告弹窗 如果有的话
		helper.isExist(main.getAdFrame(), "广告");	
		setConnectionType(NETWORK_CLOSE);
		helper.click(main.getList(), "榜单");
		helper.checkElement(main.getErrorOfGetDate(), "没有获取到数据");		
		setConnectionType(NETWORK_NORMAL);
		helper.swipeDirection("up");
		helper.click(main.getListIndex1(), "第一名应用");
		helper.sleep(3000);
		helper.back();
		setConnectionType(NETWORK_CLOSE);
		helper.click(main.getListIndex1(), "第一名应用");
		helper.sleep(15000);
		helper.checkElement(main.getErrorOfGetDate(), "没有获取到数据");
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
		clean();
		setConnectionType(NETWORK_NORMAL);
		helper.sleep(6000);
		//关闭广告弹窗 如果有的话
		helper.isExist(main.getAdFrame(), "广告");
		setConnectionType(NETWORK_CLOSE);
		helper.click(main.getClassify(), "首页分类");
		helper.sleep(15000);
		helper.checkElement(main.getErrorOfGetDate(), "没有获取到数据");
		setConnectionType(NETWORK_NORMAL);
		helper.swipeDirection("up");
		helper.checkElement(main.getClassify_Soft_Video(), "视频分类");
		helper.back();
		setConnectionType(NETWORK_DELAY);
		helper.click(main.getClassify(), "首页分类");
		helper.click(main.getClassify_Soft_Video(), "视频分类");		
		helper.sleep(10000);
		helper.checkElement(helper.findById("com.tencent.southpole.appstore:id/app_name"), "视频分类第一个应用");
		helper.swipeDirection("end");
		helper.swipeDirection("top");
		helper.click(helper.findById("com.tencent.southpole.appstore:id/app_name"), "视频分类第一个应用");
		helper.checkElement(main.getAppInfo(), "详情标签");
		helper.back();		
	}

	/**
	 * 首页分类-任意分类下的列表 
	 * pass
	 * 100%
	 */
	public void homeClassifyList() {
		setConnectionType(NETWORK_NORMAL);
		sleep(6000);
		closeAD();		
		clickClassify();	
		setConnectionType(NETWORK_CLOSE);
		randomCheckAppInfo("分类");	
		back();
		back();
		changeTabToGame();
		slideDown(1);
		sleep(15000);
		changeTabToSoft();
		slideDown(1);
		sleep(15000);
		changeTabToHome();
		slideDown(1);
		sleep(15000);
		setConnectionType(NETWORK_DELAY);
		sleep(10000);
		clickClassify();
		sleep(10000);
		randomCheckAppInfo("分类");
		sleep(10000);
		back();
		clickClassify();
		setConnectionType(NETWORK_CLOSE);
		checkList("软件", 15000);		
	}

	

	/**
	 * 福利礼包
	 * 50%
	 */
	public void WelfareGift() {
		setConnectionType(NETWORK_NORMAL);
		sleep(6000);
		closeAD();
		changeTabToMy();
		logout();
		changeTabToGame();
		clickWelfare();
		setConnectionType(NETWORK_CLOSE);
		clickOneKeyGet();
		otherLogin();
		loginQQ();
		sleep(7000);
		checkLoginView();
		back();
		clickWelfareBanner();
		sleep(15000);
		back();
		setConnectionType(NETWORK_DELAY);
		clickOneKeyGet();
		checkLoginView();
		back();
		clickDefaultGet();
		checkLoginView();
		back();
	}
	/**
	 * 搜索
	 * 70%
	 */
	public void search() {
		setConnectionType(NETWORK_NORMAL);
		sleep(6000);		
		closeAD();
		setConnectionType(NETWORK_CLOSE);
		changeTabToHome();
		clickSearch();
		sleep(15000);
		back();
		setConnectionType(NETWORK_NORMAL);
		slideUp(1);
		back();
		setConnectionType(NETWORK_DELAY);
		sendText("微视");
		sleep(10000);
		clickSearchButton();
		sleep(10000);
		randomInstall();
		back();
		setConnectionType(NETWORK_NORMAL);
		clickSearch();
		setConnectionType(NETWORK_CLOSE);
		sendText("快手");
		clickSearchButton();
		sleep(15000);
		setConnectionType(NETWORK_NORMAL);
		clickSearchButton();
		randomInstall();		
	}
	/**
	 * 应用详情
	 * 90%
	 */
	public void appInformation() {
		setConnectionType(NETWORK_NORMAL);
		sleep(6000);
		closeAD();
		changeTabToSoft();
		setConnectionType(NETWORK_CLOSE);
		randomCheckAppInfo("软件");
		sleep(15000);		
		setConnectionType(NETWORK_NORMAL);
		slideUp(1);
		back();
		setConnectionType(NETWORK_DELAY);
		randomCheckAppInfo("软件");
		sleep(10000);
		clickCommit();
		back();
		setConnectionType(NETWORK_NORMAL);
		randomCheckAppInfo("软件");
		setConnectionType(NETWORK_CLOSE);
		clickCommit();
		setConnectionType(NETWORK_NORMAL);
		randomInstall();		
	}
	/**
	 * 查看游戏图片
	 */
	public void checkGamePicture() {
		setConnectionType(NETWORK_NORMAL);
		sleep(6000);
		closeAD();
		changeTabToGame();
		clickClassifyOfGame();
		randomCheckAppInfo("游戏分类");
		setConnectionType(NETWORK_CLOSE);
		clickInformationPicture();
		slideLeft(5);
		back();
		setConnectionType(NETWORK_NORMAL);
		clickInformationPicture();
		slideLeft(5);
		back();
		setConnectionType(NETWORK_DELAY);
		clickInformationPicture();
		slideLeft(5);
		back();		
	}
	/**
	 * 我的
	 */
	public void my() {
		setConnectionType(NETWORK_NORMAL);
		sleep(6000);
		closeAD();		
		changeTabToMy();
		clickLogin();
		otherLogin();
		loginQQ();
		sleep(6000);
		setConnectionType(NETWORK_CLOSE);
		clickAppUpdate();
		back();
		clickAppUninstall();
		back();
		clickMyReserve();
		sleep(15000);
		back();
		clickMyGift();
		sleep(15000);
		back();		
	}
	/**
	 * 清理环 境
	 */
	public void clean() {
		helper.killAppStore();
		helper.killQNET();
		logger.info("已清理应用商店、QNET进程");
	}	
	/**
	 * 向上滑动
	 * @param num 次数
	 */
	public void slideUp(int num) {
		for (int i = 0; i < num; i++) {
			helper.swipeDirection("up");
			// 考虑到弱网的加载速度 此处休眠后再滑动
			helper.sleep(6000);
		}
	}

	/**
	 * 向上滑动
	 * @param num 滑动次数
	 */
	public void slideDown(int num) {
		for (int i = 0; i < num; i++) {
			helper.swipeDirection("down");
			// 考虑到弱网的加载速度 此处休眠后再滑动
			helper.sleep(6000);
		}
	}

	/**
	 * 返回桌面
	 */
	public void goHome() {
		helper.pressKeyCode(3);
	}
	
	public void tap(int x,int y) {
		helper.tap(x, y);
	}
	

	/**
	 * 返回上层界面
	 */
	public void back() {
		helper.back();
	}

	/**
	 * 滑动至底部
	 */
	public void slideEnd() {
		logger.info("向下滑动");
		helper.swipeDirection("end");
	}

	/**
	 * 滑动至底部
	 */
	public void slideTop() {
		helper.swipeDirection("top");
	}

	/**
	 * 休眠
	 * @param ms
	 */
	public void sleep(int ms) {
		helper.sleep(ms);
	}

	/**
	 * 跳过授权界面及必备界面
	 */
	public void SkipAuthorizationInterface() {
		WebElement agreementBtn = helper.findById("com.tencent.southpole.appstore:id/dialog_right_btn");
		if (agreementBtn != null) {
			helper.click(agreementBtn, "同意");
			//WebElement confireBtn = helper.findById("com.android.packageinstaller:id/ok_button");
			//helper.click(confireBtn, "确定权限");
			//confireBtn = null;
		}else {
			throw new RuntimeException("元素未找到");
		} 
	}

	/**
	 * 结束应用商店进程
	 */
	public void killAppStore() {
		helper.killAppStore();
	}

	

	/**
	 * 清量应用商店数据
	 */
	public void clearAppSroreData() {
		helper.clearAppSroreData();
	}

	/**
	 * 点击桌面的应用
	 * @param appName App名称
	 */
	public void clickDeskApp(String appName) {		
		helper.click(helper.findBySlideText_h(appName), appName);			
	}

	/**
	 * 点击必备
	 */
	public void clickNecessary() {
		helper.click(helper.findByUiautomatorText("必备"), "必备");
	}

	/**
	 * 点击榜单
	 */
	public void clickList() {
		helper.click(helper.findByUiautomatorText("榜单"), "榜单");
	}

	/**
	 * 点击分类
	 */
	public void clickClassify() {
		helper.click(helper.findByUiautomatorText("分类"), "分类");
	}

	/**
	 * 点击福利
	 */
	public void clickWelfare() {
		helper.click(helper.findByUiautomatorText("福利"), "福利");
	}

	/**
	 * 点击搜索框
	 */
	public void clickSearch() {
		helper.click(helper.findById("com.tencent.southpole.appstore:id/search_action_bar_content"), "搜索框");
	}

	/**
	 * 搜索框输入内容
	 */
	public void sendText(String text) {
		helper.send(helper.findById("com.tencent.southpole.appstore:id/search_action_bar_content"), text);
	}

	/**
	 * 搜索按钮
	 */
	public void clickSearchButton() {
		helper.click(helper.findById("com.tencent.southpole.appstore:id/search_confirm"), "搜索按钮");
	}

	/**
	 * 仅支持输入：正常网络、延迟（延迟是自定义的，上下行延迟1000）、100%丢包
	 * 
	 * @param netWorkValue
	 */
	public void setConnectionType(String netWorkValue) {		
		goHome();
		clickDeskApp("QNET");
		helper.click(helper.findByUiautomatorText(netWorkValue), netWorkValue);
		WebElement testButton = helper.findById("com.tencent.qnet:id/buttonTest");
		if (!testButton.getText().contains("结束")) {
			helper.click(testButton, "开启"+netWorkValue);
		} else {
			logger.info("弱网测试功能已开启，无需点击");
			goHome();
			clickDeskApp("应用市场");
		}
	}
	/**
	 * 关闭装机必备界面
	 */
	public void closeRecommendApp() {
		helper.click(helper.findById("com.tencent.southpole.appstore:id/close"), "关闭必备");
	}	

	
	/**
	 * 安装、更新按钮
	 * 
	 */
	public void installButton_download() {
		helper.click(helper.findById("com.tencent.southpole.appstore:id/download"), "安装");
	}

	public void installButton_download_button() {
		helper.click(helper.findById("com.tencent.southpole.appstore:id/download_button"), "安装");
	}

	
	/**
	 * 更改tab界面-game
	 */
	public void changeTabToGame() {
		helper.click(helper.findById("com.tencent.southpole.appstore:id/tab_game"), "游戏-Tab");
	}

	/**
	 * 更改tab界面-soft
	 */
	public void changeTabToSoft() {
		helper.click(helper.findById("com.tencent.southpole.appstore:id/tab_application"), "软件-Tab");
	}

	/**
	 * 更改tab界面-Home
	 */
	public void changeTabToHome() {
		helper.click(helper.findById("com.tencent.southpole.appstore:id/tab_home"), "首页-Tab");
	}

	/**
	 * 更改tab界面-Home
	 */
	public void changeTabToMy() {
		helper.click(helper.findById("com.tencent.southpole.appstore:id/tab_mine"), "首页-Tab");
	}

	/**
	 * 随机安装，后续实现
	 */
	public void randomInstall() {
		// helper.click(helper.findBySlideText("安装"), "随机安装");
		// 因下载、安装、更新、三种状态的按钮及ID均相同，无法判断应用的状态，暂不实现
		logger.info("需要开发增加按钮状态的text值，此功能暂不实现");
	}

	/**
	 * 随机查看应用详情 支持的参数：首页必备、首页、软件、全部游戏福利、分类
	 */
	public void randomCheckAppInfo(String pageName) {
		// com.tencent.southpole.appstore:id/info
		// com.tencent.southpole.appstore:id/name 新游
		// com.tencent.southpole.appstore:id/app_name //游戏分类列表
		switch (pageName) {
		case "首页必备":
			helper.click(helper.findById("com.tencent.southpole.appstore:id/tvCity"), pageName + "随机点击");
			break;
		case "全部游戏福利":
			helper.click(helper.findById("com.tencent.southpole.appstore:id/title"), pageName + "随机点击");
			break;
		case "榜单":
			helper.click(helper.findById("com.tencent.southpole.appstore:id/appName"), pageName + "随机点击");
			break;
		case "分类":
			helper.click(helper.findById("com.tencent.southpole.appstore:id/category_name"), pageName + "随机点击");
			break;
		case "游戏分类":
			helper.click(helper.findById("com.tencent.southpole.appstore:id/app_name"), pageName + "随机点击");
			break;
		default:
			//首页、游戏、软件
			helper.click(helper.findById("com.tencent.southpole.appstore:id/name"), pageName + "(default)" + "随机点击");
			break;
		}
	}

	/**
	 * 全部游戏福利
	 */
	public void clickAllGameWelfare() {
		helper.click(helper.findById("com.tencent.southpole.appstore:id/button_all_game"), "全部游戏福利");
	}

	/**
	 * GPASS游戏列表
	 */
	public void clickGpassGameList() {
		helper.click(helper.findByIdAndText("com.tencent.southpole.appstore:id/btn_title1", "G-PASS特权"), "G-PASS特权");
	}

	/**
	 * 购机福利界面
	 */
	public void clickBuyPhoneWelfare() {
		helper.click(helper.findByIdAndText("com.tencent.southpole.appstore:id/btn_title1", "购机福利"), "购机福利");
	}

	/**
	 * 福利-banner
	 */
	public void clickWelfareBanner() {
		helper.click(helper.findById("com.tencent.southpole.appstore:id/imageView12"), "福利banner页");
	}

	/**
	 * 游戏-分类
	 */
	public void clickClassifyOfGame() {
		helper.click(helper.findById("com.tencent.southpole.appstore:id/item_2"), "游戏分类");
	}

	/**
	 * 遍历游戏分类下的所有列表
	 * 
	 * @param type        软件、游戏
	 * @param sleepTimeMs 弱网状态下请指定休眠时间，正常状态可指定为2000ms
	 */
	public void checkList(String type, int sleepTimeMs) {
		if (!type.equals("软件") && !type.equals("游戏")) {
			logger.info("传入的参数错误");
			throw new RuntimeException("参数错误");
		}
		List<String> list;
		String[] gameArr = { "休闲益智", "网络游戏", "军事战争", "动作冒险", "体育竞速", "棋牌中心", "经营策略", "角色扮演", "电子竞技", "特色分类" };
		String[] softArr = { "视频", "生活", "购物", "工具", "理财", "摄影", "音乐", "阅读", "社交", "旅游", "办公", "健康", "系统", "美化", "出行",
				"教育" };
		if (type.equals("软件")) {
			list = Arrays.asList(softArr);
		} else {
			list = Arrays.asList(gameArr);
		}
		for (String string : list) {
			helper.click(helper.findBySlideText(string), string);
			helper.sleep(sleepTimeMs);
			helper.back();
		}
	}

	
	public void isExist(WebElement element,String elementName) {
		if (element != null) {
			helper.click(element, elementName);
		}else {
			logger.info(element+",未找到,已跳过");
		}
	}

	/**
	 * 游戏界面-新游
	 */
	public void clickGameTabNewGame() {
		helper.click(helper.findById("com.tencent.southpole.appstore:id/item_1"), "新游");
	}

	/**
	 * 登陆状态检查
	 */
	public boolean isLogin() {
		WebElement element = helper.findById("com.tencent.southpole.appstore:id/nick_name");
		if (element != null) {
			logger.info("状态：未登陆");
			return false;
		} else {
			logger.info("状态：已登陆");
			return true;
		}
	}

	/**
	 * 登陆
	 */
	public void clickLogin() {
		if (!isLogin()) {
			helper.findById("com.tencent.southpole.appstore:id/nick_name");
			otherLogin();
			loginQQ();
		}
	}

	/**
	 * 退出登陆
	 */
	public void logout() {
		if (isLogin()) {
			helper.findByIdAndText("com.tencent.southpole.appstore:id/personal_title", "设置");
			helper.findByUiautomatorText("退出登录");
		} else {
			logger.info("应用商店已处于未登陆状态");
		}
	}

	/**
	 * 其它方式登陆
	 */
	public void otherLogin() {
		helper.click(helper.findById("com.tencent.southpole.appstore:id/other_login"), "其他账号登录");
	}

	/**
	 * QQ登陆
	 */
	public void loginQQ() {
		helper.click(helper.findByUiautomatorText("QQ登录"), "QQ登录");
		helper.tap(500, 1050);
	}

	/**
	 * 一键领取
	 */
	public void clickOneKeyGet() {
		helper.click(helper.findById("com.tencent.southpole.appstore:id/button_receive_all"), "一键领取");
	}

	/**
	 * 默认领取
	 */
	public void clickDefaultGet() {
		helper.click(helper.findById("com.tencent.southpole.appstore:id/gift_1_receive_btn"), "领取");
	}

	/**
	 * 取消
	 */
	public void clickOneKeyGetCancel() {
		helper.click(helper.findById("com.tencent.southpole.appstore:id/dialog_left_btn"), "取消");
	}

	/**
	 * 判断登陆界面
	 */
	public void checkLoginView() {
		WebElement account = helper.findByUiautomatorText("帐号登录");
		if (account != null) {
			logger.info("登陆失败，符合预期");
		} else {
			throw new RuntimeException("界面异常");
		}
	}

	/**
	 * 评论
	 */
	public void clickCommit() {
		helper.click(helper.findByUiautomatorText("评论"), "评论");
	}

	/**
	 * 滑动查找元素
	 */
	public void slideFindElement(String text) {
		helper.click(helper.findBySlideText(text), text);
	}

	/**
	 * 详情图片
	 */
	public void clickInformationPicture() {
		helper.click(helper.findById("com.tencent.southpole.appstore:id/app_photo"), "详情图片");
	}

	/**
	 * 向左滑
	 */
	public void slideLeft(int num) {

		for (int i = 0; i < num; i++) {
			helper.swipeDirection("left");
			helper.sleep(2000);
		}
	}
	
	public void slideRight(int num) {
		for (int i = 0; i < num; i++) {
			helper.swipeDirection("right");
			helper.sleep(2000);
		}
	}
	/**
	 * 应用更新
	 */
	public void clickAppUpdate() {
		helper.click(helper.findByUiautomatorText("应用更新"), "应用更新");
	}
	/**
	 * 应用卸载
	 */
	public void clickAppUninstall() {
		helper.click(helper.findByUiautomatorText("应用卸载"), "应用卸载");
	}
	/**
	 * 我的预约
	 */
	public void clickMyReserve() {
		helper.click(helper.findByUiautomatorText("我的预约"), "我的预约");
	}
	/**
	 * 我的礼包
	 */
	public void clickMyGift() {
		helper.click(helper.findByUiautomatorText("我的礼包"), "我的礼包");
	}	
	public void killQNET() {
		helper.killQNET();
	}
	private void closeAD() {
		// TODO Auto-generated method stub
		
	}

}
