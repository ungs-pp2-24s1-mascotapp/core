package com.mascotapp.core.service.matcher;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.math3.exception.NullArgumentException;

import com.mascotapp.core.entities.Match;
import com.mascotapp.core.entities.Post;

public abstract class Matcher {
	
	public static Set<Match> getMatchs(Set<Post> founds, Set<Post> losts) throws NullArgumentException {
		if (founds == null || losts == null)
			throw new NullArgumentException();
		
		Set<Match> matches = new HashSet<Match>();
		
		if (founds.size() == 0 || losts.size() == 0) 
			return matches;
		
		for (Post found : founds) {
            for (Post lost : losts) {
                if (isMatch(found, lost)) {
                    Match match = new Match(found, lost);
                    matches.add(match);
                }
            }
        }
		
		return matches;
	}
	
	private static boolean isMatch(Post found, Post lost) throws NullArgumentException {
		if (found == null || lost == null)
			throw new NullArgumentException();
		
		String[] foundWords = getKeywords(found);
        String[] lostWords = getKeywords(lost);
        
        return isMatchingWords(foundWords, lostWords);
	}
	
	private static String[] getKeywords(Post post) {
		return post.getContent().toLowerCase().split("\\s+");
	}
	
	private static boolean isMatchingWords(String[] foundWords, String[] lostWords) {
		if (foundWords.length == 0 || lostWords.length == 0) return false;
		
		int minimumNumberWordMatches = 2;
		
        int matchingWordsCount = 0;
        for (String foundWord : foundWords) {
            for (String lostWord : lostWords) {
                if (foundWord.equals(lostWord)) {
                    matchingWordsCount++;
                    if (matchingWordsCount >= minimumNumberWordMatches) {
                        return true;
                    }
                }
            }
        }
        
        return false;
	}
}
