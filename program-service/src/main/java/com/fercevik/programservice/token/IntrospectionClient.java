package com.fercevik.programservice.token;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value = "INTROSPECTION-SERVICE", url = "http://localhost:8080")
public interface IntrospectionClient {

    @PostMapping("${keycloak.introspection.uri}")
    UserInfoDTO introspect(@RequestBody MultiValueMap<String, String> data);

}
