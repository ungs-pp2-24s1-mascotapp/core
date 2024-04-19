package com.mascotapp.core;

import java.util.HashSet;
import java.util.Set;

import com.mascotapp.core.entities.Match;
import com.mascotapp.core.entities.Post;
import com.mascotapp.core.service.dataprovider.PetDataProvider;
import com.mascotapp.core.service.matcher.Matcher;

public class MascotAppCore {
	private Set<PetDataProvider> dataProviders;
    
    public MascotAppCore(Set<PetDataProvider> dataProviders) {
        this.dataProviders = dataProviders;
    }

    public Set<Match> getMatches() {
    	Set<Post> founds = new HashSet<Post>();
    	Set<Post> losts = new HashSet<Post>();
    	
        for (PetDataProvider dataProvider : dataProviders) {
        	founds.addAll(dataProvider.getFoundPets());
        	losts.addAll(dataProvider.getLostPets());
        }
        return Matcher.getMatchs(founds, losts);
    }
}
