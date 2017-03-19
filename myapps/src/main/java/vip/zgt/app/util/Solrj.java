package vip.zgt.app.util;

import java.io.IOException;
import java.util.Map;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;

public class Solrj {

	/**
	 * 查询简单索引
	 */
	public SolrDocumentList query(String mQueryStr) {
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

	/**
	 * 添加/修改简单索引
	 */
	public void addDoc(Map<String, Object> map) {
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

	/**
	 * 删除索引
	 */
	public void deleteById(String id) {
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
	public SolrDocumentList queryPage(String queryStr, Integer start, Integer rows) {
		try {
			HttpSolrClient httpSolrClient = SolrUtils.connect();
			SolrQuery query = new SolrQuery();
			// 设定查询字段
			query.setQuery(queryStr);
			// 指定返回结果字段
			query.setIncludeScore(true);
			// query.set("fl","id,name");
			// 覆盖schema.xml的defaultOperator（有空格时用"AND"还是用"OR"操作逻辑），一般默认指定。必须大写
			query.set("q.op", "AND");
			// 分页开始页数
			query.setStart(start);
			// 设定返回记录数，默认为10条
			query.setRows(rows);
			// 设定对查询结果是否高亮
			query.setHighlight(true);
			// 设定高亮字段前置标签
			query.setHighlightSimplePre("<span style=\"color:red\">");
			// 设定高亮字段后置标签
			query.setHighlightSimplePost("</span>");
			// 设定高亮字段
			query.addHighlightField("name");
			// 设定拼写检查
			query.setRequestHandler("/spell");
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

}
