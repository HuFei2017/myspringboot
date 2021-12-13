package com.learning.examples.examples;

import com.learning.examples.util.TextUtil;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName TextTest
 * @Description TODO
 * @Author hufei
 * @Date 2021/11/6 10:28
 * @Version 1.0
 */
public class TextTest {
    public static void main(String[] args){
        String str = "cValue";
        Map<String,Object> cValue = new HashMap<>();
        cValue.put("c",str);
        cValue.put("cL",Collections.singletonList(str));
        Map<String,Object> bValue = new HashMap<>();
        bValue.put("b",cValue);
        bValue.put("bL",Collections.singletonList(cValue));
        String aValue = TextUtil.fromJson(bValue);
        String aLValue = TextUtil.fromJson(Collections.singletonList(bValue));
        Object obj1 = replaceValue("a",aValue,"test");
        Object obj2 = replaceValue("a[0]",aLValue,"test");
        Object obj3 = replaceValue("a.b",aValue,"test");
        Object obj4 = replaceValue("a.bL[0]",aValue,"test");
        Object obj5 = replaceValue("a[0].b",aLValue,"test");
        Object obj6 = replaceValue("a[0].bL[0]",aLValue,"test");
        Object obj7 = replaceValue("a.b.c",aValue,"test");
        Object obj8 = replaceValue("a.b.cL[0]",aValue,"test");
        Object obj9 = replaceValue("a.bL[0].c",aValue,"test");
        Object obj0 = replaceValue("a.bL[0].cL[0]",aValue,"test");
        Object obja = replaceValue("a[0].b.c",aLValue,"test");
        Object objb = replaceValue("a[0].b.cL[0]",aLValue,"test");
        Object objd = replaceValue("a[0].bL[0].c",aLValue,"test");
        Object obje = replaceValue("a[0].bL[0].cL[0]",aLValue,"test");
        System.out.println();
    }

    @SuppressWarnings("unchecked")
    private static Object replaceValue(String propertyName, Object data, Object value) {
        if (!propertyName.startsWith(".") && !propertyName.endsWith(".")) {
            if (propertyName.contains(".")) {
                int index = propertyName.indexOf(".");
                String prefix = propertyName.substring(0, index);
                String suffix = propertyName.substring(index + 1);
                if (prefix.contains("[")) {
                    Object[] var = TextUtil.parseJson(TextUtil.fromJson(data), Object[].class);
                    if (null != var) {
                        int sub = Integer.parseInt(prefix.substring(prefix.indexOf('[') + 1, prefix.indexOf("]")));
                        var[sub] = replaceValue("obj." + suffix, var[sub], value);
                        return var;
                    }
                } else {
                    Map var = TextUtil.parseJson(TextUtil.fromJson(data), Map.class);
                    if (null != var) {
                        String varColumn1 = suffix.contains(".") ? suffix.substring(0, suffix.indexOf(".")) : suffix;
                        String varColumn2 = varColumn1.contains("[") ? varColumn1.substring(0, varColumn1.indexOf("[")) : varColumn1;
                        var.put(varColumn2, replaceValue(suffix, var.get(varColumn2), value));
                        return var;
                    }
                }
            } else {
                if (propertyName.contains("[")) {
                    Object[] var = TextUtil.parseJson(TextUtil.fromJson(data), Object[].class);
                    if (null != var) {
                        int sub = Integer.parseInt(propertyName.substring(propertyName.indexOf('[') + 1, propertyName.indexOf("]")));
                        var[sub] = value;
                        return var;
                    }
                } else {
                    return value;
                }
            }
        }
        return data;
    }
}
