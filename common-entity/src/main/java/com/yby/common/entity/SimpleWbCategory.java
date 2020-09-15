package com.yby.common.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
@Data
public class SimpleWbCategory {

    @ApiModelProperty(value = "网站分类名称")
    private String name;

}
