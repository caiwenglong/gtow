package com.yby.uInterface.config;


import com.alibaba.fastjson.JSON;
import com.yby.common.entity.TbWebsite;
import org.apache.lucene.util.QueryBuilder;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;


@SpringBootTest
@RunWith(SpringRunner.class)
public class ElasticsearchClientConfigTest {
    private static final String INDEX_NAME = "index_gtow";
    /*
    *  通过@Autowired注解自动注入 类名称跟方法名称必须一样，如果要自定义名称需要在添加@Qualifier注解
    * */
    @Autowired
    private RestHighLevelClient restHighLevelClient;

    // 创建索引
    @Test
    public void testCreateIndex() throws IOException {
        // new一个“创建索引”请求的对象
        CreateIndexRequest createIndexRequest = new CreateIndexRequest(INDEX_NAME);
        // 执行请求，并且获得请求结果

        CreateIndexResponse createIndexResponse =
                restHighLevelClient.indices().create(createIndexRequest, RequestOptions.DEFAULT);

        System.out.println(createIndexResponse);
    }

    // 判断索引是否存在,存在true，不存在返回false
    @Test
    public void testExitIndex() throws IOException {
        // new一个 “获得索引” 请求的对象
        GetIndexRequest getIndexRequest = new GetIndexRequest(INDEX_NAME);
        // 执行请求，并且获得结果
        boolean isExists = restHighLevelClient.indices().exists(getIndexRequest, RequestOptions.DEFAULT);
        System.out.println(isExists);
    }

    // 删除索引
    @Test
    public void testDelIndex() throws IOException {
        // new 一个 “删除索引” 请求
        DeleteIndexRequest deleteIndexRequest = new DeleteIndexRequest(INDEX_NAME);

        // 执行请求
        AcknowledgedResponse delete = restHighLevelClient.indices().delete(deleteIndexRequest, RequestOptions.DEFAULT);
        System.out.println(delete);
    }

    /*
    *  文档的操作
    * */
    // 添加文档
    @Test
    public void testAddDocument() throws IOException {
        // 前期工作，先创建一个对象，用来存入文档
        TbWebsite tbWebsite = new TbWebsite();
        tbWebsite.setName("淘宝客");
        tbWebsite.setUrl("https://caiwenglong.github.io/");


        // new 一个无状态请求
        IndexRequest indexRequest = new IndexRequest(INDEX_NAME);

        // 配置规则
        indexRequest.id("2"); // 设置文档ID
        indexRequest.timeout("30s");

        // 将数据通过source方法 放入请求，都是用json形式放入的, 所以这边需要将tbWebsite对象转为json
        indexRequest.source(JSON.toJSONString(tbWebsite), XContentType.JSON);


        // 规则配置好了， 数据也塞进去了，最后就是执行请求
        IndexResponse index = restHighLevelClient.index(indexRequest, RequestOptions.DEFAULT);

        System.out.println(index.toString());
        System.out.println(index.status());
    }

    // 判断文档是否存在
    @Test
    public void testExitDocument() throws IOException {
        // 创建请求
        GetRequest getRequest = new GetRequest(INDEX_NAME, "1");

        // 执行请求
        boolean isExits = restHighLevelClient.exists(getRequest, RequestOptions.DEFAULT);
        System.out.println(isExits);
    }

    // 查询文档
    @Test
    public void testGetDocument() throws IOException {
        // new 一个 “查询文档” 请求
        GetRequest getRequest = new GetRequest(INDEX_NAME, "1");

        // 执行查询文档信息
        GetResponse documentFields = restHighLevelClient.get(getRequest, RequestOptions.DEFAULT);

        System.out.println(documentFields.getSourceAsString()); // 打印文档内容就是存入的对象信息
        System.out.println(documentFields); // 这个是打印出文档的所有信息
    }

    // 更新文档
    @Test
    public void testUpdateDocument() throws IOException {
        // new一个 更新文档 请求
        UpdateRequest updateRequest = new UpdateRequest(INDEX_NAME, "1");

        // 可以配置一些配置项

        // 设置更新的内容
        TbWebsite tbWebsite = new TbWebsite();
        tbWebsite.setName("baidu3");
        tbWebsite.setIdAdmin("cai");
        // 需要将数据转换成json放入doc中
        updateRequest.doc(JSON.toJSON(tbWebsite), XContentType.JSON);

        // 执行请求
        UpdateResponse update = restHighLevelClient.update(updateRequest, RequestOptions.DEFAULT);

        System.out.println(update.toString());
        System.out.println(update.status());

    }

    // 删除文档
    @Test
    public void testDelDocument() throws IOException {
        // new 一个 删除文档  请求
        DeleteRequest deleteRequest = new DeleteRequest(INDEX_NAME, "1");

        // 执行请求
        DeleteResponse delete = restHighLevelClient.delete(deleteRequest, RequestOptions.DEFAULT);

        System.out.println(delete);
        System.out.println(delete.status());
    }

    // 批量删除
    @Test
    public void testBatchDelDoc() throws IOException {
        for (int i = 0; i < 12; i++) {
            String id = i + "";
            DeleteRequest deleteRequest = new DeleteRequest(INDEX_NAME, id);
            DeleteResponse delete = restHighLevelClient.delete(deleteRequest, RequestOptions.DEFAULT);
            System.out.println(delete);
            System.out.println(delete.status());
        }
    }

    // 批量添加
    @Test
    public void testBulkAddDocument() throws IOException {
        // 造个list 数据
        ArrayList<TbWebsite> tbWebsiteArrayList = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            TbWebsite tbWebsite = new TbWebsite();
            tbWebsite.setName("baidu" + i);
            tbWebsite.setUrl("www.baidu.com" + i);
            tbWebsiteArrayList.add(tbWebsite);
        }
        System.out.println(tbWebsiteArrayList);

        // new一个 批量添加 请求
        BulkRequest bulkRequest = new BulkRequest();

        // 构造bulkRequest
        for (int i = 0; i < tbWebsiteArrayList.size(); i++) {
            // new 一个 无状态 的请求
            IndexRequest indexRequest = new IndexRequest(INDEX_NAME);
            // 配置ID
            indexRequest.id("" + (i+1));
            indexRequest.source(JSON.toJSONString(tbWebsiteArrayList.get(i)), XContentType.JSON);

            bulkRequest.add(indexRequest);
        }

        // 执行请求
        BulkResponse bulk = restHighLevelClient.bulk(bulkRequest, RequestOptions.DEFAULT);
        System.out.println(bulk);
        System.out.println(bulk.status());
    }


    // 查询
    @Test
    public void testSearchDocument() throws IOException {

        // new一个 查询 请求
        SearchRequest searchRequest = new SearchRequest(INDEX_NAME);

        // 通过SearchSourceBuilder来构建查询条件
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();

        /*
        *  查询条件：
        *       QueryBuilders.termQuery：精确查询
        * */
        TermQueryBuilder termQueryBuilder = QueryBuilders.termQuery("name", "baidu1");


        // 将查询条件放到查询构建器里面
        searchSourceBuilder.query(termQueryBuilder);
        searchSourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS)); // 查询超过60秒就报错

        // 将构建好的查询构造器放大查询请求里面
        searchRequest.source(searchSourceBuilder);

        // 发送查询请求
        SearchResponse search = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);

        // 通过 getHits() 方法来得到查询的数据
        for (SearchHit documentFields: search.getHits().getHits()) {
            System.out.println(documentFields.getSourceAsMap());
        }

    }

}