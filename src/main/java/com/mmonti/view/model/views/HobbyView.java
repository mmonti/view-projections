package com.mmonti.view.model.views;

import com.fasterxml.jackson.annotation.JsonView;
import com.mmonti.view.annotations.View;

@View
public interface HobbyView {

    @JsonView(value = {Views.Default.class, Views.Minimum.class})
    String getName();

}
