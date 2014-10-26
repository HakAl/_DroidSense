package com.jacmobile.droidsense.util;

import java.lang.System;

/**
 * Created by alex on 10/19/14.
 */
public class SystemInfo
{
    private static String PREFIX = "java.vm.";

    public static String getVendor()
    {
        return System.getProperty(PREFIX +"vendor");
    }

    public static String getVendorUrl()
    {
        return System.getProperty(PREFIX +"vendor.url");
    }

    public static String getVMName()
    {
        return System.getProperty("java.specification.name") + " " + System.getProperty(PREFIX +"specification.version");
    }
}