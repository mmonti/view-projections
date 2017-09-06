package com.mmonti.view.jackson;

import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.SerializationConfig;
import com.fasterxml.jackson.databind.ser.BeanPropertyWriter;
import com.fasterxml.jackson.databind.ser.BeanSerializerModifier;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.regex.Pattern;

public class SensitivePropertySerializerModifier extends BeanSerializerModifier {

    private Set<Method> methods;

    public SensitivePropertySerializerModifier(Set<Method> methodSet) {
        this.methods = methodSet;
    }

    @Override
    public List<BeanPropertyWriter> changeProperties(SerializationConfig config,
                                                     BeanDescription beanDesc,
                                                     List<BeanPropertyWriter> beanProperties) {
        for (int i = 0; i < beanProperties.size(); i++) {
            BeanPropertyWriter writer = beanProperties.get(i);
            Optional<Method> found = methods
                    .stream()
                    .filter(x -> {
                        return x.getReturnType().equals(writer.getType().getRawClass());
                    })
                    .findFirst();
            if (found.isPresent())
                beanProperties.set(i, new SensitivePropertyWriter(found.get(), writer));
        }
        return beanProperties;
    }

}