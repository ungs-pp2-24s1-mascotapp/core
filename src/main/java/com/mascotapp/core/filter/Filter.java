package com.mascotapp.core.filter;

import java.util.Set;

public interface Filter<T> {
	Set<T> filter(Set<T> objects);
}
