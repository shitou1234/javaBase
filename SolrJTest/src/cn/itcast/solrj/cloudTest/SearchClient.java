package cn.itcast.solrj.cloudTest;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

/**
 * Created by hejie@up72.com on 2015/9/25.
 */
public class SearchClient {

    public static String RayLiUrl;

    public void setRayLiUrl(String rayLiUrl) {
        RayLiUrl = rayLiUrl;
    }

    public static Logger logger = LoggerFactory.getLogger(SearchClientFactory.class);
    public static int commitWithinMs = 10;

    /** 获取Solr客户端 */
    public static SolrClient getClient(String coreUrl){
        return SearchClientFactory.getInstance().getSolrServer(coreUrl);
    }

    /**
     * 返回查询结果
     * @param solrQuery
     * @param coreUrl 核心url地址
     * @return 返回 List<SolrDocument> 对象
     */
    public static SolrDocumentList queryList(SolrQuery solrQuery, String coreUrl){
        QueryResponse response = null;
        try {
            response = SearchClient.getClient(coreUrl).query(solrQuery);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("查询异常", e);
        }
        if (response == null || response.getResponse().findRecursive("error", "failure") != null){
            return new SolrDocumentList();
        }
        return response.getResults();
    }

    /**
     * 增加索引
     * @param doc SolrInputDocument对象
     * @param coreUrl 核心地址
     * @return
     */
    public static boolean add(SolrInputDocument doc, String coreUrl){
        try {
            SearchClient.getClient(coreUrl).add(doc, 10);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("添加异常", e);
        }
        return false;
    }

    public static boolean adds(List<SolrInputDocument> list, String coreUrl){
        try {
            SearchClient.getClient(coreUrl).add(list, 10);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("添加异常", e);
        }
        return false;
    }

    /**
     * 增加索引
     */
    public static boolean addBen(Object obj, String coreUrl){
        try {
            SearchClient.getClient(coreUrl).addBean(obj, 10);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("添加异常", e);
        }
        return false;
    }

    /**
     * 增加索引
     */
    public static boolean addBeans(List<Object> objs, String coreUrl){
        try {
            SearchClient.getClient(coreUrl).addBeans(objs, 10);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("添加异常", e);
        }
        return false;
    }

    /**
     * 根据ID删除索引
     * @param sId 主键
     * @param coreUrl 核心地址
     * @return
     */
    public static boolean delById(String sId, String coreUrl){
        try {
            SearchClient.getClient(coreUrl).deleteById(sId, 10);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("删除异常", e);
        }
        return false;
    }

    public static boolean delByQuery(String query, String coreUrl){
        try {
            SearchClient.getClient(coreUrl).deleteByQuery(query, 10);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("删除异常", e);
        }
        return false;
    }

    public static SolrDocument getById(String sId, String coreUrl){
        try {
            return SearchClient.getClient(coreUrl).getById(sId);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("查询异常", e);
        }
        return null;
    }

    public static SolrDocument getById(String sId, String coreUrl, SolrQuery query){
        try {
            return SearchClient.getClient(coreUrl).getById(sId,query);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("查询异常", e);
        }
        return null;
    }

    public static boolean deleteByIds(List<String> ids, String coreUrl){
        try {
            SearchClient.getClient(coreUrl).deleteById(ids, 10);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("查询异常", e);
        }
        return false;
    }

    /**
     * 更新增量索引
     * @param coreUrl 核心地址
     * @return
     */
    public static boolean updateDeltaIndex(String coreUrl){
        SolrQuery query = new SolrQuery();
        query.setRequestHandler("/dataimport");
        query.setParam("command", "delta-import")
                .setParam("commit", "true")
                .setParam("wt", "json")
                .setParam("indent", "json")
                .setParam("verbose", "false")
                .setParam("clean", "false")
                .setParam("optimize", "false")
                .setParam("debug", "false");
        try {
            QueryResponse queryResponse = SearchClient.getClient(coreUrl).query(query);
            Map statusMsgMap = (Map)queryResponse.getResponse().get("statusMessages");
            logger.info("solr更新结果 : " + statusMsgMap);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    /**
     * 更新全量索引
     * @param coreUrl 核心地址
     * @return
     */
    public static boolean updateFullIndex(String coreUrl){
        SolrQuery query = new SolrQuery();
        query.setRequestHandler("/dataimport");
        query.setParam("command", "full-import")
                .setParam("commit", "true")
                .setParam("wt", "json")
                .setParam("indent", "json")
                .setParam("verbose", "false")
                .setParam("clean", "true")
                .setParam("optimize", "true")
                .setParam("debug", "false");
        try {
            QueryResponse queryResponse = SearchClient.getClient(coreUrl).query(query);
            Map statusMsgMap = (Map)queryResponse.getResponse().get("statusMessages");
            logger.info("solr更新结果 : " + statusMsgMap);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void main(String[] args) {
        String dbUlr = "http://192.168.1.43:8983/solr/rayli_user";
        try {
            SolrQuery query = new SolrQuery();
            query.setFields("realName");
            SolrDocument sd = SearchClient.getClient(dbUlr).getById("33_BankMemberBase",query);
            for (String str : sd.getFieldNames()){
                System.out.println(str);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
//        updateFullIndex(dbUlr);

    }

}
