package com.yby.uAdmin.service.impl;

import com.alibaba.excel.EasyExcel;
import com.yby.common.entity.SimpleWebsite;
import com.yby.service.base.ES.ES;
import com.yby.common.entity.TbWebsite;
import com.yby.uAdmin.listener.OwExcelListener;
import com.yby.uAdmin.mapper.TbWebsiteMapper;
import com.yby.uAdmin.service.TbWebsiteService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

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

    ArrayList<TbWebsite> websiteArrayList = new ArrayList<>();

    @Autowired
    @Qualifier("restHighLevelClient")
    private RestHighLevelClient client;

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
        this.websiteArrayList = owExcelListener.getExcelDataList();

    }


    @Override
    public String batchAddWebsite(MultipartFile file, TbWebsiteService tbWebsiteService) throws IOException {
        this.readWebsiteExcel(file, tbWebsiteService);
        ES es = new ES();
        es.addDoc(client, this.websiteArrayList);
        return "OK";
    }
}
