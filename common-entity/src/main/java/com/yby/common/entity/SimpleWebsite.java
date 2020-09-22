package com.yby.common.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class SimpleWebsite {

    @ApiModelProperty(value = "网站ID")
    private String id;

    @ApiModelProperty(value = "网站名称")
    private String name;

    @ApiModelProperty(value = "网站地址")
    private String url;

    @ApiModelProperty(value = "网站分类ID")
    private String idCategory;

    @ApiModelProperty(value = "管理员ID")
    private String idAdmin;

}
