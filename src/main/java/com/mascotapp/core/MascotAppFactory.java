package com.mascotapp.core;

import java.io.FileNotFoundException;
import java.util.Set;

import com.mascotapp.core.discoverer.Discoverer;
import com.mascotapp.core.service.matcher.SimpleMatcher;
import com.mascotapp.core.service.socialNetwork.SocialNetwork;

public class MascotAppFactory {
	
    public static MascotApp create(String path) throws FileNotFoundException {
        Set<SocialNetwork> socialNetworks = Discoverer.discover(path);
        MascotAppCore core = new MascotAppCore(socialNetworks, new SimpleMatcher());
        return new MascotApp(core);
    }
}
