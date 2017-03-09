package vip.zgt.app.biz;

import java.util.HashMap;
import java.util.Map;

import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Record;

import vip.zgt.app.base.BaseBiz;
import vip.zgt.app.model.MailModel;
import vip.zgt.app.util.MD5;
import vip.zgt.app.util.Mail;

public class Login extends BaseBiz{

	public static Record loginCheck(String uname, String pwd) {
		pwd = MD5.String2MD5(pwd);
		String sql = "SELECT ID, ISCHECK, MAIL, UNAME FROM YY_USER WHERE (UNAME=? AND PWD=?) OR (MAIL=? AND PWD=?)";
		return getYYPro().findFirst(sql, uname, pwd, uname, pwd);
	}

	public static Integer sendMail(Record r) {
		int rand = (int)((Math.random()*9+1)*100000);
		String content = "尊敬的" + r.getStr("UNAME") + "账户您好，您的激活号码为：" + rand + ", 5分钟内有效。<br />如非本人操作，请忽略";
		MailModel mm = new MailModel(r.getStr("MAIL"), r.getStr("UNAME"), "用户激活", content);	
		try {
			Mail.sendMsg(mm);
			return rand;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	public static boolean handleMail(Controller controller, Record r) {
		Map<String, Object> map = null;
		map = (Map<String, Object>) controller.getSession().getAttribute("MailCode");
		if (map == null) {
			map = new HashMap<String, Object>();
		}
		Integer rand = sendMail(r);
		if (rand != null) {
			map.put("code", rand);
			map.put("timestamp", System.currentTimeMillis());
			controller.getSession().setAttribute("MailCode", map);
			return true;
		} else {
			return false;
		}
	}

	public static int activate(Controller controller, String code) {
		@SuppressWarnings("unchecked")
		Map<String, Object> map = (Map<String, Object>) controller.getSession().getAttribute("MailCode");
		if (map == null) {
			return 0;
		} else {
			int lastCode = (int) map.get("code");
			long lastMail = (long) map.get("timestamp");
			long now = System.currentTimeMillis();
			float value = (now - lastMail)/1000;//秒
			if (value > 300) {
				return 1;
			} else if (Integer.valueOf(code) != lastCode){
				return 2;
			} else {
				return 0;
			}
		}
	}

}