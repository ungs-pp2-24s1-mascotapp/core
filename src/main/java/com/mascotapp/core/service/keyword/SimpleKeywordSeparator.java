package com.mascotapp.core.service.keyword;

public class SimpleKeywordSeparator implements KeywordSeparator {
	
	private String regexBySpaces = "\\s+";
    @Override
    public String[] separateKeywords(String keywordsString) {
        return keywordsString.split(regexBySpaces);
    }
}
