package com.mascotapp.core.entities;

public class Notification<T> {
    private T data;
    private String observableName;

    public Notification(T data, String observableName) {
        this.data = data;
        this.observableName = observableName;
    }

    public T getData() {
        return data;
    }

    public String getObservableName() {
        return observableName;
    }
}