package com.yby.uAdmin.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.yby.common.entity.SimpleWbCategory;
import com.yby.common.entity.WbCategory;
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
 * @since 2020-09-15
 */
public interface WbCategoryService extends IService<WbCategory> {

    // 获取全部分类
    List<WbCategory> getAllWbCategory();

    // 通过ID获取分类
    WbCategory getWbCategoryById(String id);

    // 添加单个分类
    void addWbCategory(SimpleWbCategory simpleWbCategory);

    // 批量添加分类
    Map<String, ArrayList<String>> batchAddWbCategory(MultipartFile file);

    // 删除分类
    int delWbCategoryById(String id);

    int delWbCategoryByName(String CategoryName);

    // 通过ID数组批量删除分类
    int batchDelWbCategory(MultipartFile file);

    void setCategoryNames(List<SimpleWbCategory> list);
}
