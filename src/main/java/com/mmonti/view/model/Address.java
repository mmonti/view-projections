package com.mmonti.view.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class Address extends Entity {

    private String address;
    private String city;
    private String state;

}
