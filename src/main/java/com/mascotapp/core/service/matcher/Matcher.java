package com.mascotapp.core.service.matcher;

import java.util.Set;

import org.apache.commons.math3.exception.NullArgumentException;

import com.mascotapp.core.entities.Match;
import com.mascotapp.core.entities.Post;

public interface Matcher {
	
	Set<Match> getMatchs(Set<Post> founds, Set<Post> losts) throws NullArgumentException;
}
