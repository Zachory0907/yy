#	A Tourguide for project
==========
一个自己慢慢玩儿的项目，可能会产出一些工具？
还没想好。。。
## Tools
----------
>	+ Maven version: 3.1.1 及以上    
>	+ Java version: 1.7 及以上    
>	+ Tomcat version: apache-tomcat-7 及以上    
>	+ Java IDE: 无所谓    
>	+ Git: 无所谓，代码托管在 Github 上了，能扣下来为原则    
>	+ Database: 没想好，暂用mysql 5.5.43了    

## Deploy
------------
>	+ 下载代码：`git clone https://github.com/Zachory0907/yy.git`    
>	+ 编译：`mvn clean package install -Dmaven.test.skip=true`    
>	+ 编译成IDE匹配的项目    
>	> 如果是eclipse项目：`mvn eclipse:eclipse`           
>	> 如果是idea项目：`mvn idea:idea`            
>	> 如果是其他的，就自行[百度](https://baidu.com "查看maven命令")吧    
>	+ 然后放到IDE里面开发就好了    

## Access
------------
>	+ 启动项目，访问 http://ip:port/myapps/