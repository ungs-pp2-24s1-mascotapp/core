package com.mascotapp.core;

import static org.junit.jupiter.api.Assertions.fail;

import java.io.FileNotFoundException;

import org.junit.jupiter.api.Test;

class MascotAppFactoryTest {

    @Test
    void testCreateWithNonExistentPath() {
        String nonExistentPath = "nonexistent/directory";

        try {
        	MascotAppFactory.create(nonExistentPath);
            fail("FileNotFoundException expected for a non-existent path");
        } catch (FileNotFoundException e) {
            // Excepción esperada
        }
    }
}
