package com.yby.service.base.exception.excel;

import com.yby.common.entity.TbWebsite;

public class ExcelDataConvertException extends RuntimeException{
    private Integer rowIndex;
    private Integer columnIndex;
    private TbWebsite cellData;
}
