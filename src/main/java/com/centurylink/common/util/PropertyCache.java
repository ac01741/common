package com.centurylink.common.util;

import com.ctl.esec.crypto.PropertyEncryptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;

import java.util.Enumeration;
import java.util.MissingResourceException;
import java.util.Properties;
import java.util.ResourceBundle;

@Slf4j
public final class PropertyCache {
    private static final String SHOW_ERROR = "Key was not found";
    private static PropertyCache propertyCache;
    private static final Properties configProp = new Properties();

    private PropertyCache(String resourcePath, String fileName) {
        try {
            log.info("::Start Loading PropertyCache Data::");
            String fileNameWithType = fileName + ".properties";
            String resourceFilePath = resourcePath + fileNameWithType;
            ResourceBundle resourceBundle = ResourceBundle.getBundle(fileName);
            PropertyEncryptor propEncryptor = new PropertyEncryptor(resourceFilePath);
            log.info("Resource Path ::" + resourceFilePath);
            for (Enumeration<String> enumeration = resourceBundle.getKeys(); enumeration.hasMoreElements(); ) {
                String key = enumeration.nextElement().trim();
                log.info("readSensitiveResourceBundle. Found key :: " + key);
                String value2 = propEncryptor.getSensitiveProperty(key, SHOW_ERROR);
                configProp.put(key, value2);
            }
            log.info("::End Loading PropertyCache Data::");
        } catch (final MissingResourceException mre) {
            log.error("Error While Loading config file, File :: " + mre);
        } catch (final Exception ex) {
            log.error("Error While Reading from config file, IOException:" + ex);
        }
    }

    public static String getPropertyKeyValue(String resourcePath,String key) {
        log.info("getPropertyKeyValue ::"+key);
        if (null == propertyCache) {
            log.info("::getInstance method propertyCache null::");
            propertyCache = new PropertyCache(resourcePath, "sensitive");
        }
        return configProp.getProperty(key);
    }
}
