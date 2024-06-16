package com.mascotapp.core.entities;

import java.util.Objects;

public class Match {
	private Post foundPost;
	private Post lostPost;
	
	public Match(Post foundPost, Post lostPost) {
		this.foundPost = foundPost;
		this.lostPost = lostPost;
	}
	public Post getFoundPost() {
		return foundPost;
	}
	public Post getLostPost() {
		return lostPost;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(foundPost, lostPost);
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
		return Objects.equals(foundPost, other.foundPost) && Objects.equals(lostPost, other.lostPost);
	}
	
	@Override
	public String toString() {
		return "Match [foundPost=" + foundPost + ", lostPost=" + lostPost + "]";
	}
}
