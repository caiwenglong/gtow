package com.yby.uAdmin.service;

import com.yby.common.entity.SimpleWebsite;
import com.yby.common.entity.TbWebsite;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;
import java.util.ArrayList;
import java.util.HashMap;
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

    // 修改网站
    void modifyWebsite(SimpleWebsite website);

    // 通过ID删除网站
    void delWebsiteById(String idWebsite);

    // 批量删除网站
    void batchDelWebsite(ArrayList<String> idList);

    // 查询用户上传的网站
    HashMap<Object, Object> selectAllWebsite(String idAdmin, Integer pageNum, Integer pageSize);

    // 通过网站ID查询网站详细信息
    TbWebsite selectWebsiteById(String id);

    // 通过excelUtil得到excel内容
    void setWebsiteArrayList(List<SimpleWebsite> arrayList);

    // 修改网站字段
    void modifyWebsiteKeywords(String websiteId, String keywords);
}
