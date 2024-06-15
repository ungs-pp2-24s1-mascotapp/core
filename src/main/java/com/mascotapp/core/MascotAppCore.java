package com.mascotapp.core;

import java.util.HashSet;
import java.util.Observable;
import java.util.Observer;
import java.util.Set;

import com.mascotapp.core.entities.Match;
import com.mascotapp.core.entities.Notification;
import com.mascotapp.core.entities.Post;
import com.mascotapp.core.evaluator.PostMatchEvaluator;
import com.mascotapp.core.filter.Filter;
import com.mascotapp.core.service.matcher.Matcher;
import com.mascotapp.core.service.socialNetwork.SocialNetwork;
import com.mascotapp.core.service.socialNetwork.SocialNetworkInfo;
import com.mascotapp.core.service.socialNetwork.SocialNetworkSelector;

public class MascotAppCore implements Observer {
	private SocialNetworkSelector socialNetworkSelector;
	private Set<SocialNetwork> socialNetworks;
	private Filter filterPosts;
	private Filter filterFounds;
	private Filter filterLosts;
	private PostMatchEvaluator evaluator;
    
    public MascotAppCore(Set<SocialNetwork> socialNetworks, Matcher matcher, Filter filterPosts, Filter filterFounds, Filter filterLosts) {
    	this.evaluator = new PostMatchEvaluator(matcher);
    	this.filterPosts = filterPosts;
    	this.filterFounds = filterFounds;
    	this.filterLosts = filterLosts;
    	this.socialNetworks =  socialNetworks;
    	this.socialNetworkSelector = new SocialNetworkSelector(socialNetworks);
        this.initializeObservers();
    }

    public Set<Match> getMatches() {   
    	Set<Post> foundPosts = new HashSet<>();
    	Set<Post> lostPosts = new HashSet<>();
    	
        for (SocialNetwork socialNetwork : socialNetworkSelector.getActiveSocialNetworks()) {
        	Set<Post> posts = socialNetwork.getPosts();
        	
        	Set<Post> filteredPosts = filterPosts.filter(posts);
        	Set<Post> filteredFoundsPosts = filterFounds.filter(filteredPosts);
        	Set<Post> filteredLostsPosts = filterLosts.filter(filteredPosts);
        	
        	foundPosts.addAll(filteredFoundsPosts);
        	lostPosts.addAll(filteredLostsPosts);
 
        }
        
        return evaluator.findMatches(foundPosts, lostPosts);
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
    
    private void initializeObservers() {
    	for (SocialNetwork socialNetwork : socialNetworks) {
    		socialNetwork.addObserver(this);
        }
    }

	@Override
	public void update(Observable o, Object arg) {
		if (arg instanceof Notification) {
			Notification<Set<Post>> notification = (Notification<Set<Post>>) arg;
            String observableName = notification.getObservableName();
            
            if (this.socialNetworkSelector.isSocialNetworkActive(observableName)) {
            	Set<Post> data = notification.getData();
            }
        }
	}
}
