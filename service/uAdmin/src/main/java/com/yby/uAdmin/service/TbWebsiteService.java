package com.yby.uAdmin.service;

import com.yby.common.entity.SimpleWebsite;
import com.yby.common.entity.TbWebsite;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;
import java.util.ArrayList;
import java.util.List;
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

    // 添加单条数据
    void addWebsite(SimpleWebsite website);

    // 通过excel批量添加数据
    Map<String, ArrayList<SimpleWebsite>> batchAddWebsite(MultipartFile file, String idAdmin);

    // 批量删除网站
    void batchDelWebsite(ArrayList<String> idList);

    // 查询用户上传的网站
    List<TbWebsite> selectAllWebsite(String idAdmin);

    void setWebsiteArrayList(List<SimpleWebsite> arrayList);

}
