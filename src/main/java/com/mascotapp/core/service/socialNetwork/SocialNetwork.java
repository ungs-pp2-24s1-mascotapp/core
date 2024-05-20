package com.mascotapp.core.service.socialNetwork;

import java.util.Set;

import com.mascotapp.core.entities.Post;

public interface SocialNetwork {
	String getName();
	Set<Post> getLostPets();
	Set<Post> getFoundPets();
}
