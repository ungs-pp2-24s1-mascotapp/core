package com.mascotapp.core.entities;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class MatchTest {

    @Test
    public void testEquals() {
        // Arrange
        Post foundPet1 = new Post("Dog", "Brown dog found");
        Post lostPet1 = new Post("Cat", "Black cat lost");
        Post foundPet2 = new Post("Dog", "White dog found");
        Post lostPet2 = new Post("Cat", "Black cat lost");

        Match match1 = new Match(foundPet1, lostPet1);
        Match match2 = new Match(foundPet1, lostPet1); // Same as match1
        Match match3 = new Match(foundPet2, lostPet2); // Different foundPet

        // Act & Assert
        assertEquals(match1, match2); // Two matches with the same foundPet and lostPet should be equal
        assertNotEquals(match1, match3); // Two matches with different foundPet should not be equal
    }

    @Test
    public void testHashCode() {
        // Arrange
        Post foundPet1 = new Post("Dog", "Brown dog found");
        Post lostPet1 = new Post("Cat", "Black cat lost");
        Post foundPet2 = new Post("Dog", "White dog found");
        Post lostPet2 = new Post("Cat", "Black cat lost");

        Match match1 = new Match(foundPet1, lostPet1);
        Match match2 = new Match(foundPet1, lostPet1); // Same as match1
        Match match3 = new Match(foundPet2, lostPet2); // Different foundPet

        // Act & Assert
        assertEquals(match1.hashCode(), match2.hashCode()); // Hash code should be equal for equal objects
        assertNotEquals(match1.hashCode(), match3.hashCode()); // Hash code should not be equal for different objects
    }
}
