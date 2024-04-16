package com.mascotapp.core.service.searchengine;

import java.util.ArrayList;
import java.util.List;

import com.mascotapp.core.entities.Pet;
import com.mascotapp.core.entities.Post;
import com.mascotapp.core.service.converter.PostConverter;

public class SimplePetSearchEngine implements PetSearchEngine {
	
	
	@Override
	public List<Pet> searchPets(List<Post> data, String[] keywords, PostConverter converter) {
		List<Pet> pets = new ArrayList<>();
        for (Post post : data) {
            String postLower = post.getContent().toLowerCase();
            for (String keyword : keywords) {
                if (postLower.contains(keyword)) {
                	Pet pet = converter.convertToPet(post);
                	pets.add(pet);
                    break;
                }
            }
        }
        return pets;
	}

}
