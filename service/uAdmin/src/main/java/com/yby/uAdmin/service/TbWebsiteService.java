package com.yby.uAdmin.service;

import com.yby.common.entity.SimpleWebsite;
import com.yby.common.entity.TbWebsite;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author caiwenlong
 * @since 2020-07-17
 */
public interface TbWebsiteService extends IService<TbWebsite> {

    // 读取excel文件数据
    public void readWebsiteExcel(MultipartFile file, TbWebsiteService tbWebsiteService) throws IOException;

    // 添加单条数据
    public void addWebsite(SimpleWebsite website);

    // 通过excel批量添加数据
    public Map<String, ArrayList<SimpleWebsite>> batchAddWebsite(MultipartFile file, TbWebsiteService tbWebsiteService);

    // 批量删除网站
    public void batchDelWebsite(ArrayList<SimpleWebsite> websites) throws IOException;

}
