package cn.itcast.solrj;


import org.apache.http.client.HttpClient;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.CloudSolrClient;
import org.apache.solr.client.solrj.impl.HttpClientUtil;
import org.apache.solr.client.solrj.impl.LBHttpSolrClient;
import org.apache.solr.client.solrj.request.CollectionAdminRequest;
import org.apache.solr.client.solrj.response.CollectionAdminResponse;
import org.apache.solr.common.cloud.SolrZkClient;
import org.apache.solr.common.cloud.ZkConfigManager;
import org.apache.solr.common.params.ModifiableSolrParams;

import java.io.IOException;
import java.nio.file.Paths;

public class SolrConfig {
    static String ZK_HOST="10.8.177.204:2181,10.8.177.210:2181,10.8.177.33:2181,10.8.177.22:2181";
    static SolrZkClient zkClient=new SolrZkClient(ZK_HOST,30000);
    static ZkConfigManager zkConfigManager=new ZkConfigManager(zkClient);
    /**
     * 上传配置
     * @param configName
     * @param configPath
     * @throws IOException
     */
    public static void uploadConfig(String configName,String configPath)throws IOException {
        System.out.println("准备上传配置："+configName);
        zkConfigManager.uploadConfigDir(Paths.get(configPath),configName);
        System.out.println("上传配置成功!");
    }

    /**
     * 删除集合
     * @param deleteName
     * @throws IOException
     * @throws SolrServerException
     */
    public static void deleteCollection(String deleteName)throws IOException,SolrServerException {
        SolrClient solrClient=new CloudSolrClient(ZK_HOST);
        CollectionAdminRequest.Delete delete=new CollectionAdminRequest.Delete();
        delete.setCollectionName(deleteName);
        CollectionAdminResponse response=delete.process(solrClient);
        System.out.println(response);
        solrClient.close();
    }

    /**
     * 创建集合
     * @param collectionName
     * @param configName
     * @throws IOException
     * @throws SolrServerException
     */
    public static void createCollection(String collectionName,String configName)throws IOException,SolrServerException{
        SolrClient solrClient=new CloudSolrClient(ZK_HOST);
        CollectionAdminRequest.Create create=new CollectionAdminRequest.Create();
        create.setConfigName(configName);
        create.setCollectionName(collectionName);
        create.setNumShards(4);//分片
        create.setMaxShardsPerNode(4);//每个节点最多持有片
        create.setReplicationFactor(2);//复制
        CollectionAdminResponse response=create.process(solrClient);
        System.out.println(response);
        solrClient.close();
    }
    public static void main(String[] args){
        try {
            String collectionName="shb1026";
            String config="solrAndHbase";
//            String configPath = "G:\\config\\solr_conf\\nlp\\";
//            uploadConfig(config, configPath);
//            deleteCollection(collectionName);
            createCollection(collectionName,config);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
