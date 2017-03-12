package vip.zgt.app.biz;

import java.util.List;

import com.jfinal.plugin.activerecord.Record;

import vip.zgt.app.base.BaseBiz;

/**
 * @author Zachory
 */
public class Gt3 extends BaseBiz{

	public static void save(List<String> sqls) {
//		getYYPro().batch(sqls, sqls.size());
		for (String sql : sqls) {
			System.out.println(sql);
			getYYPro().update(sql);
		}
		List<Record> rec = getYYPro().find("select * from yy_gt3_qc_name");
		for (Record r : rec) {
			System.out.println(r.getStr("OWNER"));
		}
	}

}
