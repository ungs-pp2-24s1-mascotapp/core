package com.mascotapp.core.service.matcher;

import java.util.Set;

import org.apache.commons.math3.exception.NullArgumentException;

import com.mascotapp.core.entities.Match;
import com.mascotapp.core.entities.Post;

public interface Matcher<T> {
    boolean isMatch(T obj1, T obj2) throws NullArgumentException;
}
