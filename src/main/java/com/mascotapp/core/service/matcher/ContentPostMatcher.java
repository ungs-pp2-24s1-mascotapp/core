package com.mascotapp.core.service.matcher;

import org.apache.commons.math3.exception.NullArgumentException;

import com.mascotapp.core.entities.Post;

public class ContentPostMatcher implements PostMatcher {
	
	private int minimumNumberCharacters = 3;
	private int minimumNumberWordMatches = 2;
	
	public ContentPostMatcher() {}
	
	public ContentPostMatcher(int minimumNumberCharacters, int minimumNumberWordMatches) {
		this.minimumNumberCharacters = minimumNumberCharacters;
		this.minimumNumberWordMatches = minimumNumberWordMatches;
	}
	
	@Override
	public boolean isMatch(Post found, Post lost) throws NullArgumentException {
		if (found == null || lost == null)
			throw new NullArgumentException();
		
		String[] foundWords = getKeywords(found);
        String[] lostWords = getKeywords(lost);
        
        return isMatchingWords(foundWords, lostWords);
	}
	
	private String[] getKeywords(Post post) {
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
}
