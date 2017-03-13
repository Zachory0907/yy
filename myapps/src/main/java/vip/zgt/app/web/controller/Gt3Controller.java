package vip.zgt.app.web.controller;

import com.jfinal.aop.Before;
import com.jfinal.ext.route.ControllerBind;
import com.jfinal.upload.UploadFile;

import vip.zgt.app.base.BaseController;
import vip.zgt.app.base.annotation.AuthAnnotation;
import vip.zgt.app.biz.Gt3;
import vip.zgt.app.util.Consts;
import vip.zgt.app.web.interceptor.AuthInceptor;

@ControllerBind(controllerKey = "/Gt3", viewPath = Consts.VIEW_PATH + "/gt3")
public class Gt3Controller extends BaseController {
	
	@Before({AuthInceptor.class})
	@AuthAnnotation({"YY", "VIP"})
	public void hxzgqc() {
		render("hxzgqc.html");
	}
	
	@Before({AuthInceptor.class})
	@AuthAnnotation({"YY"})
	public void postExcel() throws Exception {
		UploadFile uf = getFile("file");
		String type = getMultiPara("type");
		type = type.substring(type.indexOf(":")+1);
		String[] bmcHead = {"OWNER", "USER", "NAME_EN", "NAME_ZH", "FIELDS", "BZ"};
		String[] bzdHead = {"NAME_EN", "NAME_ZH", "FIELD_EN", "FIELD_ZH", "FIELD_TYPE", "PROPERTY", "IS_PRIMARY", "IS_NULL", "EXT"};
		String tableName = null;
		Object headList = null;
		if (type.equals("ywbmc")) {
			headList = bmcHead;
			tableName = "YY_GT3_QC_TBNAME";
			type = "yw";
		} else if (type.equals("ywbzd")){
			headList = bzdHead;
			tableName = "YY_GT3_QC_TBFIELD";
			type = "yw";
		} else if (type.equals("dmbmc")){
			headList = bmcHead;
			tableName = "YY_GT3_QC_TBNAME";
			type = "dm";
		} else if (type.equals("dmbzd")){
			headList = bzdHead;
			tableName = "YY_GT3_QC_TBFIELD";
			type = "dm";
		}
		Gt3.saveExcel(uf, type, headList, tableName);
		redirect("/Gt3/hxzgqc");
	}
	
	@Before({AuthInceptor.class})
	@AuthAnnotation({"YY"})
	public void getTable() throws Exception {
		String type = getPara("type");
		renderJson(Gt3.getTable(type));
	}
	
}
