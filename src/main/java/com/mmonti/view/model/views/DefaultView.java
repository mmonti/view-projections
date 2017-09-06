package com.mmonti.view.model.views;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class DefaultView<T> implements ProcessableView<T> {

    private String group;
    private T entity;

}
