package vip.zgt.app.util;

import java.math.BigDecimal;
import java.util.Iterator;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jfinal.kit.JsonKit;
import com.jfinal.plugin.activerecord.Record;

/**
 * JSON支持类，提供JSON的快捷操作，基于阿里的fastjson库
 * 
 * @author sunny
 *
 */
@SuppressWarnings("deprecation")
public class JSONUtil {
	// json -> string
	
	public static String toJson(Object object) {
		return JsonKit.toJson(object, Consts.JSON_UNMARSH_DEP);
	}

	// string -> object
	public static JSONObject parseObject(String jsonText) {
		return JSONObject.parseObject(jsonText);
	}

	public static JSONArray parseArray(String jsonText) {
		return JSONObject.parseArray(jsonText);
	}

	public static Record parseRecord(String jsonText) {
		JSONObject obj = JSONObject.parseObject(jsonText);
		Iterator<String> keys = obj.keySet().iterator();
		Record rec = new Record();
		while (keys.hasNext()) {
			String key = keys.next();
			Object value = obj.get(key);
			if (value instanceof String) {
				rec.set(key, value.toString());
			} else if (value instanceof Integer) {
				rec.set(key, (Integer) value);
			} else if (value instanceof BigDecimal) {
				rec.set(key, (BigDecimal) value);
			} else {
				if (value == null)
					continue;
				System.out.println(value + " not get type: " + value.getClass().getName());
			}
		}
		return rec;
	}

	// 通过传入 字符串 jsonText 表名： tableName ，tableSeq 序列名，Pkey 序列对应字段 ，自动拼接insert 语句
	// jsonText 中的键 必须是tableName 中存在的字段
	public static String parseInsSql(String jsonText, String tableName, String tableSeq, String Pkey) {
		JSONObject obj = JSONObject.parseObject(jsonText);
		Iterator<String> keys = obj.keySet().iterator();
		StringBuffer in = new StringBuffer("");
		StringBuffer va = new StringBuffer("");
		in.append("insert into " + tableName + "(");
		va.append("values(");
		if (!Pkey.equals("")) {
			in.append(Pkey + ",");
			va.append(tableSeq + ".nextval,");
		}
		while (keys.hasNext()) {
			String key = keys.next();
			Object value = obj.get(key);
			in.append(key + ",");
			if (value instanceof String) {
				va.append("'" + value.toString() + "',");
			} else if (value instanceof Integer) {
				va.append(value + ",");
			} else if (value instanceof BigDecimal) {
				va.append(value + ",");
			} else {
				if (value == null)
					continue;
				System.out.println(value + " not get type: " + value.getClass().getName());
			}

		}
		return in.substring(0, in.length() - 1) + ") " + va.substring(0, va.length() - 1) + ")";
	}

	// 通过传入 字符串 jsonText 表名： tableName ，tableSeq 序列名，Pkey 序列对应字段 ，自动拼接insert 语句
	// jsonText 中的键 必须是tableName 中存在的字段
	// FlagField jsonText 不包含的字段， FieldValue 对应的字段值
	public static String parseInsSql(String jsonText, String tableName, String tableSeq, String Pkey,
			String[] FlagField, String[] FieldValue) {
		JSONObject obj = JSONObject.parseObject(jsonText);
		Iterator<String> keys = obj.keySet().iterator();
		StringBuffer in = new StringBuffer("");
		StringBuffer va = new StringBuffer("");
		in.append("insert into " + tableName + "(");
		va.append("values(");
		if (!Pkey.equals("")) {
			in.append(Pkey + ",");
			va.append(tableSeq + ".nextval,");
		}
		if (FlagField.length > 0) {
			for (int i = 0; i < FlagField.length; i++) {
				in.append(FlagField[i] + ",");
				va.append(FieldValue[i] + ",");
			}
		}
		while (keys.hasNext()) {
			String key = keys.next();
			Object value = obj.get(key);
			in.append(key + ",");
			if (value instanceof String) {
				va.append("'" + value.toString() + "',");
			} else if (value instanceof Integer) {
				va.append(value + ",");
			} else if (value instanceof BigDecimal) {
				va.append(value + ",");
			} else {
				if (value == null)
					continue;
				System.out.println(value + " not get type: " + value.getClass().getName());
			}

		}
		return in.substring(0, in.length() - 1) + ") " + va.substring(0, va.length() - 1) + ")";
	}

	// json text ---> Model

	/*public static UFModel toModel(String jsonText) throws InstantiationException, IllegalAccessException {
		return toModel(jsonText, UFModel.class);
	}

	public static <T extends UFModel> T toModel(String jsonText, Class<T> clazz)
			throws InstantiationException, IllegalAccessException {
		T result = clazz.newInstance();
		JSONObject obj = JSONObject.parseObject(jsonText);
		Iterator<String> keys = obj.keySet().iterator();
		while (keys.hasNext()) {
			String key = keys.next();
			result.put(key, obj.get(key));
		}
		return result;
	}

	public static <T extends UFModel> List<T> toModels(String jsonText, Class<T> clazz)
			throws InstantiationException, IllegalAccessException {
		List<T> result = new ArrayList<T>();
		JSONArray arry = JSONObject.parseArray(jsonText);
		for (int i = 0, j = arry.size(); i < j; i++) {
			T item = clazz.newInstance();
			JSONObject obj = arry.getJSONObject(i);
			Iterator<String> keys = obj.keySet().iterator();
			while (keys.hasNext()) {
				String key = keys.next();
				item.put(key, obj.get(key));
			}
			result.add(item);
		}
		return result;
	}*/
}
