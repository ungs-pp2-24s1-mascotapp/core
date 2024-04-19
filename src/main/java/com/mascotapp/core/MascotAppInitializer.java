package com.mascotapp.core;

import java.io.FileNotFoundException;

public class MascotAppInitializer {
	
	public MascotApp initializeWithJarFiles(String path) throws FileNotFoundException {
		return new MascotAppFactory().create(path);
	}
	
}
