package com.yby.uAdmin.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.yby.common.entity.SimpleWebsite;
import com.yby.service.base.exception.CustomException;
import com.yby.uAdmin.service.TbWebsiteService;

import java.util.ArrayList;
import java.util.Map;

public class OwExcelListener extends AnalysisEventListener<SimpleWebsite> {

    /*
     *   这边需要用到TbWebsiteService来保存读到的excel内容
     *   但是这个不能通过@RestController、@service等注解来交给spring boot来管理
     *   只能通过手动new来创建，不能交给spring boot 管理，那么就不能通过注解@Autowire来自动注入service
     *   因此，只能通过参数的形式将TbWebsiteService传进来，所以这边需要创建有参构造器
     * */

    public TbWebsiteService tbWebsiteService;

    ArrayList<SimpleWebsite> websiteArrayList = new ArrayList<>();

    // 无参构造
    public OwExcelListener() {
    }

    // 有参构造
    public OwExcelListener(TbWebsiteService tbWebsiteService) {
        this.tbWebsiteService = tbWebsiteService;
    }

    /*
    *   easyExcel读取文件监听器有两个方法：
    *   invoke: 用来读取excel内容，通过一行一行按顺序来读取
    *       - TbWebsite tbWebsite：第一个参数就是每行的内容，也就是一个对象（一行即是一个对象）
    * */
    @Override
    public void invoke(SimpleWebsite website, AnalysisContext analysisContext) {

        if(website == null) {
            throw new CustomException("OW20002", "website信息不能为空！");
        }

        SimpleWebsite tbWebsite = new SimpleWebsite();
        tbWebsite.setName(website.getName());
        tbWebsite.setUrl(website.getUrl());
        tbWebsite.setIdAdmin(website.getIdAdmin());
        tbWebsite.setIdCategory(website.getIdCategory());


        this.websiteArrayList.add(tbWebsite);
    }

    //读取excel表头信息
    @Override
    public void invokeHeadMap(Map<Integer, String> headMap, AnalysisContext context) {
        System.out.println("表头信息："+headMap);
    }

    // 读取完成后执行该方法
    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }

    // 返回已读取的数据
    public ArrayList<SimpleWebsite> getExcelDataList() {
        return this.websiteArrayList;
    }

}
