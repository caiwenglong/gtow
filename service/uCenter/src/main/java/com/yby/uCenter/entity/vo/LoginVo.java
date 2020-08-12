package com.yby.uCenter.entity.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class LoginVo {
    @ApiModelProperty(value = "电话号码")
    private String username;

    @ApiModelProperty(value = "密码")
    private String password;
}
