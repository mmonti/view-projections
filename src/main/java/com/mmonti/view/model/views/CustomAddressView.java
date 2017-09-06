package com.mmonti.view.model.views;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import com.mmonti.view.annotations.View;

import java.util.List;

@View
public interface CustomAddressView {

    @JsonView(value = Views.Default.class)
    @JsonProperty(value = "firstAddress")
    List<String> getOne();
}
