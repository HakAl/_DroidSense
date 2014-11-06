package com.jacmobile.sensorpanellite.util;

import java.util.ArrayList;
import java.util.Properties;

/**
 * Created by alex on 10/19/14.
 */
public class SystemInfo {
    private static Properties properties;

    public SystemInfo(){
        this.properties = System.getProperties();
    }

    public static ArrayList<String> getAllProperties()
    {
        return new ArrayList<String>(properties.stringPropertyNames());
    }

    private static String PREFIX = "java.vm.";

    public static String getVendor() {
        return System.getProperty(PREFIX + "vendor");
    }

    public static String getVendorUrl() {
        return System.getProperty(PREFIX + "vendor.url");
    }

    public static String getVMName() {
        return System.getProperty("java.specification.name") + " " + System.getProperty(PREFIX + "specification.version");
    }
}