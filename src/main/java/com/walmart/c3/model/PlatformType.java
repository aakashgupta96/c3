package com.walmart.c3.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum PlatformType {
    AZURE("azure"),
    WCNP("wcnp");

    @Getter
    private String parameterName;
}
