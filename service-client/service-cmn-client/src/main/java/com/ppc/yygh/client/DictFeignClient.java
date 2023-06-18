package com.ppc.yygh.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("service-cmn")//指定被调用方在注册中心的应用名称
public interface DictFeignClient {

    @GetMapping("/admin/cmn/{value}")
    public String getNameByValue(@PathVariable("value") Long value);
    @GetMapping("/admin/cmn/{dictCode}/{value}")
    public String getNameByDictCodeAndValue(@PathVariable("value") Long value,@PathVariable("dictCode") String dictCode);

}
