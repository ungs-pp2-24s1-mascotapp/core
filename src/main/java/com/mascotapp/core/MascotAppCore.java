package com.mascotapp.core;

import java.util.HashSet;
import java.util.Map;
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
import com.mascotapp.core.service.socialNetwork.SocialNetworkActivator;

@SuppressWarnings("deprecation")
public class MascotAppCore implements Observer {
	private Set<SocialNetwork> socialNetworks;
	private SocialNetworkActivator socialNetworkActivator;
	private MatchEvaluator evaluator;
	
	private Filter<Post> filterPosts;
    private Filter<Post> filterFounds;
    private Filter<Post> filterLosts;
	
	
	private Set<Post> foundPosts;
    private Set<Post> lostPosts;
	private Set<Match> matches;
	
	private MascotApp mascotApp;
    
    public MascotAppCore(Set<SocialNetwork> socialNetworks, Matcher<Post> matcher, 
    		Filter<Post> filterPosts, Filter<Post> filterFounds, Filter<Post> filterLosts) {
    	this.socialNetworks = socialNetworks;
    	this.socialNetworkActivator = new SocialNetworkActivator(socialNetworks);
    	this.evaluator = new PostMatchEvaluator(matcher);
    	
    	this.filterPosts = filterPosts;
    	this.filterFounds = filterFounds;
    	this.filterLosts = filterLosts;
    	
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
    
    @Override
	public void update(Observable o, Object arg) {
		if (arg instanceof Notification) {
            @SuppressWarnings("unchecked")
            Notification<Set<Post>> notification = (Notification<Set<Post>>) arg;
            String socialNetworkName = notification.getObservableName();
      
            Set<Post> data = notification.getData();
            this.updatePosts(data, socialNetworkName);
            
            if (socialNetworkActivator.isSocialNetworkActive(socialNetworkName)) {
                this.updateAndNotifyMatches();
            }
        }
	}
	
	private void initializePosts() {
		for (SocialNetwork socialNetwork : socialNetworks) {
        	Set<Post> posts = socialNetwork.getPosts();
        	this.updatePosts(posts, socialNetwork.getName());
        }
	}
	
	private void updateAndNotifyMatches() {
		this.updateMatches();
		this.notifyMatches();
    }
	
	private void updateMatches() {
		this.matches = evaluator.findMatches(this.foundPosts, this.lostPosts);
	}
	
	private void notifyMatches() {
		if (this.mascotApp != null) {
            this.mascotApp.notifyMatches(this.getMatches());
        }
	}
	
	private void updatePosts(Set<Post> posts, String source) {
        if (posts == null) return;
        
        Set<Post> filteredPosts = filterPosts.filter(posts);
        Set<Post> filteredFoundsPosts = filterFounds.filter(filteredPosts);
        Set<Post> filteredLostsPosts = filterLosts.filter(filteredPosts);
        
        setSource(filteredFoundsPosts, source);
        setSource(filteredLostsPosts, source);
        
        foundPosts.addAll(filteredFoundsPosts);
        lostPosts.addAll(filteredLostsPosts);
    }

    private void setSource(Set<Post> posts, String source) {
        for (Post post : posts) {
            post.setSource(source);
        }
    }
    
    private void initializeObservers() {
    	for (SocialNetwork socialNetwork : socialNetworks) {
            socialNetwork.addObserver(this);
    	}
    }
}
