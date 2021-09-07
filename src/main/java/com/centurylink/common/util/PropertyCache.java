package com.centurylink.common.util;

import com.ctl.esec.crypto.PropertyEncryptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Enumeration;
import java.util.MissingResourceException;
import java.util.Properties;
import java.util.ResourceBundle;

public final class PropertyCache {
    private static final Logger LOGGER = LoggerFactory.getLogger(PropertyCache.class);
    private static final String SHOW_ERROR = "Key was not found";
    private static PropertyCache propertyCache;
    private final Properties configProp = new Properties();

    private PropertyCache(String resourcePath, String fileName) {
        try {
            LOGGER.info("Start Loading PropertyCache Data");
            ResourceBundle resourceBundle = ResourceBundle.getBundle(fileName);
            String fileNameWithType = fileName + ".properties";
            String resourceFilePath = resourcePath + fileNameWithType;
            PropertyEncryptor propEncryptor = new PropertyEncryptor(resourceFilePath);
            for (Enumeration<String> enumeration = resourceBundle.getKeys(); enumeration.hasMoreElements(); ) {
                String key = enumeration.nextElement().trim();
                LOGGER.info("readSensitiveResourceBundle. Found key = " + key);
                String value2 = propEncryptor.getSensitiveProperty(key, SHOW_ERROR);
                configProp.put(key, value2);
            }
            LOGGER.info("End Loading PropertyCache Data");
        } catch (final MissingResourceException mre) {
            System.out.println("Error While Loading config file, File " + mre);
        } catch (final Exception ex) {
            System.out.println("Error While Reading from config file, IOException:" + ex);
        }
    }

    public static synchronized PropertyCache getInstance(String resourcePath, String fileName) {
        if (null == propertyCache) {
            propertyCache = new PropertyCache(resourcePath, fileName);
        }
        return propertyCache;
    }

    public String getPropertyKeyValue(String key) {
        return configProp.getProperty(key);
    }
}
