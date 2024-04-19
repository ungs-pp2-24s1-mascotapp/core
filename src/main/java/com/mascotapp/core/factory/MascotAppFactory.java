package com.mascotapp.core.factory;

import java.io.FileNotFoundException;
import java.util.Set;

import com.mascotapp.core.service.dataprovider.PetDataProvider;
import com.mascotapp.core.MascotApp;
import com.mascotapp.core.MascotAppCore;
import com.mascotapp.core.discovery.MascotAppDiscovery;

public class MascotAppFactory {
	
	MascotAppDiscovery discoverer;

    public MascotAppFactory() {
        this.discoverer = new MascotAppDiscovery();
    }

    public MascotApp create(String path) throws FileNotFoundException {
        Set<PetDataProvider> dataProviders = discoverer.discover(path);
        MascotAppCore core = new MascotAppCore(dataProviders);
        return new MascotApp(core);
    }
}
