package com.meitu.service;
import java.util.Arrays;
import java.util.List;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebElement;
import com.meitu.utils.Helper;
public class WeakNetworkService {
	Helper helper;
	Logger logger = Logger.getLogger(this.getClass());

	public WeakNetworkService(Helper helper) {		
		this.helper = helper;
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
			WebElement confireBtn = helper.findById("com.android.packageinstaller:id/ok_button");
			helper.click(confireBtn, "确定权限");
			confireBtn = null;
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
	 * 结束QNET进程
	 */
	public void killQNET() {
		helper.killQNET();
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
		helper.click(helper.find_by_slide_text_h(appName), appName);
		helper.sleep(3000);		
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
	public void changeNetwork(String netWorkValue) {
		killQNET();
		goHome();
		clickDeskApp("QNET");
		helper.click(helper.findByUiautomatorText(netWorkValue), netWorkValue);
		WebElement testButton = helper.findById("com.tencent.qnet:id/buttonTest");
		if (!testButton.getText().contains("结束")) {
			helper.click(testButton, "开启弱网测试");
		} else {
			logger.info("弱网测试功能已开启，无需点击");
		}
	}
	/**
	 * 关闭装机必备界面
	 */
	public void closeRecommendApp() {
		helper.click(helper.findById("com.tencent.southpole.appstore:id/close"), "关闭必备");
	}

	/**
	 * 全选按钮
	 */
	public void selectAllButton() {
		helper.click(helper.findByUiautomatorText("全选"), "全选");
	}

	/**
	 * 取消全选按钮
	 */
	public void disableAllButton() {
		helper.click(helper.findByUiautomatorText("取消全选"), "取消全选");
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
	 * 安装、更新按钮 使用坐标方式点击
	 */
	public void installButtonByAxis(int x, int y) {
		helper.tap(x, y);
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
		// helper.click(helper.find_by_slide_text("安装"), "随机安装");
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
			helper.click(helper.find_by_slide_text(string), string);
			helper.sleep(sleepTimeMs);
			helper.back();
		}
	}

	/**
	 * 关闭广告
	 */
	public void closeAD() {
		logger.info("关闭启动广告弹窗");
		WebElement element = helper.findById("com.tencent.southpole.appstore:id/ic_close");
		if (element != null) {
			helper.click(element, "关闭推广");
		}else {
			logger.info("广告弹窗未找到，已跳过");
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
		helper.click(helper.find_by_slide_text(text), text);
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
	

}
