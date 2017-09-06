package com.mmonti.view;

import lombok.Getter;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;

/**
 * @author mmonti
 */
public abstract class AbstractInvocationHandler implements InvocationHandler {

    private Object[] NO_ARGS = new Object[0];

    @Getter
    private Object target;

    /**
     *
     * @param target
     */
    public AbstractInvocationHandler(final Object target) {
        this.target = target;
    }

    /**
     *
     * @param proxy
     * @param method
     * @param args
     * @return
     * @throws Throwable
     */
    @Override
    public Object invoke(final Object proxy, final Method method, Object[] args) throws Throwable {
        if (args == null) {
            args = NO_ARGS;
        }

        if (args.length == 0 && method.getName().equals("hashCode")) {
            return hashCode();
        }

        if (args.length == 1 && method.getName().equals("equals") && method.getParameterTypes()[0] == Object.class) {
            Object arg = args[0];
            if (arg == null) {
                return false;
            }
            if (proxy == arg) {
                return true;
            }

            return isProxyOfSameInterfaces(arg, proxy.getClass()) && equals(Proxy.getInvocationHandler(arg));
        }

        if (args.length == 0 && method.getName().equals("toString")) {
            return "Proxy{ target=[" + target + "]}";
        }

        return handle(proxy, method, args);
    }

    /**
     *
     * @param arg
     * @param proxyClass
     * @return
     */
    private static boolean isProxyOfSameInterfaces(final Object arg, final Class<?> proxyClass) {
        return proxyClass.isInstance(arg)
                // Equal proxy instances should mostly be instance of proxyClass
                // Under some edge cases (such as the proxy of JDK types serialized and then deserialized)
                // the proxy type may not be the same.
                // We first check isProxyClass() so that the common case of comparing with non-proxy objects
                // is efficient.
                || (Proxy.isProxyClass(arg.getClass())
                && Arrays.equals(arg.getClass().getInterfaces(), proxyClass.getInterfaces()));

    }

    /**
     *
     * @param clazz
     * @return
     */
    protected static boolean isTargetCollection(final Class clazz) {
        return Collection.class.isAssignableFrom(clazz);
    }

    protected static boolean isTargetMap(final Class clazz) {
        return Map.class.isAssignableFrom(clazz);
    }

    /**
     *
     * @param proxy
     * @param method
     * @param args
     * @return
     * @throws Throwable
     */
    public Object handle(Object proxy, Method method, Object[] args) throws Throwable {
        return handle(getTarget(), proxy, method, args);
    }

    /**
     *
     * @param target
     * @param proxy
     * @param method
     * @param args
     * @return
     * @throws Throwable
     */
    public abstract Object handle(final Object target, final Object proxy, final Method method, final Object[] args) throws Throwable;

    /**
     *
     * @param target
     * @param proxy
     * @param method
     * @param args
     * @return
     * @throws Throwable
     */
    public abstract Object handleMap(final Map target, Object proxy, Method method, Object[] args) throws Throwable;

    /**
     *
     * @param proxy
     * @param method
     * @param args
     * @return
     * @throws Throwable
     */
    public abstract Object handleCollection(final Collection target, Object proxy, Method method, Object[] args) throws Throwable;

}
