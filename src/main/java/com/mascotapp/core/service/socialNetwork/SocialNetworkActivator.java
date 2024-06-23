package com.mascotapp.core.service.socialNetwork;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class SocialNetworkActivator {
	private Set<SocialNetwork> socialNetworks;
	private Map<SocialNetwork, Boolean> socialNetworkStates;
	
	public SocialNetworkActivator(Set<SocialNetwork> socialNetworks) {
        this.socialNetworks = socialNetworks;
        this.initialize();
    }
    
    public Set<SocialNetwork> getActiveSocialNetworks() {
    	Set<SocialNetwork> activeSocialNetworks = new HashSet<>();
        for (SocialNetwork socialNetwork : this.socialNetworks) {
            if (isSocialNetworkActive(socialNetwork)) {
            	activeSocialNetworks.add(socialNetwork);
            }
        }
        return activeSocialNetworks;
    }
    
    public Set<SocialNetworkInfo> getSocialNetworks() {
        Set<SocialNetworkInfo> socialNetworksInfo = new HashSet<>();
        for (SocialNetwork socialNetwork : socialNetworks) {
            boolean isActive = isSocialNetworkActive(socialNetwork);
            socialNetworksInfo.add(new SocialNetworkInfo(socialNetwork.getName(), isActive));
        }
        return socialNetworksInfo;
    }
    
    public void activateSocialNetwork(String name) throws IllegalArgumentException {
        changeStatusSocialNetwork(name, true);
    }

    public void deactivateSocialNetwork(String name) throws IllegalArgumentException {
    	changeStatusSocialNetwork(name, false);
    }
    
    public boolean isSocialNetworkActive(String name) {
		for (SocialNetwork socialNetwork : this.getActiveSocialNetworks()) {
			if(socialNetwork.getName().equals(name)) {
				return true;
			}
		}
		return false;
	}
	
	public Map<SocialNetwork, Boolean> getSocialNetworkStates() {
		return this.socialNetworkStates;
	}
    
    private void changeStatusSocialNetwork(String name, boolean value) throws IllegalArgumentException {
    	for (SocialNetwork socialNetwork : socialNetworks) {
            if (socialNetwork.getName().equals(name)) {
                socialNetworkStates.put(socialNetwork, value);
                return;
            }
        }
    	
    	throw new IllegalArgumentException("Social network not valid: " + name);
    }
    
    private void initialize() {
		this.socialNetworkStates = new HashMap<>();
		for (SocialNetwork socialNetwork : this.socialNetworks) {
			addSocialNetwork(socialNetwork);
		}
	}
	
	private void addSocialNetwork(SocialNetwork socialNetwork) {
		socialNetworks.add(socialNetwork);
		socialNetworkStates.put(socialNetwork, true);
    }
	
	private boolean isSocialNetworkActive(SocialNetwork socialNetwork) {
        return this.socialNetworkStates.getOrDefault(socialNetwork, false);
    }
}
