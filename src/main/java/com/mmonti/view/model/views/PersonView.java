package com.mmonti.view.model.views;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.mmonti.view.annotations.View;
import com.mmonti.view.annotations.ViewProperty;
import com.mmonti.view.jackson.CustomSerializer;
import com.mmonti.view.model.CustomAddress;
import com.mmonti.view.model.providers.AddressProviderImpl;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

@View
@JsonPropertyOrder(value = {"name", "things", "otherThings", "otherOtherThings", "hobbies", "address"})
public interface PersonView {

    @JsonView(value = {Views.Default.class, Views.Minimum.class})
    String getName();

    @ViewProperty(handler = AddressProviderImpl.class, as = CustomAddressView.class)
    @JsonView(value = {Views.Default.class, Views.Minimum.class})
    Object getAddress();

    @JsonView(value = {Views.Default.class, Views.Minimum.class})
    List<HobbyView> getHobbies();

    @JsonView(value = {Views.Default.class, Views.Minimum.class})
    Map<String, HobbyView> getThings();

    @JsonView(value = {Views.Default.class})
    List<String> getOtherThings();

    @JsonView(value = {Views.Default.class})
    Map<String, String> getOtherOtherThings();
}
