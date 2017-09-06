package com.mmonti.view.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class Hobby extends Entity {

    private String name;

    public static Hobby of(String name) {
        return new Hobby(name);
    }
}
