package com.mmonti.view.jackson;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;

public class ViewSerializer extends StdSerializer<Object> {

    private Class asType;

    protected ViewSerializer(Class<Object> t) {
        super(t);
    }

    protected ViewSerializer(JavaType type) {
        super(type);
    }

    public ViewSerializer(Class<?> t, boolean dummy) {
        super(t, dummy);
    }

    protected ViewSerializer(StdSerializer<?> src) {
        super(src);
    }

    public ViewSerializer(Class<?> t, boolean dummy, Class asType) {
        super(t, dummy);
        this.asType = asType;
    }

    @Override
    public void serialize(Object value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        gen.writeObject(asType.cast(value));
    }


}
