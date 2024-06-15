package com.mascotapp.core.entities;

import java.util.Objects;

public class MatchResult<T> {
	private T obj1;
    private T obj2;
    private boolean match;
    
    public MatchResult(T obj1, T obj2, boolean match) {
        this.obj1 = obj1;
        this.obj2 = obj2;
        this.match = match;
    }

    public T getObj1() {
        return obj1;
    }

    public T getObj2() {
        return obj2;
    }

    public boolean isMatch() {
        return match;
    }

    @Override
    public String toString() {
        return "MatchResult{" +
                "obj1=" + obj1 +
                ", obj2=" + obj2 +
                ", match=" + match +
                '}';
    }

	@Override
	public int hashCode() {
		return Objects.hash(match, obj1, obj2);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MatchResult<T> other = (MatchResult<T>) obj;
		return match == other.match && Objects.equals(obj1, other.obj1) && Objects.equals(obj2, other.obj2);
	}
    
    
}
