package com.dwk.redis.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;

@Component
@FeignClient(name = "Jpa")
public interface JpaFeign {

    /**调用Jpa服务操作数据库*/

}
