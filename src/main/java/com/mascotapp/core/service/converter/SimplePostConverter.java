package com.mascotapp.core.service.converter;

import com.mascotapp.core.entities.Pet;
import com.mascotapp.core.entities.Post;

public class SimplePostConverter implements PostConverter {
	
	@Override
	public Pet convertToPet(Post post) {
        String name = this.extractName(post.getContent());
        String animalType = this.extractAnimalType(post.getContent());
        return new Pet(name, animalType, post);
    }

    private String extractName(String content) {
        String[] words = content.split("\\s+");
        if (words.length > 2) {
        	return words[2];
        }
        return content;
    }

    private String extractAnimalType(String content) {
    	String[] words = content.split("\\s+");
        if (words.length > 1) {
        	return words[1];
        }
        return content;
    }

}
