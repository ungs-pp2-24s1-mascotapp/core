package com.mascotapp.core.entities;

import java.util.Objects;

public class Post {
	private String content;
	private String url;
	
	public Post(String content, String url) {
		this.content = content;
		this.url = url;
	}

	@Override
	public int hashCode() {
		return Objects.hash(content, url);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Post other = (Post) obj;
		return Objects.equals(content, other.content) && Objects.equals(url, other.url);
	}

	public String getContent() {
		return content;
	}

	public String getUrl() {
		return url;
	}
	
	
	
	
}
