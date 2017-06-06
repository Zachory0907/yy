package vip.zgt.app.util;

import org.apache.log4j.Logger;

import com.jfinal.plugin.activerecord.Record;

import vip.zgt.app.base.BaseBiz;

/**
 * 获得数据库的DDL语句：增删改查
 * @author 赵郭桃
 */
public class GetDDL extends BaseBiz{
	
	private static final Logger log = Logger.getLogger(GetDDL.class);
	
	/**
	 * 只是根据表名称获得数据库的建表语句
	 * @param tablename 表名
	 * @param username 表所在的用户名
	 * @return
	 */
	public static String getCreateOnly(String tablename, String username){
		String createSql = "";
		String sql = "SELECT DBMS_METADATA.GET_DDL('TABLE', '" + tablename.toUpperCase() + "', '" + username.toUpperCase() + "') AS fullSql FROM DUAL";
		log.info(sql);
		try {
			Record rec = getOraclePro().findFirst(sql);
			String fullSql = rec.getStr("FULLSQL");
			createSql = utilCreate(fullSql);
		} catch (Exception e) {
			createSql = e.getMessage();
		}
		return createSql;
	}
	
	public static String utilCreate(String sql){
		int sig = 0;
		int sigCount = 0;
		for (int i=0; i<sql.length(); i++) {
			if(sql.charAt(i) == '('){
				sig ++;
				sigCount ++;
			}else if(sql.charAt(i) == ')') {
				sig --;
			}
			if (sig == 0 && sigCount > 0) {
				return sql.substring(0, i+1);
			}
		}
		return null;
	}

}
