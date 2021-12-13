package com.learning.metadata.test;

import com.google.common.io.Files;
import com.learning.metadata.definition.MetaObject;
import com.learning.metadata.definition.MetaProperty;
import com.learning.metadata.definition.MetaType;
import com.learning.metadata.enums.TypeStatus;
import com.learning.metadata.handler.ObjectHandler;
import com.learning.metadata.handler.TypeHandler;

import java.io.File;

/**
 * @ClassName Test
 * @Description TODO
 * @Author hufei
 * @Date 2021/2/3 15:32
 * @Version 1.0
 */
public class MemoryManagerTest {

    public static void main(String[] args) throws Exception {

        //定义类型
        //demo: class A {int a,string b}
        MetaType typeA = new MetaType()
                .setName("A")
                .setStatus(TypeStatus.Object)
                .addProperty(
                        new MetaProperty()
                                .setName("a")
                                .setType(
                                        new MetaType()
                                                .setStatus(TypeStatus.Int)
                                )
                )
                .addProperty(
                        new MetaProperty()
                                .setName("b")
                                .setType(
                                        new MetaType()
                                                .setStatus(TypeStatus.Double)
                                )
                );

        MetaType typeB = new MetaType()
                .setName("B")
                .setStatus(TypeStatus.Object)
                .addProperty(
                        new MetaProperty()
                                .setName("c")
                                .setType(typeA)
                )
                .addProperty(
                        new MetaProperty()
                                .setName("d")
                                .setType(
                                        new MetaType()
                                                .setStatus(TypeStatus.String)
                                )
                );

        MetaObject object = ObjectHandler.instance(typeB);

        object.setFieldValue("c.a", 123);
        object.setFieldValue("c.b", 12.3);
        object.setFieldValue("d", "456");

        MetaObject object2 = ObjectHandler.instance(typeB);

        object2.setFieldValue("c.a", 123);
        object2.setFieldValue("c.b", 12.3);
        object2.setFieldValue("d", "456");

        Object a = object.getFieldValue("c.a");
        System.out.println(a);

        Object b = object.getFieldValue("c.b");
        System.out.println(b);

        Object d = object.getFieldValue("d");
        System.out.println(d);

        System.out.println("object and object2 is " + (object.equals(object2) ? "" : "not ") + "equal");

        String value = TypeHandler.serializeTypeJson(typeB);
        Files.write(value.getBytes(), new File("/var/type3.json"));

        System.out.println("#################################");

    }

}
