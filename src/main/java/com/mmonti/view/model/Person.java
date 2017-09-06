package com.mmonti.view.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.Map;

@Getter
@Builder
@AllArgsConstructor
public class Person extends Entity {

    private String name;
    private Address address;
    private List<Hobby> hobbies;
    private Map<String, Hobby> things;
    private List<String> otherThings;
    private Map<String, String> otherOtherThings;
}
