package vip.zgt.app.core.plugin;

import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.impl.XMLResponseParser;
import org.apache.solr.common.SolrException;
import org.apache.solr.common.SolrInputDocument;

import com.jfinal.plugin.IPlugin;

import vip.zgt.app.util.Sysinfo;
import vip.zgt.app.util.Utils;

public class SolrPro implements IPlugin {

	/**
	 * solr 服务器访问地址
	 */
//	private static String url = "http://localhost:8983/solr/collection1";
//	private static Integer connectionTimeout = 100;
//	private static Integer defaltMaxConnectionsPerHost = 100;
//	private static Integer maxTotalConnections = 100;
//	private static Boolean followRedirects = false; //
//	private static Boolean allowCompression = true;
	private static Properties props = new Properties();
	public static HttpSolrClient httpSolrClient = null;

	static {
		try {
			props = Utils.getProperties("/solr.properties");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean start() {
		try{
			httpSolrClient = new HttpSolrClient.Builder(props.getProperty("url")).build();
			httpSolrClient.setParser(new XMLResponseParser());// 设定xml文档解析器
			httpSolrClient.setSoTimeout(Integer.valueOf(props.getProperty("connectionTimeout")));
			httpSolrClient.setAllowCompression(Boolean.valueOf(props.getProperty("allowCompression")));
			httpSolrClient.setMaxTotalConnections(Integer.valueOf(props.getProperty("maxTotalConnections")));
			httpSolrClient.setDefaultMaxConnectionsPerHost(Integer.valueOf(props.getProperty("defaltMaxConnectionsPerHost")));
			httpSolrClient.setFollowRedirects(Boolean.valueOf(props.getProperty("followRedirects")));
			System.out.println(buildSolrInfo());
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean stop() {
		return true;
	}
	
	public static String buildSolrInfo() {
		StringBuilder sb = new StringBuilder();
		Sysinfo.buildInfoString(sb, "solr.baseUrl", httpSolrClient.getBaseURL());
		Sysinfo.buildInfoString(sb, "solr.paser", "XMLResponseParser");
		Sysinfo.buildInfoString(sb, "solr.soTimeout", props.getProperty("connectionTimeout"));
		Sysinfo.buildInfoString(sb, "solr.soTimeout", props.getProperty("connectionTimeout"));
		Sysinfo.buildInfoString(sb, "solr.allowCompression", props.getProperty("allowCompression"));
		Sysinfo.buildInfoString(sb, "solr.maxTotalConnections", props.getProperty("maxTotalConnections"));
		Sysinfo.buildInfoString(sb, "solr.defaltMaxConnectionsPerHost", props.getProperty("defaltMaxConnectionsPerHost"));
		Sysinfo.buildInfoString(sb, "solr.followRedirects", props.getProperty("followRedirects"));
		Sysinfo.buildLine(sb);
		return sb.toString();
	}

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
			httpSolrClient = new HttpSolrClient.Builder(props.getProperty("url")).build();
			httpSolrClient.setParser(new XMLResponseParser());// 设定xml文档解析器
			httpSolrClient.setSoTimeout(Integer.valueOf(props.getProperty("connectionTimeout")));
			httpSolrClient.setAllowCompression(Boolean.valueOf(props.getProperty("allowCompression")));
			httpSolrClient.setMaxTotalConnections(Integer.valueOf(props.getProperty("maxTotalConnections")));
			httpSolrClient.setDefaultMaxConnectionsPerHost(Integer.valueOf(props.getProperty("defaltMaxConnectionsPerHost")));
			httpSolrClient.setFollowRedirects(Boolean.valueOf(props.getProperty("followRedirects")));
		} catch (SolrException e) {
			e.printStackTrace();
		}
		return httpSolrClient;
	}

}