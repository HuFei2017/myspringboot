package com.learning.metadata.deparated;

import com.learning.metadata.definition.MetaField;
import com.learning.metadata.definition.MetaProperty;
import com.learning.metadata.definition.MetaType;

/**
 * @ClassName ObjectHandler
 * @Description 对象帮助类
 * @Author hufei
 * @Date 2021/1/28 14:28
 * @Version 1.0
 */
public interface CustomObjectHandler {

    /**
     * @param context  对象上下文
     * @param property 属性信息
     * @param data     json数据
     * @Description 创建对象字段
     * @Param [context, property, data]
     * @Return com.learning.metadata.definition.MetaField
     * @Author hufei
     * @Date 2021/1/28 19:31
     */
    MetaField buildObjectField(ObjectContext context, MetaProperty property, Object data);

    /**
     * @param context 对象上下文
     * @param data    json数据
     * @param type    类型信息
     * @Description 分析对象
     * @Param [context, data, type]
     * @Return java.lang.Object
     * @Author hufei
     * @Date 2021/1/28 19:32
     */
    Object parseObject(ObjectContext context, Object data, MetaType type);

    /**
     * @param context 对象上下文
     * @param type    类型信息
     * @param id      对象id
     * @Description 获取对象
     * @Param [context, type, id]
     * @Return java.lang.Object
     * @Author hufei
     * @Date 2021/1/29 10:31
     */
    Object getObject(ObjectContext context, MetaType type, int id);

    /**
     * @param context 对象上下文
     * @param data    json数据
     * @param type    类型信息
     * @param id      id
     * @Description 创建对象
     * @Param [context, data, type, id]
     * @Return java.lang.Object
     * @Author hufei
     * @Date 2021/1/29 10:33
     */
    Object buildObject(ObjectContext context, Object data, MetaType type, int id);

    /**
     * @param property 属性信息
     * @param obj      属性值
     * @Description 创建对象
     * @Param [property, obj]
     * @Return com.learning.metadata.definition.MetaField
     * @Author hufei
     * @Date 2021/1/29 10:47
     */
    MetaField buildField(MetaProperty property, Object obj);
}
