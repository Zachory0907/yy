package vip.zgt.app.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;

public class Solrj {

	/**
	 * 查询简单索引
	 */
	public static SolrDocumentList query(String mQueryStr) {
		try {
			HttpSolrClient httpSolrClient = SolrUtils.connect();
			SolrQuery query = new SolrQuery();
			// 设定查询字段
			query.setQuery("*:*");
			// 指定返回结果字段
			// query.set("fl","id,name");
			// 覆盖schema.xml的defaultOperator（有空格时用"AND"还是用"OR"操作逻辑），一般默认指定。必须大写
			// query.set("q.op","AND");
			// 设定返回记录数，默认为10条
			query.setRows(10);
			QueryResponse response = httpSolrClient.query(query);
			SolrDocumentList list = response.getResults();
			return list;
		} catch (SolrServerException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static void addDoc(Map<String, Object> map) {
		try {
			HttpSolrClient httpSolrClient = SolrUtils.connect();
			SolrInputDocument document = new SolrInputDocument();
			document = SolrUtils.addFileds(map, document);
			httpSolrClient.add(document);
			httpSolrClient.commit();
		} catch (SolrServerException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void insertAndUpdateIndex() throws Exception {
		HttpSolrClient httpSolrClient = SolrUtils.connect();
		SolrInputDocument doc = new SolrInputDocument();
		doc.addField("id", "c003");
		doc.addField("name", "c003");
		httpSolrClient.add(doc);
		httpSolrClient.commit();
	}

	/**
	 * 删除索引
	 */
	public static void deleteById(String id) {
		try {
			HttpSolrClient httpSolrClient = SolrUtils.connect();
			httpSolrClient.deleteById(id);
			httpSolrClient.commit();
		} catch (SolrServerException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * 分页查询
	 */
	public static Map<String, Object> queryPage(String queryStr, Integer start, Integer rows) {
		try {
			HttpSolrClient httpSolrClient = SolrUtils.connect();
			SolrQuery query = new SolrQuery();
			// 设定查询字段 *:*
			query.setQuery(queryStr);
			// 指定返回结果字段
//			query.setIncludeScore(true);
			// query.set("fl","id,name");
			// 覆盖schema.xml的defaultOperator（有空格时用"AND"还是用"OR"操作逻辑），一般默认指定。必须大写
//			query.set("q.op", "AND");
			// 设置过滤条件
			// 如果设置多个过滤条件的话，需要使用query.addFilterQuery(fq)
//			query.setFilterQueries("xxx:[1 TO 10]");
			// 设置排序
//			query.setSort("xxx", ORDER.asc);
			// 分页开始条数
			query.setStart((start-1)*rows);
			// 设定返回记录数，默认为10条
			query.setRows(rows);
			// 设置显示的Field的域集合
//			query.setFields("id");
			// 设定对查询结果是否高亮
			query.setHighlight(true);
			// 设定高亮字段前置标签
			query.setHighlightSimplePre("<span style=\"color:red\">");
			// 设定高亮字段后置标签
			query.setHighlightSimplePost("</span>");
			// 设定高亮字段
			query.addHighlightField("yy_gt3_tbname_zh");
			query.addHighlightField("yy_gt3_field_zh");
			query.addHighlightField("yy_gt3_user");
			// 设定拼写检查
//			query.setRequestHandler("/spell");
			QueryResponse response = httpSolrClient.query(query);
			SolrDocumentList list = response.getResults();
			long totalRow = list.getNumFound(); //总条数
			int totalPage = (int) (totalRow / rows); //总页数
			if (totalRow % rows > 0)
				totalPage++;
			List<Map<String, Object>> results = new ArrayList<Map<String, Object>>();
			// 获取高亮信息
			Map<String, Map<String, List<String>>> highlighting = response.getHighlighting();
			for (SolrDocument doc : list) {
				Map<String, Object> res = new HashMap<String, Object>();
				// ID
				res.put("id", doc.get("id").toString());
				// TYPE
				res.put("type", doc.get("yy_gt3_type").toString());
				StringBuilder sb = new StringBuilder();
				Map<String, List<String>> ls = highlighting.get(doc.get("id"));
				// 表中文名称
				List<String> yy_gt3_tbname_zh = ls.get("yy_gt3_tbname_zh");
				if (yy_gt3_tbname_zh != null){
					sb.append("表中文名称：").append(yy_gt3_tbname_zh.get(0)).append(";");
				}
				// 表英文名称
				List<String> yy_gt3_tbname_en = ls.get("yy_gt3_tbname_en");
				if (yy_gt3_tbname_en != null){
					sb.append("表英文名称：").append(yy_gt3_tbname_en.get(0)).append(";");
				}
				// 表yy_gt3_owner
				List<String> yy_gt3_owner = ls.get("yy_gt3_owner");
				if (yy_gt3_owner != null){
					sb.append("OWNER：").append(yy_gt3_owner.get(0)).append(";");
				}
				// 表yy_gt3_user
				List<String> yy_gt3_user = ls.get("yy_gt3_user");
				if (yy_gt3_user != null){
					sb.append("USER：").append(yy_gt3_user.get(0)).append(";");
				}
				// 字段中文名称
				List<String> yy_gt3_field_zh = ls.get("yy_gt3_field_zh");
				if (yy_gt3_field_zh != null){
					sb.append("字段中文名称：").append(yy_gt3_field_zh.get(0)).append(";");
				}
				// 字段英文名称
				List<String> yy_gt3_field_en = ls.get("yy_gt3_field_en");
				if (yy_gt3_field_en != null){
					sb.append("字段文名称：").append(yy_gt3_field_en.get(0)).append(";");
				}
				res.put("content", sb.toString());
				results.add(res);
			}
			
			Map<String, Object> m = new HashMap<String, Object>();
			m.put("totalRow", totalRow);
			m.put("totalPage", totalPage);
			m.put("pageNumber", start);
			m.put("pageSize", rows);
			m.put("list", results);
			return m;
		} catch (SolrServerException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;

	}

}