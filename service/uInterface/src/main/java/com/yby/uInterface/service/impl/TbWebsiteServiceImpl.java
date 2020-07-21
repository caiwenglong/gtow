package com.yby.uInterface.service.impl;

import com.yby.uInterface.entity.TbWebsite;
import com.yby.uInterface.mapper.TbWebsiteMapper;
import com.yby.uInterface.service.TbWebsiteService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.poi.hssf.record.DVALRecord;
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

import com.yby.commonUtils.ES.ES;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

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

    @Autowired
    @Qualifier("restHighLevelClient")
    private RestHighLevelClient client;

    @Override
    public ArrayList<TbWebsite> getWebsiteSourceMapList(String keyword, int pageNo, int pageSize) throws IOException {



        ES es = new ES();
        ArrayList<Map<String, Object>> websiteSourceMapList = es.getWebsiteSourceMapList(client, keyword, pageNo, pageSize);

        ArrayList<TbWebsite> websiteArrayList = new ArrayList<>();
        for(Map<String, Object> website: websiteSourceMapList) {
            TbWebsite tbWebsite = new TbWebsite();
            tbWebsite.setName(website.get("highlightName").toString());
            tbWebsite.setUrl(website.get("url").toString());
            websiteArrayList.add(tbWebsite);
        }
        return websiteArrayList;
    }

}
