package com.yby.uInterface.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
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
import java.util.List;
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

    @Autowired
    private TbWebsiteMapper tbWebsiteMapper;

    @Override
    public ArrayList<TbWebsite> getWebsiteSourceMapList(String keyword, int pageNo, int pageSize) throws IOException {
        // 创建page对象
        Page<TbWebsite> tbWebsitePage = new Page<TbWebsite>(pageNo, pageSize);

        // 构造查询条件
        QueryWrapper<TbWebsite> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("name", keyword);
        IPage<TbWebsite> websiteIPage = tbWebsiteMapper.selectPage(tbWebsitePage, queryWrapper);
        List<TbWebsite> list = websiteIPage.getRecords();

        ArrayList<TbWebsite> websiteArrayList = new ArrayList<>();
        for (TbWebsite website : list) {
            websiteArrayList.add(website);
        }
        return websiteArrayList;
    }

    /*@Override
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
    }*/


}
