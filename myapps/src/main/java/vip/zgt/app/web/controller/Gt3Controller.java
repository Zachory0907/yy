package vip.zgt.app.web.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.jfinal.aop.Before;
import com.jfinal.ext.route.ControllerBind;
import com.jfinal.upload.UploadFile;

import vip.zgt.app.base.BaseController;
import vip.zgt.app.base.annotation.AuthAnnotation;
import vip.zgt.app.biz.Gt3;
import vip.zgt.app.util.Consts;
import vip.zgt.app.util.ExecutExcel;
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
		String fileName = uf.getFileName();
		String filePath = uf.getUploadPath();
		List<String> sqls = new ArrayList<String>(); 
		String[] headList = {"OWNER", "USER", "NAME_EN", "NAME_ZH", "FIELDS"};
		List<List<Map<String, Object>>> content = ExecutExcel.readFromSingleExcel(filePath + File.separator + fileName, 0, 1, 1, headList);
		for (List<Map<String, Object>> rows : content) {
			StringBuffer sbkeys = new StringBuffer("ID,");
			StringBuffer sbValues = new StringBuffer("null,");
			for (Map<String, Object> cols: rows) {
				for(Map.Entry<String, Object> v : cols.entrySet()) {
					sbkeys.append(v.getKey() + ",");
					sbValues.append("\"" + String.valueOf(v.getValue() instanceof Double ? ((Double) v.getValue()).intValue():v.getValue()) + "\",");
				}
			}
			sbkeys.append("TYPE");
			sbValues.append("\"dm\"");
			String reskeys = sbkeys.toString();
			String resvalues = sbValues.toString();
			sqls.add("INSERT INTO YY_GT3_QC_NAME (" + reskeys + ") VALUES (" + resvalues + ")");
		}
		Gt3.save(sqls);
		redirect("/Gt3/hxzgqc");
	}
	
}
