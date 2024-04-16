package com.mascotapp.core.service.dataprovider;

import java.util.List;

import com.mascotapp.core.entities.Post;

public interface PetDataProvider {
	List<Post> fetchData();
}
