package com.mmonti.view.jackson;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.mmonti.view.DefaultInvocationHandler;
import com.mmonti.view.annotations.ViewProperty;

import java.io.IOException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Optional;

import static com.google.common.base.Preconditions.checkNotNull;

public class MyClassSerializer extends JsonSerializer<Object> {

    private final JsonSerializer<Object> defaultSerializer;
    private Method method = null;

    public MyClassSerializer(Method method, JsonSerializer<Object> serializer) {
        this.defaultSerializer = checkNotNull(serializer);
        this.method = method;
    }

    @Override
    public void serialize(Object value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        ViewProperty vp = method.getDeclaredAnnotation(ViewProperty.class);
        Class<?> as = vp.as();

        if (value.getClass().equals(method.getReturnType())) {
            defaultSerializer.serialize(as.cast(((DefaultInvocationHandler) Proxy.getInvocationHandler(value)).getTarget()), gen, provider);
        }
        defaultSerializer.serialize(value, gen, provider);
    }
}