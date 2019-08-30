## 一.使用前必读
* 请先安装Node.js环境,并保证为可用状态,关于Node.js的安装,请自行搜索相关网页,附中文网址：http://nodejs.cn/ 
* JDK1.8版本
* ADB环境(在CMD下输入adb即可)
* 打开CMD窗口,执行:npm install appium@1.9.1 -g(如果Npm安装失败,请将npm镜像替换为taobao镜像 
具体方法:https://blog.csdn.net/bbatyki/article/details/82918851) 
* 项目测试使用的appium为1.9.1版本,其它版本不保证兼容,如果想安装其它版本,可将1.9.1替换为相应版本号即可 
* 安装IDE工具,Eclipse&IDEA均可,本工程是使用的Eclipse(请搭配好maven环境,不然相关JAR包无法导入,)
* 安装TestNG支持

## 二.如何使用
* domain包下的文件,均为业务执行的逻辑
* 项目中有二个java文件,其中一个是:WeakNetwork.java,这个文件是我所对应项目的弱网功能测试脚本,脚本中所调用的方法,均由com.meitu.service包下的WeakNetworkService.java来实现的,分层是为了维护方便
* WeakNetworkService.java中持用Helper的引用,而Helper中 集成了常用的元素查找,滑动,截图等一些用常功能(可以简单理解为helper是为了和androidDriver打交道的),具体请自己查看 
* 整个脚本的实现流程:domain层(业务流程的拼接执行)----调用----service(业务的实现)----调用----Helper,如果Helper中默认提供的方法不不满足你的需要,你可以自己实现方法

## 三. 脚本执行必不可少的cases.xls文件
* 参数说明(以下二个参数是必要参数,一定要指定正确)
  * appPackage:指定要启动应用的包名
  * appActivity:指定activity参数
* step:任意,一般指定为执行的步骤名称
* operation:固定,仅支持:method
* type:这个参数需要额外说明,首先,domain包下的文件名,都需要在cases.xls中,有一个对应的sheet名称,如我的弱网项目叫WeakNetwork.java,那我需要将sheet名称改为:WeakNetwork(区分大小写,一定要写对),如果你有多个domain,那就写多个sheet
* arg:目前不用了
* text:目前不用了
* expect:true或false(就是你期待这个模块是失败还是成功)
## 四.代码中需要注意的地方
* 1.com.meitu.test/TestCl.java这个文件是项目的入口
    	*       sheetNameList.add("WeakNetwork");		---指定你要测试的模块,支持多个模块
	*	driverEntity.setPath(JustinUtil.getRootPathCase()+"\\cases.xls");----不用改,默认指向工程目录下的case文件夹
	*	driverEntity.setPort("4723"); ----不用改,默认是4723
	*	driverEntity.setUdid("ZS60A19404A00132");		----手机设备ID,通过adb devices查看,根据自己的参数来指定
	*	driverEntity.setVersion("9.0"); ----安卓版本
	*	driverEntity.setSheetNameList(sheetNameList);	----不用改
## 五.一个简单Demo
* 有一个测试项目,叫HelloWorld,那么就在domian包下建立一个叫HelloWorld.java文件,在service包中建立一个HelloWorldService.java
* 业务测试中需要点击一个叫Hello的按钮,在HelloWorldService.java中编写一个方法
```javascript
 public void clickHelloButton(){
 	helper.click(helper.find_by_id("id/hello"),"Hello按钮");
 }	
  
```
* 在HelloWorld.java中编写一个方法
```javascript
 public void clickHelloButton(){
 	HelloWorldService.clickHelloButton();
 }
  
```
* 打开case目录下的cases.xls文件,添加appPackagename和activity参数
	* 将sheetName改为:HelloWorld
	* step:测试Hello
	* operation:method
	* type:clickHelloButton(说明:这个参数是HelloWorld.java中的方法,而不是service层中的方法,如果指定的方法不存在,将会执行失败,并抛异常)
	* arg:不用指定,留空
	* text:不用指定,留空
	* expect:true
* 打开com.meitu.test/TestCl.java
	* 指定sheetNameList.add("HelloWorld");
	* driverEntity.setUdid("你的ID");
	* 右键,执行TestNG



