package com.mascotapp.core.discovery;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Files;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import com.mascotapp.core.logger.Logger;
import com.mascotapp.core.service.dataprovider.PetDataProvider;

public class MascotAppDiscovery {
	// Expresión regular para verificar la validez de una ruta de directorio
	private static final String DIRECTORY_REGEX = "^[^\\s^\\x00-\\x1f\\\\?*:\"\";<>|/,][^\\x00-\\x1f\\\\?*:\"\";<>|/,]*[^,\\s^\\x00-\\x1f\\\\?*:\"\";<>|]+$";
	private static final String CLASS_EXTENSION = ".class";
	private static final String JAR_EXTENSION = ".jar";
	
	/**
     * Descubre los proveedores de datos de mascotas en una ubicación específica.
     * @param path La ruta donde se buscarán los proveedores de datos.
     * @return Un conjunto de proveedores de datos de mascotas.
     * @throws FileNotFoundException Si la ubicación especificada no existe.
     * @throws IllegalArgumentException Si la ubicación especificada no es válida.
     */
    public static Set<PetDataProvider> discover(String path) throws FileNotFoundException, IllegalArgumentException {
        File directory = new File(path);

        if (!path.matches(DIRECTORY_REGEX)) {
            throw new IllegalArgumentException("Invalid location: " + path);
        }

        if (!directory.exists()) {
            throw new FileNotFoundException("Location does not exist: " + path);
        }

        return findClasses(directory);
    }
    
    /**
     * Busca las clases de proveedores de datos de mascotas dentro de una ubicación específica.
     * @param path La ruta donde se buscarán las clases.
     * @return Un conjunto de proveedores de datos de mascotas encontrados.
     */
    private static Set<PetDataProvider> findClasses(File directory) {
    	Set<PetDataProvider> dataProviders = new HashSet<>();
    	    
        if (directory.isDirectory()) {
            try {
                Files.walk(directory.toPath())
                     .filter(Files::isRegularFile)
                     .filter(file -> file.toString().endsWith(JAR_EXTENSION))
                     .forEach(file -> dataProviders.addAll(findDataProvidersInJar(file.toFile())));
            } catch (IOException e) {
            	Logger.error("Error walking directories: " + e.getMessage());
            }
        } else if (directory.isFile() && directory.getName().endsWith(JAR_EXTENSION)) {
            dataProviders.addAll(findDataProvidersInJar(directory));
        }
        
        return dataProviders;
    }
    
    /**
     * Busca proveedores de datos de mascotas dentro de un archivo JAR.
     * @param jarFile El archivo JAR en el que se buscarán los proveedores de datos.
     * @return Un conjunto de proveedores de datos de mascotas encontrados en el JAR.
     */
    private static Set<PetDataProvider> findDataProvidersInJar(File jarFile) {
        Set<PetDataProvider> dataProviders = new HashSet<>();

        try (JarFile jar = new JarFile(jarFile)) {
            Enumeration<JarEntry> entries = jar.entries();

            while (entries.hasMoreElements()) {
                JarEntry entry = entries.nextElement();
                if (!entry.isDirectory() && entry.getName().endsWith(CLASS_EXTENSION)) {
                    instantiateClassFromJar(jarFile, entry, dataProviders);
                }
            }
        } catch (IOException e) {
            Logger.error("Error reading jar file: " + e.getMessage());
        }

        return dataProviders;
    }
    
    /**
     * Instancia una clase de proveedor de datos de mascotas desde un archivo JAR.
     * @param jarFile El archivo JAR que contiene la clase.
     * @param entry La entrada del archivo JAR que representa la clase.
     * @param dataProviders Conjunto para almacenar los proveedores de datos encontrados.
     */
    private static void instantiateClassFromJar(File jarFile, JarEntry entry, Set<PetDataProvider> dataProviders) {
        Class<?> cls = loadClassFromJar(jarFile, entry.getName());
        if (cls != null && PetDataProvider.class.isAssignableFrom(cls)) {
        	try {
				dataProviders.add((PetDataProvider) cls.getDeclaredConstructor().newInstance());
			} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
					| InvocationTargetException | NoSuchMethodException | SecurityException e) {
				Logger.error("Error instantiating class: " + e.getMessage());
			}
        }
    }
    
    /**
     * Carga dinámicamente una clase desde un archivo JAR.
     * @param jarFile El archivo JAR que contiene la clase.
     * @param className El nombre de la clase a cargar.
     * @return La clase cargada, o null si hay un error.
     */
    private static Class<?> loadClassFromJar(File jarFile, String className) {
        URLClassLoader classLoader = null;
        try {
            classLoader = URLClassLoader.newInstance(new URL[]{jarFile.toURI().toURL()});
            String canonicalClassName = className.replace("/", ".").replace(CLASS_EXTENSION, "");
            return Class.forName(canonicalClassName, true, classLoader);
        } catch (IOException | ClassNotFoundException e) {
            Logger.error("Error loading class from jar: " + e.getMessage());
            return null;
        } finally {
            if (classLoader != null) {
                try {
                    classLoader.close();
                } catch (IOException e) {
                    Logger.error("Error closing loader: " + e.getMessage());
                }
            }
        }
    }
}
