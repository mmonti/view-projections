package com.mmonti.view;

import com.mmonti.view.annotations.ViewProperty;
import com.mmonti.view.model.providers.Provider;
import com.mmonti.view.model.providers.ProviderRegistry;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Map;
import java.util.function.Function;

import static com.mmonti.view.support.ClassSupport.isSimpleValueType;
import static com.mmonti.view.support.MethodSupport.*;
import static com.mmonti.view.support.ProxySupport.isProxy;
import static com.mmonti.view.support.ProxySupport.simpleProxy;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;

/**
 * @author mmonti
 */
public class DefaultInvocationHandler extends AbstractInvocationHandler {

    private ProviderRegistry providerRegistry;

    /**
     *
     * @param target
     */
    public DefaultInvocationHandler(final ProviderRegistry providers, final Object target) {
        super(target);
        this.providerRegistry = providers;
    }

    @Override
    public Object handle(final Object target, final Object proxy, final Method method, final Object[] args) throws Throwable {
        final Class targetMethodReturningType = getReturningType(target, method);

        // = Get the value
        final Object targetValue = getTargetValue(method, target);

        // = Handle simple values.
        if (isSimpleValueType(targetMethodReturningType)) {
            return targetValue;
        }

        if (isTargetMap(targetMethodReturningType)) {
            return handleMap((Map) targetValue, proxy, method, args);
        }

        // = Handle collections.
        if (isTargetCollection(targetMethodReturningType)) {
            return handleCollection((Collection) targetValue, proxy, method, args);
        }

        if (isProxy(targetValue)) {
            return targetValue;
        }

        // = Handle typed objects.
        return simpleProxy(new DefaultInvocationHandler(providerRegistry, targetValue), method.getReturnType());
    }

    @Override
    public Object handleMap(Map targetMap, Object proxy, Method method, Object[] args) throws Throwable {
        final Class keyReturnType = getGenericReturnType(method, 0);
        final Class valReturnType = getGenericReturnType(method, 1);

        return targetMap
                .entrySet()
                .stream()
                .collect(toMap(
                        (Function<Map.Entry, Object>) entry -> getTargetValue(keyReturnType, entry.getKey()),
                        (Function<Map.Entry, Object>) entry -> getTargetValue(valReturnType, entry.getValue()))
                );
    }

    @Override
    public Object handleCollection(final Collection targetValue, final Object proxy, final Method method,
                                   final Object[] args) throws Throwable {

        final Class methodReturningType = getGenericReturnType(method);
        if (isSimpleValueType(methodReturningType)) {
            return targetValue;
        }
        return targetValue
                .stream()
                .map(currentTarget -> getTargetValue(methodReturningType, currentTarget))
                .collect(toList());
    }

    /**
     *
     * @param method
     * @param target
     * @param args
     * @return
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     * @throws NoSuchMethodException
     */
    private Object getTargetValue(final Method method, final Object target, final Object ... args) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        final Method methodOnTarget = getMethodOnTarget(target, method);
        if (methodOnTarget == null) {
            return null;
        }
        final Object targetValue = methodOnTarget.invoke(target);

        final ViewProperty viewPropertyAnnotationOnMethod = method.getDeclaredAnnotation(ViewProperty.class);
        if (viewPropertyAnnotationOnMethod == null) {
            return targetValue;
        }

        final Class handler = viewPropertyAnnotationOnMethod.handler();
        final Class as = viewPropertyAnnotationOnMethod.as();

        final Provider provider = providerRegistry.get(handler);
        if (provider == null) {
            return targetValue;
        }

        final Object result = provider.provide(getTarget(), targetValue);

        try {
            method.getReturnType().cast(result);
        } catch (Exception e) {
            System.out.println("Cannot cast "+result.getClass()+" to "+method.getReturnType());
        }
        if (as != null) {
            return simpleProxy(new DefaultInvocationHandler(providerRegistry, result), as);
        }

        return result;
    }

    /**
     *
     * @param returningType
     * @param targetValue
     * @return
     */
    private Object getTargetValue(final Class returningType, final Object targetValue) {
        if (isSimpleValueType(returningType)) {
            return targetValue;
        }
        return simpleProxy(new DefaultInvocationHandler(providerRegistry, targetValue), returningType);
    }
}
