package com.mmonti.view.support;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static java.util.Arrays.*;

public class MethodSupport {

    /**
     *
     * @param target
     * @param method
     * @return
     * @throws NoSuchMethodException
     */
    public static Class getReturningType(final Object target, final Method method) throws NoSuchMethodException {
        final Method methodOnTarget = getMethodOnTarget(target, method);
        final Class methodOnTargetReturnType = methodOnTarget.getReturnType();
        return methodOnTargetReturnType;
    }

    /**
     *
     * @param target
     * @param method
     * @return
     * @throws NoSuchMethodException
     */
    public static Method getMethodOnTarget(final Object target, final Method method, final Class ... args) throws NoSuchMethodException {
        Map<String, Method> cache = new HashMap<>();

        // = Current class
        registerMethods(target.getClass(), cache);

        // = Hierarchy
        Class currentClass = target.getClass();
        while (!currentClass.getSuperclass().equals(Object.class)) {
            currentClass = currentClass.getSuperclass();
            registerMethods(currentClass, cache);
        }

        final String methodName = method.getName();
        final Method methodOnTarget = cache.get(methodName);

        return methodOnTarget;
    }

    private static void registerMethods(final Class targetClass, final Map<String, Method> cache) {
        stream(targetClass.getDeclaredMethods())
                .forEach(currentMethod -> cache.put(currentMethod.getName(), currentMethod));
    }

    /**
     *
     * @param method
     * @return
     */
    public static Class getGenericReturnType(final Method method) {
        return getGenericReturnType(method, 0);
    }

    /**
     *
     * @param method
     * @return
     */
    public static Class getGenericReturnType(final Method method, final int position) {
        final ParameterizedType parameterizedType = (ParameterizedType) method.getGenericReturnType();
        return (Class) parameterizedType.getActualTypeArguments()[position];
    }

}
