package com.quasar.backend.core;

import java.util.HashMap;
import java.util.Map;

public class MyThreadLocal {

    private static ThreadLocal<Map> localMap = new ThreadLocal<Map>();


    public static void set(String key, Object value) {
        Map myMap = localMap.get();
        if (myMap == null) {
            myMap = new HashMap();
            localMap.set(myMap);
        }
        myMap.put(key, value);
    }

    public static Object get(String key) {
        Map myMap = localMap.get();
        if (myMap == null) {
            myMap = new HashMap();
            localMap.set(myMap);
        }
        return myMap.get(key);
    }

    public static void remove(String key) {
        Map myMap = localMap.get();
        if (myMap == null) {
            myMap = new HashMap();
            localMap.set(myMap);
        }
        myMap.remove(key);
    }

}
