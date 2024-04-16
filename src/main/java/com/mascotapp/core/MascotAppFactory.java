package com.mascotapp.core;

import java.util.ArrayList;
import java.util.List;

import com.mascotapp.core.service.dataprovider.FacebookPetDataProvider;
import com.mascotapp.core.service.dataprovider.PetDataProvider;
import com.mascotapp.core.service.searchengine.PetSearchEngine;
import com.mascotapp.core.service.searchengine.SimplePetSearchEngine;
import com.mascotapp.core.service.keyword.KeywordSeparator;
import com.mascotapp.core.service.keyword.SimpleKeywordSeparator;
import com.mascotapp.core.service.converter.PostConverter;
import com.mascotapp.core.service.converter.SimplePostConverter;

public class MascotAppFactory {
	public static MascotApp createMascotApp() {
        List<PetDataProvider> dataProviders = new ArrayList<>();
        dataProviders.add(new FacebookPetDataProvider());
        PetSearchEngine petSearchEngine = new SimplePetSearchEngine();
        KeywordSeparator keywordSeparator = new SimpleKeywordSeparator();
        PostConverter converter = new SimplePostConverter();
        return new MascotApp(dataProviders, petSearchEngine, keywordSeparator, converter);
    }
}
