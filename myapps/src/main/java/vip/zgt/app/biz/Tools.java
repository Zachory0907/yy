package vip.zgt.app.biz;

import java.io.File;
import java.util.List;

import com.jfinal.plugin.activerecord.Record;

import vip.zgt.app.base.BaseBiz;
import vip.zgt.app.util.FileHandler;

/**
 * ToolsController所需的service
 * @author Zachory
 */
public class Tools extends BaseBiz{

	public static String makeKtr(List<Record> res, List<Record> tabMsg, String ktrTemplatePath) throws Exception {
		String infoName = res.get(0).getStr("NAME_ZH");
		String tableOutputName = res.get(0).getStr("NAME_EN");
		String tableInputName = tabMsg.get(0).getStr("USER") + "." + tableOutputName;
		StringBuffer sb = new StringBuffer();
		for (Record r : res) {
			String field = r.getStr("FIELD_EN");
			if (field.equals("UUID")) {
				sb.append("<key>").append("\r\n");
				sb.append("<name>UUID</name>").append("\r\n");
				sb.append("<field>UUID</field>").append("\r\n");
				sb.append("<condition>=</condition>").append("\r\n");
				sb.append("</key>").append("\r\n");
			} else if (field.equals("XH")) {
				sb.append("<key>").append("\r\n");
				sb.append("<name>XH</name>").append("\r\n");
				sb.append("<field>XH</field>").append("\r\n");
				sb.append("<condition>=</condition>").append("\r\n");
				sb.append("</key>").append("\r\n");
			} else {
				sb.append("<value>").append("\r\n");
				sb.append("<name>").append(field).append("</name>").append("\r\n");
				sb.append("<rename>").append(field).append("</rename>").append("\r\n");
				sb.append("<update>N</update>").append("\r\n");
				sb.append("</value>").append("\r\n");
			}
		}
		String schema = sb.toString();
		File template = new File(ktrTemplatePath);
		String content = FileHandler.readFile(template);
		content = content.replaceAll("##infoName##", infoName);
		content = content.replaceAll("##tableOutputName##", tableOutputName);
		content = content.replaceAll("##tableInputName##", tableInputName);
		content = content.replaceAll("##schema##", schema);
		return content;
	}
	
}