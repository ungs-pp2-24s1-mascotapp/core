package com.mascotapp.core.service.searchengine;

import java.util.List;

import com.mascotapp.core.entities.Pet;
import com.mascotapp.core.entities.Post;
import com.mascotapp.core.service.converter.PostConverter;

public interface PetSearchEngine {
	List<Pet> searchPets(List<Post> data, String[] keywords, PostConverter converter);
}
