package cn.itcast.solrj.cloudTest;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @description SolrClient工厂类，主要提供两种类型的SolrClient -- CloudSolrClient & HttpSolrClient
 * 
 * @author hejie@up72.com
 * @date 2015-9-25
 * @version 1.0
 */
public class SearchClientFactory {

	public static Logger logger = LoggerFactory.getLogger(SearchClientFactory.class);
	
	/**
	 * SolrClient map容器
	 */
	private static Map<String, SolrClient> solrClientMap = Collections.synchronizedMap(new HashMap<String, SolrClient>());
	
	/**
	 * 单例对象
	 */
	public static SearchClientFactory searchClientFactory = new SearchClientFactory();

	/**
	 * 私有构造方法
	 */
	private SearchClientFactory() {}
	
	/**
	 * 获取实例
	 * @return
	 */
	public static SearchClientFactory getInstance(){
		if(searchClientFactory == null){
			searchClientFactory = new SearchClientFactory();
		}
		return searchClientFactory;
	}

	/**
	 * 获取CommonsHttpSolrServer
	 * 
	 * @param solrServerUrl
	 * @return SolrServer
	 */
	public SolrClient getHttpSolrServer(final String solrServerUrl) {
		HttpSolrClient solrServer = null;
		if (!solrClientMap.containsKey(solrServerUrl)) {
			solrServer = new HttpSolrClient(solrServerUrl);
			if (solrServer != null) {
				//http连接的查询优化
				solrServer.setMaxTotalConnections(10000);
				solrServer.setSoTimeout(150000); // socket read timeout
				solrServer.setConnectionTimeout(80000);
//	            solrServer.setDefaultMaxConnectionsPerHost(maxConnectionsPerHost);
		        solrServer.setFollowRedirects(false);
		        solrServer.setAllowCompression(true);
				solrClientMap.put(solrServerUrl, solrServer);
				logger.info("服务 " + solrServerUrl + " 加载完毕");
			}
		}
		return solrClientMap.get(solrServerUrl);
	}

	/**
	 * 获取SolrServer 服务
	 * @param key
	 * @return
	 */
	public SolrClient getSolrServer(String key){
		if(key == null || "".equals(key)){
			return null;
		}
		if(solrClientMap.containsKey(key)){
			return solrClientMap.get(key);
		}

		if((key.toLowerCase().startsWith("http"))){
			return getHttpSolrServer(key);
		}
		return null;
	}

	public static Map<String, SolrClient> getSolrClientMap() {
		return solrClientMap;
	}
	
}
