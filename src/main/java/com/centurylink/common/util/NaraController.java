package com.centurylink.common.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NaraController {
    @Value("${message}")
    private String message;

    @Value("${RESOURCE_PLACE}")
    private String RESOURCE_PLACE;

    @GetMapping("/hello")
    public String welcome(){
        return message;
    }

    @GetMapping("/keyvalue/{key}")
    public String getKeyValue(@PathVariable("key") String keyId){
        String KeyValue = PropertyCache.getInstance(RESOURCE_PLACE,"sensitive").getPropertyKeyValue(keyId);
        return KeyValue;
    }
}
