package com.learning.common.util;

import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.JsonNode;
import com.github.victools.jsonschema.generator.*;
import com.learning.common.abstraction.DefaultDescription;
import com.learning.common.abstraction.DefaultValue;

/**
 * @ClassName SchemaUtil
 * @Description Schema工具类, 获取元数据
 * @Author hufei
 * @Date 2020/7/16 9:17
 * @Version 1.0
 */
public class SchemaUtil {

    /**
     * 获取schema
     *
     * @param className 类型名称
     * @return 返回元数据字符串
     */
    public static String getSchema(Class className) {
        SchemaGeneratorConfigBuilder configBuilder = new SchemaGeneratorConfigBuilder(SchemaVersion.DRAFT_2019_09, OptionPreset.PLAIN_JSON);
        configBuilder.forFields()
                .withDefaultResolver(field -> {
                    DefaultValue var1 = field.getAnnotation(DefaultValue.class);
                    return null == var1 ? null : var1.value();
                })
                .withTitleResolver(field -> {
                    JsonPropertyDescription var2 = field.getAnnotation(JsonPropertyDescription.class);
                    return null == var2 ? null : var2.value();
                })
                .withDescriptionResolver(field -> {
                    DefaultDescription var3 = field.getAnnotation(DefaultDescription.class);
                    return null == var3 ? null : var3.value();
                });
        SchemaGeneratorConfig config = configBuilder.build();
        SchemaGenerator generator = new SchemaGenerator(config);
        JsonNode jsonSchema = generator.generateSchema(className);

        return jsonSchema.toString();
    }

}
