package com.learning.dto;

import lombok.Data;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.Setting;

import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName Devlevel
 * @Description TODO
 * @Author hufei
 * @Date 2022/3/4 19:37
 * @Version 1.0
 */
@Data
@Document(indexName = "sim-devlevel-data")
@Setting(shards = 3, replicas = 0)
public class Devlevel {

    @Field(type = FieldType.Keyword)
    private String ordernumber;
    @Field(type = FieldType.Keyword)
    private String filename;
    @Field(type = FieldType.Keyword)
    private String creator;
    @Field(type = FieldType.Keyword)
    private String filePath;

    public Map<String, Object> getMapValue() {
        Map<String, Object> map = new HashMap<>();
        map.put("ordernumber", this.ordernumber);
        map.put("filename", this.filename);
        map.put("creator", this.creator);
        map.put("filePath", this.filePath);
        return map;
    }
}
