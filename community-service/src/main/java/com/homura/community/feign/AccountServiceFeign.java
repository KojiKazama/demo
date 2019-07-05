package com.homura.community.feign;

import com.homura.common.constant.ReturnResult;
import com.homura.community.feign.hystrix.AccountServiceHystrix;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;


@FeignClient(name= "account-service", fallback = AccountServiceHystrix.class)
public interface AccountServiceFeign {

    @RequestMapping(value = "/user/findAll")
    public ReturnResult findAll();
}
