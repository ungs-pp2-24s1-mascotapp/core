package com.mascotapp.core.evaluator;

import java.util.HashSet;
import java.util.Set;

import com.mascotapp.core.entities.Match;
import com.mascotapp.core.entities.Post;
import com.mascotapp.core.service.matcher.Matcher;

public class PostMatchEvaluator implements MatchEvaluator {
	
	private Matcher<Post> matcher;
	
	public PostMatchEvaluator(Matcher<Post> matcher) {
		this.matcher = matcher;
	}
	
	public Set<Match> findMatches(Set<Post> founds, Set<Post> losts) {
		Set<Match> results = new HashSet<>();

        for (Post found : founds) {
            for (Post lost : losts) {
                boolean match = matcher.isMatch(found, lost);
                if (match) results.add(new Match(found,lost));
            }
        }

        return results;
	}
}
