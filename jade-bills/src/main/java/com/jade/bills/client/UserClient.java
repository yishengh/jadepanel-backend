package com.jade.bills.client;

import com.jade.user.api.UserApi;
import com.jade.user.entity.UserInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@FeignClient(value = "user-service")

public interface UserClient extends UserApi {

    @GetMapping("userInfo/findOne")
    public UserInfo findId(@RequestParam(value = "userName") String userName);
}
