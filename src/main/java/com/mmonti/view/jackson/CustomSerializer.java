package com.mmonti.view.jackson;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;
import java.io.Serializable;

public class CustomSerializer extends StdSerializer<Serializable> {

    public CustomSerializer() {
        this(null);
    }

    public CustomSerializer(Class<Serializable> t) {
        super(t);
    }

    @Override
    public void serialize(Serializable value, JsonGenerator gen, SerializerProvider serializers) throws IOException, JsonProcessingException {
        System.out.println(value);
    }
}
