package com.mmonti.view.jackson;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.BeanPropertyWriter;
import com.mmonti.view.DefaultInvocationHandler;
import com.mmonti.view.annotations.ViewProperty;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class SensitivePropertyWriter extends BeanPropertyWriter {

    private final ClassPool POOL = ClassPool.getDefault();
    private final BeanPropertyWriter writer;
    private final Method method;

    public SensitivePropertyWriter(Method method, final BeanPropertyWriter writer) {
        super(writer);
        this.writer = writer;
        this.method = method;
    }

    @Override
    public void serializeAsField(Object bean, JsonGenerator gen, SerializerProvider prov) throws Exception {
        ViewProperty viewProperty = method.getDeclaredAnnotation(ViewProperty.class);
        viewProperty.as().getDeclaredMethods();

//        _accessorMethod.
//        CtClass source = POOL.getCtClass(bean.getClass().getName());

       DefaultInvocationHandler dih = (DefaultInvocationHandler) Proxy.getInvocationHandler(bean);
        Object target = dih.getTarget();

        System.out.println(target);

//
//        ViewProperty viewProperty = method.getDeclaredAnnotation(ViewProperty.class);
//        Object bean1 = ProxySupport.simpleProxy(new DefaultInvocationHandler(new ProviderRegistry(), bean), viewProperty.as());
//        gen.writeObjectFieldStart(writer.getName());
//
//
//        Arrays.stream(viewProperty.as().getDeclaredMethods()).forEach(x->{
//            String property = StringSupport.decapitalize(x.getName());
//            try {
//                Object val = x.invoke(bean);
//                gen.writeFieldName(property);
//                gen.writeObject(val);
//
//            } catch (IllegalAccessException e) {
//            } catch (InvocationTargetException e) {
//            } catch (Throwable e) {
//
//            }
//        });
//        gen.writeEndObject();
    }




}