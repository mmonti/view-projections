package com.mmonti.view;

import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.mmonti.view.annotations.ViewProperty;
import com.mmonti.view.jackson.SensitivePropertySerializerModifier;
import com.mmonti.view.model.Address;
import com.mmonti.view.model.Hobby;
import com.mmonti.view.model.Person;
import com.mmonti.view.model.providers.AddressProviderImpl;
import com.mmonti.view.model.providers.ProviderRegistry;
import com.mmonti.view.model.views.PersonView;
import com.mmonti.view.model.views.Views;
import com.mmonti.view.services.AddressServiceImpl;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.bytecode.ClassFile;
import org.reflections.Reflections;
import org.reflections.scanners.MethodAnnotationsScanner;
import org.reflections.util.ConfigurationBuilder;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.mmonti.view.support.ProxySupport.simpleProxy;

public class CGLibMain {

    public static void main(String[] args) throws Exception {

        final Reflections f = new Reflections(new ConfigurationBuilder()
                .forPackages("com.mmonti.view")
                .addScanners(new MethodAnnotationsScanner())
        );

        final SimpleModule module = new SimpleModule();
        Set<Method> methodSet = f.getMethodsAnnotatedWith(ViewProperty.class)
                .stream()
                .map(m -> {
                    final ViewProperty viewProperty = m.getAnnotation(ViewProperty.class);
//                    module.addSerializer(new ViewSerializer(m.getReturnType(), false, viewProperty.as()));
                    return m;
                })
                .collect(Collectors.toSet());

//        Class method = methodSet.iterator().next().getDeclaringClass();
//        CtClass ct = ClassPool.getDefault().get(method.getCanonicalName());
//        CtMethod[] mms = ct.getDeclaredMethods();
//
//        ClassFile cf = new ClassFile(true, CGLibMain.class.getPackage().getName(), null);
//        cf.addMethod(mms[1].getMethodInfo2());
//        Method[] i = ClassPool.getDefault().makeClass(cf).toClass().getMethods();
//        System.out.println(i);
//        cf.addMethod();

        module.setSerializerModifier(new SensitivePropertySerializerModifier(methodSet));

        final Person person = Person.builder()
                .name("Mauro")
                .address(Address.builder()
                        .address("620 W Wilson")
                        .city("Glendale")
                        .state("CA").build())
                .things(new HashMap<String, Hobby>(){{put("key", Hobby.builder().name("value").build());}})
                .otherThings(Arrays.asList("thing1", "thing2"))
                .otherOtherThings(new HashMap<String, String>(){{put("otherKey1", "otherValue2");}})
                .hobbies(Arrays.asList(
                        Hobby.builder().name("Fly").build(), Hobby.builder().name("Travel").build()
                )).build();

        final ProviderRegistry providerRegistry = new ProviderRegistry();
        providerRegistry.register(new AddressProviderImpl(new AddressServiceImpl()));

        ObjectMapper mapper = new ObjectMapper();
//        mapper.registerModule(module);

        mapper.disable(MapperFeature.DEFAULT_VIEW_INCLUSION);

        Object proxy = simpleProxy(new DefaultInvocationHandler(providerRegistry, person), PersonView.class);
        String result = mapper
                .writerWithView(Views.Default.class).with(new DefaultPrettyPrinter())
                .writeValueAsString(proxy);

        System.out.println(result);
    }
}
