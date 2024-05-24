package com.mascotapp.core.service.socialNetwork;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SocialNetworkInfoTest {

    private SocialNetworkInfo activeNetwork;
    private SocialNetworkInfo inactiveNetwork;
    private SocialNetworkInfo anotherActiveNetwork;

    @BeforeEach
    void setUp() {
        activeNetwork = new SocialNetworkInfo("Facebook", true);
        inactiveNetwork = new SocialNetworkInfo("Twitter", false);
        anotherActiveNetwork = new SocialNetworkInfo("Facebook", true);
    }

    @Test
    void testGetName() {
        assertEquals("Facebook", activeNetwork.getName());
        assertEquals("Twitter", inactiveNetwork.getName());
    }

    @Test
    void testIsActive() {
        assertTrue(activeNetwork.isActive());
        assertFalse(inactiveNetwork.isActive());
    }

    @Test
    void testHashCode() {
        assertEquals(activeNetwork.hashCode(), anotherActiveNetwork.hashCode());
        assertNotEquals(activeNetwork.hashCode(), inactiveNetwork.hashCode());
    }

    @Test
    void testToString() {
        assertEquals("Facebook (Activo)", activeNetwork.toString());
        assertEquals("Twitter (Inactivo)", inactiveNetwork.toString());
    }

    @Test
    void testEquals() {
        assertTrue(activeNetwork.equals(anotherActiveNetwork));
        assertFalse(activeNetwork.equals(inactiveNetwork));
        assertFalse(activeNetwork.equals(null));
        assertFalse(activeNetwork.equals(new Object()));
    }
}
