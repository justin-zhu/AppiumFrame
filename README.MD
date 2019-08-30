# AppiumProject
一.使用前必读
1.使用前，请安装Node.js环境,并保证为可用状态,关于Node.js的安装,请自行搜索相关网页,附中文网址：http://nodejs.cn/
2.打开CMD窗口,执行:npm install appium@1.9.1 -g(如果Npm安装失败,请将npm镜像替换为taobao镜像
具体方法:https://blog.csdn.net/bbatyki/article/details/82918851)
3.项目测试使用的appium为1.9.1版本,其它版本不保证兼容,如果想安装其它版本,可将1.9.1替换为相应版本号即可
4.安装IDE工具,Eclipse&IDEA均可,本工程是使用的Eclipse(请搭配好maven环境,不然相关JAR包无法导入)

二.如何使用
1.com.meitu.domain包下的文件,均为脚本的具体实现,打个比方:WeakNetwork.java是我所对应项目的弱网功能测试脚本,脚本中所调用的
方法,均由com.meitu.service包下的WeakNetworkService.java来实现的,分层是为了维护方便,WeakNetworkService.java中持用Helper的引用,而Helper中
集成了常用的元素查找,滑动,截图等一些用常功能,具体请自己查看
所以整个脚本的实现流程就是:com.meitu.domain(业务流程的拼接执行)----调用----com.meitu.service(业务的实现)----调用----Helper(Helper与androidDriver交互
实现元素查看,点击,截图等一列表操作)

三.脚本执行必不可少的case.xml文件