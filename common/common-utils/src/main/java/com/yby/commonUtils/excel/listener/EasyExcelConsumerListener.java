package com.yby.commonUtils.excel.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class EasyExcelConsumerListener<T> extends AnalysisEventListener<T> {

    private List<T> list;
    private Consumer<List<T>> consumer;

    public EasyExcelConsumerListener(Consumer<List<T>> consumer) {
        this.consumer = consumer;
        list = new ArrayList<>();
    }

    @Override
    public void invoke(T data, AnalysisContext analysisContext) {
        list.add(data);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        consumer.accept(list);
    }
}
