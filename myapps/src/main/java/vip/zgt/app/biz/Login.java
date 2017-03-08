package vip.zgt.app.biz;

import com.jfinal.plugin.activerecord.Record;

import vip.zgt.app.base.BaseBiz;
import vip.zgt.app.model.MailModel;
import vip.zgt.app.util.MD5;
import vip.zgt.app.util.Mail;

public class Login extends BaseBiz{

	public static Record loginCheck(String uname, String pwd) {
		pwd = MD5.String2MD5(pwd);
		String sql = "SELECT ISCHECK FROM YY_USER WHERE (UNAME=? AND PWD=?) OR (MAIL=? AND PWD=?)";
		return getYYPro().findFirst(sql, uname, pwd);
	}

	public static Integer sendMail(Record r) {
		int rand = (int)((Math.random()*9+1)*100000);
		String content = "尊敬的" + r.getStr("UNAME") + "账户您好，您的激活号码为：" + rand + ", 5分钟内有效。";
		MailModel mm = new MailModel(r.getStr("MAIL"), r.getStr("UNAME"), "用户激活", content);	
		try {
			Mail.sendMsg(mm);
			return rand;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
