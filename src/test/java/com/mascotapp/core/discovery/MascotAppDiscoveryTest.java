package com.mascotapp.core.discovery;

import org.junit.jupiter.api.Test;

import com.mascotapp.core.service.socialNetwork.SocialNetwork;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.FileNotFoundException;
import java.util.Set;

public class MascotAppDiscoveryTest {

    @Test
    public void testDiscoverWithNonExistentPath() {
        String nonExistentPath = "nonexistent/directory";
        try {
        	MascotAppDiscovery.discover(nonExistentPath);
            fail("FileNotFoundException expected for a non-existent path");
        } catch (FileNotFoundException e) {
            // Exception expected
        }
    }

    @Test
    public void testDiscoverWithInvalidPath() {
        String invalidPath = "invalid path";
        try {
        	MascotAppDiscovery.discover(invalidPath);
            fail("IllegalArgumentException expected for an invalid path");
        } catch (IllegalArgumentException | FileNotFoundException e) {
            // Exception expected
        }
    }
    
    @Test
    public void testDiscoverWithValidPathZeroImplementation() throws FileNotFoundException, IllegalArgumentException {
        String validPath = "src/test/resources/US2/ZeroImplementation";
        Set<SocialNetwork> providers = MascotAppDiscovery.discover(validPath);
        assertEquals(providers.size(), 0);
    }
}
