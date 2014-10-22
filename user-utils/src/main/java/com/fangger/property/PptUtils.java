package com.fangger.property;

import java.io.*;
import java.util.*;
import java.util.zip.InflaterInputStream;

/**
 * Created by popo on 2014/10/2.
 */
public class PptUtils {
    private static final Map<String,Properties> propertiesCacheMap = new HashMap<String,Properties>();
    private static Properties props = new Properties();

    public static Properties load(String path,boolean cached) throws IOException {
        return cached?loadAndCache(path):loadWithoutCache(path);
    }

    public static void deleteFromCache(String path){
        propertiesCacheMap.remove(path);
    }

    private static Properties loadAndCache(String path) throws IOException {
        Properties properties = propertiesCacheMap.get(path);
        if(properties == null){
            InputStream inputStream = new BufferedInputStream(new FileInputStream(path));
            properties = new Properties();
            props.load(inputStream);
        }
        return props;
    }



    private static Properties loadWithoutCache(String path) throws IOException {
        InputStream inputStream = new BufferedInputStream(new FileInputStream(path));
        props.load(inputStream);
        return props;
    }

    public static void main(String[] args) {

    }
}
