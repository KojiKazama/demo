package com.homura.community.feign.hystrix;

import com.homura.common.constant.ReturnResult;
import com.homura.community.feign.AccountServiceFeign;
import org.springframework.stereotype.Component;

@Component
public class AccountServiceHystrix implements AccountServiceFeign {
    @Override
    public ReturnResult findAll() {
        ReturnResult returnResult = new ReturnResult();
        returnResult.setFlag(false);
        returnResult.setMessage("Server Exception");
        return returnResult;
    }
}
