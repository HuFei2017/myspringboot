package com.learning.metadata.handler;

import com.fasterxml.jackson.databind.JsonNode;
import com.learning.metadata.context.TypeContext;
import com.learning.metadata.definition.MetaField;
import com.learning.metadata.definition.MetaProperty;
import com.learning.metadata.definition.MetaType;
import com.learning.metadata.enums.TypeStatus;
import com.learning.metadata.util.JsonUtil;

import java.util.*;

/**
 * @ClassName TypeHandler
 * @Description TODO
 * @Author hufei
 * @Date 2021/2/3 16:17
 * @Version 1.0
 */
public class TypeHandler {

    private static final String[] nestedType = {"Components", "Basic", "Necessary", "Important", "Secondary", "Parameters", "MeasurementPoints", "Indexes", "ModelCode"};

    public static List<String> getEnumValues(MetaType type) {

        if (null == type || type.getStatus() != TypeStatus.Enum)
            return new ArrayList<>();

        List<String> values = new ArrayList<>();

        for (MetaProperty property : type.getProperties()) {
            values.add(property.getName());
        }

        return values;
    }

    public static MetaType loadTypeJson(String data) {

        if (null == data || data.isEmpty())
            return null;

        JsonNode node = JsonUtil.parseJson(data);

        if (null == node)
            return null;

        if (!node.isArray()) {
            return parseMetaType(node);
        } else {
            boolean empty = true;
            MetaType type = null;
            //先把所有的类加载出来,此时包含类引用信息
            Map<Integer, MetaType> map = new HashMap<>();
            for (JsonNode item : node) {
                int id = JsonUtil.getIntValue(item, "id");
                MetaType meta = parseMetaType(item);
                map.put(id, meta);
                //第一个是需要的类
                if (empty) {
                    type = meta;
                    empty = false;
                }
            }
            //把类引用信息替换为具体的类
            if (null != type) {
                replaceRefType(type, map);
            }
            return type;
        }
    }

    public static TypeContext loadTypeContext(String data) {

        TypeContext context = new TypeContext();

        if (null == data || data.isEmpty())
            return context;

        JsonNode node = JsonUtil.parseJson(data);

        if (null == node)
            return context;

        if (!node.isArray()) {
            context.addType(parseMetaType(node));
        } else {
            //先把所有的类加载出来,此时包含类引用信息
            List<MetaType> list = new ArrayList<>();
            Map<Integer, MetaType> map = new HashMap<>();
            for (JsonNode item : node) {
                int id = JsonUtil.getIntValue(item, "id");
                MetaType meta = parseMetaType(item);
                map.put(id, meta);
                list.add(meta);
            }
            //把类引用信息替换为具体的类
            for (MetaType item : list) {
                MetaType type = replaceRefType(item, map);
                if (null != type)
                    context.addType(type);
            }
        }

        return context;
    }

    public static String serializeTypeJson(MetaType type) {

        Map<String, MetaType> map = new HashMap<>();
        //把所有的类析出
        subMetaType(map, type);

        //从其他地方加载过来的类名称就是其ID
        Set<String> typeNames = map.keySet();
        int maxID = getMaxID(typeNames);
        Map<String, Integer> typeIdMap = getTypeID(typeNames, maxID);

        List<Map<String, Object>> list = new ArrayList<>();

        for (Map.Entry<String, MetaType> entry : map.entrySet()) {
            if (!JsonUtil.contains(nestedType, entry.getKey())) {
                list.add(replaceRefID(entry.getValue(), String.valueOf(typeIdMap.get(entry.getKey())), String.valueOf(typeIdMap.get(entry.getKey())), typeIdMap));
            }
        }

        return JsonUtil.writeValue(list);
    }

    public static String serializeTypeContext(TypeContext context) {


        return "";
    }

