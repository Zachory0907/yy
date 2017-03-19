package vip.zgt.app.util;

import java.util.Iterator;
import java.util.Map;

import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.impl.XMLResponseParser;
import org.apache.solr.common.SolrException;
import org.apache.solr.common.SolrInputDocument;

public class SolrUtils {

	/**
	 * solr 服务器访问地址
	 */
	private static String url = "http://localhost:8983/solr/#/collection1";
	private static Integer connectionTimeout = 100;
	private static Integer defaltMaxConnectionsPerHost = 100;
	private static Integer maxTotalConnections = 100;
	private static Boolean followRedirects = false; //
	private static Boolean allowCompression = true;

	public static SolrInputDocument addFileds(Map<String, Object> map, SolrInputDocument document) {
		if (document == null) {
			document = new SolrInputDocument();
		}
		@SuppressWarnings("rawtypes")
		Iterator iterator = map.keySet().iterator();
		while (iterator.hasNext()) {
			String key = iterator.next().toString();
			document.setField(key, map.get(key));
		}
		return document;
	}

	/**
	 * 建立solr链接，获取 HttpSolrClient
	 * 
	 * @return HttpSolrClient
	 */
	public static HttpSolrClient connect() {

		HttpSolrClient httpSolrClient = null;
		try {
			httpSolrClient = new HttpSolrClient.Builder(url).build();
			httpSolrClient.setParser(new XMLResponseParser());// 设定xml文档解析器
			httpSolrClient.setConnectionTimeout(connectionTimeout);
			httpSolrClient.setAllowCompression(allowCompression);
			httpSolrClient.setMaxTotalConnections(maxTotalConnections);
			httpSolrClient.setDefaultMaxConnectionsPerHost(defaltMaxConnectionsPerHost);
			httpSolrClient.setFollowRedirects(followRedirects);
		} catch (SolrException e) {
			e.printStackTrace();
		}
		return httpSolrClient;
	}

}
