package com.learning.metadata.handler;

import com.learning.metadata.definition.MetaField;
import com.learning.metadata.definition.MetaObject;
import com.learning.metadata.definition.MetaProperty;
import com.learning.metadata.definition.MetaType;

import java.util.ArrayList;

/**
 * @ClassName ObjectHandler
 * @Description TODO
 * @Author hufei
 * @Date 2021/2/4 11:32
 * @Version 1.0
 */
public class ObjectHandler {

    private static int id = 1;

    public static MetaObject instance(MetaType type) {

        MetaObject obj = new MetaObject(type);

        for (MetaProperty property : type.getProperties()) {

            MetaField field = new MetaField();
            field.setProperty(property);

            switch (property.getType().getStatus()) {
                case Object:
                    field.setValue(instance(property.getType()));//也可以设置为null
                    break;
                case Boolean:
                    field.setValue(false);
                    break;
                case Int:
                    field.setValue(0);
                    break;
                case Double:
                    field.setValue(0.0);
                    break;
                case String:
                    field.setValue("");//也可以设置为null
                    break;
                case Array:
                    field.setValue(new ArrayList<>());//也可以设置为null
                    break;
                default:
                    field.setValue(null);
            }

            obj.addField(field);
        }

        obj.setId(id++);

        return obj;
    }

}
