package com.yby.uInterface.service.impl;

import com.yby.common.entity.TbWebsite;
import com.yby.uInterface.mapper.TbWebsiteMapper;
import com.yby.uInterface.service.TbWebsiteService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.yby.service.base.ES.ES;

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
