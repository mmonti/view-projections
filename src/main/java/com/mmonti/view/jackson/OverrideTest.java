package com.mmonti.view.jackson;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.Modifier;
import javassist.bytecode.Descriptor;
import org.junit.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

import static org.junit.Assert.assertEquals;

public class OverrideTest {

    final ClassPool POOL = ClassPool.getDefault();
  
    class Foo {  
        public Integer getFoo() { return new Integer(1); }  
        public String doA() { return getFoo().toString(); }  
    }  
  
    class FooExt {  
        public String getFoo() { return "A"; }  
    }  
  
    @Test
    public void d() throws Throwable {  
        CtClass srcClass = POOL.getCtClass(Foo.class.getName());
        CtClass extClass = POOL.getCtClass(FooExt.class.getName());  
        CtClass d = POOL.makeClass("Derp");  
        CtClass STRING = POOL.get("java.lang.String");  
        CtClass INT = POOL.get("java.lang.Integer");

        {  
            CtMethod doA1 = srcClass.getMethod("doA", Descriptor.ofMethod(STRING, new CtClass[0]));
            CtMethod getFoo1 = srcClass.getMethod("getFoo", Descriptor.ofMethod(INT, new CtClass[0]));  
            CtMethod getFoo = new CtMethod(INT, "getFoo", new CtClass[0], d);  
            CtMethod doA = new CtMethod(STRING, "doA", new CtClass[0], d);  
            d.addMethod(doA);  
            d.addMethod(getFoo);  
            doA.setBody(doA1, null);  
            getFoo.setBody(getFoo1, null);  
            d.setModifiers(d.getModifiers() & ~Modifier.ABSTRACT);  
  
            d.removeMethod(getFoo);  
            CtMethod getFooExt = new CtMethod(STRING, "getFoo", new CtClass[0], d);  
            d.addMethod(getFooExt);  
            CtMethod getFooExt1 = extClass.getMethod("getFoo", Descriptor.ofMethod(STRING, new CtClass[0]));  
            getFooExt.setBody(getFooExt1, null);  
  
            doA.setBody("{ return getFoo().toString(); }");  
            d.setModifiers(d.getModifiers() & ~Modifier.ABSTRACT);
        }  

        {
            Class<?> c = d.toClass();  
            Constructor<?> ctor = c.getConstructor();
            Object derp = ctor.newInstance();  
            Method getFoo = derp.getClass().getMethod("getFoo");  
            Method doA = derp.getClass().getMethod("doA");
            Object doResult = doA.invoke(derp);  
            Object getResult = getFoo.invoke(derp);  
            assertEquals("A", getResult);  
            assertEquals("A", doResult);  
        }  
    }  
}  