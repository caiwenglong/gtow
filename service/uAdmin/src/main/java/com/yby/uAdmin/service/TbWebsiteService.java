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

    public void readWebsiteExcel(MultipartFile file, TbWebsiteService tbWebsiteService) throws IOException;

    public Map<String, ArrayList<SimpleWebsite>> batchAddWebsite(MultipartFile file, TbWebsiteService tbWebsiteService) throws IOException;

    public void batchDelWebsite(ArrayList<SimpleWebsite> websites) throws IOException;

}
