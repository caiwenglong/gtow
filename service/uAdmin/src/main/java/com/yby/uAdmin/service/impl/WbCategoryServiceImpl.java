package com.yby.uAdmin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yby.common.entity.SimpleWbCategory;
import com.yby.common.entity.WbCategory;
import com.yby.commonUtils.excel.ExcelUtil;
import com.yby.service.base.exception.CustomException;
import com.yby.uAdmin.mapper.WbCategoryMapper;
import com.yby.uAdmin.service.WbCategoryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
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

    private ArrayList<SimpleWbCategory> SimpleWbCategorys;

    // 获取所有的分类
    @Override
    public List<WbCategory> getAllWbCategory() {
        QueryWrapper<WbCategory> objectQueryWrapper = new QueryWrapper<>();
        return baseMapper.selectList(objectQueryWrapper);
    }

    // 通过ID获取分类
    @Override
    public WbCategory getWbCategoryById(String id) {
        QueryWrapper<WbCategory> wbCategoryQueryWrapper = new QueryWrapper<>();
        wbCategoryQueryWrapper.eq("id", id);
        return baseMapper.selectOne(wbCategoryQueryWrapper);
    }

    // 添加单个分类
    @Override
    public void addWbCategory(SimpleWbCategory simpleWbCategory) {
        WbCategory category = new WbCategory();
        BeanUtils.copyProperties(simpleWbCategory, category);
        baseMapper.insert(category);
    }

    // 通过excel批量添加分类
    @Override
    public Map<String, ArrayList<String>> batchAddWbCategory(MultipartFile file) {

        //  1. 获取文件输入流
        getExcelContent(file);

        ArrayList<String> exitCategory = new ArrayList<>();
        ArrayList<String> SuccessCategory = new ArrayList<>();
        Map<String, ArrayList<String>> batchAddResult = new HashMap<>();

        if(this.getCategoryNames().size() > 0) {
            for(SimpleWbCategory simpleWbCategory: SimpleWbCategorys) {
                if(!isExitCategory(simpleWbCategory.getName())) {
                    addWbCategory(simpleWbCategory);
                    SuccessCategory.add(simpleWbCategory.getName());
                } else {
                    exitCategory.add(simpleWbCategory.getName());
                }
            }
        } else {
            throw new CustomException("OW20002", "上传的网站分类内容为空！");
        }

        batchAddResult.put("exitCategory", exitCategory);
        batchAddResult.put("SuccessCategory", SuccessCategory);
        return batchAddResult;
    }

    // 通过ID删除分类
    @Override
    public int delWbCategoryById(String id) {
        return baseMapper.deleteById(id);
    }

    // 通过名称删除分类
    @Override
    public int delWbCategoryByName(String categoryName) {
        QueryWrapper<WbCategory> wbCategoryQueryWrapper = new QueryWrapper<>();
        wbCategoryQueryWrapper.eq("name", categoryName);
        return baseMapper.delete(wbCategoryQueryWrapper);
    }

    // 批量删除分类
    @Override
    public int batchDelWbCategory(MultipartFile file) {
        getExcelContent(file);
        int delCount = 0;
        if(SimpleWbCategorys.size() > 0) {
            for (SimpleWbCategory simpleWbCategory : SimpleWbCategorys) {
                delCount += delWbCategoryByName(simpleWbCategory.getName());
            }
        } else {
            throw new CustomException("OW20002", "上传的网站分类内容为空！");
        }
        return delCount;
    }

    @Override
    public void setCategoryNames(List<SimpleWbCategory> categoryNameList) {
        this.SimpleWbCategorys = (ArrayList<SimpleWbCategory>) categoryNameList;
    }

    // 读取excel内容
    public void getExcelContent(MultipartFile file) {
        try {
            InputStream inputStream = file.getInputStream();
            ExcelUtil.read(inputStream, SimpleWbCategory.class, categoryNameList -> wbCategoryService.setCategoryNames(categoryNameList)).sheet().doRead();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 得到上传的内容
    public List<SimpleWbCategory> getCategoryNames() {
        return this.SimpleWbCategorys;
    }

    // 判断网站分类名称是否存在
    public Boolean isExitCategory(String categoryName) {
        QueryWrapper<WbCategory> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("name", categoryName);
        Integer count = baseMapper.selectCount(queryWrapper);
        return count > 0;
    }
}
