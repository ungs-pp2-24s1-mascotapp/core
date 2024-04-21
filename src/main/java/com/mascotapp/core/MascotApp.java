package com.mascotapp.core;

import java.util.HashSet;
import java.util.Observable;
import java.util.Set;

import com.mascotapp.core.entities.Match;

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
}
