package com.yby.uAdmin.service;

import com.yby.common.entity.TbWebsite;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;

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

    public String batchAddWebsite(MultipartFile file, TbWebsiteService tbWebsiteService) throws IOException;

}
