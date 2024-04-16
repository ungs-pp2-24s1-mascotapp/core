package com.mascotapp.core;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.Set;
import java.util.stream.Collectors;

import com.mascotapp.core.entities.Pet;
import com.mascotapp.core.entities.Post;
import com.mascotapp.core.service.converter.PostConverter;
import com.mascotapp.core.service.converter.SimplePostConverter;
import com.mascotapp.core.service.dataprovider.FacebookPetDataProvider;
import com.mascotapp.core.service.dataprovider.PetDataProvider;
import com.mascotapp.core.service.keyword.KeywordSeparator;
import com.mascotapp.core.service.keyword.SimpleKeywordSeparator;
import com.mascotapp.core.service.searchengine.PetSearchEngine;
import com.mascotapp.core.service.searchengine.SimplePetSearchEngine;

@SuppressWarnings("deprecation")
public class MascotApp extends Observable {
	
	private MascotAppCore core;
	
    public MascotApp() {
    	List<PetDataProvider> dataProviders = getDataProviders();
    	PetSearchEngine petSearchEngine = getSearchEngine();
    	KeywordSeparator keywordSeparator = getKeywordSeparator();
    	PostConverter converter = getConverter();
    	this.core = new MascotAppCore(dataProviders, petSearchEngine, keywordSeparator, converter);
    }

    public MascotApp(List<PetDataProvider> dataProviders, PetSearchEngine petSearchEngine,
			KeywordSeparator keywordSeparator, PostConverter converter) {
    	this.core = new MascotAppCore(dataProviders, petSearchEngine, keywordSeparator, converter);
	}

	@SuppressWarnings("deprecation")
	public List<Pet> search(String query) {
    	List<Pet> results = new ArrayList<>();
    	results.addAll(this.core.searchPets(query));
        
        setChanged();
        notifyObservers(results);
        
        return results;
    }
    
    private List<PetDataProvider> getDataProviders() {
		List<PetDataProvider> providers = new ArrayList<PetDataProvider>();
		providers.add(new FacebookPetDataProvider());
    	return providers;
    }
    
    private PetSearchEngine getSearchEngine() {
    	return new SimplePetSearchEngine();
    }
    
    private KeywordSeparator getKeywordSeparator() {
    	return new SimpleKeywordSeparator();
    }
    
    private PostConverter getConverter() {
    	return new SimplePostConverter();
    }
}
