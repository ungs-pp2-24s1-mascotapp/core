package com.mascotapp.core;

import java.util.HashSet;
import java.util.Observable;
import java.util.Observer;
import java.util.Set;

import com.mascotapp.core.entities.Match;
import com.mascotapp.core.entities.Notification;
import com.mascotapp.core.entities.Post;
import com.mascotapp.core.evaluator.MatchEvaluator;
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
	private MatchEvaluator evaluator;
	private Set<Post> foundPosts;
	private Set<Post> lostPosts;
	private Set<Match> matches;
	private MascotApp mascotApp;
    
    public MascotAppCore(Set<SocialNetwork> socialNetworks, Matcher matcher, Filter filterPosts, Filter filterFounds, Filter filterLosts) {
    	this.evaluator = new PostMatchEvaluator(matcher);
    	
    	this.filterPosts = filterPosts;
    	this.filterFounds = filterFounds;
    	this.filterLosts = filterLosts;
    	
    	this.socialNetworks =  socialNetworks;
    	this.socialNetworkSelector = new SocialNetworkSelector(socialNetworks);
    	
    	this.foundPosts = new HashSet<>();
        this.lostPosts = new HashSet<>();
        this.matches = new HashSet<>();
        
        this.initializePosts();
        this.updateAndNotifyMatches();
        this.initializeObservers(); 
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
    
    private void initializeObservers() {
    	for (SocialNetwork socialNetwork : socialNetworks) {
    		socialNetwork.addObserver(this);
        }
    }

	@Override
	public void update(Observable o, Object arg) {
		if (arg instanceof Notification) {
			Notification<Set<Post>> notification = (Notification<Set<Post>>) arg;
            String socialNetworkName = notification.getObservableName();
            
            if (this.socialNetworkSelector.isSocialNetworkActive(socialNetworkName)) {
            	Set<Post> data = notification.getData();
            	this.updatePosts(data, socialNetworkName);
            	this.updateAndNotifyMatches();
            }
        }
	}
	
	private void initializePosts() {
		for (SocialNetwork socialNetwork : socialNetworkSelector.getActiveSocialNetworks()) {
        	Set<Post> posts = socialNetwork.getPosts();
        	this.updatePosts(posts, socialNetwork.getName());
        }
	}
	
	private void updatePosts(Set<Post> posts, String source) {
		if(posts == null) return;
		
		Set<Post> filteredPosts = filterPosts.filter(posts);
    	Set<Post> filteredFoundsPosts = filterFounds.filter(filteredPosts);
    	Set<Post> filteredLostsPosts = filterLosts.filter(filteredPosts);
    	
    	setSource(filteredFoundsPosts, source);
    	setSource(filteredLostsPosts, source);
    	
    	foundPosts.addAll(filteredFoundsPosts);
    	lostPosts.addAll(filteredLostsPosts);
	}
	
	private void updateAndNotifyMatches() {
		this.updateMatches();
		this.notifyMatches();
    }
	
	private void setSource(Set<Post> posts, String source) {
		for (Post post : posts) {
			post.setSource(source);
		}
	}
	
	private void updateMatches() {
		this.matches = evaluator.findMatches(foundPosts, lostPosts);
	}
	
	private void notifyMatches() {
		if (this.mascotApp != null) {
            this.mascotApp.notifyMatches(this.getMatches());
        }
	}
}
