package com.learning.metadata.definition;

import com.learning.metadata.util.JsonUtil;
import lombok.Getter;
import lombok.Setter;

/**
 * @ClassName MetaField
 * @Description TODO
 * @Author hufei
 * @Date 2021/1/28 14:46
 * @Version 1.0
 */
@Getter
@Setter
public class MetaField {

    private MetaProperty property;
    private Object value;

    public MetaField() {
    }

    public MetaField(MetaProperty property) {
        this();
        this.property = property;
    }

    public MetaField(MetaProperty property, Object value) {
        this();
        this.property = property;
        this.value = value;
    }

    public MetaField setProperty(MetaProperty property) {
        this.property = property;
        return this;
    }

    public MetaField setValue(Object value) {
        this.value = value;
        return this;
    }

    public boolean equals(MetaField field) {

        if (null == field)
            return false;

        MetaObject obj1 = JsonUtil.parseJson(value, MetaObject.class);
        MetaObject obj2 = JsonUtil.parseJson(field.getValue(), MetaObject.class);
        if (null == obj1 && null == obj2) {
            if (!value.equals(field.getValue()))
                return false;
        } else {
            if (null == obj1 || null == obj2)
                return false;
            else if (!obj1.equals(obj2))
                return false;
        }

        if (property != field.getProperty())
            return property.equals(field.getProperty());

        return true;
    }
}
