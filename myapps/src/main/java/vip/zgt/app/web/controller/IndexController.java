package vip.zgt.app.web.controller;

import java.util.List;

import com.jfinal.aop.Before;
import com.jfinal.ext.route.ControllerBind;
import com.jfinal.plugin.activerecord.DbPro;
import com.jfinal.plugin.activerecord.Record;

import vip.zgt.app.base.BaseController;
import vip.zgt.app.base.annotation.AuthAnnotation;
import vip.zgt.app.util.Consts;
import vip.zgt.app.web.interceptor.AuthInceptor;
import vip.zgt.app.web.interceptor.TestInceptor;

@ControllerBind(controllerKey = "/", viewPath = Consts.VIEW_PATH + "/main")
public class IndexController extends BaseController {
	
	@Before({AuthInceptor.class})
	@AuthAnnotation({"YY", "FULL", "VIP"})
	public void index() {
		render("index.html");
	}
	
	@Before({AuthInceptor.class, TestInceptor.class})
	@AuthAnnotation
	public void test() {
		List<Record> res1 = DbPro.use(Consts.ARP_NAME_YY).find("select * from test");
		System.out.println("res1:" + res1.toString());
		List<Record> res2 = DbPro.use(Consts.ARP_NAME_TEST).find("select * from test");
		System.out.println("res2:" + res2.toString());
		render("index.html");
	}
}
