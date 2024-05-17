package com.mascotapp.core.service.dataprovider;

import java.util.Set;

import com.mascotapp.core.entities.Post;

public interface PetDataProvider {
	String getName();
	Set<Post> getLostPets();
	Set<Post> getFoundPets();
}
