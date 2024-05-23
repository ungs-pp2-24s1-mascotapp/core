package com.mascotapp.core;

import java.util.HashSet;
import java.util.Set;

import com.mascotapp.core.entities.Match;
import com.mascotapp.core.entities.Post;
import com.mascotapp.core.service.matcher.Matcher;
import com.mascotapp.core.service.socialNetwork.SocialNetwork;

public class MascotAppCore {
	private Set<SocialNetwork> socialNetworks;
	private Matcher matcher;
    
    public MascotAppCore(Set<SocialNetwork> socialNetworks, Matcher matcher) {
        this.socialNetworks = socialNetworks;
        this.matcher = matcher;
    }

    public Set<Match> getMatches() {
    	Set<Post> founds = new HashSet<>();
    	Set<Post> losts = new HashSet<>();
    	
        for (SocialNetwork socialNetwork : socialNetworks) {
        	founds.addAll(socialNetwork.getFoundPets());
        	losts.addAll(socialNetwork.getLostPets());
        }
        return matcher.getMatchs(founds, losts);
    }
}
