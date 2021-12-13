package com.learning.metadata.deparated;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.learning.metadata.context.TypeContext;
import com.learning.metadata.definition.MetaField;
import com.learning.metadata.definition.MetaProperty;
import com.learning.metadata.definition.MetaType;
import com.learning.metadata.enums.TypeStatus;

import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName TypeHelper
 * @Description TODO
 * @Author hufei
 * @Date 2021/1/30 15:09
 * @Version 1.0
 */
public class CustomTypeHelper {

    private static final ObjectMapper mapper = new ObjectMapper();

    public static TypeContext loadTypeContext(String data) throws JsonProcessingException {

        if (null == data)
            return null;

        TypeContext context = new TypeContext();

        JsonNode types = mapper.readTree(data);

        if (null != types && types.isArray()) {
            for (JsonNode item : types) {
                if (!item.isArray()) {
                    String typeName = item.has("type") ? item.get("type").textValue() : "";
                    if (!typeName.isEmpty()) {
                        parseTypeInfo(context, item, typeName);
                    }
                }
            }

            for (MetaType type : context.getTypes()) {
                resolveRefTypeInfo(context, type);
            }
        }

        return context;
    }

    private static MetaType parseTypeInfo(TypeContext context, JsonNode data, String typeName) {

        if (null == context || null == data || null == typeName || typeName.isEmpty())
            return null;

        switch (typeName.toLowerCase()) {
            case "bool":
            case "enum":
            case "text":
            case "number":
                TypeStatus status = parseStatus(typeName, data.has("format") ? data.get("format").textValue() : "");
                return parseObjectTypeInfo(context, data, status, status.toString().toLowerCase());
            case "ref":
                int refId = data.has("refId") ? data.get("refId").intValue() : -1;
                return parseObjectTypeInfo(context, data, TypeStatus.Ref, String.valueOf(refId));
            case "object":
                JsonNode id = data.get("id");
                if (null != id && !id.isNull()) {
                    if (id.isInt()) {
                        return parseObjectTypeInfo(context, data, TypeStatus.Object, String.valueOf(id.intValue()));
                    } else if (id.isTextual()) {
                        return parseObjectTypeInfo(context, data, TypeStatus.Nested, id.textValue());
                    }
                }
                return null;
            case "array":
                JsonNode items = data.get("items");
                if (null != items && !items.isNull() && !items.isArray()) {
                    MetaType type = parseObjectTypeInfo(context, data, TypeStatus.Array, "array");
                    if (null != type) {
                        String arrayType = items.has("type") ? items.get("type").textValue() : "";
                        MetaType nestedType = parseTypeInfo(context, items, arrayType);
                        if (null != nestedType) {
                            //type.addNestedType(nestedType.getTypes());
                        }
                    }
                    return type;
                }
                return null;
        }

        return null;
    }

    private static MetaType parseObjectTypeInfo(TypeContext context, JsonNode data, TypeStatus status, String typeName) {

        if (null == context || null == data || null == status || null == typeName || typeName.isEmpty())
            return null;

        MetaType type = getTypeInfo(context, status, typeName);

        if (null == type) {
            type = new MetaType(typeName, status);

            if (status == TypeStatus.Object || status == TypeStatus.Nested) {
                JsonNode properties = data.get("properties");

                if (null != properties && properties.isArray()) {
                    for (JsonNode property : properties) {
                        parsePropertyInfo(context, property, type);
                    }
                }

                JsonNode fields = data.get("fields");

                if (null != fields && fields.isArray()) {
                    for (JsonNode field : fields) {
                        parseTypeField(context, field, type);
                    }
                }
            }

            if (status != TypeStatus.Nested && status != TypeStatus.Array)
                context.addType(type);
        }

        return type;
    }

    private static MetaType getTypeInfo(TypeContext context, TypeStatus status, String name) {

        if (null == context || null == status || null == name || name.isEmpty())
            return null;

        for (MetaType type : context.getTypes()) {
            if (type.getStatus() == status && type.getName().equals(name))
                return type;
        }

        return null;
    }

    private static MetaProperty parsePropertyInfo(TypeContext context, JsonNode data, MetaType type) {

        if (null == context || null == data || null == type)
            return null;

        MetaProperty property = new MetaProperty();

        String propertyName = data.has("id") ? data.get("id").textValue() : "";
        String typeName = data.has("type") ? data.get("type").textValue() : "";

        if (!propertyName.isEmpty() && !typeName.isEmpty()) {

            MetaType propertyType = parseTypeInfo(context, data, typeName);

            if (null != propertyType) {

                if (propertyType.getStatus() == TypeStatus.Nested || propertyType.getStatus() == TypeStatus.Array)
                    type.addNestedType(propertyType);

                property.setName(propertyName);
                property.setType(propertyType);
                type.addProperty(property);
            }
        }

        return property;
    }

