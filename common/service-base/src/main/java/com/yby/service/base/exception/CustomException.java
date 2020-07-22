package com.yby.service.base.exception;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomException extends RuntimeException{

    @ApiModelProperty("异常状态码")
    private String exCode;

    @ApiModelProperty("异常信息")
    private String exMassage;

}
