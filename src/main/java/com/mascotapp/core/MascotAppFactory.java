package com.mascotapp.core;

import java.io.FileNotFoundException;
import java.util.Set;

import com.mascotapp.core.service.dataprovider.PetDataProvider;
import com.mascotapp.core.discovery.MascotAppDiscovery;

public class MascotAppFactory {
	
    public static MascotApp create(String path) throws FileNotFoundException {
        Set<PetDataProvider> dataProviders = MascotAppDiscovery.discover(path);
        MascotAppCore core = new MascotAppCore(dataProviders);
        return new MascotApp(core);
    }
}
