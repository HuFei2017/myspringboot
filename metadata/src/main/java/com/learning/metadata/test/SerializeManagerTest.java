package com.learning.metadata.test;

import com.google.common.io.Files;
import com.learning.metadata.context.TypeContext;
import com.learning.metadata.definition.MetaType;
import com.learning.metadata.handler.TypeHandler;

import java.io.File;
import java.nio.charset.Charset;

/**
 * @ClassName SerializeManagerTest
 * @Description TODO
 * @Author hufei
 * @Date 2021/2/5 15:56
 * @Version 1.0
 */
public class SerializeManagerTest {

    public static void main(String[] args) throws Exception {

        String str = Files.readFirstLine(new File("/var/type.json"), Charset.defaultCharset());

        MetaType type = TypeHandler.loadTypeJson(str);

        TypeContext context = TypeHandler.loadTypeContext(str);

        System.out.println("object and object2 is " + (type.equals(context.getMetaType(type.getName())) ? "" : "not ") + "equal");

        String value = TypeHandler.serializeTypeJson(type);

        Files.write(value.getBytes(), new File("/var/type2.json"));

        System.out.println("#################################");
    }

}
