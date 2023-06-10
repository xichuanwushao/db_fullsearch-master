package com.imooc.utils;

import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.nio.client.HttpAsyncClientBuilder;
import org.apache.http.impl.nio.reactor.IOReactorConfig;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;

import java.io.IOException;
import java.util.Map;

/**
 * ES工具类
 * Created by xuwei
 */
public class EsUtil {
    private EsUtil(){}
    private static RestHighLevelClient client;
    static{
        final CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        credentialsProvider.setCredentials(AuthScope.ANY,
                new UsernamePasswordCredentials("elastic", "BZB1kqA=UH=A"));

        //获取RestClient连接
        //注意：高级别客户端其实是对低级别客户端的代码进行了封装，所以连接池使用的是低级别客户端中的连接池
        client = new RestHighLevelClient(
                RestClient.builder(
                        new HttpHost("192.168.1.50",9200,"http"),
                        new HttpHost("192.168.1.50",9200,"http"),
                        new HttpHost("192.168.1.50",9200,"http"))
                        .setHttpClientConfigCallback(new RestClientBuilder.HttpClientConfigCallback() {
                            public HttpAsyncClientBuilder customizeHttpClient(HttpAsyncClientBuilder httpClientBuilder) {
                                return httpClientBuilder.setDefaultCredentialsProvider(credentialsProvider)
                                        .setDefaultIOReactorConfig(IOReactorConfig.custom()
                                                .setIoThreadCount(1)
                                                .build());
                            }
                        }));
    }

    /**
     * 获取客户端
     * @return
     */
    public static RestHighLevelClient getRestClient(){
        return client;
    }

    /**
     * 关闭客户端
     * 注意：调用高级别客户单的close方法时，会将低级别客户端创建的连接池整个关闭掉，最终导致client无法继续使用
     * 所以正常是用不到这个close方法的，只有在程序结束的时候才需要调用
     * @throws IOException
     */
    public static void closeRestClient()throws IOException {
        client.close();
    }

    /**
     * 建立索引
     * @param index
     * @param id
     * @param map
     * @throws IOException
     */
    public static void addIndex(String index, String id, Map<String,String> map)throws IOException{
        IndexRequest request = new IndexRequest(index);
        request.id(id);
        request.source(map);
        //执行
        client.index(request, RequestOptions.DEFAULT);
    }
}
