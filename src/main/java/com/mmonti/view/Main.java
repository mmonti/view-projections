package com.mmonti.view;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mmonti.view.model.Address;
import com.mmonti.view.model.Entity;
import com.mmonti.view.model.Hobby;
import com.mmonti.view.model.Person;
import com.mmonti.view.model.views.*;
import com.mmonti.view.process.ViewProcessor;

import java.util.Arrays;

public class Main {

    public static void main(String[] args) {
        Person person = Person.builder()
                .name("Mauro")
                .address(Address.builder()
                        .address("620 W Wilson")
                        .city("Glendale")
                        .state("CA").build())
                .hobbies(Arrays.asList(Hobby.builder().name("Fly").build())).build();

        ViewRegistry viewRegistry = new ViewRegistry();
        viewRegistry.register(Person.class, PersonView.class);
        viewRegistry.register(Address.class, AddressView.class);
        viewRegistry.register(Hobby.class, HobbyView.class);
        viewRegistry.register(Entity.class, EntityView.class);

        ViewProcessor viewProcessor = new ViewProcessor(viewRegistry);

        Object result = viewProcessor.process(new DefaultView<Person>("default", person));

        ObjectMapper mapper = new ObjectMapper();

        String output = null;
        try {
            mapper.writer(new DefaultPrettyPrinter()).writeValueAsString(result);
        } catch (JsonProcessingException e) {
            System.out.println(e.getMessage());
        }
        System.out.println(output);
    }
}
