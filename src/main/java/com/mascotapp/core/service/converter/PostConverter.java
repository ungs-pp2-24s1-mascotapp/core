package com.mascotapp.core.service.converter;

import com.mascotapp.core.entities.Pet;
import com.mascotapp.core.entities.Post;

public interface PostConverter {
	Pet convertToPet(Post post);
}
