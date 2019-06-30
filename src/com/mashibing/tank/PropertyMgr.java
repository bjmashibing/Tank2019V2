package com.mashibing.tank;

import java.io.IOException;
import java.util.Properties;

public class PropertyMgr {
    private static Properties props;

    static {
        try {
            props = new Properties();
            props.load(PropertyMgr.class.getClassLoader().getResourceAsStream("config"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String get(String key) {
        return (String)props.get(key);
    }

}
