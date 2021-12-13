package com.learning.metadata.deparated;

import com.learning.metadata.definition.MetaField;
import com.learning.metadata.definition.MetaProperty;
import com.learning.metadata.definition.MetaType;
import com.learning.metadata.context.TypeContext;
import com.learning.metadata.enums.TypeStatus;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName TypeHandler
 * @Description 类型帮助类
 * @Author hufei
 * @Date 2021/1/28 14:28
 * @Version 1.0
 */
public abstract class CustomTypeHandler {

    /**
     * @param context 类型上下文
     * @param data    json字符串
     * @param type    类型信息
     * @Description 分析属性信息
     * @Param [context, data, type]
     * @Return com.learning.metadata.definition.MetaProperty
     * @Author hufei
     * @Date 2021/1/28 19:28
     */
    public MetaProperty getProperty(TypeContext context, Object data, MetaType type) {
        return null;
    }

    /**
     * @param context 类型上下文
     * @param data    json字符串
     * @param type    类型信息
     * @Description 分析类型字段
     * @Param [context, data, type]
     * @Return com.learning.metadata.definition.MetaField
     * @Author hufei
     * @Date 2021/1/28 19:31
     */
    abstract MetaField getField(TypeContext context, Object data, MetaType type);

    /**
     * @param context 类型上下文
     * @param type    类型信息
     * @Description 解析（去除）引用类型信息
     * @Param [context, type]
     * @Return void
     * @Author hufei
     * @Date 2021/1/28 19:36
     */
    abstract void resolveRefType(TypeContext context, MetaType type);

    /**
     * @param context 类型上下文
     * @param status  类型标识
     * @param name    类型名称
     * @Description 获取类型信息
     * @Param [context, status, name]
     * @Return com.learning.metadata.definition.MetaType
     * @Author hufei
     * @Date 2021/1/29 9:13
     */
    public MetaType getType(TypeContext context, TypeStatus status, String name) {

        if (null == context || null == name || name.isEmpty())
            return null;

        for (MetaType type : context.getTypes()) {
            if (type.getStatus() == status && type.getName().equals(name))
                return type;
        }

        return null;
    }

    /**
     * @param context  类型上下文
     * @param data     json数据
     * @param status   类型标识
     * @param typeName 类型名称
     * @Description 分析类型信息
     * @Param [context, data, status, typeName]
     * @Return com.learning.metadata.definition.MetaType
     * @Author hufei
     * @Date 2021/1/29 9:51
     */
    public MetaType parseObjectType(TypeContext context, Object data, TypeStatus status, String typeName) {

        if (null == context || null == typeName || typeName.isEmpty())
            return null;

        MetaType type = getType(context, status, typeName);

        if (null == type) {

            type = new MetaType();
            List<MetaType> types = new ArrayList<>();

            if (status == TypeStatus.Object || status == TypeStatus.Nested) {
//JsonNode???
            }
            if (status != TypeStatus.Nested && status != TypeStatus.Array) {
                types.addAll(context.getTypes());
            }

            //type.setTypes(types);
        }

        return type;

    }

    /**
     * @param name 属性名称
     * @param type 属性类型
     * @Description 创建属性信息
     * @Param [name, type]
     * @Return com.learning.metadata.definition.MetaProperty
     * @Author hufei
     * @Date 2021/1/29 9:54
     */
    public MetaProperty buildProperty(String name, MetaType type) {

        if (null == name || null == type || name.isEmpty())
            return null;

        MetaProperty property = new MetaProperty();
        property.setName(name);
        property.setType(type);

        return property;
    }

    /**
     * @param context  类型上下文
     * @param data     json数据
     * @param typeName 类型名称
     * @Description 分析类信息
     * @Param [context, data, typeName]
     * @Return com.learning.metadata.definition.MetaType
     * @Author hufei
     * @Date 2021/1/29 10:01
     */
    public MetaType parseType(TypeContext context, Object data, String typeName) {

        if (null == context || null == data || null == typeName || typeName.isEmpty())
            return null;

        MetaType type = new MetaType();

        switch (typeName.toLowerCase()) {
            case "boolean":
                return parseObjectType(context, data, TypeStatus.Boolean, typeName);
            case "number":
                break;
            case "int":
                return parseObjectType(context, data, TypeStatus.Int, typeName);
            case "enum":
                return parseObjectType(context, data, TypeStatus.Enum, typeName);
            case "text":
                return parseObjectType(context, data, TypeStatus.String, "string");
            case "ref":
                break;
            case "object":
                break;
            case "array":
                return parseObjectType(context, data, TypeStatus.Array, typeName);
        }

        return type;
    }

    /**
     * @Description 分析属性信息
     * @Param [context, data, type]
     * @Return com.learning.metadata.definition.MetaProperty
     * @Author hufei
     * @Date 2021/1/29 10:21
     */
    public MetaProperty parseProperty(TypeContext context, Object data, MetaType type) {
        return null;
    }

    /**
     * @Description 分析字段信息
     * @Param [context, data, type]
     * @Return com.learning.metadata.definition.MetaField
     * @Author hufei
     * @Date 2021/1/29 10:22
     */
    abstract MetaField parseTypeField(TypeContext context, Object data, MetaType type);

    /**
     * @param type 类型信息
     * @Description 获取数组元素类型信息
     * @Param [type]
     * @Return com.learning.metadata.definition.MetaType
     * @Author hufei
     * @Date 2021/1/29 10:08
     */
    abstract MetaType getArrayType(MetaType type);

    /**
     * @param context  类型上下文
     * @param property 属性信息
     * @Description 分析（去除）属性信息中的引用类型信息
     * @Param [context, property]
     * @Return void
     * @Author hufei
     * @Date 2021/1/29 10:18
     */
    abstract void resolveRefProperty(TypeContext context, MetaProperty property);
}
