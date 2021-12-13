package com.learning.metadata.deparated;

import com.learning.metadata.context.TypeContext;
import com.learning.metadata.definition.MetaObject;
import lombok.Data;

import java.util.List;

/**
 * @ClassName ObjectContext
 * @Description TODO
 * @Author hufei
 * @Date 2021/1/28 14:41
 * @Version 1.0
 */
@Data
public class ObjectContext {
    private int id;
    private TypeContext types;
    private List<MetaObject> objects;
}
