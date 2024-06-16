package com.mascotapp.core.evaluator;

import java.util.Set;

import com.mascotapp.core.entities.Match;
import com.mascotapp.core.entities.Post;

public interface MatchEvaluator {
	Set<Match> findMatches(Set<Post> founds, Set<Post> losts);
}
