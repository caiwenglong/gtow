package com.yby.uAdmin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yby.common.entity.SimpleWbCategory;
import com.yby.common.entity.WbCategory;
import com.yby.commonUtils.excel.ExcelUtil;
import com.yby.commonUtils.excel.listener.EasyExcelConsumerListener;
import com.yby.service.base.exception.CustomException;
import com.yby.uAdmin.mapper.WbCategoryMapper;
import com.yby.uAdmin.service.WbCategoryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author caiwenlong
 * @since 2020-09-15
 */
@Service
public class WbCategoryServiceImpl extends ServiceImpl<WbCategoryMapper, WbCategory> implements WbCategoryService {

    @Autowired
    private WbCategoryService wbCategoryService;

    private ArrayList<SimpleWbCategory> categoryNames;

    @Override
    public ArrayList<WbCategory> getAllWbCategory() {
        return null;
    }

    @Override
    public WbCategory getWbCategoryById(String id) {
        QueryWrapper<Object> objectQueryWrapper = new QueryWrapper<>();
        return null;
    }

    // 添加单个分类
    @Override
    public WbCategory addWbCategory(SimpleWbCategory simpleWbCategory) {
        WbCategory category = new WbCategory();
        BeanUtils.copyProperties(simpleWbCategory, category);
        if(!isExitCategory(category.getName())) {
            baseMapper.insert(category);
        } else {
            throw new CustomException("OW20007", "要添加的网站分类已存在！");
        }

        return category;
    }

    @Override
    public Map<String, ArrayList<WbCategory>> batchAddWbCategory(MultipartFile file) {
        //  1. 获取文件输入流
        try {
            InputStream inputStream = file.getInputStream();
            ExcelUtil.read(inputStream, SimpleWbCategory.class, categoryNameList -> wbCategoryService.setCategoryNames(categoryNameList)).sheet().doRead();
        } catch (IOException e) {
            e.printStackTrace();
        }

        ArrayList<WbCategory> exitCategory = new ArrayList<>();
        ArrayList<WbCategory> SuccessCategory = new ArrayList<>();

        if(this.getCategoryNames().size() > 0) {
            for(SimpleWbCategory simpleWbCategory: categoryNames) {
                addWbCategory(simpleWbCategory);
            }
        } else {
            throw new CustomException("OW20002", "上传的网站分类内容为空！");
        }

        return null;
    }

    @Override
    public WbCategory delWbCategory(String id) {
        return null;
    }

    @Override
    public Map<String, ArrayList<WbCategory>> batchDelWbCategory() {
        return null;
    }

    @Override
    public void setCategoryNames(List<SimpleWbCategory> categoryNameList) {
        this.categoryNames = (ArrayList<SimpleWbCategory>) categoryNameList;
    }

    public List<SimpleWbCategory> getCategoryNames() {
        return this.categoryNames;
    }

    // 判断网站是否存在
    public Boolean isExitCategory(String categoryName) {
        QueryWrapper<WbCategory> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("name", categoryName);
        Integer count = baseMapper.selectCount(queryWrapper);
        return count > 0;
    }
}
