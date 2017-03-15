package vip.zgt.app.base;

import com.jfinal.plugin.activerecord.DbPro;

import vip.zgt.app.util.Consts;

public class BaseBiz {

	protected static DbPro getYYPro(){
		return DbPro.use(Consts.ARP_MYSQL_YY);
	}
	
	protected static DbPro getTestPro(){
		return DbPro.use(Consts.ARP_MYSQL_TEST);
	}
	
	protected static DbPro getOraclePro(){
		return DbPro.use(Consts.ARP_ORACLE_GT3);
	}
}
