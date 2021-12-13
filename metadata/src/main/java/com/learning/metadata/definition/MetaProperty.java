package com.learning.metadata.definition;

import lombok.Getter;
import lombok.Setter;

/**
 * @ClassName MetaProperty
 * @Description TODO
 * @Author hufei
 * @Date 2021/1/28 14:27
 * @Version 1.0
 */
@Getter
@Setter
public class MetaProperty {

    private String name;
    private MetaType type;

    public MetaProperty() {
    }

    public MetaProperty(String name) {
        this();
        this.name = name;
    }

    public MetaProperty(String name, MetaType type) {
        this();
        this.name = name;
        this.type = type;
    }

    public MetaProperty setName(String name) {
        this.name = name;
        return this;
    }

    public MetaProperty setType(MetaType type) {
        this.type = type;
        return this;
    }

    public boolean equals(MetaProperty property) {

        if (null == property)
            return false;

        if (!name.equals(property.getName()))
            return false;

        if (type != property.getType())
            return type.equals(property.getType());

        return true;
    }

}