    private static Map<String, Object> replaceRefID(MetaType type, String typeName, String propertyName, Map<String, Integer> typeID) {

        Map<String, Object> map = new HashMap<>();

        if (null == type)
            return map;

        if (!propertyName.isEmpty())
            map.put("id", propertyName);

        if (type.getStatus() == TypeStatus.Boolean) {
            map.put("type", "bool");
        } else if (type.getStatus() == TypeStatus.Int) {
            map.put("type", "number");
            map.put("format", "int");
        } else if (type.getStatus() == TypeStatus.Double) {
            map.put("type", "number");
            map.put("format", "double");
        } else if (type.getStatus() == TypeStatus.String) {
            map.put("type", "text");
        } else if (type.getStatus() == TypeStatus.Enum) {
            map.put("type", "enum");

            List<Map<String, String>> enums = new ArrayList<>();

            for (MetaProperty property : type.getProperties()) {
                Map<String, String> item = new HashMap<>();
                item.put("value", property.getName());
                enums.add(item);
            }

            map.put("enum", enums);
        } else if (type.getStatus() == TypeStatus.Array) {
            map.put("type", "array");
            map.put("items", replaceRefID(type.getTypes().get(0), typeName, "", typeID));
        } else if (type.getStatus() == TypeStatus.Ref) {
            map.put("type", "ref");
        } else if (type.getStatus() == TypeStatus.Object) {
            //object
            if (JsonUtil.contains(nestedType, propertyName) || typeName.equals(propertyName)) {
                map.put("type", "object");

                List<Map<String, Object>> properties = new ArrayList<>();

                for (MetaProperty property : type.getProperties()) {
                    properties.add(replaceRefID(property.getType(), typeName, property.getName(), typeID));
                }

                if (properties.size() > 0)
                    map.put("properties", properties);

                List<Map<String, Object>> fields = new ArrayList<>();

                for (MetaField field : type.getFields()) {
                    Map<String, Object> fieldItem = replaceRefID(field.getProperty().getType(), typeName, field.getProperty().getName(), typeID);
                    fieldItem.put("value", field.getValue());
                    fields.add(fieldItem);
                }

                if (fields.size() > 0)
                    map.put("fields", fields);
            }
            //ref
            else {
                map.put("type", "ref");
                map.put("refId", typeID.get(type.getName()));
            }
        }

        return map;
    }

    private static int getMaxID(Collection<String> names) {

        int max = 0;

        for (String name : names) {
            try {
                int tmp = Integer.parseInt(name);
                if (tmp > max)
                    max = tmp;
            } catch (NumberFormatException ignored) {
            }
        }

        return max;
    }

    private static Map<String, Integer> getTypeID(Collection<String> names, int maxID) {

        Map<String, Integer> map = new HashMap<>();

        for (String name : names) {
            try {
                map.put(name, Integer.parseInt(name));
            } catch (NumberFormatException ignored) {
                map.put(name, ++maxID);
            }
        }

        return map;
    }

    private static void subMetaType(Map<String, MetaType> map, MetaType type) {
        if (type.getStatus() == TypeStatus.Object) {
            if (JsonUtil.contains(nestedType, type.getName()) || !map.containsKey(type.getName())) {
                map.put(type.getName(), type);
                for (MetaType metaType : type.getTypes())
                    subMetaType(map, metaType);
                for (MetaField field : type.getFields())
                    subMetaType(map, field.getProperty().getType());
                for (MetaProperty property : type.getProperties())
                    subMetaType(map, property.getType());
            }
        } else if (type.getStatus() == TypeStatus.Array) {
            if (JsonUtil.contains(nestedType, type.getName()) || !map.containsKey(type.getName())) {
                for (MetaType metaType : type.getTypes())
                    subMetaType(map, metaType);
            }
        }
    }

