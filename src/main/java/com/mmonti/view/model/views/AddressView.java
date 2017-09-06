package com.mmonti.view.model.views;

import com.fasterxml.jackson.annotation.JsonView;
import com.mmonti.view.annotations.View;

@View
public interface AddressView {

    @JsonView(value = {Views.Default.class, Views.Minimum.class})
    String getCity();

    @JsonView(value = {Views.Default.class})
    String getAddress();

    @JsonView(value = {Views.Default.class})
    String getState();

}
