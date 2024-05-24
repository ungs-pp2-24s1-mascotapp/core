package com.mascotapp.core;

import java.util.HashSet;
import java.util.Observable;
import java.util.Set;

import com.mascotapp.core.entities.Match;
import com.mascotapp.core.service.socialNetwork.SocialNetworkInfo;

@SuppressWarnings("deprecation")
public class MascotApp extends Observable {
	
	private final MascotAppCore core;

    public MascotApp(MascotAppCore core) {
    	this.core = core;
	}

	public Set<Match> getMatches() {
		Set<Match> results = new HashSet<>();
    	results.addAll(this.core.getMatches());
        
        setChanged();
        notifyObservers(results);
        
        return results;
    }
	
	public Set<SocialNetworkInfo> getSocialNetworks() {
    	return this.core.getSocialNetworks();
    }
	
	public void activateSocialNetwork(String name) {
		this.core.activateSocialNetwork(name);
	}
	
	public void deactivateSocialNetwork(String name) {
		this.core.deactivateSocialNetwork(name);
	}
}
