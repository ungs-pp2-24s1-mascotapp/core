package com.mascotapp.core;

import java.util.ArrayList;
import java.util.List;

import com.mascotapp.core.entities.Pet;
import com.mascotapp.core.entities.Post;
import com.mascotapp.core.service.converter.PostConverter;
import com.mascotapp.core.service.dataprovider.PetDataProvider;
import com.mascotapp.core.service.keyword.KeywordSeparator;
import com.mascotapp.core.service.searchengine.PetSearchEngine;

public class MascotAppCore {
	private List<PetDataProvider> dataProviders;
    private PetSearchEngine searchEngine;
    private KeywordSeparator keywordSeparator;
    private PostConverter converter;
    
    public MascotAppCore(List<PetDataProvider> dataProviders, PetSearchEngine searchEngine, KeywordSeparator keywordSeparator, PostConverter converter) {
        this.dataProviders = dataProviders;
        this.searchEngine = searchEngine;
        this.keywordSeparator = keywordSeparator;
        this.converter = converter;
    }

    public List<Pet> searchPets(String query) {
        List<Post> allData = new ArrayList<>();
        for (PetDataProvider dataProvider : dataProviders) {
        	allData.addAll(dataProvider.fetchData());
        }
        String[] keywords = this.keywordSeparator.separateKeywords(query);
        return searchEngine.searchPets(allData, keywords, converter);
    }
}
