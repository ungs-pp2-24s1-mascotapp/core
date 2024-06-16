package com.mascotapp.core.service.socialNetwork;

import java.util.Observable;
import java.util.Set;

import com.mascotapp.core.entities.Notification;
import com.mascotapp.core.entities.Post;

@SuppressWarnings("deprecation")
public abstract class SocialNetwork extends Observable{
	public abstract String getName();
	public abstract Set<Post> getPosts();
	public void notifyNewPosts(Set<Post> posts) {
		for (Post post : posts) {
			post.setSource(getName());
		}
		setChanged();
        notifyObservers(new Notification<Set<Post>>(posts, getName()));
	}
}
