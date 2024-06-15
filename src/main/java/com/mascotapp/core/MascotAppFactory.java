package com.mascotapp.core;

import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Set;

import com.mascotapp.core.discoverer.Discoverer;
import com.mascotapp.core.filter.ContentPostFilter;
import com.mascotapp.core.service.matcher.ContentPostMatcher;
import com.mascotapp.core.service.matcher.SimpleMatcher;
import com.mascotapp.core.service.socialNetwork.SocialNetwork;

public class MascotAppFactory {
	
    public static MascotApp create(String path) throws FileNotFoundException {
        Set<SocialNetwork> socialNetworks = Discoverer.discover(path);
        Set<String> keywordsFound =  new HashSet<>();
        keywordsFound.add("vi");
        keywordsFound.add("encontre");
        keywordsFound.add("encontró");
        
        Set<String> keywordsLost =  new HashSet<>();
        keywordsLost.add("perdí");
        keywordsLost.add("buscando");
        keywordsLost.add("perdio");
        keywordsLost.add("escapó");
        
        MascotAppCore core = new MascotAppCore(
        	socialNetworks, 
        	new ContentPostMatcher(), 
        	new ContentPostFilter(), 
        	new ContentPostFilter(keywordsFound),
        	new ContentPostFilter(keywordsLost)
        );
        return new MascotApp(core);
    }
}
