package com.centurylink.common.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class NaraController {
    @Value("${message}")
    private String message;

    @Value("${RESOURCE_PLACE}")
    private String RESOURCE_PLACE;

    @GetMapping("/hello")
    public String welcome() {
        return message;
    }

    @GetMapping("/keyvalue/{key}")
    public String getKeyValue(@PathVariable("key") String keyId) {
        log.info("Path Of File" + RESOURCE_PLACE + "Key " + keyId);
        String KeyValue = PropertyCache.getPropertyKeyValue(RESOURCE_PLACE,keyId);
        return KeyValue;
    }
}
