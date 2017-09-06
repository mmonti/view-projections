package com.mmonti.view.model;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class CustomAddress {

    private List<String> one;
    private List<String> two;

}
