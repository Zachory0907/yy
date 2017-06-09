package vip.zgt.app.web.controller;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jfinal.aop.Before;
import com.jfinal.ext.route.ControllerBind;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.upload.UploadFile;

import vip.zgt.app.base.BaseController;
import vip.zgt.app.base.annotation.AuthAnnotation;
import vip.zgt.app.biz.Gt3;
import vip.zgt.app.biz.Tools;
import vip.zgt.app.util.Consts;
import vip.zgt.app.util.ExecutExcel;
import vip.zgt.app.util.FileHandler;
import vip.zgt.app.util.GetDDL;
import vip.zgt.app.util.JSONUtil;
import vip.zgt.app.util.Utils;
import vip.zgt.app.web.interceptor.AuthInceptor;

@ControllerBind(controllerKey = "/Tools", viewPath = Consts.VIEW_PATH + "/tools")
public class ToolsController extends BaseController {

	@Before({ AuthInceptor.class })
	@AuthAnnotation({ "YY", "VIP" })
	public void getDDL() {
		render("getDDL.html");
	}

	@Before({ AuthInceptor.class })
	@AuthAnnotation({ "YY" })
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

	@Before({ AuthInceptor.class })
	@AuthAnnotation({ "YY" })
	public void postExcel() throws Exception {
		UploadFile uf = getFile("file");
		String fileName = uf.getFileName();
		String filePath = uf.getUploadPath();
		int suc = 0;
		int fail = 0;
		String[] headList = { "tablename", "gt3name", "filename" };
		List<List<Map<String, Object>>> content = ExecutExcel.readFromSingleExcel(filePath + File.separator + fileName, 0, 1, 0, headList);
		for (List<Map<String, Object>> rows : content) {
			String tablename = "";
			String filename = "";
			String fileRootPath = Utils.getAttachmentDir("ddls");
			filename = filename + fileRootPath;
			for (Map<String, Object> cols : rows) {
				for (Map.Entry<String, Object> v : cols.entrySet()) {
					if (v.getKey().equals("tablename")) {
						tablename = (String) v.getValue();
					}
					if (v.getKey().equals("filename")) {
						filename = filename + File.separator + (String) v.getValue() + ".txt";
					}
				}
			}
			File f = new File(filename);
			String ddl = GetDDL.getCreateOnly(tablename, "taxinspect");
			ddl = ddl.replaceAll("\"TAXINSPECT\".", "");
			if (FileHandler.createFile(f)) {
				if (FileHandler.writeToFile(ddl, f)) {
					System.out.println(tablename + "创建成功！");
					suc += 1;
				} else {
					System.out.println(tablename + "创建失败！");
					fail += 1;
				}
			} else {
				System.out.println(tablename + "创建失败！");
				fail += 1;
			}
		}
		Map<String, Integer> res = new HashMap<String, Integer>();
		res.put("success", suc);
		res.put("failed", fail);
		renderJson(res);
	}
	
	@Before({ AuthInceptor.class })
	@AuthAnnotation({ "YY", "VIP" })
	public void getKtr() {
		render("getKtr.html");
	}

	@Before({ AuthInceptor.class })
	@AuthAnnotation({ "YY" })
	public void maleKtrByTablename() throws Exception {
		String tablename = getPara("tbname");
		List<Record> res = Gt3.searchTableMxByTablename(tablename);
		List<Record> tabMsg = Gt3.searchTableByTablename(tablename);
		Map<String, Object> map = new HashMap<String, Object>();
		if (res.size() > 0 && tabMsg.size() > 0){
			map.put("status", "ok");
			map.put("fields", res);
			map.put("tabMsg", tabMsg.get(0));
			String ktrTemplatePath = this.getSession().getServletContext().getRealPath("/WEB-INF/classes/ktrTemplate.ktr");
			String ktrFile = Tools.makeKtr(res, tabMsg, ktrTemplatePath);
			map.put("ktrFile", ktrFile);
			String filename = "";
			String fileRootPath = Utils.getAttachmentDir("ktrs");
			filename = filename + fileRootPath + File.separator + tabMsg.get(0).getStr("NAME_ZH") + ".ktr";
			File f = new File(filename);
			if(FileHandler.writeToFile(ktrFile, f)) {
				map.put("filePath", filename);
			}
		} else {
			map.put("status", "nodata");
			map.put("fields", "未发现响应表！");
		}
		renderJson(map);
	}
	
	@Before({ AuthInceptor.class })
	@AuthAnnotation({ "YY" })
	public void exportKtr(){
		String filePath = getPara("filePath");
		File f = new File(filePath);
		renderFile(f);
	}
	
	@Before({ AuthInceptor.class })
	@AuthAnnotation({ "YY" })
	public void postKtrExcel() throws Exception {
		UploadFile uf = getFile("file");
		String fileName = uf.getFileName();
		String filePath = uf.getUploadPath();
		int suc = 0;
		int fail = 0;
		String[] headList = { "tablename", "gt3name", "filename" };
		List<List<Map<String, Object>>> content = ExecutExcel.readFromSingleExcel(filePath + File.separator + fileName, 0, 1, 0, headList);
		for (List<Map<String, Object>> rows : content) {
			String tablename = "";
			String filename = "";
			String fileRootPath = Utils.getAttachmentDir("ktrs");
			filename = filename + fileRootPath;
			for (Map<String, Object> cols : rows) {
				for (Map.Entry<String, Object> v : cols.entrySet()) {
					if (v.getKey().equals("tablename")) {
						tablename = (String) v.getValue();
					}
					if (v.getKey().equals("filename")) {
						filename = filename + File.separator + (String) v.getValue() + ".ktr";
					}
				}
			}
			File f = new File(filename);
			List<Record> res = Gt3.searchTableMxByTablename(tablename);
			List<Record> tabMsg = Gt3.searchTableByTablename(tablename);
			String ktrTemplatePath = this.getSession().getServletContext().getRealPath("/WEB-INF/classes/ktrTemplate.ktr");
			String ktrFile = null;
			if (res.size() > 0 && tabMsg.size() > 0){
				ktrFile = Tools.makeKtr(res, tabMsg, ktrTemplatePath);
			}
			if (ktrFile == null) {
				System.out.println(tablename + "创建失败！");
				fail += 1;
			} else if(FileHandler.createFile(f)) {
				if (FileHandler.writeToFile(ktrFile, f)) {
					System.out.println(tablename + "创建成功！");
					suc += 1;
				} else {
					System.out.println(tablename + "创建失败！");
					fail += 1;
				}
			} else {
				System.out.println(tablename + "创建失败！");
				fail += 1;
			}
		}
		Map<String, Integer> res = new HashMap<String, Integer>();
		res.put("success", suc);
		res.put("failed", fail);
		renderJson(res);
	}
}
