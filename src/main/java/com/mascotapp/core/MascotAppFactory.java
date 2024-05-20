package com.mascotapp.core;

import java.io.FileNotFoundException;
import java.util.Set;

import com.mascotapp.core.discovery.MascotAppDiscovery;
import com.mascotapp.core.service.socialNetwork.SocialNetwork;

public class MascotAppFactory {
	
    public static MascotApp create(String path) throws FileNotFoundException {
        Set<SocialNetwork> socialNetworks = MascotAppDiscovery.discover(path);
        MascotAppCore core = new MascotAppCore(socialNetworks);
        return new MascotApp(core);
    }
}
