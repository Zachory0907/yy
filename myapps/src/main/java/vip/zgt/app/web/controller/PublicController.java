package vip.zgt.app.web.controller;

import com.jfinal.ext.route.ControllerBind;
import com.jfinal.plugin.activerecord.Record;

import vip.zgt.app.base.BaseController;
import vip.zgt.app.biz.Login;
import vip.zgt.app.biz.Register;
import vip.zgt.app.util.Consts;
import vip.zgt.app.util.JSONUtil;

/**
 * 系统前后交互的controller
 * 需配置controllerKey：浏览器地址；viewPath：controller与静态文件交互的地址
 * @author Zachory
 */
@ControllerBind(controllerKey = "/pub", viewPath = Consts.VIEW_PATH + "/pub")
public class PublicController extends BaseController {

	public void index() {
		render("login.html");
	}

	public void loginCheck() {
		Record rec = JSONUtil.parseRecord(getPostData());
		String uname = rec.getStr("uname");
		String pwd = rec.getStr("pwd");
		Record r = Login.loginCheck(uname, pwd);
		if (r == null) {
			renderJson("{\"status\":\"error\"}");
			return;
		}
		Integer res = r.getInt("ISCHECK");
		if (res == 1) {
			Login.setLoginInfo(this, r);
			renderJson("{\"status\":\"ok\"}");
		} else if (res == 0) {
			Login.setLoginInfo(this, r);
			if(Login.handleMail(this, r)) {
				renderJson("{\"status\":\"nocheck\",\"id\":" + r.getInt("ID") + ",\"mail\":\"" + r.getStr("MAIL") + "\"}");
			} else {
				renderJson("{\"status\":\"fatal\"}");
			}
		} else {
			renderJson("{\"status\":\"fatal\"}");
		}
	}
	
	public void activate() {
		String code = getPara("code");
		String userid = getPara("userid");
		int i = Login.activate(this, code);
		if (i == 2) {
			renderJson("{\"status\":\"wrong\"}");
		} else if (i == 1) {
			renderJson("{\"status\":\"outtime\"}");
		} else {
			Register.activate(userid);
			renderJson("{\"status\":\"ok\"}");
		}
	}

	public void regist() {
		render("regist.html");
	}

	public void isDuplicate() {
		String k = getPara("k");
		String v = getPara("v");
		if (Register.duplicate(k, v).size() > 0) {
			renderJson("{\"status\":\"error\"}");
		} else {
			renderJson("{\"status\":\"ok\"}");
		}
	}

	public void registUser() {
		Record rec = JSONUtil.parseRecord(getPostData());
		String uname = rec.getStr("uname");
		String mail = rec.getStr("mail");
		String pwd = rec.getStr("pwd");
		int count = Register.registUser(uname, mail, pwd);
		renderJson("{\"count\":\"" + count + "\"}");
	}
	
	public void resend() {
		String uname = getPara("uname");
		String mail = getPara("mail");
		Record r = new Record();
		r.set("UNAME", uname);
		r.set("MAIL", mail);
		if(Login.handleMail(this, r)) {
			renderJson("{\"status\":\"ok\"}");
		} else {
			renderJson("{\"status\":\"error\"}");
		}
	}

}
