package com.mascotapp.core.service.dataprovider;

import java.util.ArrayList;
import java.util.List;

import com.mascotapp.core.entities.Post;

public class FacebookPetDataProvider implements PetDataProvider {

	@Override
	public List<Post> fetchData() {
		List<Post> posts = new ArrayList();
		
		posts.add(new Post("Mi perro Roco", "https://www.facebook.com/photo/?fbid=7254493387979597&set=gm.7639499106113128&idorvanity=920534681342971"));
		posts.add(new Post("Mi gato Chimuelo", "https://www.facebook.com/photo/?fbid=10231739600810207&set=gm.6734843496615778&idorvanity=577438702356319"));
		
		return posts;
	}

}
