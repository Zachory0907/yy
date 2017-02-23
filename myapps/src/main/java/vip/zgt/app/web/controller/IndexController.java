package vip.zgt.app.web.controller;

import java.util.List;

import com.jfinal.core.Controller;
import com.jfinal.ext.route.ControllerBind;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;

import vip.zgt.app.util.Consts;

@ControllerBind(controllerKey = "/", viewPath = Consts.VIEW_PATH + "/main")
public class IndexController extends Controller{

	public void index(){
		List<Record> res = Db.find("select * from test");
		System.out.println(res.toString());
		render("index.html");
	}
}
