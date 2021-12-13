package com.learning.common.cache;

import java.io.Serializable;

/**
 * @ClassName CacheEntity
 * @Description 缓存实体
 * @Author jiashudong
 * @Date 2020/8/7 13:52
 * @Version 1.0
 */
public class CacheEntity implements Serializable {

    private static final long serialVersionUID = 2082223810638865724L;
    private String key;//key
    private Object value;//值
    private Long timestamp;//缓存的时候存的时间戳，用来计算该元素是否过期
    private int expire;//默认长期有效
    private Group group;//容器

    public CacheEntity(String key, Object value, Long timestamp, int expire, Group group) {
        super();
        this.key = key;
        this.value = value;
        this.timestamp = timestamp;
        this.expire = expire;
        this.group = group;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    void setExpire(int expire) {
        this.expire = expire;
    }

    /**
     * 获取剩余时间
     */
    int ttl() {
        if (this.expire == 0) {
            return this.expire;
        }
        return this.expire - getTime();
    }

    /**
     * 获取当前时间和元素的相差时间
     */
    private int getTime() {
        if (this.expire == 0) {
            return this.expire;
        }
        Long current = System.currentTimeMillis();
        long value = current - this.timestamp;
        return (int) (value / 1000) + 1;
    }

    /**
     * 是否到期
     */
    boolean isExpire() {
        if (this.expire == 0) {
            return true;
        }
        if (getTime() > this.expire) {
            // 失效了就移除
            group.delete(key);
            return false;
        }
        return true;
    }
}
