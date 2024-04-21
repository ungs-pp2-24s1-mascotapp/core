package com.mascotapp.core.discovery;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.mascotapp.core.service.dataprovider.PetDataProvider;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.FileNotFoundException;
import java.util.Set;

public class MascotAppDiscoveryTest {

    private MascotAppDiscovery discovery;

    @BeforeEach
    public void setUp() {
        discovery = new MascotAppDiscovery();
    }

    @Test
    public void testDiscoverWithNonExistentPath() {
        String nonExistentPath = "nonexistent/directory";
        try {
            discovery.discover(nonExistentPath);
            fail("FileNotFoundException expected for a non-existent path");
        } catch (FileNotFoundException e) {
            // Exception expected
        }
    }

    @Test
    public void testDiscoverWithInvalidPath() {
        String invalidPath = "invalid path";
        try {
            discovery.discover(invalidPath);
            fail("IllegalArgumentException expected for an invalid path");
        } catch (IllegalArgumentException | FileNotFoundException e) {
            // Exception expected
        }
    }
    
    @Test
    public void testDiscoverWithValidPathOneImplementation() throws FileNotFoundException, IllegalArgumentException {
        String validPath = "src/test/resources/US2/OneImplementation";
        Set<PetDataProvider> providers = discovery.discover(validPath);
        assertEquals(providers.size(), 1);
    }
    
    @Test
    public void testDiscoverWithValidPathZeroImplementation() throws FileNotFoundException, IllegalArgumentException {
        String validPath = "src/test/resources/US2/ZeroImplementation";
        Set<PetDataProvider> providers = discovery.discover(validPath);
        assertEquals(providers.size(), 0);
    }
}
