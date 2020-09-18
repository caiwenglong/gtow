package com.yby.uAdmin.service.impl;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yby.common.entity.SimpleWebsite;
import com.yby.commonUtils.JwtUtils;
import com.yby.service.base.ES.ES;
import com.yby.common.entity.TbWebsite;
import com.yby.service.base.exception.CustomException;
import com.yby.uAdmin.listener.OwExcelListener;
import com.yby.uAdmin.mapper.TbWebsiteMapper;
import com.yby.uAdmin.service.TbWebsiteService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
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
 * @since 2020-07-17
 */
@Service
public class TbWebsiteServiceImpl extends ServiceImpl<TbWebsiteMapper, TbWebsite> implements TbWebsiteService {

    ArrayList<SimpleWebsite> websiteArrayList = new ArrayList<>();

    @Autowired
    @Qualifier("restHighLevelClient")
    private RestHighLevelClient client;
    ES es = new ES();

    // 读取excel文件
    @Override
    public void readWebsiteExcel(MultipartFile file, TbWebsiteService tbWebsiteService) throws IOException {

        //  1. 获取文件输入流
        InputStream inputStream = file.getInputStream();

        //  2. 读取文件
        /*
        *   easyExcel通过read 读取文件时，需要三个参数：
        *   1. 文件输入流
        *   2. 对象的class
        *   3. 用来读写excel的监听器
        * */
        OwExcelListener owExcelListener = new OwExcelListener(tbWebsiteService);
        EasyExcel.read(inputStream, SimpleWebsite.class, owExcelListener).sheet().doRead();
        websiteArrayList = owExcelListener.getExcelDataList();

    }

    // 判断网站是否存在
    public Boolean isExitWebsite(String url) {
        QueryWrapper<TbWebsite> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("url", url);
        Integer count = baseMapper.selectCount(queryWrapper);
        return count > 0;
    }

    // 添加单个网站
    @Override
    public void addWebsite(SimpleWebsite website) {
        //es.esAddDoc(client, website);
        if(!isExitWebsite(website.getUrl())) {
            TbWebsite tbWebsite = new TbWebsite();
            BeanUtils.copyProperties(website, tbWebsite);
            baseMapper.insert(tbWebsite);
        } else {
            throw new CustomException("OW20007", "该网站已经存在！");
        }
    }


    // 批量添加网站
    @Override
    public Map<String, ArrayList<SimpleWebsite>> batchAddWebsite(MultipartFile file, TbWebsiteService tbWebsiteService) {
        ArrayList<SimpleWebsite> exitWebsites = new ArrayList<>();
        ArrayList<SimpleWebsite> successWebsites = new ArrayList<>();
        Map<String, ArrayList<SimpleWebsite>> batchAddResult = new HashMap<>();
        try {
            readWebsiteExcel(file, tbWebsiteService);
        } catch (IOException e) {
            e.printStackTrace();
        }
//        return es.esBatchAddDoc(client, this.websiteArrayList);
        for (SimpleWebsite website : websiteArrayList) {
            if(isExitWebsite(website.getUrl())) {
                exitWebsites.add(website);
            } else {
                addWebsite(website);
                successWebsites.add(website);
            }
            batchAddResult.put("exitWebsites", exitWebsites);
            batchAddResult.put("successWebsites", successWebsites);
        }
        return batchAddResult;
    }

    // 批量删除
    @Override
    public void batchDelWebsite(ArrayList<String> idList) {
//        es.esBatchDelDoc(client, websites);
        baseMapper.deleteBatchIds(idList);
    }

    @Override
    public List<TbWebsite> selectAllWebsite(String idAdmin) {
        QueryWrapper<TbWebsite> tbWebsiteQueryWrapper = new QueryWrapper<>();
        tbWebsiteQueryWrapper.eq("id_admin", idAdmin);
        return baseMapper.selectList(tbWebsiteQueryWrapper);
    }
}
