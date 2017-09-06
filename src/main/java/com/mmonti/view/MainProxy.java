package com.mmonti.view;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mmonti.view.model.Address;
import com.mmonti.view.model.Hobby;
import com.mmonti.view.model.Person;
import com.mmonti.view.model.providers.AddressProviderImpl;
import com.mmonti.view.model.providers.ProviderRegistry;
import com.mmonti.view.model.views.PersonView;
import com.mmonti.view.model.views.Views;
import com.mmonti.view.services.AddressServiceImpl;
import com.mmonti.view.support.ProxySupport;

import java.util.Arrays;
import java.util.HashMap;

public class MainProxy {

    public static void main(String[] args) throws JsonProcessingException {
        Person person = Person.builder()
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

        ProviderRegistry providerRegistry = new ProviderRegistry();
        providerRegistry.register(new AddressProviderImpl(new AddressServiceImpl()));

        PersonView viewProxy = ProxySupport.simpleProxy(new DefaultInvocationHandler(providerRegistry, person), PersonView.class);

        ObjectMapper mapper = new ObjectMapper();
        mapper.disable(MapperFeature.DEFAULT_VIEW_INCLUSION);

        String result = mapper
                .writerWithView(Views.Default.class).with(new DefaultPrettyPrinter())
                .writeValueAsString(viewProxy);

        System.out.println(result);


    }

}
