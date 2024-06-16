package com.mascotapp.core.discoverer;

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
import com.mascotapp.core.service.socialNetwork.SocialNetwork;

public class Discoverer {

	private static final String CLASS_EXTENSION = ".class";
	private static final String JAR_EXTENSION = ".jar";
	
	/**
     * Descubre las redes sociales en una ubicación específica.
     * @param path La ruta donde se buscarán las redes sociales.
     * @return Un conjunto de redes sociales.
     * @throws FileNotFoundException Si la ubicación especificada no existe.
     * @throws IllegalArgumentException Si la ubicación especificada no es válida.
     */
    public static Set<SocialNetwork> discover(String path) throws FileNotFoundException, IllegalArgumentException {
        File directory = new File(path);    

        if (!directory.exists()) {
            throw new FileNotFoundException("File location does not exist: " + path);
        }

        if (!directory.isDirectory() && !fileEndsWith(directory, JAR_EXTENSION)) {
            throw new IllegalArgumentException("Location is not a valid directory: " + path);
        }
        
        return findClasses(directory);
    }
    
    /**
     * Busca las clases de proveedores de datos de mascotas dentro de una ubicación específica.
     * @param path La ruta donde se buscarán las clases.
     * @return Un conjunto de proveedores de datos de mascotas encontrados.
     */
    private static Set<SocialNetwork> findClasses(File directory) {
    	Set<SocialNetwork> socialNets = new HashSet<>();
    	    
        if (directory.isDirectory()) {
            try {
                Files.walk(directory.toPath())
                     .filter(Files::isRegularFile)
                     .filter(file -> file.toString().endsWith(JAR_EXTENSION))
                     .forEach(file -> socialNets.addAll(findSocialNetsInJar(file.toFile())));
            } catch (IOException e) {
            	Logger.error("Error walking directories: " + e.getMessage());
            }
        } else if (directory.isFile() && directory.getName().endsWith(JAR_EXTENSION)) {
        	socialNets.addAll(findSocialNetsInJar(directory));
        }
        
        return socialNets;
    }
    
    /**
     * Busca redes sociales dentro de un archivo JAR.
     * @param jarFile El archivo JAR en el que se buscarán las redes sociales.
     * @return Un conjunto de redes sociales encontrados en el JAR.
     */
    private static Set<SocialNetwork> findSocialNetsInJar(File jarFile) {
        Set<SocialNetwork> socialNets = new HashSet<>();

        try (JarFile jar = new JarFile(jarFile)) {
            Enumeration<JarEntry> entries = jar.entries();

            while (entries.hasMoreElements()) {
                JarEntry entry = entries.nextElement();
                if (!entry.isDirectory() && entry.getName().endsWith(CLASS_EXTENSION)) {
                    instantiateClassFromJar(jarFile, entry, socialNets);
                }
            }
        } catch (IOException e) {
            Logger.error("Error reading jar file: " + e.getMessage());
        }

        return socialNets;
    }
    
    /**
     * Instancia una clase de red social desde un archivo JAR.
     * @param jarFile El archivo JAR que contiene la clase.
     * @param entry La entrada del archivo JAR que representa la clase.
     * @param socialNets Conjunto para almacenar las redes sociales encontradas.
     */
    private static void instantiateClassFromJar(File jarFile, JarEntry entry, Set<SocialNetwork> socialNets) {
        Class<?> cls = loadClassFromJar(jarFile, entry.getName());
        if (cls != null && SocialNetwork.class.isAssignableFrom(cls)) {
        	try {
        		socialNets.add((SocialNetwork) cls.getDeclaredConstructor().newInstance());
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
    
    private static boolean fileEndsWith(File directory, String extension) {
    	return directory.getName().endsWith(JAR_EXTENSION);
    }
}
