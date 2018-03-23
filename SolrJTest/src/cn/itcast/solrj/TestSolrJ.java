package cn.itcast.solrj;

import java.util.List;
import java.util.Map;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrQuery.ORDER;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;

/**
 * solrJ API测试。
 * <p>Title: TestSolrJ</p>
 * <p>Description: </p>
 * <p>Company: www.itcast.com</p> 
 * @author	传智.入云龙
 * @date	2015-10-16下午2:44:20
 * @version 1.0
 */
public class TestSolrJ {

	//添加文档
	@Test
	public void addDocument() throws Exception {
		// 第一步：创建一个服务端的连接SolrServer对象。HttpSolrServer连接单机版使用的客户端。集群版使用CloudSolrServer。
		//参数：服务端的url地址，默认是collection1
		SolrClient solrServer = new HttpSolrClient("http://localhost:8080/solr/solrCore1");
//		SolrServer solrServer = new HttpSolrServer("http://localhost:8080/solr/solrCore1");
		// 第二步：创建一个文档对象SolrInputDocument。
		SolrInputDocument document = new SolrInputDocument();
		// 第三步：向文档对象中添加域。必须有一个id域，域名必须在schema.xml中定义。
		document.addField("id", "t002");
		document.addField("product_name", "中兴交换机");
		document.addField("product_price", "1599");
		// 第四步：使用SolrServer对象把文档对象发送给服务端。
		solrServer.add(document);
		// 第五步：commit。
		solrServer.commit();
	}
	
	//根据id删除文档
	@Test
	public void deleteDocumentById() throws Exception {
		SolrClient solrServer = new HttpSolrClient("http://localhost:8080/solr/solrCore1");
		//调用删除方法
		//参数：指定要删除文档的id
		solrServer.deleteById("t001");
		//commit
		solrServer.commit();
	}
	
	//根据查询删除文档
	@Test
	public void deleteDocumentByQuery() throws Exception {
		SolrClient solrServer = new HttpSolrClient("http://localhost:8080/solr/solrCore1");
		//参数：查询条件使用Lucene的查询语句。
		solrServer.deleteByQuery("*:*");
		//提交
		solrServer.commit();
	}
	
	//查询索引库
	@Test
	public void queryIndex() throws Exception {
		SolrClient solrServer = new HttpSolrClient("http://localhost:8080/solr/solrCore1");
		//创建一查询对象
		SolrQuery query = new SolrQuery();
		//指定查询条件
		//query.setQuery("*:*");
		query.set("q", "*:*");
		//执行查询
		QueryResponse queryResponse = solrServer.query(query);
		SolrDocumentList solrDocumentList = queryResponse.getResults();
		//取查询结果总数量
		System.out.println("查询结果总数量：" + solrDocumentList.getNumFound());
		System.out.println("当前结果集中的记录数：" + solrDocumentList.size());
		//遍历结果集
		for (SolrDocument solrDocument : solrDocumentList) {
			//取属性并打印
			System.out.println(solrDocument.get("id"));
			System.out.println(solrDocument.get("product_name"));
			System.out.println(solrDocument.get("product_price"));
			System.out.println(solrDocument.get("product_picture"));
			System.out.println(solrDocument.get("product_catalog_name"));
		}
	}
	
	//复杂查询
	@Test
	public void searchIndex() throws Exception {
		SolrClient solrServer = new HttpSolrClient("http://localhost:8080/solr/solrCore1");
		//创建一查询对象
		SolrQuery query = new SolrQuery();
		//设置查询条件
		query.setQuery("家天下");
		//设置过滤条件
		query.addFilterQuery("product_catalog_name:幽默杂货", "product_price:[0 TO 50]");
		//设置排序条件
		query.setSort("product_price", ORDER.asc);
		//分页条件
		query.setStart(0);
		query.setRows(20);
		//返回结果中包含的域
		query.setFields("id","product_name","product_price");
		//设置默认搜索域
		query.set("df", "product_keywords");
		//开启高亮
		query.setHighlight(true);
		//设置高亮显示的域
		query.addHighlightField("product_name");
		//高亮前缀
		query.setHighlightSimplePre("<em>");
		//高亮后缀
		query.setHighlightSimplePost("</em>");
		//执行查询
		QueryResponse response = solrServer.query(query);
		//取查询结果
		SolrDocumentList documentList = response.getResults();
		//取返回结果总数量
		System.out.println("查询结果总数量：" + documentList.getNumFound());
		//取结果列表
		for (SolrDocument solrDocument : documentList) {
			//取属性并打印
			System.out.println(solrDocument.get("id"));
			//取高亮显示
			Map<String, Map<String, List<String>>> highlighting = response.getHighlighting();
			List<String> list = highlighting.get(solrDocument.get("id")).get("product_name");
			//判断list是否为空
			String productName = "";
			if (null != list && list.size() > 0) {
				productName = list.get(0);
			} else {
				productName = (String) solrDocument.get("product_name");
			}
			System.out.println(productName);
			System.out.println(solrDocument.get("product_price"));
		}
	}
}
