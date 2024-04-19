package com.mascotapp.core;

import com.mascotapp.core.entities.Match;
import com.mascotapp.core.entities.Post;
import com.mascotapp.core.service.dataprovider.MockPetDataProvider;
import com.mascotapp.core.service.dataprovider.PetDataProvider;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MascotAppTest {

    private MascotApp mascotApp;

    @BeforeEach
    public void setUp() {
    	Set<PetDataProvider> dataProviders = new HashSet<PetDataProvider>();
    	Set<Post> founds = new HashSet<>();
    	Set<Post> losts = new HashSet<>();
    	dataProviders.add(new MockPetDataProvider(founds,losts));
    	MascotAppCore core = new MascotAppCore(dataProviders);
        mascotApp = new MascotApp(core);
    }

    @Test
    public void testMatchs() {
        // Test
        Set<Match> searchResults = mascotApp.getMatches();
        assertEquals(0, searchResults.size());
    }
}
