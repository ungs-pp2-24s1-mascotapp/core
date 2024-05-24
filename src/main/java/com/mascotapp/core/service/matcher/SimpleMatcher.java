package com.mascotapp.core.service.matcher;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.math3.exception.NullArgumentException;

import com.mascotapp.core.entities.Match;
import com.mascotapp.core.entities.Post;

public class SimpleMatcher implements Matcher {
	
	private Set<String> keywords;
	private int minimumNumberWordMatches = 2;
	private int minimumNumberCharacters = 3;
	private int minimumKeywordsToFound = 2;
	
	public SimpleMatcher() {
		keywords = new HashSet<String>();
		keywords.add("perro");
		keywords.add("perra");
		keywords.add("perrito");
		keywords.add("perrita");
		keywords.add("labrador");
		keywords.add("mestizo");
		keywords.add("caniche");
		keywords.add("pastor");
		keywords.add("pug");
		keywords.add("gato");
		keywords.add("gata");
		keywords.add("gatito");
		keywords.add("gatita");
		keywords.add("siames");
		keywords.add("golden");
		keywords.add("retriever");
	}
	
	public SimpleMatcher(Set<String> keywords) {
		this.keywords = keywords;
	}
	
	public Set<Match> getMatchs(Set<Post> founds, Set<Post> losts) throws NullArgumentException {
		if (founds == null || losts == null)
			throw new NullArgumentException();
		
		Set<Match> matches = new HashSet<>();
		
		if (founds.isEmpty() || losts.isEmpty()) 
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
	
	private boolean isMatch(Post found, Post lost) throws NullArgumentException {
		if (found == null || lost == null)
			throw new NullArgumentException();
		
		String[] foundWords = getKeywords(found);
        String[] lostWords = getKeywords(lost);
        
        return isMatchingWords(foundWords, lostWords);
	}
	
	private String[] getKeywords(Post post) {
		String content = post.getContent().toLowerCase();
		if(!containsAny(content, keywords)) return null;
		return post.getContent().toLowerCase().split("\\s+");
	}
	
	private boolean isMatchingWords(String[] foundWords, String[] lostWords) {
		if (foundWords == null || foundWords.length == 0 || lostWords == null || lostWords.length == 0) return false;
		
        int matchingWordsCount = 0;
        
        for (String foundWord : foundWords) {
        	if (!hasEnoughCharacters(foundWord))
        		continue;
        	
            for (String lostWord : lostWords) {
            	if (!hasEnoughCharacters(lostWord))
            		continue;
            	
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
	
	private boolean hasEnoughCharacters(String word) {		
		return word.length() >= minimumNumberCharacters;
	}
	
	private boolean containsAny(String longString, Set<String> searchStrings) {
		int countKeywordsFounded = 0;
		
        for (String searchString : searchStrings) {
            if (longString.contains(searchString.toLowerCase())) {
            	countKeywordsFounded++;
            	
            	if (minimumKeywordsToFound == countKeywordsFounded)
            		return true;
            }
        }
        return false;
    }

}
