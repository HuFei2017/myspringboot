package com.learning.metadata.definition;

import com.learning.metadata.util.JsonUtil;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName MetaObject
 * @Description TODO
 * @Author hufei
 * @Date 2021/1/28 14:36
 * @Version 1.0
 */
@Getter
@Setter
public class MetaObject {

    private int id;
    private MetaType type;
    private List<MetaField> fields;

    public MetaObject() {
        fields = new ArrayList<>();
    }

    public MetaObject(MetaType type) {
        this();
        this.type = type;
    }

    public MetaObject setId(int id) {
        this.id = id;
        return this;
    }

    public MetaObject setType(MetaType type) {
        this.type = type;
        return this;
    }

    public MetaObject addField(MetaField field) {
        fields.add(field);
        return this;
    }

    public boolean equals(MetaObject obj) {

        if (null == obj)
            return false;

        if (type != obj.getType()) {
            if (!type.equals(obj.getType()))
                return false;
        }

        if (fields != obj.getFields()) {
            if (null == obj.getFields())
                return false;

            if (fields.size() != obj.getFields().size())
                return false;

            int size = fields.size();

            for (int i = 0; i < size; i++) {
                if (!fields.get(i).equals(obj.getFields().get(i)))
                    return false;
            }
        }

        return true;
    }

    public MetaType getFieldType(String fieldName) {

        if (fieldName.startsWith(".") || fieldName.endsWith("."))
            return null;

        return type.getFieldType(fieldName);
    }

    public Object getFieldValue(String fieldName) {

        if (fieldName.startsWith(".") || fieldName.endsWith("."))
            return null;

        if (fieldName.contains(".")) {
            int index = fieldName.indexOf(".");
            String prefix = fieldName.substring(0, index);
            String suffix = fieldName.substring(index + 1);
            for (MetaField field : fields) {
                if (field.getProperty().getName().equals(prefix)) {
                    MetaObject object = JsonUtil.parseJson(field.getValue(), MetaObject.class);
                    if (null == object)
                        return null;
                    return object.getFieldValue(suffix);
                }
            }
        } else {
            for (MetaField field : fields) {
                if (field.getProperty().getName().equals(fieldName))
                    return field.getValue();
            }
        }

        return null;
    }

    public void setFieldValue(String fieldName, Object value) {
        if (!fieldName.startsWith(".") && !fieldName.endsWith(".")) {
            if (fieldName.contains(".")) {
                int index = fieldName.indexOf(".");
                String prefix = fieldName.substring(0, index);
                String suffix = fieldName.substring(index + 1);
                for (MetaField field : fields) {
                    if (field.getProperty().getName().equals(prefix)) {
                        MetaObject object = JsonUtil.parseJson(field.getValue(), MetaObject.class);
                        if (null != object) {
                            object.setFieldValue(suffix, value);
                            field.setValue(object);
                        }
                    }
                }
            } else {
                for (MetaField field : fields) {
                    if (field.getProperty().getName().equals(fieldName)) {
                        field.setValue(value);
                        break;
                    }
                }
            }
        }
    }
}
