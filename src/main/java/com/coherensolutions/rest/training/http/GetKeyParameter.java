package com.coherensolutions.rest.training.http;

public enum GetKeyParameter {
    OLDERTHAN("olderThan"),
    YOUNGERTHAN("youngerThan"),
    SEX("sex");

    public final String value;

    GetKeyParameter(String value) {
        this.value = value;
    }

}
