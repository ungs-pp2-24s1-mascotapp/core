package com.mascotapp.core.service.matcher;

import org.apache.commons.math3.exception.NullArgumentException;

public interface Matcher<T> {
    boolean isMatch(T obj1, T obj2) throws NullArgumentException;
}
