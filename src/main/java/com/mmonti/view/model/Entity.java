package com.mmonti.view.model;

import lombok.Getter;

import java.util.UUID;

@Getter
public class Entity {

    private String id = UUID.randomUUID().toString();

}
