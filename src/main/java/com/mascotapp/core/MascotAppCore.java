package com.mascotapp.core;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.mascotapp.core.entities.Match;
import com.mascotapp.core.entities.Post;
import com.mascotapp.core.evaluator.MatchEvaluator;
import com.mascotapp.core.evaluator.PostMatchEvaluator;
import com.mascotapp.core.filter.Filter;
import com.mascotapp.core.service.matcher.Matcher;
import com.mascotapp.core.service.socialNetwork.SocialNetwork;
import com.mascotapp.core.service.socialNetwork.SocialNetworkInfo;
import com.mascotapp.core.service.socialNetwork.SocialNetworkListener;
import com.mascotapp.core.service.socialNetwork.SocialNetworkSelector;
import com.mascotapp.core.service.updater.PostUpdater;

public class MascotAppCore {
	private SocialNetworkSelector socialNetworkSelector;
	private PostUpdater postUpdater;
	private MatchEvaluator evaluator;
	private Set<Match> matches;
	private MascotApp mascotApp;
    
    public MascotAppCore(Set<SocialNetwork> socialNetworks, Matcher<Post> matcher, 
    		Filter<Post> filterPosts, Filter<Post> filterFounds, Filter<Post> filterLosts) {
    	this.evaluator = new PostMatchEvaluator(matcher);
    	
    	this.postUpdater = new PostUpdater(filterPosts, filterFounds, filterLosts);
    	
    	this.socialNetworkSelector = new SocialNetworkSelector(socialNetworks);
    	
        this.matches = new HashSet<>();
        
        this.initializePosts();
        this.updateAndNotifyMatches();
        
        new SocialNetworkListener(socialNetworks, socialNetworkSelector, postUpdater, this);
    }
    
    public Set<Match> getMatches() {
    	Set<Match> results = new HashSet<>();
    	for (Match match : matches) {
    		String foundSource = match.getFoundPost().getSource();
    		String lostSource = match.getLostPost().getSource();
    		
			if(socialNetworkSelector.isSocialNetworkActive(foundSource) && 
				socialNetworkSelector.isSocialNetworkActive(lostSource)) {
				results.add(match);
			}
		}
    	return results;
    }
    
    public Set<SocialNetworkInfo> getSocialNetworks() {
    	return this.socialNetworkSelector.getSocialNetworks();
    }
    
    public Map<SocialNetwork, Boolean> getSocialNetworkStates() {
    	return this.socialNetworkSelector.getSocialNetworkStates();
    }
    
    public void activateSocialNetwork(String name) {
    	this.socialNetworkSelector.activateSocialNetwork(name);
    	this.notifyMatches();
    }
    
    public void deactivateSocialNetwork(String name) {
    	this.socialNetworkSelector.deactivateSocialNetwork(name);
    	this.notifyMatches();
    }
    
    public void setMascotApp(MascotApp mascotApp) {
        this.mascotApp = mascotApp;
    }
	
	private void initializePosts() {
		for (SocialNetwork socialNetwork : socialNetworkSelector.getActiveSocialNetworks()) {
        	Set<Post> posts = socialNetwork.getPosts();
        	postUpdater.updatePosts(posts, socialNetwork.getName());
        }
	}
	
	public void updateAndNotifyMatches() {
		this.updateMatches();
		this.notifyMatches();
    }
	
	private void updateMatches() {
		this.matches = evaluator.findMatches(postUpdater.getFoundPosts(), postUpdater.getLostPosts());
	}
	
	private void notifyMatches() {
		if (this.mascotApp != null) {
            this.mascotApp.notifyMatches(this.getMatches());
        }
	}
}
