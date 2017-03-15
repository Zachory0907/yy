package vip.zgt.app.biz;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.upload.UploadFile;

import vip.zgt.app.base.BaseBiz;
import vip.zgt.app.util.ExecutExcel;
import vip.zgt.app.util.GetDDL;

/**
 * @author Zachory
 */
public class Gt3 extends BaseBiz{

	public static void save(List<String> sqls, String tableName) {
		getYYPro().batch(sqls, sqls.size());
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
					String value = String.valueOf(v.getValue() instanceof Double ? ((Double) v.getValue()).intValue():v.getValue());
					value = value.replace("\"", "'");
					sbValues.append("\"" + value + "\",");
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

	public static Page<Record> getTable(String type, int p, int l) {
		StringBuffer sb = new StringBuffer("select * from YY_GT3_QC_TBNAME"); 
		if (type != null) {
			sb.append(" WHERE TYPE='" + type + "'");
		}
		return getYYPro().paginate(p, l, sb.toString());
	}

	public static List<Record> getField(String tb) {
		String sql = "SELECT * FROM YY_GT3_QC_TBFIELD WHERE NAME_EN=?";
		return getYYPro().find(sql, tb);
	}

	public static void getDDLs() {
		String sql = "SELECT USER, NAME_EN FROM YY_GT3_QC_TBNAME LIMIT 5";
		List<Record> tbnames = getYYPro().find(sql);
		List<String> sqls = new ArrayList<String>();
		for (Record rec:tbnames) {
			String tb = rec.getStr("NAME_EN");
			String user = rec.getStr("USER");
			String ddl = GetDDL.getCreateOnly(tb, user);
			sqls.add(ddl);
			System.out.println(ddl);
		}
	}

}
