package com.mascotapp.core.filter;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import com.mascotapp.core.entities.Post;

public class ContentPostFilter implements PostFilter {
	
	private Set<String> keywords;
	private int minimumKeywordsToFound = 1;
	
	public ContentPostFilter(Set<String> keywords) {
		this.keywords = keywords;
	}
	
	public ContentPostFilter(String[] keywords) {
		Set<String> stringSet = new HashSet<>(Arrays.asList(keywords));
		this.keywords = stringSet;
	}
	
	public ContentPostFilter(Set<String> keywords, int minimumKeywordsToFound) {
		this.keywords = keywords;
		this.minimumKeywordsToFound = minimumKeywordsToFound;
	}
	
	@Override
	public Set<Post> filter(Set<Post> objects) {
		Set<Post> posts = new HashSet<>();
		
		for (Post post : objects) {
			String[] words = getKeywords(post);
			if(containsKeywords(words, keywords)) posts.add(post);
		}
		
		return posts;
	}
	
	private boolean containsKeywords(String[] words, Set<String> keywords) {
		int countKeywordsFounded = 0;
		
        for (String word : words) {
        	for (String keyword : keywords) {
				if (word.equalsIgnoreCase(keyword)) {
					countKeywordsFounded++;
					if (minimumKeywordsToFound == countKeywordsFounded)
	            		return true;
				}	
			}
        }
        
        return false;
    }
	
	private String[] getKeywords(Post post) {
		return post.getContent().toLowerCase().split("\\s+");
	}

}
