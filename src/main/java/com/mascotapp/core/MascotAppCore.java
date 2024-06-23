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
import com.mascotapp.core.service.socialNetwork.SocialNetworkActivator;
import com.mascotapp.core.service.updater.PostUpdater;

public class MascotAppCore {
	private SocialNetworkActivator socialNetworkActivator;
	private PostUpdater postUpdater;
	private MatchEvaluator evaluator;
	private Set<Match> matches;
	private MascotApp mascotApp;
    
    public MascotAppCore(Set<SocialNetwork> socialNetworks, Matcher<Post> matcher, 
    		Filter<Post> filterPosts, Filter<Post> filterFounds, Filter<Post> filterLosts) {
    	this.evaluator = new PostMatchEvaluator(matcher);
    	
    	this.postUpdater = new PostUpdater(filterPosts, filterFounds, filterLosts);
    	
    	this.socialNetworkActivator = new SocialNetworkActivator(socialNetworks);
    	
        this.matches = new HashSet<>();
        
        this.initializePosts();
        this.updateAndNotifyMatches();
        
        new SocialNetworkListener(socialNetworks, socialNetworkActivator, postUpdater, this);
    }
    
    public Set<Match> getMatches() {
    	Set<Match> results = new HashSet<>();
    	for (Match match : matches) {
    		String foundSource = match.getFoundPost().getSource();
    		String lostSource = match.getLostPost().getSource();
    		
			if(socialNetworkActivator.isSocialNetworkActive(foundSource) && 
				socialNetworkActivator.isSocialNetworkActive(lostSource)) {
				results.add(match);
			}
		}
    	return results;
    }
    
    public Set<SocialNetworkInfo> getSocialNetworks() {
    	return this.socialNetworkActivator.getSocialNetworks();
    }
    
    public Map<SocialNetwork, Boolean> getSocialNetworkStates() {
    	return this.socialNetworkActivator.getSocialNetworkStates();
    }
    
    public void activateSocialNetwork(String name) {
    	this.socialNetworkActivator.activateSocialNetwork(name);
    	this.notifyMatches();
    }
    
    public void deactivateSocialNetwork(String name) {
    	this.socialNetworkActivator.deactivateSocialNetwork(name);
    	this.notifyMatches();
    }
    
    public void setMascotApp(MascotApp mascotApp) {
        this.mascotApp = mascotApp;
    }
	
	private void initializePosts() {
		for (SocialNetwork socialNetwork : socialNetworkActivator.getActiveSocialNetworks()) {
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
