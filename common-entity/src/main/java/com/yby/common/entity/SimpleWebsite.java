package com.yby.common.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class SimpleWebsite {
    @ApiModelProperty(value = "网站名称")
    private String name;

    @ApiModelProperty(value = "网站地址")
    private String url;
}
