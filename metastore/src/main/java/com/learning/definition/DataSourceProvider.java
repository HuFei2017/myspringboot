package com.learning.definition;

/**
 * @ClassName m
 * @Description TODO
 * @Author hufei
 * @Date 2021/4/21 9:45
 * @Version 1.0
 */
public interface DataSourceProvider {

    void createReader();

    void createWriter();

}
