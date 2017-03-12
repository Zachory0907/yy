1、先确认mysql数据库配置文件的编码：
	1.1 停掉mysql服务
	1.2 进入mysql的安装目录下进入my.ini文件：***/mysql/my.ini
	1.3 查看：[mysql] default-character-set=utf8 ，如果编码不是utf8，写成utf8（注意，以后配置中均为utf8而不是utf-8，没有“-”）
2、进入mysql确认编码：
	2.1 开启myql服务
	2.2  分别输入： show database 数据库名；和 show create table 表名；看看是否为utf-8
	2.3 不是的话 alter database 数据库名 character set “utf8”； 命令来修改数据库字符集
	