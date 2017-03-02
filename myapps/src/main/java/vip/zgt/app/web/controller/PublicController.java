package vip.zgt.app.web.controller;

import com.jfinal.ext.route.ControllerBind;

import vip.zgt.app.base.BaseController;
import vip.zgt.app.util.Consts;

@ControllerBind(controllerKey = "/pub", viewPath = Consts.VIEW_PATH + "/pub")
public class PublicController extends BaseController{

	public void index() {
		render("login.html");
	}
	
	public void loginCheck() {
		System.out.println("aaa");
		redirect("/pub/a");
	}
	
	public void a () {
		render("a.html");
	}
	
}