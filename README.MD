## 一.使用前必读
* 使用前，请安装Node.js环境,并保证为可用状态,关于Node.js的安装,请自行搜索相关网页,附中文网址：http://nodejs.cn/ 
* 打开CMD窗口,执行:npm install appium@1.9.1 -g(如果Npm安装失败,请将npm镜像替换为taobao镜像 
具体方法:https://blog.csdn.net/bbatyki/article/details/82918851) 
* 项目测试使用的appium为1.9.1版本,其它版本不保证兼容,如果想安装其它版本,可将1.9.1替换为相应版本号即可 
* 安装IDE工具,Eclipse&IDEA均可,本工程是使用的Eclipse(请搭配好maven环境,不然相关JAR包无法导入)

## 二.如何使用
* com.meitu.domain包下的文件,均为脚本的具体实现
* 项目中有二个java文件,其中一个是:WeakNetwork.java,这个文件是我所对应项目的弱网功能测试脚本,脚本中所调用的方法,均由com.meitu.service包下的WeakNetworkService.java来实现的,分层是为了维护方便
* WeakNetworkService.java中持用Helper的引用,而Helper中 集成了常用的元素查找,滑动,截图等一些用常功能(可以简单理解为helper是为了和androidDriver打交道的),具体请自己查看 
* 所以整个脚本的实现流程就是:domain层(业务流程的拼接执行)----调用----service(业务的实现)----调用----Helper(Helper与androidDriver交互 实现元素查看,点击,截图等一列表操作),如果Helper中默认提供的方法不不满足你的需要,你可以自己实现方法

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
## 五.以上搞定后,可以跑起来了,如果脚本使用过程中有问题,可加我QQ:493241246或邮件给我

