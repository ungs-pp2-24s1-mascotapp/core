package com.mascotapp.core.filter;

public class ContentPetPostFilter extends ContentPostFilter{
	public ContentPetPostFilter() {
		super(new String[] {"perro", "perra", "perrito", "perrita", "labrador", "mestizo",
				"caniche", "pastor", "pug", "gato", "gata", "gatito", "gatita", "siames", "golden", "retriever"});
	}
}
