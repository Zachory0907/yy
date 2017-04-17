package vip.zgt.app.biz;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.upload.UploadFile;

import vip.zgt.app.base.BaseBiz;
import vip.zgt.app.util.ExecutExcel;
import vip.zgt.app.util.GetDDL;
import vip.zgt.app.util.Solrj;
import vip.zgt.app.util.UUID;

/**
 * @author Zachory
 */
public class Gt3 extends BaseBiz {

	public static void save(List<String> sqls, String tableName) {
//		System.out.println("ok");
		getYYPro().batch(sqls, sqls.size());
	}

	public static void saveExcel(UploadFile uf, String type, Object headList, String tableName) {
		String fileName = uf.getFileName();
		String filePath = uf.getUploadPath();
		List<String> sqls = new ArrayList<String>();
		List<List<Map<String, Object>>> content = ExecutExcel.readFromSingleExcel(filePath + File.separator + fileName,
				0, 1, 1, (String[]) headList);
		for (List<Map<String, Object>> rows : content) {
			StringBuffer sbkeys = new StringBuffer("ID,");
			StringBuffer sbValues = new StringBuffer("null,");
			for (Map<String, Object> cols : rows) {
				for (Map.Entry<String, Object> v : cols.entrySet()) {
					sbkeys.append(v.getKey() + ",");
					String value = String.valueOf(
							v.getValue() instanceof Double ? ((Double) v.getValue()).intValue() : v.getValue());
					value = value.replace("\"", "'");
					sbValues.append("\"" + value + "\",");
				}
			}
			addSolrDoc(rows, type);
			sbkeys.append("TYPE");
			sbValues.append("\"" + type + "\"");
			String reskeys = sbkeys.toString();
			String resvalues = sbValues.toString();
			sqls.add("INSERT INTO " + tableName + "(" + reskeys + ") VALUES (" + resvalues + ")");
		}
		Gt3.save(sqls, tableName);
	}

	public static void addSolrDoc(List<Map<String, Object>> rows, String type) {
		Map<String, Object> solrDoc = new HashMap<String, Object>();
		String uuid = "yy_gt3_" + UUID.getUUID(4);
		solrDoc.put("id", uuid);
		solrDoc.put("yy_gt3_type", type);
		if (type.indexOf("mc") >= 0) {
			// yy_gt3_qc_tbname表
			for (Map<String, Object> cols : rows) {
				for (Map.Entry<String, Object> v : cols.entrySet()) {
					if (v.getKey().equals("NAME_ZH")) {
						solrDoc.put("yy_gt3_tbname_zh", v.getValue());
					}
					if (v.getKey().equals("NAME_EN")) {
						solrDoc.put("yy_gt3_tbname_en", v.getValue());
					}
					if (v.getKey().equals("OWNER")) {
						solrDoc.put("yy_gt3_owner", v.getValue());
					}
					if (v.getKey().equals("USER")) {
						solrDoc.put("yy_gt3_user", v.getValue());
					}
				}
			}
		} else {
			// yy_gt3_qc_tbfield表
			for (Map<String, Object> cols : rows) {
				for (Map.Entry<String, Object> v : cols.entrySet()) {
					if (v.getKey().equals("FIELD_ZH")) {
						solrDoc.put("yy_gt3_field_zh", v.getValue());
					}
					if (v.getKey().equals("FIELD_EN")) {
						solrDoc.put("yy_gt3_field_en", v.getValue());
					}
				}
			}
		}
		try {
			Solrj.addDoc(solrDoc);
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
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
	
	public static String getDDL(String tb) {
		String sql = "SELECT * FROM YY_GT3_QC_DDL WHERE NAME_EN=?";
		String ddl = "";
		try {
			ddl = new String(getYYPro().findFirst(sql, tb).getBytes("DDL"),"utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return ddl;
	}

	public static void getDDLs() {
		String sql = "SELECT USER, NAME_EN FROM YY_GT3_QC_TBNAME";
		List<Record> tbnames = getYYPro().find(sql);
		List<String> sqls = new ArrayList<String>();
		for (Record rec : tbnames) {
			String tb = rec.getStr("NAME_EN");
//			String user = rec.getStr("USER");
			String user = "UFAUD";
			String ddl = GetDDL.getCreateOnly(tb, user);
			if (ddl == null) {
				continue;
			}
			String insertDDL = "INSERT INTO YY_GT3_QC_DDL(ID, NAME_EN, DDL) VALUES (NULL, '" + tb + "', '" + ddl + "')";
			sqls.add(insertDDL);
			String updateSql = "UPDATE YY_GT3_QC_TBNAME SET EXT = 1 WHERE NAME_EN='" + tb + "'";
			sqls.add(updateSql);
		}
		getYYPro().update("DELETE FROM YY_GT3_QC_DDL");
		getYYPro().batch(sqls, sqls.size());
	}
}
