package com.thoughtworks.restful.restful.client;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "translation", url = "http://localhost:9500")
public interface TranslateClient {

    @GetMapping(value = "/translations/{text}")
    String getTranslate(@PathVariable(value = "text") String text);

}
