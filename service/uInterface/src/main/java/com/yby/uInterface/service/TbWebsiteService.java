package com.yby.uInterface.service;

import com.yby.uInterface.entity.TbWebsite;
import com.baomidou.mybatisplus.extension.service.IService;
import org.elasticsearch.client.RestHighLevelClient;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author caiwenlong
 * @since 2020-07-03
 */
public interface TbWebsiteService extends IService<TbWebsite> {

    public ArrayList<TbWebsite> getWebsiteSourceMapList(String keyword, int pageNo, int pageSize) throws IOException;

}
