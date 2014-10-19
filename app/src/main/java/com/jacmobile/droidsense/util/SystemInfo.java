package com.jacmobile.droidsense.util;

import java.lang.System;

/**
 * Created by alex on 10/19/14.
 */
public class SystemInfo
{
    private static String A = "java.vm.";

    public static String getVendor()
    {
        return System.getProperty(A+"vendor");
    }

    public static String getVendorUrl()
    {
        return System.getProperty(A+"vendor.url");
    }

    public static String getVMName()
    {
        return System.getProperty("java.specification.name") + " " + System.getProperty(A+"specification.version");
    }
}
