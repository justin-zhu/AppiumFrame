package com.meitu.page;

import java.time.Duration;

import org.openqa.selenium.WebElement;

import com.meitu.utils.Helper;

import io.appium.java_client.android.AndroidTouchAction;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;
/**
 * 首页
 * @author Administrator
 *
 */
public class PageOfMain {
	Helper helper;

	public PageOfMain(Helper helper) {		
		this.helper = helper;
	}
	
	public PageOfMain() {
		super();
	}	
	
	
	/**
	 * 底部导航区的首页元素
	 * @return
	 */
	public WebElement getIndex() {
		return helper.findById("com.tencent.southpole.appstore:id/tab_home");
	}
	/**
	 * 首页顶部:新游 
	 * @return
	 */
	public WebElement getNewGame() {
		return helper.findById("com.tencent.southpole.appstore:id/tag_layout_1");
	}
	/**
	 * 首页顶部:福利
	 * @return
	 */
	public WebElement getBoon() {
		return helper.findById("com.tencent.southpole.appstore:id/tag_layout_2");
	}
	/**
	 * 福利界面-一键领取按钮
	 */
	public WebElement getBoonOnekeyGet() {		
		return helper.findById("com.tencent.southpole.appstore:id/button_receive_all");
	}
	/**
	 * 领取按钮
	 * @return
	 */
	public WebElement getReceiveOfOne() {
		return helper.findById("com.tencent.southpole.appstore:id/receive_button");
	}
	/**
	 * 首页顶部:榜单
	 * @return
	 */
	public WebElement getList() {
		return helper.findById("com.tencent.southpole.appstore:id/tag_layout_3");
	}
	/**
	 * 榜单排名第一的应用
	 */
	public WebElement getListIndex1() {
		return helper.findById("com.tencent.southpole.appstore:id/app_1_icon");
	}
	/**
	 * 榜单界面下的：流行榜
	 * @return
	 */
	public WebElement getList_FashionList(){
		return helper.findByUiautomatorText("流行榜");
	}
	/**
	 * 榜单界面下的：热销榜
	 * @return
	 */
	public WebElement getList_HotAppsList() {
		return helper.findByUiautomatorText("热销榜");
	}
	/**
	 * 榜单界面下的：新品榜
	 * @return
	 */
	public WebElement getList_NewProduct() {
		return helper.findByUiautomatorText("新品榜");
	}
	/**
	 * 榜单界面中的下载按钮
	 * @return
	 */
	public WebElement getList_DownBtn() {
		return helper.findById("com.tencent.southpole.appstore:id/download_button");
	}
	/**
	 * 首页顶部:分类
	 * @return
	 */
	public WebElement getClassify() {
		return helper.findById("com.tencent.southpole.appstore:id/tag_layout_4");
	}
	/**
	 * 分类界面下的软件分类
	 * @return
	 */
	public WebElement getClassify_Soft() {
		return helper.findByXpath("//androidx.appcompat.app.ActionBar.Tab[@content-desc=\"软件\"]");
	}
		
	/**
	 * 首页下载按钮
	 * @return
	 */
	public WebElement getDownBtn() {
		return helper.findById("com.tencent.southpole.appstore:id/download");
	}
	/**
	 * 首页:搜索
	 * @return
	 */
	public WebElement getSearchContext() {
		return helper.findById("com.tencent.southpole.appstore:id/search_input");
	}
	/**
	 * 搜索按钮
	 */
	public WebElement getSearchBtn() {
		return helper.findById("com.tencent.southpole.appstore:id/search_confirm");
	}
	/**
	 * 搜索结果 首个元素
	 */
	public WebElement getSerachResultFirstApp() {
		return helper.findByXpath("//*[@resource-id='com.tencent.southpole.appstore:id/search_result_appname']");
	}
	/**
	 * appium无法获取动态界面上的元素 所以需要将动态的顶部栏，滑动至不可见
	 */
	public void swipeHideTheBar() {
		new AndroidTouchAction(helper.getAndroidDriver()).press(PointOption.point(500, 1550))
		.waitAction(WaitOptions.waitOptions(Duration.ofSeconds(2)))
		.moveTo(PointOption.point(535, 807)).release().perform();		
	}
	
	/**
	 * 热搜标签
	 */
	public WebElement getHotSearchLabel() {
		return helper.findById("com.tencent.southpole.appstore:id/search_hot_title");
	}	
}
