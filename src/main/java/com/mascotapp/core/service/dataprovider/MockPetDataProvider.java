package com.mascotapp.core.service.dataprovider;

import java.util.Set;

import com.mascotapp.core.entities.Post;

public class MockPetDataProvider implements PetDataProvider {
	
	private Set<Post> foundPets;
	private Set<Post> lostPets;
	
	public MockPetDataProvider(Set<Post> foundPets, Set<Post> lostPets) {
		this.foundPets = foundPets;
		this.lostPets = lostPets;
	}

	@Override
	public Set<Post> getLostPets() {
		return lostPets;
	}

	@Override
	public Set<Post> getFoundPets() {
		return foundPets;
	}

}
