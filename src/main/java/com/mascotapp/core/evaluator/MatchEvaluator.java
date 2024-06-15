package com.mascotapp.core.evaluator;

import java.util.HashSet;
import java.util.Set;

import com.mascotapp.core.entities.MatchResult;
import com.mascotapp.core.service.matcher.Matcher;

public abstract class MatchEvaluator<T> {
	
	private Matcher<T> matcher;
	
	public MatchEvaluator(Matcher<T> matcher) {
		this.matcher = matcher;
	}
	
	public Set<MatchResult<T>> findMatches(Set<T> set1, Set<T> set2) {
		Set<MatchResult<T>> results = new HashSet<>();

        for (T objSet1 : set1) {
            for (T objSet2 : set2) {
                boolean match = matcher.isMatch(objSet1, objSet2);
                results.add(new MatchResult<T>(objSet1, objSet2, match));
            }
        }

        return results;
	}
}
