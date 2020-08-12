package com.yby.service.sms.controller;

import com.yby.commonUtils.RS;
import com.yby.commonUtils.RandomUtil;
import com.yby.service.sms.service.MsmService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Api(tags = "发送短信验证码")
@RestController
@RequestMapping("/service/sms")
@CrossOrigin
public class MsmController {

    @Autowired
    private MsmService msmService;

    @GetMapping("send/{phoneNum}")
    public RS sendMsm(
            @ApiParam(name = "phoneNum", value = "注册的电话号码", required = true)
            @PathVariable String phoneNum
    ) {
        String code = RandomUtil.getSixBitRandom();
        // 这边使用map的原因是因为发送验证码的时候需要发送json格式，map转为json格式比较方便
        Map<String,Object> param = new HashMap<>();
        param.put("code", code);
        Boolean isSend = msmService.sendPhoneCode(param, phoneNum);

        if(isSend) {
            return RS.success();
        } else {
            return RS.error().message("短信发送失败");
        }
    }
}
