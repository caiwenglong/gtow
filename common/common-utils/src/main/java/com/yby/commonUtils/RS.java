package com.yby.commonUtils;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class RS {

    @ApiModelProperty(value = "是否成功")
    private Boolean isSuccess;

    @ApiModelProperty(value = "返回的code")
    private String code;

    @ApiModelProperty(value = "信息")
    private String message;

    @ApiModelProperty(value = "返回的data")
    private Map<String, Object> data = new HashMap<String, Object>();


}
