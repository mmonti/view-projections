package com.mmonti.view.model.views;

import java.util.HashMap;
import java.util.Map;

/**
 *
 */
public class ViewRegistry {

    private Map<Class<?>, Class<?>> registry = new HashMap();

    public void register(Class entity, Class view) {
        registry.put(entity, view);
    }

    public Class getView(Class entity) {
        Class viewType = registry.get(entity);
//        if (viewType == null) {
//            throw new NotFoundException("no view registered for entity=["+entity+"]");
//        }
        return viewType;
    }
}
