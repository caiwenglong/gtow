package com.yby.commonUtils.excel;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.read.builder.ExcelReaderBuilder;
import com.yby.commonUtils.excel.listener.EasyExcelConsumerListener;

import java.io.File;
import java.io.InputStream;
import java.util.List;
import java.util.function.Consumer;

public class ExcelUtil extends EasyExcel {
    private ExcelUtil() {}

    public static <T> ExcelReaderBuilder read(File file, Class<T> head, Consumer<List<T>> consumer) {
        return read(file, head, new EasyExcelConsumerListener<>(consumer));
    }

    public static <T> ExcelReaderBuilder read(InputStream inputStream, Class<T> head, Consumer<List<T>> consumer) {
        return read(inputStream, head, new EasyExcelConsumerListener<>(consumer));
    }
}
