package cn.itcast.solrj.cloudTest;

import org.apache.http.client.HttpClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.CloudSolrClient;
import org.apache.solr.client.solrj.impl.ConcurrentUpdateSolrClient;
import org.apache.solr.client.solrj.impl.HttpClientUtil;
import org.apache.solr.client.solrj.impl.LBHttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.apache.solr.common.params.ModifiableSolrParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Created by hejie@up72.com on 2015/9/25.
 */
public class SearchCloudClient {

    public static Logger logger = LoggerFactory.getLogger(SearchCloudClient.class);

    private static CloudSolrClient cloudSolrClient = null;
    /**
     * 获取server连接
     * @return
     */
    public CloudSolrClient getClient(String collection){
        return getClient(collection,null);
    }

    public CloudSolrClient getClient(String collection, String zkHost){
        if(null == cloudSolrClient){
            ModifiableSolrParams params = new ModifiableSolrParams();
            params.set(HttpClientUtil.PROP_MAX_CONNECTIONS, 1000);//10
            params.set(HttpClientUtil.PROP_MAX_CONNECTIONS_PER_HOST, 500);//5
            HttpClient client = HttpClientUtil.createClient(params);
            LBHttpSolrClient lbServer = new LBHttpSolrClient(client);
            String zkHostString = zkHost==null?BaseConstant.SOLR_CLOUD_ZKHOST:zkHost;
            cloudSolrClient = new CloudSolrClient(zkHostString,lbServer);
            cloudSolrClient.setDefaultCollection(collection);
            cloudSolrClient.setZkClientTimeout(5000);
            cloudSolrClient.setZkConnectTimeout(5000);
            cloudSolrClient.connect();
        }
        return cloudSolrClient;
    }

//    public static ConcurrentUpdateSolrClient getUpdateSolrClient(){
//        return getUpdateSolrClient(null,500,50);
//    }
//
//    public static ConcurrentUpdateSolrClient getUpdateSolrClient(String solrUrl, int queueSize, int threadCount){
//        String url = solrUrl==null?BaseConstant.SOLR_URL_RAYLI_MEMBER:solrUrl;
//        return new ConcurrentUpdateSolrClient(url,queueSize,threadCount);
//    }

    public void close(){
        try {
            cloudSolrClient.optimize();
            if(false && null!=cloudSolrClient){
//                cloudSolrClient.optimize();
                cloudSolrClient.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 返回查询结果
     */
    public SolrDocumentList queryList(SolrQuery solrQuery, String solrCore){
        QueryResponse response = null;
        try {
            response = getClient(solrCore).query(solrQuery);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("查询异常", e);
        } finally {
            close();
        }
        if (response == null || response.getResponse().findRecursive("error", "failure") != null){
            return new SolrDocumentList();
        }
        return response.getResults();
    }

    /**
     * 增加索引
     */
    public boolean add(SolrInputDocument doc, String solrCore){
        try {
            UpdateResponse r =  getClient(solrCore).add(doc, 10);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("添加异常", e);
        } finally {
            close();
        }
        return false;
    }

    public boolean adds(List<SolrInputDocument> list, String solrCore){
        try {
            UpdateResponse r = getClient(solrCore).add(list, 10);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("添加异常", e);
        } finally {
            close();
        }
        return false;
    }

    /**
     * 增加索引
     */
    public boolean addBen(Object obj, String solrCore){
        try {
            UpdateResponse r =  getClient(solrCore).addBean(obj, 10);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("添加异常", e);
        } finally {
            close();
        }
        return false;
    }

    /**
     * 增加索引
     */
    public boolean addBeans(List<Object> objs, String solrCore){
        try {
            UpdateResponse r = getClient(solrCore).addBeans(objs, 10);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("添加异常", e);
        } finally {
            close();
        }
        return false;
    }

    /**
     * 根据ID删除索引
     */
    public boolean delById(String sId, String solrCore){
        try {
            UpdateResponse r = getClient(solrCore).deleteById(sId, 10);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("删除异常", e);
        } finally {
            close();
        }
        return false;
    }

    public boolean delByQuery(String query, String solrCore){
        try {
            UpdateResponse r = getClient(solrCore).deleteByQuery(query, 10);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("删除异常", e);
        } finally {
            close();
        }
        return false;
    }

    public SolrDocument getById(String sId, String solrCore){
        try {
            return getClient(solrCore).getById(sId);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("查询异常", e);
        } finally {
            close();
        }
        return null;
    }

    public SolrDocument getById(String sId, String solrCore, SolrQuery query){
        try {
            return getClient(solrCore).getById(sId,query);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("查询异常", e);
        } finally {
            close();
        }
        return null;
    }

    public boolean deleteByIds(List<String> ids, String solrCore){
        try {
            UpdateResponse r = getClient(solrCore).deleteById(ids, 10);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("查询异常", e);
        } finally {
            close();
        }
        return false;
    }

    /**
     * 更新增量索引
     */
    public boolean updateDeltaIndex(String solrCore){
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
            QueryResponse queryResponse = getClient(solrCore).query(query);
            Map statusMsgMap = (Map)queryResponse.getResponse().get("statusMessages");
            logger.info("solr更新结果 : " + statusMsgMap);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            close();
        }
    }


    /**
     * 更新全量索引
     */
    public boolean updateFullIndex(String solrCore){
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
            QueryResponse queryResponse = getClient(solrCore).query(query);
            Map statusMsgMap = (Map)queryResponse.getResponse().get("statusMessages");
            logger.info("solr更新结果 : " + statusMsgMap);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            close();
        }
    }

    /**
     * 返回查询结果
     */
    public QueryResponse query(SolrQuery solrQuery, String solrCore){
        QueryResponse response = null;
        try {
            response = getClient(solrCore).query(solrQuery);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("查询异常", e);
        } finally {
            close();
        }
        return response;
    }

    public static void main(String[] args) {
//        CloudSolrClient cloudSolrClient = new SearchCloudClient().getClient("rayli_user","192.168.1.46:2181,192.168.1.43:2181,192.168.1.47:2181");
        CloudSolrClient cloudSolrClient = new SearchCloudClient().getClient("collection1","192.168.3.43:4180,192.168.3.43:4181,192.168.3.43:4182");
        SolrInputDocument sd = new SolrInputDocument();
        sd.addField("id","123323");
        sd.addField("nick_name","shitou123123123");
        try {
            cloudSolrClient.add(sd,10);
        } catch (SolrServerException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
