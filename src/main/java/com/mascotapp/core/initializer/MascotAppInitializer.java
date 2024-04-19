package com.mascotapp.core.initializer;

import java.io.FileNotFoundException;

import com.mascotapp.core.MascotApp;
import com.mascotapp.core.factory.MascotAppFactory;

public abstract class MascotAppInitializer {
	public MascotApp initializeWithJarFiles(String path) throws FileNotFoundException {
		return new MascotAppFactory().create(path);
	}
}
