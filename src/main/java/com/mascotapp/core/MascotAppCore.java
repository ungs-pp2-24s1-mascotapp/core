package com.mascotapp.core;

import java.util.HashSet;
import java.util.Set;

import com.mascotapp.core.entities.Match;
import com.mascotapp.core.entities.Post;
import com.mascotapp.core.service.matcher.Matcher;
import com.mascotapp.core.service.socialNetwork.SocialNetwork;
import com.mascotapp.core.service.socialNetwork.SocialNetworkInfo;
import com.mascotapp.core.service.socialNetwork.SocialNetworkSelector;

public class MascotAppCore {
	private Matcher matcher;
	private SocialNetworkSelector socialNetworkSelector;
    
    public MascotAppCore(Set<SocialNetwork> socialNetworks, Matcher matcher) {
    	this.socialNetworkSelector = new SocialNetworkSelector(socialNetworks);
        this.matcher = matcher;
    }

    public Set<Match> getMatches() {
    	Set<Post> founds = new HashSet<>();
    	Set<Post> losts = new HashSet<>();
    	
        for (SocialNetwork socialNetwork : socialNetworkSelector.getActiveSocialNetworks()) {
        	founds.addAll(socialNetwork.getFoundPets());
        	losts.addAll(socialNetwork.getLostPets());
        }
        
        return matcher.getMatchs(founds, losts);
    }
    
    public Set<SocialNetworkInfo> getSocialNetworks() {
    	return this.socialNetworkSelector.getSocialNetworks();
    }
    
    public void activateSocialNetwork(String name) {
    	this.socialNetworkSelector.activateSocialNetwork(name);
    }
    
    public void deactivateSocialNetwork(String name) {
    	this.socialNetworkSelector.deactivateSocialNetwork(name);
    }
}
