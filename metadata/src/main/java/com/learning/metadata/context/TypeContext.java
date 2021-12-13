package com.learning.metadata.context;

import com.learning.metadata.definition.MetaType;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName TypeContext
 * @Description TODO
 * @Author hufei
 * @Date 2021/1/28 14:41
 * @Version 1.0
 */
@Getter
public class TypeContext {

    private List<MetaType> types;

    public TypeContext() {
        this.types = new ArrayList<>();
    }

    public TypeContext addType(MetaType type) {
        types.add(type);
        return this;
    }

    public MetaType getMetaType(String name) {

        if (null == name || name.isEmpty())
            return null;

        for (MetaType type : types) {
            if (null != type) {
                if (type.getName().equals(name))
                    return type;
            }
        }

        return null;
    }
}
