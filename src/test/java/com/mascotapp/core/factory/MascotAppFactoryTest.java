package com.mascotapp.core.factory;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.mascotapp.core.MascotApp;
import com.mascotapp.core.MascotAppCore;
import com.mascotapp.core.discovery.MascotAppDiscovery;
import com.mascotapp.core.entities.Post;
import com.mascotapp.core.service.dataprovider.PetDataProvider;

public class MascotAppFactoryTest {

    private MascotAppDiscovery discoverer;
    private MascotAppFactory factory;

    @BeforeEach
    public void setUp() {
        discoverer = new MascotAppDiscovery();
        factory = new MascotAppFactory();
        factory.discoverer = discoverer;
    }

    @Test
    public void testCreateWithNonExistentPath() {
        String nonExistentPath = "nonexistent/directory";

        try {
            factory.create(nonExistentPath);
            fail("FileNotFoundException expected for a non-existent path");
        } catch (FileNotFoundException e) {
            // Excepción esperada
        }
    }
}
