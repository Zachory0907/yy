package vip.zgt.app.web.controller;

import java.util.HashMap;
import java.util.Map;

import com.jfinal.aop.Before;
import com.jfinal.ext.route.ControllerBind;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.upload.UploadFile;

import vip.zgt.app.base.BaseController;
import vip.zgt.app.base.annotation.AuthAnnotation;
import vip.zgt.app.biz.Gt3;
import vip.zgt.app.util.Consts;
import vip.zgt.app.util.Solrj;
import vip.zgt.app.web.interceptor.AuthInceptor;


@ControllerBind(controllerKey = "/Gt3", viewPath = Consts.VIEW_PATH + "/gt3")
public class Gt3Controller extends BaseController {
	
	@Before({AuthInceptor.class})
	@AuthAnnotation({"YY", "VIP"})
	public void adSearch() {
		render("hxzgAdsearch.html");
	}
	
	@Before({AuthInceptor.class})
	@AuthAnnotation({"YY", "VIP"})
	public void executeAdSearch() {
		String content = getPara("content");
		if (content.equals("")) {
			content = "*";
		}
		Integer p = getParaToInt("p");
		Integer l = getParaToInt("l");
		String queryStr = "yy_gt3_all:" + content;
		renderJson(Solrj.queryPage(queryStr, p, l));
	}
	
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
		} else if (type.equals("ywbzd")){
			headList = bzdHead;
			tableName = "YY_GT3_QC_TBFIELD";
		} else if (type.equals("dmbmc")){
			headList = bmcHead;
			tableName = "YY_GT3_QC_TBNAME";
		} else if (type.equals("dmbzd")){
			headList = bzdHead;
			tableName = "YY_GT3_QC_TBFIELD";
		}
		Gt3.saveExcel(uf, type, headList, tableName);
		redirect("/Gt3/hxzgqc");
	}
	
	@Before({AuthInceptor.class})
	@AuthAnnotation({"YY"})
	public void getTable() throws Exception {
		String type = getPara("v");
		int p = getParaToInt("p");
		int l = getParaToInt("l");
		renderJson(Gt3.getTable(type, p, l));
	}
	
	@Before({AuthInceptor.class})
	@AuthAnnotation({"YY"})
	public void getField() throws Exception {
		String tb = getPara("tb");
		renderJson(Gt3.getField(tb));
	}
	
	@Before({AuthInceptor.class})
	@AuthAnnotation({"YY"})
	public void getDDL() throws Exception {
		String tb = getPara("tb");
		String ddl = Gt3.getDDL(tb);
		Map<String, String> map = new HashMap<String, String>();
		map.put("ddl", ddl);
		renderJson(map);
	}
	
	@Before({AuthInceptor.class})
	@AuthAnnotation({"YY"})
	public void getDDLs() throws Exception {
		Gt3.getDDLs();
		renderok();
	}
	
	@Before({AuthInceptor.class})
	@AuthAnnotation({"YY"})
	public void getTableMx() {
		String tb = getPara("tb");
		Record rec = Gt3.getTableMx(tb);
		if (rec == null) {
			rendererror();
		} else {
			renderJson(rec);
		}
	}
	
}
