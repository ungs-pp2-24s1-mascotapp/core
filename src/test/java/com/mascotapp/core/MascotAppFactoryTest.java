package com.mascotapp.core;

import static org.junit.jupiter.api.Assertions.fail;

import java.io.FileNotFoundException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.mascotapp.core.discovery.MascotAppDiscovery;

class MascotAppFactoryTest {

    private MascotAppDiscovery discoverer;
    private MascotAppFactory factory;

    @BeforeEach
    public void setUp() {
        discoverer = new MascotAppDiscovery();
        factory = new MascotAppFactory();
        factory.discoverer = discoverer;
    }

    @Test
    void testCreateWithNonExistentPath() {
        String nonExistentPath = "nonexistent/directory";

        try {
            factory.create(nonExistentPath);
            fail("FileNotFoundException expected for a non-existent path");
        } catch (FileNotFoundException e) {
            // Excepción esperada
        }
    }
}
