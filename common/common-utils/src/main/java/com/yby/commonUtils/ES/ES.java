package com.yby.commonUtils.ES;

import com.alibaba.fastjson.JSON;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.QueryStringQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.sort.ScoreSortBuilder;
import org.elasticsearch.search.sort.SortOrder;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class ES {
    private static final String INDEX_NAME = "index_gtow";
    String FIELD_ID = "id";
    String FIELD_NAME = "name";
    String FIELD_URL = "url";

    // 通过关键字查询文档
    public ArrayList<Map<String, Object>> getWebsiteSourceMapList(RestHighLevelClient client, String keyword, int pageNo, int pageSize) throws IOException {

        ElasticsearchClientConfig clientConfig = new ElasticsearchClientConfig();

        // new 一个查询对象
        SearchRequest searchRequest = new SearchRequest(INDEX_NAME);

        // 通过SearchSourceBuilder来构建查询条件
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        // 分页
        searchSourceBuilder.from(pageNo - 1);
        searchSourceBuilder.size(pageSize);


        // 构建模糊查询：对关键字进行分词匹配
        //assert keyword != null; // 断言keyword不能为空
        // queryStringQuery会在所有的字段中查询，这边设置缩小范围，只在字段为name和url这两个中查询
        QueryStringQueryBuilder queryBuilder = QueryBuilders.queryStringQuery(keyword)
                .field(FIELD_NAME)
                .field(FIELD_URL);

//        MatchQueryBuilder queryBuilder = QueryBuilders.matchQuery("name", keyword);


        searchSourceBuilder.query(queryBuilder);


        // 构建高亮
        HighlightBuilder highlightBuilder = new HighlightBuilder();
        // 要高亮的字段
        highlightBuilder.field(FIELD_NAME).field(FIELD_URL);
        // 是否多个字段都高亮
        highlightBuilder.requireFieldMatch(false);
        // 匹配到的关键字添加html，用来写样式高亮
        highlightBuilder.preTags("<span class='hit-keyword-highlight'>");
        highlightBuilder.postTags("</span>");

        searchSourceBuilder.highlighter(highlightBuilder);


        // 排序
        ScoreSortBuilder scoreSortBuilder = new ScoreSortBuilder();
        scoreSortBuilder.order(SortOrder.ASC);

        searchSourceBuilder.sort(scoreSortBuilder);


        //  查询超过60s，就超时
        searchSourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS));


        // 将构建好的查询放到searchRequest中
        searchRequest.source(searchSourceBuilder);

        // 发送查询请求
        SearchResponse search = client.search(searchRequest, RequestOptions.DEFAULT);

        // 通过 getHits() 方法来得到查询的数据
        ArrayList<Map<String, Object>> sourceMapArrayList = new ArrayList<>();
        for (SearchHit documentHit: search.getHits().getHits()) {

            // 得到高亮字段
            Text[] names = documentHit.getHighlightFields().get("name").fragments();
            StringBuilder highlightName = new StringBuilder();
            for (Text name: names) {
                highlightName.append(name);
            }

            Map<String, Object> sourceAsMap = documentHit.getSourceAsMap();
            sourceAsMap.put("highlightName", highlightName.toString());

            sourceMapArrayList.add(sourceAsMap);
        }



        return sourceMapArrayList;
    }

    // 批量添加文档
    public String addDoc(RestHighLevelClient client, Object object) throws IOException {


        // new 一个无状态请求
        IndexRequest indexRequest = new IndexRequest(INDEX_NAME);

        // 配置规则
        UUID uuid = UUID.randomUUID();
        indexRequest.id(String.valueOf(uuid)); // 设置文档ID
        indexRequest.timeout("60s");

        // 将数据通过source方法 放入请求，都是用json形式放入的, 所以这边需要将tbWebsite对象转为json
        indexRequest.source(JSON.toJSONString(object), XContentType.JSON);


        // 规则配置好了， 数据也塞进去了，最后就是执行请求
        IndexResponse index = client.index(indexRequest, RequestOptions.DEFAULT);

        System.out.println(index.toString());
        System.out.println(index.status());
        return "OK";
    }
}