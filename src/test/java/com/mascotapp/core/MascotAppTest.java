package com.mascotapp.core;

import com.mascotapp.core.entities.Match;
import com.mascotapp.core.entities.Post;
import com.mascotapp.core.filter.ContentFoundPostFilter;
import com.mascotapp.core.filter.ContentLostPostFilter;
import com.mascotapp.core.filter.ContentPetPostFilter;
import com.mascotapp.core.service.matcher.ContentPostMatcher;
import com.mascotapp.core.service.socialNetwork.MockSocialNetwork;
import com.mascotapp.core.service.socialNetwork.SocialNetwork;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MascotAppTest {

    private MascotApp mascotApp;

    @BeforeEach
    public void setUp() {
    	Set<SocialNetwork> socialNetworks = new HashSet<SocialNetwork>();
    	Set<Post> posts = new HashSet<>();
    	socialNetworks.add(new MockSocialNetwork(posts, "Mock"));
    	MascotAppCore core = new MascotAppCore(
            	socialNetworks, 
            	new ContentPostMatcher(), 
            	new ContentPetPostFilter(), 
            	new ContentFoundPostFilter(),
            	new ContentLostPostFilter()
            );
        mascotApp = new MascotApp(core);
    }

    @Test
    public void testMatchs() {
        Set<Match> searchResults = mascotApp.getMatches();
        assertEquals(0, searchResults.size());
    }
    
    @Test
    public void testStartService() throws InterruptedException {
        mascotApp.startService();
        mascotApp.stopService();
        mascotApp.startService(0, 10, TimeUnit.MILLISECONDS);
        Thread.sleep(20);
        mascotApp.stopService();
    }

    @Test
    public void testStopService() {
        mascotApp.stopService();
    }
}
