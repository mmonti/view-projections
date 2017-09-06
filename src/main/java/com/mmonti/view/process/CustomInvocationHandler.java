//package com.mmonti.view.process;
//
//import com.mmonti.view.model.views.ViewRegistry;
//import com.mmonti.view.support.ProxySupport;
//
//import java.lang.reflect.Method;
//import java.lang.reflect.ParameterizedType;
//import java.math.BigDecimal;
//import java.util.*;
//
//public class CustomInvocationHandler extends ProxySupport.InterfaceInvocationHandler {
//
//    final ViewRegistry viewRegistry;
//
//    /**
//     * @param target
//     * @param viewRegistry
//     */
//    public CustomInvocationHandler(Object target, ViewRegistry viewRegistry) {
//        super(target);
//        this.viewRegistry = viewRegistry;
//    }
//
//    @Override
//    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
//        Object object = super.invoke(proxy, method, args);
//        if (object != null) {
//            return object;
//        }
//
//        final Object target = getTarget();
//        final Method targetMethod = target.getClass().getMethod(method.getName());
//
//        if (!isWrapperType(target.getClass())) {
//            if (isCollection(targetMethod.getReturnType())) {
//                Class targetType = (Class) ((ParameterizedType)targetMethod.getGenericReturnType()).getActualTypeArguments()[0];
//                Class targetTypeReturningIface = viewRegistry.getView(targetType);
//                Object p = ProxySupport.simpleProxy(this, targetTypeReturningIface);
//                return p;
//            }
//            final Class targetReturningIface = viewRegistry.getView(targetMethod.getReturnType());
//            final Object targetReturningIfaceProxy = ProxySupport.ifaceProxy(target, targetReturningIface);
//            return targetReturningIfaceProxy;
//        } else {
//            return targetMethod.invoke(target, args);
//        }
//    }
//
//    private static final Set<Class> WRAPPER_TYPES = new HashSet(Arrays.asList(
//            BigDecimal.class,
//            Boolean.class,
//            Character.class,
//            Byte.class,
//            Short.class,
//            Integer.class,
//            Long.class,
//            Float.class,
//            Double.class,
//            Void.class,
//            Object.class,
//            Date.class,
//            String.class)
//    );
//
//    private static boolean isWrapperType(final Class clazz) {
//        return WRAPPER_TYPES.contains(clazz);
//    }
//
//    private static boolean isCollection(final Class clazz) {
//        return Collection.class.isAssignableFrom(clazz);
//    }
//
//    private static String decapitalize(String value) {
//        if (value == null || value.length() == 0) {
//            return value;
//        }
//        char c[] = value.toCharArray();
//        c[0] = Character.toLowerCase(c[0]);
//        return new String(c);
//    }
//}
