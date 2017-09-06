package com.mmonti.view.support;

import com.mmonti.view.DefaultInvocationHandler;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.stream.Stream;

/**
 * @author mmonti
 */
public class ProxySupport {

    /**
     *
     * @param handler
     * @param ifaces
     * @param <T>
     * @return
     */
    public static <T> T simpleProxy(final InvocationHandler handler, final Class<?> ... ifaces) {
        final Class<?>[] allInterfaces = Stream.of(ifaces).distinct().toArray(Class<?>[]::new);
        return (T) Proxy.newProxyInstance(ifaces[0].getClassLoader(), allInterfaces, handler);
    }

    /**
     *
     * @param iface
     * @param target
     * @param <T>
     * @return
     */
    public static <T> T passThroughProxy(final T target, final Class<? extends T> iface) {
        return simpleProxy(new PassThroughInvocationHandler(target), iface);
    }

    /**
     *
     * @param targetValue
     * @return
     */
    public static boolean isProxy(final Object targetValue) {
        return Proxy.isProxyClass(targetValue.getClass());
    }

    /**
     * Pass-through Invocation Handler
     */
    public static class PassThroughInvocationHandler implements InvocationHandler {

        /**
         *
         */
        private final Object target;

        /**
         *
         * @param target
         */
        public PassThroughInvocationHandler(final Object target) {
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
        public Object invoke(final Object proxy, final Method method, final Object[] args) throws Throwable {
            return method.invoke(target, args);
        }
    }
}
