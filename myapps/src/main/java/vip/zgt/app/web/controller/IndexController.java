package vip.zgt.app.web.controller;

import com.jfinal.core.Controller;
import com.jfinal.ext.route.ControllerBind;
import com.jfinal.plugin.activerecord.Db;

import vip.zgt.app.util.Consts;

@ControllerBind(controllerKey = "/", viewPath = Consts.VIEW_PATH + "/main")
public class IndexController extends Controller{

	public void index(){
		Db.find("select * from vote");
		render("index.html");
	}
}
