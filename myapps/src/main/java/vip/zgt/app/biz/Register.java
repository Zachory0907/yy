package vip.zgt.app.biz;

import java.util.List;

import com.jfinal.plugin.activerecord.Record;

import vip.zgt.app.base.BaseBiz;
import vip.zgt.app.util.MD5;

public class Register extends BaseBiz{

	public static List<Record> duplicate (String k, String v) {
		String sql = "SELECT * FROM YY_USER WHERE " + k + "='" + v + "'";
		System.out.println(sql);
		return getYYPro().find(sql);
	}

	public static int registUser(String uname, String mail, String pwd) {
		pwd = MD5.String2MD5(pwd);
		String userSql = "INSERT INTO YY_USER(ID, UNAME, MAIL, PWD, ISCHECK) VALUES (NULL, ?, ?, ?, ?)";
		getYYPro().update(userSql, uname, mail, pwd, 0);
		String authSql = "INSERT INTO YY_AUTH(ID, UNAME, AUTH) VALUES (NULL, ?, 'YY')";
		getYYPro().update(authSql, uname);
		return 1;
	}

	public static int activate(String userid) {
		String sql = "UPDATE YY_USER SET ISCHECK=1 WHERE ID=?";
		return getYYPro().update(sql, userid);
	}

}