    private static MetaField parseTypeField(TypeContext context, JsonNode data, MetaType type) {

        if (null == context || null == data || null == type)
            return null;

        String fieldName = data.has("id") ? data.get("id").textValue() : "";
        String typeName = data.has("type") ? data.get("type").textValue() : "";
        JsonNode value = data.get("value");

        MetaField field = new MetaField();

        if (!fieldName.isEmpty() && !typeName.isEmpty() && null != value) {
            MetaType fieldType = parseTypeInfo(context, data, typeName);
            if (null != fieldType) {

                if (fieldType.getStatus() == TypeStatus.Nested || fieldType.getStatus() == TypeStatus.Array)
                    type.addNestedType(fieldType);

                MetaProperty property = new MetaProperty(fieldName, fieldType);
                field.setProperty(property);
                field.setValue(value);
                type.addField(field);
            }
        }

        return field;
    }

    private static void resolveRefPropertyInfo(TypeContext context, MetaProperty property) {
        if (null != context && null != property) {
            if (property.getType().getStatus() == TypeStatus.Ref) {
                property.setType(getTypeInfo(context, TypeStatus.Object, property.getType().getName()));
            } else if (property.getType().getStatus() == TypeStatus.Nested) {
                resolveRefTypeInfo(context, property.getType());
            } else if (property.getType().getStatus() == TypeStatus.Array) {
                MetaType arrayType = getArrayType(property.getType());
                if (null != arrayType && arrayType.getStatus() == TypeStatus.Ref) {
                    MetaType objType = getTypeInfo(context, TypeStatus.Object, arrayType.getName());
                    if (null != objType) {
                        //property.getType().addNestedType(objType.getTypes());
                    }
                }
            }
        }
    }

    private static void resolveRefTypeInfo(TypeContext context, MetaType type) {
        if (null != context && null != type) {
            if (type.getStatus() == TypeStatus.Object || type.getStatus() == TypeStatus.Nested) {
                for (MetaProperty property : type.getProperties()) {
                    resolveRefPropertyInfo(context, property);
                }
                for (MetaField field : type.getFields()) {
                    resolveRefPropertyInfo(context, field.getProperty());
                }
            }
        }
    }

    private static MetaType getArrayType(MetaType type) {
        if (null == type)
            return null;
        if (type.getStatus() == TypeStatus.Array) {
            if (type.getTypes().size() > 0)
                return type.getTypes().get(0);
        }
        return null;
    }


    public static TypeContext loadTypeContext(Object data) throws JsonProcessingException {

        JsonNode node = mapper.readTree(data.toString());

        Map<Integer, MetaType> refMap = new HashMap<>();

        TypeContext context = new TypeContext();

        if (null != node && node.isArray()) {
            for (JsonNode item : node) {
                int id = item.get("id").intValue();
                MetaType type = loadTypeJson(item);
                context.addType(type);
                refMap.put(id, type);
            }
        }

        return context;
    }

    private static MetaType loadTypeJson(JsonNode node) {

        MetaType ret = new MetaType();

        if (null != node) {

            //获取类型名称
            if (node.has("category"))
                ret.setName(node.get("category").textValue());
            else if (node.has("type"))
                ret.setName(node.get("type").textValue());
            else
                ret.setName("");

            //获取类型标识, 代表是通用数据类型/引用类型/数组等
            TypeStatus status = parseStatus(node.get("type").textValue(), node.has("format") ? node.get("format").textValue() : "");
            ret.setStatus(status);

            if (status == TypeStatus.Array)
                //ret.setItems(loadTypeJson(node.get("items")));

            //获取类型属性
            if (node.has("properties") && node.get("properties").isArray()) {
                for (JsonNode item : node.get("properties")) {
                    MetaProperty property = new MetaProperty();
                    property.setName(item.get("id").textValue());

                    MetaType nestedType = loadTypeJson(item);

                    property.setType(nestedType);
                    ret.addProperty(property);
                    ret.addNestedType(nestedType);
                }
            }

            //获取类型静态字段
            if (node.has("fields") && node.get("fields").isArray()) {
                for (JsonNode item : node.get("fields")) {
                    MetaField field = new MetaField(new MetaProperty(item.get("name").textValue(), loadTypeJson(item)));
                    field.setValue(item.get("value"));
                    ret.addField(field);
                }
            }

        }

        return ret;
    }

    private static TypeStatus parseStatus(String status) {

        if (status.toLowerCase().equals("text"))
            return TypeStatus.String;
        else if (status.toLowerCase().equals("bool"))
            return TypeStatus.Boolean;

        for (TypeStatus item : TypeStatus.values())
            if (item.toString().toLowerCase().equals(status.toLowerCase()))
                return item;

        return null;
    }

    private static TypeStatus parseStatus(String status, String format) {

        if (status.toLowerCase().equals("number")) {
            switch (format.toLowerCase()) {
                case "double":
                case "float":
                    return TypeStatus.Double;
                case "int":
                case "long":
                case "short":
                default:
                    return TypeStatus.Int;
            }
        }

        return parseStatus(status);
    }

}
