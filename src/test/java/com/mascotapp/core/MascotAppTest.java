package com.mascotapp.core;

import com.mascotapp.core.entities.Pet;
import com.mascotapp.core.service.dataprovider.FacebookPetDataProvider;
import com.mascotapp.core.service.dataprovider.PetDataProvider;
import com.mascotapp.core.service.searchengine.PetSearchEngine;
import com.mascotapp.core.service.keyword.KeywordSeparator;
import com.mascotapp.core.service.converter.SimplePostConverter;
import com.mascotapp.core.service.keyword.SimpleKeywordSeparator;
import com.mascotapp.core.service.searchengine.SimplePetSearchEngine;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class MascotAppTest {

    private MascotApp mascotApp;

    @BeforeEach
    public void setUp() {
        mascotApp = MascotAppFactory.createMascotApp();
    }

    @Test
    public void testSearch() {
        // Test
        List<Pet> searchResults = mascotApp.search("query");
        assertEquals(0, searchResults.size());
    }
}
