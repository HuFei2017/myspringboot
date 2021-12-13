package com.learning.common.util;

import com.learning.common.cache.CacheFactory;

/**
 * @ClassName CacheUtil
 * @Description 内存管理
 * @Author jiashudong
 * @Date 2020/8/7 17:22
 * @Version 1.0
 */
public class CacheUtil {

    private static CacheFactory cacheFactory;

    public static CacheFactory getCacheFactory() {
        if (null == cacheFactory)
            cacheFactory = new CacheFactory();
        return cacheFactory;
    }
}
