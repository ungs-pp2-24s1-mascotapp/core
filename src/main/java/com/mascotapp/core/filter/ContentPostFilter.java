package com.mascotapp.core.filter;

import java.util.HashSet;
import java.util.Set;

import com.mascotapp.core.entities.Post;

public class ContentPostFilter implements PostFilter {
	
	private Set<String> keywords;
	private int minimumKeywordsToFound = 2;
	
	public ContentPostFilter() {
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
	
	public ContentPostFilter(Set<String> keywords) {
		this.keywords = keywords;
	}
	
	public ContentPostFilter(Set<String> keywords, int minimumKeywordsToFound) {
		this.keywords = keywords;
		this.minimumKeywordsToFound = minimumKeywordsToFound;
	}
	
	@Override
	public Set<Post> filter(Set<Post> objects) {
		Set<Post> posts = new HashSet<>();
		
		for (Post post : objects) {
			String content = post.getContent().toLowerCase();
			if(containsKeywords(content, keywords)) posts.add(post);
		}
		
		return posts;
	}
	
	private boolean containsKeywords(String longString, Set<String> searchStrings) {
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
