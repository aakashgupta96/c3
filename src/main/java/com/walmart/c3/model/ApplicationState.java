package com.walmart.c3.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum ApplicationState {
    RUNNING("Running"),
    STOPPED("Stopped"),
    NOT_DEFINED("NotDefined");

    @Getter
    private String parameterName;
}
