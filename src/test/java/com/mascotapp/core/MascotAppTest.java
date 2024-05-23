package com.mascotapp.core;

import com.mascotapp.core.entities.Match;
import com.mascotapp.core.entities.Post;
import com.mascotapp.core.service.matcher.SimpleMatcher;
import com.mascotapp.core.service.socialNetwork.MockSocialNetwork;
import com.mascotapp.core.service.socialNetwork.SocialNetwork;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MascotAppTest {

    private MascotApp mascotApp;

    @BeforeEach
    public void setUp() {
    	Set<SocialNetwork> socialNetworks = new HashSet<SocialNetwork>();
    	Set<Post> founds = new HashSet<>();
    	Set<Post> losts = new HashSet<>();
    	socialNetworks.add(new MockSocialNetwork(founds,losts));
    	MascotAppCore core = new MascotAppCore(socialNetworks, new SimpleMatcher());
        mascotApp = new MascotApp(core);
    }

    @Test
    public void testMatchs() {
        Set<Match> searchResults = mascotApp.getMatches();
        assertEquals(0, searchResults.size());
    }
}