package vip.zgt.app.web.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.jfinal.ext.route.ControllerBind;
import com.jfinal.plugin.activerecord.Record;

import vip.zgt.app.base.BaseController;
import vip.zgt.app.biz.Login;
import vip.zgt.app.biz.Register;
import vip.zgt.app.model.MailModel;
import vip.zgt.app.util.Consts;
import vip.zgt.app.util.JSONUtil;

@ControllerBind(controllerKey = "/pub", viewPath = Consts.VIEW_PATH + "/pub")
public class PublicController extends BaseController{

	public void index() {
		render("login.html");
	}
	
	public void loginCheck() {
		Map<String, String> result = new HashMap<String, String>();
		Record rec = JSONUtil.parseRecord(getPostData());
		String uname = rec.getStr("uname");
		String pwd = rec.getStr("pwd");
		Record r = Login.loginCheck(uname, pwd);
		Integer res = r.getInt("ISCHECK");
		if (res == null) {
			result.put("status", "error");
			renderJson(result);
		} else if (res == 1) {
			result.put("status", "ok");
			getSession(true).setAttribute("username", "FULL");
		} else if (res == 0) {
			Integer rand = Login.sendMail(r);
			if (rand != null) {
				result.put("status", "nocheck");
				result.put("rand", String.valueOf(rand));
				result.put("time", String.valueOf(System.currentTimeMillis()));
			} else {
				result.put("status", "fatal");
			}
			renderJson(result);
		}
	}
	
	public void a () {
		render("a.html");
	}
	
	public void regist () {
		render("regist.html");
	}
	
	public void isDuplicate () {
		String k = getPara("k");
		String v = getPara("v");
		if (Register.duplicate(k, v).size() > 0){
			renderJson("{\"status\":\"error\"}");
		} else {
			renderJson("{\"status\":\"ok\"}");
		}
	}
	
	public void registUser () {
		Record rec = JSONUtil.parseRecord(getPostData());
		String uname = rec.getStr("uname");
		String mail = rec.getStr("mail");
		String pwd = rec.getStr("pwd");
		int count = Register.registUser(uname, mail, pwd);
		renderJson("{\"count\":\"" + count + "\"}");
	}
	
}
