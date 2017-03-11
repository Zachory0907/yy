package vip.zgt.app.web.controller;

import com.jfinal.aop.Before;
import com.jfinal.ext.route.ControllerBind;

import vip.zgt.app.base.BaseController;
import vip.zgt.app.base.annotation.AuthAnnotation;
import vip.zgt.app.util.Consts;
import vip.zgt.app.web.interceptor.AuthInceptor;

@ControllerBind(controllerKey = "/Gt3", viewPath = Consts.VIEW_PATH + "/gt3")
public class Gt3Controller extends BaseController {
	
	@Before({AuthInceptor.class})
	@AuthAnnotation({"YY", "FULL", "VIP"})
	public void hxzgqc() {
		render("hxzgqc.html");
	}
	
}
