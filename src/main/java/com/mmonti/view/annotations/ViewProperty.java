package com.mmonti.view.annotations;

import com.mmonti.view.model.providers.Provider;

import java.io.Serializable;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.function.Function;

@Retention(value = RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface ViewProperty {

    Class<? extends Provider> handler();
    String method() default "";
    Class as() default Serializable.class;

}
