package com.mascotapp.core;

import java.io.FileNotFoundException;
import java.util.Set;

import com.mascotapp.core.discoverer.Discoverer;
import com.mascotapp.core.filter.ContentFoundPostFilter;
import com.mascotapp.core.filter.ContentLostPostFilter;
import com.mascotapp.core.filter.ContentPetPostFilter;
import com.mascotapp.core.service.matcher.ContentPostMatcher;
import com.mascotapp.core.service.socialNetwork.SocialNetwork;

public class MascotAppFactory {
	
    public static MascotApp create(String path) throws FileNotFoundException {
        Set<SocialNetwork> socialNetworks = Discoverer.discover(path);        
        MascotAppCore core = new MascotAppCore(
        	socialNetworks, 
        	new ContentPostMatcher(), 
        	new ContentPetPostFilter(), 
        	new ContentFoundPostFilter(),
        	new ContentLostPostFilter()
        );
        return new MascotApp(core);
    }
}
