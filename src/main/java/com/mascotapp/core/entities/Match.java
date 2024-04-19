package com.mascotapp.core.entities;

import java.util.Objects;

public class Match {
	private Post foundPet;
	private Post lostPet;
	
	public Match(Post foundPet, Post lostPet) {
		this.foundPet = foundPet;
		this.lostPet = lostPet;
	}
	public Post getFoundPet() {
		return foundPet;
	}
	public Post getLostPet() {
		return lostPet;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(foundPet, lostPet);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Match other = (Match) obj;
		return Objects.equals(foundPet, other.foundPet) && Objects.equals(lostPet, other.lostPet);
	}
	
	
}
