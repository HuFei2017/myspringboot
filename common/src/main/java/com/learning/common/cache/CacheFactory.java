package com.learning.common.cache;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @ClassName CacheFactory
 * @Description 内存工厂
 * @Author jiashudong
 * @Date 2020/8/7 13:50
 * @Version 1.0
 */
public class CacheFactory {

    //数据容器
    private Map<String, Object> container;

    public CacheFactory() {
        container = new LinkedHashMap<>();
    }

    /**
     * 如果组存在就返回,不存在就创建,默认容量1000
     */
    public Group allocate(String key, int capacity) {
        Group group;
        Object entry = container.get(key);
        if (entry != null) {
            group = (Group) entry;
        } else {
            group = new Group(capacity < 0 ? 1000 : capacity);
            container.put(key, group);
        }

        return group;
    }
}
