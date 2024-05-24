package com.mascotapp.core.service.socialNetwork;

import java.util.Set;

import com.mascotapp.core.entities.Post;

public class MockSocialNetwork implements SocialNetwork {
	
	private Set<Post> foundPets;
	private Set<Post> lostPets;
	private String name;
	
	public MockSocialNetwork(Set<Post> foundPets, Set<Post> lostPets, String name) {
		this.foundPets = foundPets;
		this.lostPets = lostPets;
		this.name = name;
	}

	@Override
	public Set<Post> getLostPets() {
		return lostPets;
	}

	@Override
	public Set<Post> getFoundPets() {
		return foundPets;
	}

	@Override
	public String getName() {
		return name;
	}

}
