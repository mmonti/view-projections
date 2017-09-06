package com.mmonti.view.jackson;

import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializationConfig;
import com.fasterxml.jackson.databind.ser.BeanSerializerModifier;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Optional;
import java.util.Set;

public class MyClassSerializerModifier extends BeanSerializerModifier {

    private Set<Method> methods = null;

    public MyClassSerializerModifier(Set<Method> methodSet) {
        this.methods = methodSet;
    }

    @Override
    public JsonSerializer<?> modifySerializer(SerializationConfig config, BeanDescription beanDesc, JsonSerializer<?> serializer) {
//        Optional<Method> foundMethod = Arrays.stream(beanDesc.getBeanClass().getDeclaredMethods())
//                .filter(method -> {
//                    Class rt = method.getReturnType();
//                    String name = method.getName();
//
//                    Optional<Method> found = methods
//                            .stream()
//                            .filter(current -> current.getReturnType().equals(rt) && current.getName().equals(name))
//                            .findFirst();
//
//                    return found.isPresent();
//                })
//                .findFirst();

        Optional<Method> foundMethod = methods
                .stream()
                .filter(current -> {
                    Class rt = current.getReturnType();
                    String name = current.getName();

                    Optional<Method> found = Arrays.stream(beanDesc.getBeanClass().getDeclaredMethods())
                            .filter(method -> method.getReturnType().equals(rt) && method.getName().equals(name))
                            .findFirst();

                    return found.isPresent();
                })
                .findFirst();

        if (foundMethod.isPresent()) {
            return new MyClassSerializer(foundMethod.get(), (JsonSerializer<Object>) serializer);
        }
        return serializer;
    }
}