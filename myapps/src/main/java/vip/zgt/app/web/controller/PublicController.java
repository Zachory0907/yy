package vip.zgt.app.web.controller;

import com.jfinal.ext.route.ControllerBind;
import com.jfinal.plugin.activerecord.Record;

import vip.zgt.app.base.BaseController;
import vip.zgt.app.util.Consts;
import vip.zgt.app.util.JSONUtil;

@ControllerBind(controllerKey = "/pub", viewPath = Consts.VIEW_PATH + "/pub")
public class PublicController extends BaseController{

	public void index() {
		render("login.html");
	}
	
	public void loginCheck() {
		getSession(true).setAttribute("username", "FULL");
		renderJson("{\"status\":\"ok\"}");
	}
	
	public void a () {
		render("a.html");
	}
	
	public void regist () {
		render("regist.html");
	}
	
	public void isDuplicate () {
		Record rec = JSONUtil.parseRecord(getPostData());
		String name = rec.getStr("uname");
		String email = rec.getStr("madil");
		renderJson("{\"status\":\"ok\"}");
	}
	
	public void registUser () {
		System.out.println("reisting...");
		renderJson("{\"status\":\"ok\"}");
	}
	
}
