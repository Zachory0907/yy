package vip.zgt.app.web.controller;

import java.util.List;

import com.jfinal.core.Controller;
import com.jfinal.ext.route.ControllerBind;
import com.jfinal.plugin.activerecord.DbPro;
import com.jfinal.plugin.activerecord.Record;

import vip.zgt.app.util.Consts;

@ControllerBind(controllerKey = "/", viewPath = Consts.VIEW_PATH + "/main")
public class IndexController extends Controller{

	public void index(){
		List<Record> res1 = DbPro.use(Consts.ARP_NAME_YY).find("select * from test");
		System.out.println("res1:" + res1.toString());
		List<Record> res2 = DbPro.use(Consts.ARP_NAME_TEST).find("select * from test");
		System.out.println("res2:" + res2.toString());
		render("index.html");
	}
}
