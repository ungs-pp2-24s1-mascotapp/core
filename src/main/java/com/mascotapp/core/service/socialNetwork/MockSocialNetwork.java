package com.mascotapp.core.service.socialNetwork;

import java.util.Set;

import com.mascotapp.core.entities.Post;

public class MockSocialNetwork extends SocialNetwork {
	
	private Set<Post> posts;
	private String name;
	
	public MockSocialNetwork(Set<Post> posts, String name) {
		this.posts = posts;
		this.name = name;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public Set<Post> getPosts() {
		return posts;
	}

}
