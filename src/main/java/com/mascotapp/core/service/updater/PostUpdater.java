package com.mascotapp.core.service.updater;

import java.util.HashSet;
import java.util.Set;
import com.mascotapp.core.entities.Post;
import com.mascotapp.core.filter.Filter;

public class PostUpdater {
    private Filter<Post> filterPosts;
    private Filter<Post> filterFounds;
    private Filter<Post> filterLosts;
    private Set<Post> foundPosts;
    private Set<Post> lostPosts;

    public PostUpdater(Filter<Post> filterPosts, Filter<Post> filterFounds, Filter<Post> filterLosts) {
        this.filterPosts = filterPosts;
        this.filterFounds = filterFounds;
        this.filterLosts = filterLosts;
        this.foundPosts = new HashSet<>();
        this.lostPosts = new HashSet<>();
    }

    public void updatePosts(Set<Post> posts, String source) {
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

    public Set<Post> getFoundPosts() {
        return foundPosts;
    }

    public Set<Post> getLostPosts() {
        return lostPosts;
    }
}
