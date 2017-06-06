package vip.zgt.app.web.controller;

import java.util.HashMap;
import java.util.Map;

import com.jfinal.aop.Before;
import com.jfinal.ext.route.ControllerBind;
import com.jfinal.plugin.activerecord.Record;

import vip.zgt.app.base.BaseController;
import vip.zgt.app.base.annotation.AuthAnnotation;
import vip.zgt.app.biz.Gt3;
import vip.zgt.app.util.Consts;
import vip.zgt.app.util.GetDDL;
import vip.zgt.app.util.JSONUtil;
import vip.zgt.app.web.interceptor.AuthInceptor;


@ControllerBind(controllerKey = "/Tools", viewPath = Consts.VIEW_PATH + "/tools")
public class ToolsController extends BaseController {
	
	
	@Before({AuthInceptor.class})
	@AuthAnnotation({"YY", "VIP"})
	public void getDDL() {
		render("getDDL.html");
	}
	
	@Before({AuthInceptor.class})
	@AuthAnnotation({"YY"})
	public void searchDDL() throws Exception {
		Record rec = JSONUtil.parseRecord(getPostData());
		String tablename = rec.getStr("tablename");
		String user = rec.getStr("user");
		String ddl = GetDDL.getCreateOnly(tablename, user);
		Map<String, String> map = new HashMap<String, String>();
		if (ddl == null) {
			map.put("ddl", "未查询到DDL");
		} else {
			map.put("ddl", ddl);			
		}
		renderJson(map);
	}
	
	@Before({AuthInceptor.class})
	@AuthAnnotation({"YY"})
	public void getDDLs() throws Exception {
		Gt3.getDDLs();
		renderok();
	}
	
}
