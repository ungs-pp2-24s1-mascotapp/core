package com.mascotapp.core.service.socialNetwork;

import java.util.Set;

import com.mascotapp.core.MascotAppCore;
import com.mascotapp.core.entities.Notification;
import com.mascotapp.core.entities.Post;
import com.mascotapp.core.service.updater.PostUpdater;

public class SocialNetworkListener {
	@SuppressWarnings("deprecation")
	public SocialNetworkListener(Set<SocialNetwork> socialNetworks, SocialNetworkActivator socialNetworkActivator, PostUpdater postUpdater, MascotAppCore mascotAppCore) {
        for (SocialNetwork socialNetwork : socialNetworks) {
            socialNetwork.addObserver((o, arg) -> {
                if (arg instanceof Notification) {
                    @SuppressWarnings("unchecked")
                    Notification<Set<Post>> notification = (Notification<Set<Post>>) arg;
                    String socialNetworkName = notification.getObservableName();
              
                    Set<Post> data = notification.getData();
                    postUpdater.updatePosts(data, socialNetworkName);
                    
                    if (socialNetworkActivator.isSocialNetworkActive(socialNetworkName)) {
                        mascotAppCore.updateAndNotifyMatches();
                    }
                }
            });
        }
    }
}
