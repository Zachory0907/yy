package vip.zgt.app.biz;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.jfinal.plugin.activerecord.Record;
import com.jfinal.upload.UploadFile;

import vip.zgt.app.base.BaseBiz;
import vip.zgt.app.util.ExecutExcel;

/**
 * @author Zachory
 */
public class Gt3 extends BaseBiz{

	public static void save(List<String> sqls, String tableName) {
//		getYYPro().batch(sqls, sqls.size());
		for (String sql : sqls) {
			System.out.println(sql);
			getYYPro().update(sql);
		}
		List<Record> rec = getYYPro().find("select * from " + tableName);
		for (Record r : rec) {
			System.out.println(r.getStr("NAME_ZH"));
		}
	}

	public static void saveExcel(UploadFile uf, String type, Object headList, String tableName) {
		String fileName = uf.getFileName();
		String filePath = uf.getUploadPath();
		List<String> sqls = new ArrayList<String>(); 
		List<List<Map<String, Object>>> content = ExecutExcel.readFromSingleExcel(filePath + File.separator + fileName, 0, 1, 1, (String[])headList);
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
			sbValues.append("\"" + type + "\"");
			String reskeys = sbkeys.toString();
			String resvalues = sbValues.toString();
			sqls.add("INSERT INTO " + tableName + "(" + reskeys + ") VALUES (" + resvalues + ")");
		}
		Gt3.save(sqls, tableName);
	}

}
