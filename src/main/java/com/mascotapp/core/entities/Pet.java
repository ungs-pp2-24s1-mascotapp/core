package com.mascotapp.core.entities;

import java.util.Objects;

public class Pet {

    private String name;
    private String animalType;
    private Post post;

    public Pet(String name, String animalType, Post post) {
        this.name = name;
        this.animalType = animalType;
        this.post = post;
    }

    public String getName() {
        return name;
    }

    public String getAnimalType() {
        return animalType;
    }
    
    public Post getPost() {
    	return post;
    }

	@Override
	public int hashCode() {
		return Objects.hash(animalType, name, post);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Pet other = (Pet) obj;
		return Objects.equals(animalType, other.animalType) && Objects.equals(name, other.name) && Objects.equals(post, other.post);
	}
}
