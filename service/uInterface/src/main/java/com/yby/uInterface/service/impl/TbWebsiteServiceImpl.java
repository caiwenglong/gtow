package com.yby.uInterface.service.impl;

import com.yby.uInterface.entity.TbWebsite;
import com.yby.uInterface.mapper.TbWebsiteMapper;
import com.yby.uInterface.service.TbWebsiteService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.*;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.sort.ScoreSortBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author caiwenlong
 * @since 2020-07-03
 */
@Service
public class TbWebsiteServiceImpl extends ServiceImpl<TbWebsiteMapper, TbWebsite> implements TbWebsiteService {

    private static final String INDEX_NAME = "index_gtow";

    @Autowired
    @Qualifier("restHighLevelClient")
    private RestHighLevelClient client;

    public ArrayList<TbWebsite> getTbWebsite(String keyword, int pageNo, int pageSize) throws IOException {

        // 搜索
        String field_id = "id";
        String field_name = "name";
        String field_url = "url";

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
                .field(field_name)
                .field(field_url);

//        MatchQueryBuilder queryBuilder = QueryBuilders.matchQuery("name", keyword);


        searchSourceBuilder.query(queryBuilder);


        // 构建高亮
        HighlightBuilder highlightBuilder = new HighlightBuilder();
        // 要高亮的字段
        highlightBuilder.field(field_name).field(field_url);
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
        ArrayList<TbWebsite> tbWebsiteArrayList = new ArrayList<>();
        for (SearchHit documentHit: search.getHits().getHits()) {

            // 得到高亮字段
            Text[] names = documentHit.getHighlightFields().get("name").fragments();
            StringBuilder highlightName = new StringBuilder();
            for (Text name: names) {
                highlightName.append(name);
            }

            Map<String, Object> sourceAsMap = documentHit.getSourceAsMap();

            TbWebsite tbWebsite = new TbWebsite();
            tbWebsite.setName(highlightName.toString());
            tbWebsite.setUrl(sourceAsMap.get("url").toString());
            tbWebsiteArrayList.add(tbWebsite);
        }



        return tbWebsiteArrayList;
    }
}