    private static MetaType parseMetaType(JsonNode data) {

        if (null == data || data.isNull())
            return null;

        String type = JsonUtil.getStringValue(data, "type");

        if (type.isEmpty())
            return null;

        switch (type) {
            case "text":
                return new MetaType(TypeStatus.String);
            case "bool":
                return new MetaType(TypeStatus.Boolean);
            case "number":
                String format = JsonUtil.getStringValue(data, "format");
                if (format.equals("double") || format.equals("float"))
                    return new MetaType(TypeStatus.Double);
                else
                    return new MetaType(TypeStatus.Int);
            case "enum": {
                MetaType meta = new MetaType()
                        .setName("enum")
                        .setStatus(TypeStatus.Enum);
                JsonNode enums = data.get("enum");
                if (null != enums && enums.isArray()) {
                    for (JsonNode value : enums) {
                        meta.addEnumValues(JsonUtil.getStringValue(value, "value"));
                    }
                }
                return meta;
            }
            case "array": {
                MetaType meta = new MetaType()
                        .setName("array")
                        .setStatus(TypeStatus.Array);
                JsonNode items = data.get("items");
                if (null != items && !items.isNull() && !items.isArray()) {
                    meta.setArrayType(parseMetaType(items));
                }
                return meta;
            }
            case "ref":
                return new MetaType()
                        .setName(JsonUtil.getStringValue(data, "refId"))
                        .setStatus(TypeStatus.Ref);
            case "object":
                JsonNode id = data.get("id");
                if (null != id && !id.isNull()) {

                    String idStr = JsonUtil.getStringValue(data, "id");
                    //名称和标识
                    MetaType meta = new MetaType()
                            .setName(idStr)
                            .setStatus(TypeStatus.Object);
                    //静态属性
                    JsonNode fields = data.get("fields");
                    if (null != fields && fields.isArray()) {
                        for (JsonNode field : fields) {
                            meta.addField(new MetaField(
                                    new MetaProperty(JsonUtil.getStringValue(field, "id"), parseMetaType(field)),
                                    field.get("value")
                            ));
                        }
                    }
                    //属性
                    JsonNode properties = data.get("properties");
                    if (null != properties && properties.isArray()) {
                        for (JsonNode property : properties) {
                            meta.addProperty(new MetaProperty(
                                    JsonUtil.getStringValue(property, "id"), parseMetaType(property)
                            ));
                        }
                    }

                    return meta;
                }
                break;
        }

        return null;
    }

    private static MetaType replaceRefType(MetaType type, Map<Integer, MetaType> map) {

        if (null != type) {
            if (type.getStatus() == TypeStatus.Ref) {
                //引用必须有效,即存在引用ID
                if (!type.getName().isEmpty()) {
                    int id = Integer.parseInt(type.getName());
                    return replaceRefType(map.get(id), map);
                }
            } else if (type.getStatus() == TypeStatus.Array) {
                //替换数组类型元素类型
                type.setArrayType(replaceRefType(type.getTypes().get(0), map));
                return type;
            } else if (type.getStatus() == TypeStatus.Object) {
                //属性类型替换
                for (MetaProperty property : type.getProperties()) {
                    MetaType meta = replaceRefType(property.getType(), map);
                    property.setType(meta);
                    boolean existed = false;
                    for (MetaType nest : type.getTypes()) {
                        if (nest.getName().equals(meta.getName())) {
                            existed = true;
                            break;
                        }
                    }
                    //属性类型使用了其他类型,类型归并到内部类中且不重复
                    if (!existed)
                        type.addNestedType(meta);
                }
                //静态属性类型替换
                for (MetaField field : type.getFields()) {
                    field.getProperty().setType(replaceRefType(field.getProperty().getType(), map));
                }
                //内部类类型替换
                List<MetaType> types = new ArrayList<>();
                for (MetaType metaType : type.getTypes()) {
                    types.add(replaceRefType(metaType, map));
                }
                type.resetNestedType(types);
                return type;
            }
            //其他情况原样返回
            return type;
        }

        return null;
    }

}
